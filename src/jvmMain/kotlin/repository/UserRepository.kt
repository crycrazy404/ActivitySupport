package repository

import entity.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import table.UserTable
import table.UserTable.toUser
import java.util.*

class UserRepository: CrudRepository<User, UUID> {
    override fun create(entity: User): UUID {
        return transaction {
            UserTable.insertAndGetId {
                it[telegramId] = entity.telegramId
                it[firsName] = entity.firsName
                it[secondName] = entity.secondName
                it[group] = entity.group
                it[role] = entity.role
            }.value
        }
    }

    override fun findById(id: UUID): User? {
        return transaction {
            UserTable.selectAll().where { UserTable.id eq id }
                .map { it.toUser() }
                .singleOrNull()
        }
    }

    override fun findAll(): List<User> {
        return transaction {
            UserTable.selectAll().map { it.toUser() }
        }
    }

    override fun deleteById(id: UUID): Boolean {
        return transaction {
            UserTable.deleteWhere { UserTable.id eq id } > 0
        }
    }

    override fun update(entity: User): Boolean {
        return transaction {
            UserTable.update({ UserTable.id eq entity.id }) {
                it[telegramId] = entity.telegramId
                it[firsName] = entity.firsName
                it[secondName] = entity.secondName
                it[group] = entity.group
                it[role] = entity.role
            } > 0
        }
    }
}