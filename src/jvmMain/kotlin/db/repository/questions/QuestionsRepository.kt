package db.repository.questions

import db.dto.questions.QuestionInputDto
import db.dto.questions.QuestionOutputDto
import db.models.question.QuestionEntity
import db.repository.user.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

class QuestionsRepository: IQuestionsRepository {
    override fun save(dto: QuestionInputDto): QuestionEntity {
        return transaction {
            try {
                // Создание нового вопроса в базе данных
                val questionEntity = QuestionEntity.new {
                    question = dto.question
                    slide = dto.slideId
                    // Ищем пользователя по ID, если не найден, выбрасываем исключение
                    user = UserRepository().findByTelegramId(dto.userID)
                        ?: error("Пользователь с ID ${dto.userID} не найден")
                }

                // Выводим информацию о сохраненном вопросе
                println("Вопрос сохранен: ${questionEntity.question}")

                // Возвращаем сохраненный объект
                return@transaction questionEntity
            } catch (e: Exception) {
                println("Ошибка при сохранении вопроса: ${e.message}")
                throw e // Перебрасываем ошибку для дальнейшей обработки
            }
        }
    }

    override fun getAll(): List<QuestionOutputDto> {
        println("🔍 Запрос к базе данных: получение всех вопросов")

        return transaction {
            println("📂 Транзакция начата")

            QuestionEntity.all().mapNotNull { questionEntity ->
                println("📦 Обработка вопроса: ${questionEntity.question}")

                try {
                    val userEntity = UserRepository().findByTelegramId(questionEntity.user.telegramId)
                    if (userEntity != null) {
                        val userDTO = userEntity.toDTO()
                        println("👤 Пользователь найден: ${userDTO.firstName} ${userDTO.secondName}")

                        QuestionOutputDto(
                            question = questionEntity.question,
                            slideId = questionEntity.slide,
                            user = userDTO
                        )
                    } else {
                        println("🚨 Пользователь с telegramId ${questionEntity.user.telegramId} не найден")
                        null
                    }
                } catch (e: IllegalArgumentException) {
                    println("🚨 Ошибка при поиске пользователя: ${e.message}")
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}