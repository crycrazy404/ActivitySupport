package db.models.answers

import db.models.answers.table.AnswersTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AnswersEntity(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<db.models.answers.AnswersEntity>(db.models.answers.table.AnswersTable)
    val user by AnswersTable.user
    val questionId by AnswersTable.questionId
    val answer by AnswersTable.answer
    val grade by AnswersTable.grade
}
