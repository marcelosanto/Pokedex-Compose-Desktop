import io.ktor.client.call.*
import io.ktor.client.request.*
import model.Api

class GetPokemonList {
    suspend fun getPokemons(): Api = KtorClient.client.get("https://pokeapi.co/api/v2/pokemon?limit=100").body()
}