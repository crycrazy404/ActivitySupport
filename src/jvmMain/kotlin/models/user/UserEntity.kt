package models.user

import models.answers.table.AnswersTable.uniqueIndex
import models.user.table.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)
    var telegramId by UserTable.telegramId.uniqueIndex()
    var firstName by UserTable.firstName
    var secondName by UserTable.secondName
    var group by UserTable.group
}
