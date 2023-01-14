import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.AsyncImage
import components.loadImageBitmap
import kotlinx.coroutines.runBlocking
import network.model.Pokemon
import utils.Const.urlImage

@Composable
@Preview
fun App() {

    val pokemonList = runBlocking {
        GetPokemonList().getPokemons().results
    }

    val pokeInfo = remember { mutableStateListOf<Pokemon>() }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Row(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
                Row(modifier = Modifier.background(Color.Red)) {
                    PokemonList(pokemonList) {
                        pokeInfo.clear()
                        pokeInfo.add(it)
                        println(it)
                    }
                }
                Row(modifier = Modifier.background(Color.Blue).fillMaxSize()) {
                    LazyColumn {
                        items(pokeInfo) {
                            Row(modifier = Modifier.background(Color.Blue).fillMaxWidth()) {
                                AsyncImage(
                                    key = urlImage(
                                        it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString()
                                    ),
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
                                Column {
                                    Text(it.name)
                                    Text("#${it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString()}")
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun PokemonList(pokemonList: List<Pokemon>, onClick: (Pokemon) -> Unit) {
    LazyColumn(userScrollEnabled = true) {
        items(pokemonList) {
            Card(
                modifier = Modifier.padding(16.dp).clickable {
                    onClick(it)
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

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}



