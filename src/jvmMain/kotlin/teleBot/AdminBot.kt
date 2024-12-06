package teleBot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import teleBot.utils.RegistrationHandler

class AdminBot(
    private var botName: String,
    private var botToken: String)
    : TelegramLongPollingBot(botToken)
{
    override fun getBotUsername(): String {
        return botName
    }

    private val registrationHandler = RegistrationHandler{ message: SendMessage ->
        execute(message)
    }

    override fun onUpdateReceived(update: Update?) {
        update?.let {
            if (it.hasCallbackQuery()) {
                when{
                    registrationHandler.isUserInRegistration(it.callbackQuery.from.id) -> {
                        registrationHandler.handleRegistrationCallBackQuery(it.callbackQuery)
                    }
                    else ->  sendMessage(it.callbackQuery.from.id.toString(), "Нечего обрабатывать")
                }
            }
            if (it.hasMessage()) {
                val chatId = it.message.chatId.toString()
                val receivedText = it.message.text
                if (receivedText == "/start" && it.message.isUserMessage) {
                    val text = "Привет, ${it.message.from.firstName}! Для начала регистрации напиши /register"
                    sendMessage(chatId, text)
                } else if (receivedText == "/register" && it.message.isUserMessage) {
                    registrationHandler.startRegistration(it.message.from.id)
                } else if (registrationHandler.isUserInRegistration(it.message.from.id)) {
                    registrationHandler.handelUserRegistrationQuery(it.message)
                }
            }
        }
    }

    private fun sendMessage(chatId: String, text: String){
        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = text
            this.parseMode = "Markdown"
        }
        try {
            execute(message)
        }catch (e: TelegramApiException){
            e.printStackTrace()
        }
    }


    private fun sendMessageWithInlineKeyboard(userId: String, text: String, replyMarkup: InlineKeyboardMarkup) {
        val message = SendMessage(userId, text)
        try {
            message.replyMarkup = replyMarkup
            execute(message)
        }catch (exc: TelegramApiException){
            exc.printStackTrace()
        }
    }

    private fun sendMessageWithKeyboard(userId: String, text: String) {

    }
}
