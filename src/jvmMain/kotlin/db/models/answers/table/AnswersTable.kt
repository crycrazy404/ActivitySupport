package db.models.answers.table

import org.jetbrains.exposed.dao.id.LongIdTable

object AnswersTable: LongIdTable(name = "answers") {
    val user = reference("user", db.models.user.table.UserTable)
    val questionId = uuid("questionId")
    val answer = varchar("answer", 1000)
    val grade = double("grade")

}