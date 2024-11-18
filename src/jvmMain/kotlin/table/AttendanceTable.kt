package table

import entity.Attendance
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.util.*

object AttendanceTable: UUIDTable() {
    val userId: Column<UUID> = uuid("userId").references(UserTable.id)
    val date: Column<LocalDate> = date("date")
    val status: Column<Boolean> = bool("status")

    fun ResultRow.toAttendance(): Attendance {
        return Attendance(
            id = this[AttendanceTable.id].value,
            userId = this[userId],
            date = this[date],
            status = this[status],
        )
    }
}