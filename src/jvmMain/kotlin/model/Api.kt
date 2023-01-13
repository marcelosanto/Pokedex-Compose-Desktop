package model

data class Api(
    val count: Long,
    val next: String,
    val previous: String? = null,
    val results: List<Pokemon>
)

data class Pokemon (
    val name: String,
    val url: String
)
