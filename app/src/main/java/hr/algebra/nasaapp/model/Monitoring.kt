package hr.algebra.nasaapp.model

data class Monitoring (
    val _id: Int?,
    val userBeingMonitored: Int,
    val monitorUser: Int,
    val isAccepted: Boolean
)