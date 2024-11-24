package models.roles.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object RoleTable: LongIdTable("role") {
    val rolesEnum: Column<RolesEnum> = enumerationByName("role_name", 100)
}