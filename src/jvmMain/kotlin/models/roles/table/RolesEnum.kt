package models.roles.table

object ConstantRoles {
    const val USER = "user"
    const val ADMIN = "admin"
}
enum class RolesEnum(
    val role: Long,
    val constantRole: String,
)  {
    ADMIN(1, constantRole = ConstantRoles.ADMIN),
    USER(2, constantRole = ConstantRoles.USER),
}