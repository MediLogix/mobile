package hr.algebra.nasaapp.model

data class User(
    val _id: Long?,
    val displayName: String,
    val email: String,
    val uniqueIdentifier: String,
    val isDeleted: Boolean
)
