package table

import entity.Answers
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object AnswersTable: UUIDTable() {
    val userId: Column<UUID> = uuid("user").references(UserTable.id)
    val questionId = uuid("questionId")
    val answer = varchar("answer", 255)
    val grade = integer("grade")

    fun ResultRow.toAnswer(): Answers {
        return Answers(
            id = this[AnswersTable.id].value,
            userId = this[userId],
            questionId = this[questionId],
            answer = this[answer],
            grade = this[grade],
        )
    }
}