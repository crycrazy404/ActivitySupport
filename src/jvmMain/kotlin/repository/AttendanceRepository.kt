package repository

import entity.Attendance
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import table.AttendanceTable
import table.AttendanceTable.toAttendance
import java.util.*

class AttendanceRepository: CrudRepository<Attendance, UUID> {
    override fun create(entity: Attendance): UUID {
        return transaction {
            AttendanceTable.insertAndGetId {
                it[userId] = entity.userId
                it[date] = entity.date
                it[status] = entity.status
            }.value
        }
    }

    override fun findById(id: UUID): Attendance? {
        return transaction {
            AttendanceTable.selectAll().where { AttendanceTable.id eq id }
                .map { it.toAttendance() }
                .singleOrNull()
        }
    }

    override fun findAll(): List<Attendance> {
        return transaction {
            AttendanceTable.selectAll().map { it.toAttendance() }
        }    }

    override fun deleteById(id: UUID): Boolean {
        return transaction {
            AttendanceTable.deleteWhere { AttendanceTable.id eq id } > 0
        }
    }

    override fun update(entity: Attendance): Boolean {
        return transaction {
            AttendanceTable.update({ AttendanceTable.id eq entity.id }) {
                it[userId] = entity.userId
                it[date] = entity.date
                it[status] = entity.status
            } > 0
        }
    }
}