package db.dto.questions

data class QuestionInputDto(
    var userID: Long,
    var question: String,
    var slideId: Long,
)
