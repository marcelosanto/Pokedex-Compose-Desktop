import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.runBlocking
import model.Pokemon

@Composable
@Preview
fun App() {
    val pokemonList = mutableListOf<Pokemon>()

    val teste = runBlocking {
        GetPokemonList().getPokemons().results
    }

    MaterialTheme {
        Surface {
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
                            Image(
                                painter = painterResource(resourcePath = "006.png"),
                                contentDescription = null,
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
