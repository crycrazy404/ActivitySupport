package models.attendance.table

import models.user.table.UserTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

object AttendanceTable: LongIdTable(name = "attendance") {
    val user = reference("user", UserTable)
    val date = date("date")
    val status= varchar("status", 1000)
}