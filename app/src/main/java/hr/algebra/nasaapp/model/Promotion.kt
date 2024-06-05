package hr.algebra.nasaapp.model

data class Promotion (
    val _id: Int?,
    val name: String,
    val imageURL: String,
    val description: String,
    val detailsURL: String,
    val isDeleted: Boolean
)