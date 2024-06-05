package hr.algebra.nasaapp.model

data class Reminder(
    val _id: Int?,
    val user: Int,
    val type: String,
    val period: Int,
    val alertTimeHours: Int,
    val alertTimeMinutes: Int,
    val snoozeCount: Int,
    val snoozeDuration: Int,
    val isCompleted: Boolean,
    val completionDateTime: String,
    val note: String,
    val isDeleted: Boolean
)
