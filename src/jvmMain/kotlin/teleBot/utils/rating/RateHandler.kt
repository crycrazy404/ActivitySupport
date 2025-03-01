package teleBot.utils.rating

import db.dto.reactions.ReactionDTO
import db.repository.reactions.ReactionsRepository
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class RateHandler(
    private val sendMessage: (SendMessage) -> Unit
) {


    private val reactionsRepository = ReactionsRepository()
    fun handleRate(callback: CallbackQuery) {
        val rating = callback.data.split("_")[1].toIntOrNull()
        val slidID = callback.data.split("_")[3].toIntOrNull()
        if (rating != null && rating in 1..5 && slidID != null) {

            reactionsRepository.save(ReactionDTO(rating, slidID))

            send(callback.from.id.toString(), "Вы оценили $slidID слайд на $rating")

        } else {
            send(callback.from.id.toString(), "Неверная оценка")
        }
    }

    private fun send(chatId: String, text: String) {
        val privateMessage = SendMessage().apply {
            this.chatId = chatId
            this.text = text
            this.enableMarkdown(true)
        }
        try {
            println("Попытка отправить сообщение: Chat ID = $chatId, Text = $text")
            sendMessage(privateMessage)
            println("Сообщение успешно отправлено")
        } catch (e: TelegramApiException) {
            println("Ошибка отправки сообщения: ${e.message}")
            e.printStackTrace()
        }
    }
}