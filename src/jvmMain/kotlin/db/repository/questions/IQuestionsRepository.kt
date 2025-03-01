package db.repository.questions

import db.dto.questions.QuestionInputDto
import db.dto.questions.QuestionOutputDto
import db.models.question.QuestionEntity

interface IQuestionsRepository {

    fun save(dto: QuestionInputDto): QuestionEntity
    fun getAll(): List<QuestionOutputDto>
}