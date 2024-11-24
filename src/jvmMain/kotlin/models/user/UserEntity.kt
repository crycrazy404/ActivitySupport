package models.user

import models.roles.RoleEntity
import models.user.table.UserRoleTable
import models.user.table.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UserTable)
    var telegramId by UserTable.telegramId
    var firstName by UserTable.firstName
    var secondName by UserTable.secondName
    var group by UserTable.group
    val role by RoleEntity via UserRoleTable
}
