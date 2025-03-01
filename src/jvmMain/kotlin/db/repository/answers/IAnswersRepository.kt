package db.repository.answers

interface IAnswersRepository {
    fun save(dto: db.dto.answer.AnswerInputDto): db.models.answers.AnswersEntity
    fun findById(id: Long): db.models.answers.AnswersEntity
    fun delete(id: Long): Boolean
}