package hr.algebra.nasaapp.model

data class PromotionUser (
    val _id: Int?,
    val user: Int,
    val promotion: Promotion,
    val isChecked: Boolean
)