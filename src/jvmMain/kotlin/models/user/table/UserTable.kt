package models.user.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object UserTable: LongIdTable() {
    val telegramId: Column<Long> = long("telegramId").uniqueIndex()
    val firstName: Column<String> = varchar("firsName", 1000)
    val secondName: Column<String> = varchar("secondName", 1000)
    val group: Column<String> = varchar("group", 1000)

}