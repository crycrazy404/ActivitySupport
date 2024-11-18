package repository

interface CrudRepository<T, ID> {
    fun create(entity: T): ID
    fun findById(id: ID): T?
    fun findAll(): List<T>
    fun update(entity: T): Boolean
    fun deleteById(id: ID): Boolean
}