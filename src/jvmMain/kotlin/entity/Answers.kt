package entity

import java.util.*

data class Answers(
    val id: UUID,
    val userId: UUID,
    val questionId: UUID,
    val answer: String,
    val grade: Int,
)
