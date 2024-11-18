package entity

import java.time.LocalDate
import java.util.*

data class Attendance(
    val id: UUID,
    val userId: UUID,
    val date: LocalDate,
    val status: Boolean
)
