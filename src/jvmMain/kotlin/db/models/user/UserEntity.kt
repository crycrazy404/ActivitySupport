package db.models.user

import db.dto.user.UserDTO
import db.models.answers.table.AnswersTable.uniqueIndex
import db.models.question.QuestionEntity
import db.models.question.table.QuestionTable
import db.models.user.table.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)
    var telegramId by UserTable.telegramId.uniqueIndex()
    var firstName by UserTable.firstName
    var secondName by UserTable.secondName
    var group by UserTable.group
    val questions by QuestionEntity referrersOn QuestionTable.user

    fun toDTO(): UserDTO = UserDTO(
        telegramId = telegramId,
        firstName = firstName,
        secondName = secondName,
        group = group,
    )
}
