package repository.user

import dto.UserDTO
import models.user.UserEntity

interface IUserRepository {
    fun create(dto: UserDTO): Long
    fun findById(id: Long): UserEntity?
    fun findAll(): List<UserEntity>
    fun update(dto: UserDTO): Boolean
    fun deleteById(id: Long): Boolean
    fun isRegistered(id: Long): Boolean
}