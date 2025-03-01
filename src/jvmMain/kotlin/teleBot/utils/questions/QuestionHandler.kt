package teleBot.utils.questions

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import util.parser.Iterator

class QuestionHandler(
    private val sendMessage: (SendMessage) -> Unit,
    private var iter: Iterator? = null // Сделали итератор необязательным и nullable
) {
    private val userRepository = db.repository.user.UserRepository()
    private val questionRepository = db.repository.questions.QuestionsRepository()
    private val users = mutableListOf<Long>()

    fun setIterator(iterator: Iterator) {
        this.iter = iterator
    }

    fun startQuestion(message: Message) {
        val userId = message.from.id
        if (!userRepository.isRegistered(userId)) {
            send(userId.toString(), "Вы не зарегистрированы!")
            return
        }
        if (iter == null) {
            send(userId.toString(), "Итератор не задан. Попробуйте позже.")
            return
        }
        users.add(userId)
        send(userId.toString(), "Напишите свой вопрос для слайда № ${iter!!.getCurrent() + 1}")
    }

    fun isQuestion(id: Long): Boolean = users.contains(id)

    fun handleQuestion(message: Message) {
        if (iter == null) {
            send(message.from.id.toString(), "Итератор не задан. Вопрос не может быть сохранен.")
            return
        }

        val newQuestion = db.dto.questions.QuestionInputDto(
            userID = message.from.id,
            question = message.text,
            slideId = iter!!.getCurrent().toLong()
        )
        println(newQuestion.toString())
        questionRepository.save(newQuestion)
        send(message.from.id.toString(), "Ваш вопрос успешно сохранен")
        users.remove(message.from.id)
    }

    private fun send(chatId: String, text: String) {
        val privateMessage = SendMessage().apply {
            this.chatId = chatId
            this.text = text
            this.enableMarkdown(true)
        }
        try {
            sendMessage(privateMessage)
        } catch (e: TelegramApiException) {
            println("Ошибка отправки сообщения: ${e.message}")
            e.printStackTrace()
        }
    }
}