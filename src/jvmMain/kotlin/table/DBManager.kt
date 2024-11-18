package table

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DBManager {
    fun initDatabase(dbType: String){
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
            SchemaUtils.createMissingTablesAndColumns(UserTable, AnswersTable, AttendanceTable)
            println("Таблицы созданы или уже существуют.")
        }
    }
}