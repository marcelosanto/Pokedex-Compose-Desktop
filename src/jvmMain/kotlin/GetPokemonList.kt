import io.ktor.client.call.*
import io.ktor.client.request.*
import network.model.Api
import network.model.KtorClient

class GetPokemonList {
    suspend fun getPokemons(): Api = KtorClient.client.get("https://pokeapi.co/api/v2/pokemon?limit=100").body()
}