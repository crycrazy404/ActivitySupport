package models.user.table

import models.roles.table.RoleTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UserRoleTable: Table(name = "user_role") {
    val user = reference(
        name = "user",
        UserTable,
        onDelete = ReferenceOption.CASCADE,)
    val role = reference(
        name = "role_id",
        RoleTable,
    )
    override val primaryKey = PrimaryKey(user, role, name = "USER_ROLE_PK")
}