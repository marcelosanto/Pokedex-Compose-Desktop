import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.Pokemon
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.net.URL

@Composable
@Preview
fun App() {
    val pokemonList = mutableListOf<Pokemon>()

    val teste = runBlocking {
        GetPokemonList().getPokemons().results
    }



    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(0.9f), contentAlignment = Alignment.Center) {



            LazyColumn {
                items(teste) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        elevation = 12.dp
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                key = urlImage(it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString()),
                                load = { loadImageBitmap(urlImage(it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString())) },
                                painterFor = { BitmapPainter(it) },
                                contentDescription = "",
                                modifier = Modifier.size(200.dp)
                            )
                            Text(it.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            Text(
                                it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }

                    }
                }
            }

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    key: Any?,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null, key) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use { loadImageBitmap(it) }

fun urlImage(id: String) = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"