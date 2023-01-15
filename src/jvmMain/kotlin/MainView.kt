import kotlinx.coroutines.runBlocking
import network.model.Pokemon


class MainViewModel() {

    val pokemons = mutableListOf<Pokemon>()

    init {
        getListPokemons()
    }

    fun getListPokemons() = runBlocking {
        GetPokemonList().getPokemons().results.forEach {
            pokemons.add(
                Pokemon(
                    name = it.name,
                    url = it.url.dropLast(1).takeLastWhile { it.isDigit() }.toInt().toString()
                )
            )
        }
    }

    fun getPokemon(id: String) = runBlocking {
        GetPokemonList().getPokemon(id)
    }

}