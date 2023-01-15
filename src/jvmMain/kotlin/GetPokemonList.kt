import io.ktor.client.call.*
import io.ktor.client.request.*
import network.model.Api
import network.model.KtorClient
import network.model.data.PokemonDetail

class GetPokemonList {
    suspend fun getPokemons(): Api = KtorClient.client.get("https://pokeapi.co/api/v2/pokemon?limit=100").body()
    suspend fun getPokemon(id: String): PokemonDetail =
        KtorClient.client.get("https://pokeapi.co/api/v2/pokemon/$id").body()
}