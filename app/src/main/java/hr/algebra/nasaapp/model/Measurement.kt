package hr.algebra.nasaapp.model

data class Measurement(
    val id: Int,
    val userId: Int?,
    val name: String?,
    val note: String?,
    val parameters: List<Parameter>
)
