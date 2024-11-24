package models.answers.table

import models.user.table.UserTable
import org.jetbrains.exposed.dao.id.LongIdTable

object AnswersTable: LongIdTable(name = "answers") {
    val user = reference("user", UserTable)
    val questionId = uuid("questionId")
    val answer = varchar("answer", 1000)
    val grade = integer("grade")

}