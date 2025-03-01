package db.models.question.table

import db.models.user.table.UserTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object QuestionTable: LongIdTable(name = "questions") {
    val user = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE)
    val question = text("question")
    val slideId = long("slideID")
}