package repository

import models.roles.RoleEntity
import models.user.UserEntity
import models.user.table.UserRoleTable
import models.user.table.UserTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UserRepository: CrudRepository<UserEntity, Long> {
    override fun create(entity: UserEntity): Long {
        val newUser =
            transaction {
                UserEntity.new {
                    telegramId = entity.telegramId
                    firstName = entity.firstName
                    secondName = entity.secondName
                    group = entity.group
                }.let { u ->
                    UserRoleTable.insert {
                        it[user] = u.id
                        it[role] = RoleEntity.findById(entity.id)!!.id
                    }
                    commit()
                    u
                }
            }
        return newUser.id.value
    }

    fun isUserRegistered(telegramId: Long): Boolean =
        UserEntity.find{
            UserTable.telegramId eq telegramId
        }.empty().not()


    override fun findById(id: Long): UserEntity? =
         UserEntity.find{
             UserTable.id eq id
         }.firstOrNull()


    override fun findAll(): List<UserEntity> =
        UserEntity.all().toList()



    override fun deleteById(id: Long): Boolean {
        return transaction {
            UserTable.deleteWhere { UserTable.id eq id } > 0
        }
    }

    override fun update(entity: UserEntity): Boolean =
        UserTable.update({ UserTable.id eq entity.id }) {
            it[telegramId] = entity.telegramId
            it[firstName] = entity.firstName
            it[secondName] = entity.secondName
            it[group] = entity.group
            it[role] = 2
            } > 0

}