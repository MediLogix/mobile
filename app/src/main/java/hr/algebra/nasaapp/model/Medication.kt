package hr.algebra.nasaapp.model

data class Medication (
    val _id: Int?,
    val user: Int,
    val name: String,
    val dosage: Double,
    val unit: String,
    val stash: Double,
    val note: String
)