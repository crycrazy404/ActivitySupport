package entity

import java.util.*

data class User(
    val id:UUID,
    val telegramId: Long,
    val firsName: String,
    val secondName: String,
    val group: String,
    val role: Int,
    )
