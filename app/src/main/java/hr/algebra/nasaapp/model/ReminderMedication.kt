package hr.algebra.nasaapp.model

data class ReminderMedication (
    val _id: Int?,
    val reminder: Int,
    val medication: Int,
    val dose: Double,
    val unit: String
)