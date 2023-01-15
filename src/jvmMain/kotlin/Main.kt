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
import network.model.Pokemon
import network.model.data.PokemonDetail
import utils.Const.urlImage

@Composable
@Preview
fun App() {

    val mainViewModel = MainViewModel()

    val pokemonList = mainViewModel.pokemons

    val pokeInfo = remember { mutableStateListOf<PokemonDetail>() }

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Row(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
                Row(modifier = Modifier.background(Color.Red)) {
                    PokemonList(pokemonList) {
                        pokeInfo.clear()
                        pokeInfo.add(mainViewModel.getPokemon(it.url))
                    }
                }
                Row(modifier = Modifier.background(Color.Blue).fillMaxSize()) {
                    LazyColumn {
                        items(pokeInfo) {
                            PokemonInfoDetail(it)
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
                        key = urlImage(it.url.toInt()),
                        load = {
                            loadImageBitmap(
                                urlImage(
                                    it.url.toInt()
                                )
                            )
                        },
                        painterFor = { BitmapPainter(it) },
                        contentDescription = "",
                        modifier = Modifier.size(200.dp)
                    )
                    Text(it.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text(
                        it.url,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PokemonInfoDetail(pokemon: PokemonDetail) {
    Card(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    key = urlImage(pokemon.id),
                    load = {
                        loadImageBitmap(
                            urlImage(
                                pokemon.id
                            )
                        )
                    },
                    painterFor = { BitmapPainter(it) },
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text("#${pokemon.id}", fontSize = 20.sp, color = Color.Gray)
                    Text(pokemon.name, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    Card(backgroundColor = Color.Blue.copy(0.7f)) {
                        Text(
                            "WATER",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }
            Text("Spits fire that is hot enough to melt boulders. uKnown to cause forest fires nunintentionally.")
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}



