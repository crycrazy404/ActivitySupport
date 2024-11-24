package repository

import models.answers.AnswersEntity
import models.answers.table.AnswersTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class AnswerRepository: CrudRepository<AnswersEntity, Long> {
    override fun create(entity: AnswersEntity): Long {
        val newAnswer =
            transaction {
                AnswersTable.insertAndGetId {
                    it[id] = entity.id.value
                    it[user] = entity.user.value
                    it[answer] = entity.answer
                    it[grade] = entity.grade
                }
            }
        return newAnswer.value
    }

    override fun findById(id: Long): AnswersEntity? =
        AnswersEntity.find{
            AnswersTable.id eq id
        }.firstOrNull()


    override fun findAll(): List<AnswersEntity> =
        AnswersEntity.all().toList()


    override fun update(entity: AnswersEntity): Boolean =
        AnswersTable.update({ AnswersTable.id eq entity.id }) {
            it[questionId] = entity.questionId
            it[answer] = entity.answer
            it[grade] = entity.grade
        } > 0


    override fun deleteById(id: Long): Boolean {
        return transaction {
            AnswersTable.deleteWhere { AnswersTable.id eq id } > 0
        }
    }
}