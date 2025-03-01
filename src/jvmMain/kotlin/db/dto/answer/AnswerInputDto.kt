package db.dto.answer

data class AnswerInputDto(
    val user: Long,
    val questionID: Long,
    val answer: String,
    val grade: Double
) {
}