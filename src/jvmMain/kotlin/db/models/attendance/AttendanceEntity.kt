package db.models.attendance

import db.models.attendance.table.AttendanceTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AttendanceEntity(id: EntityID<Long>): LongEntity(id){
    companion object : LongEntityClass<db.models.attendance.AttendanceEntity>(db.models.attendance.table.AttendanceTable)
    val user by AttendanceTable.user
    val date by AttendanceTable.date
    val status by AttendanceTable.status
}

