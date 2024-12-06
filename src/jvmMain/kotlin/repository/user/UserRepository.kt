package repository.user

import dto.UserDTO
import models.user.UserEntity
import models.user.table.UserTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserRepository: IUserRepository {
    override fun create(dto: UserDTO): Long {
        return transaction {
            UserEntity.new {
                telegramId = dto.telegramId
                firstName = dto.firstName
                secondName = dto.secondName
                group = dto.group
            }.id.value
        }
    }

    override fun findById(id: Long): UserEntity? = transaction {
        UserEntity.find {
            UserTable.id eq id
        }.firstOrNull()
    }

    override fun findAll():List<UserEntity> = transaction {
        UserEntity.all().toList()
    }

    override fun update(dto: UserDTO): Boolean = transaction {
        UserTable.update({ UserTable.telegramId eq dto.telegramId })
        {
            it[telegramId] = dto.telegramId
            it[firstName] = dto.firstName
            it[secondName] = dto.secondName
            it[group] = dto.group
        } > 0
    }

    override fun deleteById(id: Long): Boolean =
        transaction {
            UserTable.deleteWhere { UserTable.id eq id } > 0
        }

    override fun isRegistered(id: Long): Boolean = transaction {
        UserEntity.find {
            UserTable.telegramId eq id
        }.empty().not()
    }
}