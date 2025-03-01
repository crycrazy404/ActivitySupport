package db.dto.questions

import db.dto.user.UserDTO

data class QuestionOutputDto(
    var question: String,
    var slideId: Long,
    var user: UserDTO
)
