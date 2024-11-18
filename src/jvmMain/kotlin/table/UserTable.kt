package table

import entity.User
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object UserTable: UUIDTable() {
    val telegramId: Column<Long> = long("telegramId").uniqueIndex()
    val firsName: Column<String> = varchar("firsName", 50)
    val secondName: Column<String> = varchar("secondName", 50)
    val group: Column<String> = varchar("group", 50)
    val role: Column<Int> = integer("role")

    fun ResultRow.toUser(): User {
        return User(
            id = this[id].value,
            telegramId = this[telegramId],
            firsName = this[firsName],
            secondName = this[secondName],
            group = this[group],
            role = this[role],
        )
    }
}