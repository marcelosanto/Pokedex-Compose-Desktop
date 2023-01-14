package network.model

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

object KtorClient {
    val client = HttpClient(CIO) {
        install(HttpTimeout) {
            socketTimeoutMillis = 3000
            requestTimeoutMillis = 3000
            connectTimeoutMillis = 3000
        }

        install(ContentNegotiation) {
            gson()
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}