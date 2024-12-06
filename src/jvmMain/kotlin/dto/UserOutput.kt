package dto

import models.user.UserEntity

class UserOutput(
    val telegramId: Long,
    val firstName: String,
    val secondName: String,
    val group: String,
) {
    constructor(u: UserEntity) : this(
        u.telegramId,
        u.firstName,
        u.secondName,
        u.group,
    )
}