package db.models.question

import db.models.question.table.QuestionTable
import db.models.user.UserEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class QuestionEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<QuestionEntity>(QuestionTable)
    var slide by QuestionTable.slideId
    var question by QuestionTable.question
    var user by UserEntity referencedOn QuestionTable.user
}