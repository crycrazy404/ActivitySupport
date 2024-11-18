package repository

import entity.Answers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import table.AnswersTable
import table.AnswersTable.toAnswer
import java.util.*

class AnswerRepository: CrudRepository<Answers, UUID> {
    override fun create(entity: Answers): UUID {
        return transaction {
            AnswersTable.insertAndGetId {
                it[userId] = entity.userId
                it[questionId] = entity.questionId
                it[answer] = entity.answer
                it[grade] = entity.grade
            }.value
        }
    }

    override fun findById(id: UUID): Answers? {
        return transaction {
            AnswersTable.selectAll().where { AnswersTable.id eq id }
                .map { it.toAnswer() }
                .singleOrNull()
        }
    }

    override fun findAll(): List<Answers> {
        return transaction {
            AnswersTable.selectAll().map { it.toAnswer() }
        }
    }

    override fun update(entity: Answers): Boolean {
        return transaction {
            AnswersTable.update({ AnswersTable.id eq entity.id }) {
                it[userId] = entity.userId
                it[questionId] = entity.questionId
                it[answer] = entity.answer
                it[grade] = entity.grade
            } > 0
        }
    }

    override fun deleteById(id: UUID): Boolean {
        return transaction {
            AnswersTable.deleteWhere { AnswersTable.id eq id } > 0
        }
    }
}