package dto

import models.roles.table.RolesEnum
import models.user.UserEntity

class UserOutput(
    val telegramId: Long,
    val firstName: String,
    val secondName: String,
    val group: String,
    val role: List<RolesEnum>,
) {
    constructor(u: UserEntity) : this(
        u.telegramId,
        u.firstName,
        u.secondName,
        u.group,
        u.role.map { it.name }
    )
}