package db.repository.user

interface IUserRepository {
    fun create(dto: db.dto.user.UserDTO): Long
    fun findById(id: Long): db.models.user.UserEntity?
    fun findByTelegramId(id: Long): db.models.user.UserEntity?
    fun findAll(): List<db.models.user.UserEntity>
    fun update(dto: db.dto.user.UserDTO): Boolean
    fun deleteById(id: Long): Boolean
    fun isRegistered(id: Long): Boolean
}