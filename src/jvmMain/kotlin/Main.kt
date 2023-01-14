import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.AsyncImage
import components.loadImageBitmap
import kotlinx.coroutines.runBlocking
import utils.Const.urlImage

@Composable
@Preview
fun App() {

    val pokemonList = runBlocking {
        GetPokemonList().getPokemons().results
    }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(0.9f), contentAlignment = Alignment.Center) {


            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 200.dp)) {
                items(pokemonList) {
                    Card(
                        modifier = Modifier.padding(16.dp).clickable {
                            println(it)
                        },
                        elevation = 12.dp
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                key = urlImage(it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString()),
                                load = {
                                    loadImageBitmap(
                                        urlImage(
                                            it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString()
                                        )
                                    )
                                },
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



