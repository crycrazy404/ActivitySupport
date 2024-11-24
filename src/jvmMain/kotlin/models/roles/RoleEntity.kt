package models.roles

import models.roles.table.RoleTable
import models.user.UserEntity
import models.user.table.UserRoleTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoleEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<RoleEntity>(RoleTable)

    var name by RoleTable.rolesEnum

    var user by UserEntity via UserRoleTable
}