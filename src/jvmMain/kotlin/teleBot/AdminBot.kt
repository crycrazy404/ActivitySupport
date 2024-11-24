package teleBot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import teleBot.handlers.RegistrationHandler

class AdminBot(
    private var botName: String,
    private var botToken: String)
    : TelegramLongPollingBot(botToken)
{

    override fun getBotUsername(): String = botName
    private val registrationManager = RegistrationHandler {
        message: SendMessage -> execute(message) // Функция для отправки сообщений

    }

    override fun onUpdateReceived(update: Update?) {
        update?.let {
            if (it.hasMessage() && it.message.hasText()) {
                val message = it.message
                val userId = message.from.id

                // Если это команда /register
                if (message.text.startsWith("/register") && isFromRegistrationTopic(message)) {
                    registrationManager.startRegistration(userId, message.from.userName ?: "User")
                } else if (registrationManager.isUserInRegistration(userId)) {
                    registrationManager.handleUserRegistrationInput(userId, message.text)
                }
            }
            if (it.hasCallbackQuery()) {
                val callbackQuery = it.callbackQuery
                registrationManager.handleCallbackQuery(callbackQuery.from.id, callbackQuery.data)
            }
        }
    }

    private fun sendMessage(chatId: Long, text: String) {
        val message = SendMessage()
        message.chatId = chatId.toString()
        message.text = text
        try {
            execute(message) // Отправка сообщения через API Telegram
        } catch (e: Exception) {
            println("Ошибка при отправке сообщения: ${e.message}")
        }
    }

    private fun isFromRegistrationTopic(message: Message): Boolean{
        return message.isGroupMessage && message.messageThreadId == 5
    }
}
