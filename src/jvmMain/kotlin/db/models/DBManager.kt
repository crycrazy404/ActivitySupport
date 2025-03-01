package db.models

import db.models.answers.table.AnswersTable
import db.models.question.table.QuestionTable
import db.models.reactions.table.ReactionsTable
import db.models.user.table.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DBManager(dbType: String) {
    init {
        when(dbType){
            "h2" ->{
                Database.connect(
                    url = "jdbc:h2:./activityDatabase",
                    driver = "org.h2.Driver"
                )
            }
            "sqlight" ->{
                Database.connect(
                    url = "jdbc:sqlite:./activityDatabase.db",
                    driver = "org.sqlite.JDBC"
                )
            }
        }
    }

    fun createTables(){
        transaction{
            SchemaUtils.createMissingTablesAndColumns(
                UserTable,
                AnswersTable,
                AnswersTable,
                QuestionTable,
                ReactionsTable
            )
            println("Таблицы созданы или уже существуют.")
        }
    }
}