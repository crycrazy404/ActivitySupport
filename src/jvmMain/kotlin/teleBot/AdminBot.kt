package teleBot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

class AdminBot(private var botName: String, private var botToken: String): TelegramLongPollingBot() {


    override fun getBotToken(): String {
        return this.botToken
    }

    override fun getBotUsername(): String {
        return this.botName
    }

    override fun onUpdateReceived(update: Update?) {
        if (update?.hasMessage() == true && update.message?.hasText() == true) {
            val message = update.message
            val chatId = update.message.chatId

            if (message.text.startsWith("/register") && isFromRegistrationTopic(message)) {
                handleRegisterCommand(chatId)
                val mapper= jacksonObjectMapper()
                val json = mapper.writeValueAsString(message)
                println(json)
            }
        }
    }

    private fun handleRegisterCommand(chatId: Long?) {
        val responseText = "Вы успешно зарегистрированы!"

        val message = SendMessage().apply {
            this.chatId = chatId.toString()
            this.text = responseText
        }

        try {
            execute(message)
        } catch (e: Exception) {
            println("Error sending message: ${e.message}")
        }
    }

    private fun isFromRegistrationTopic(message: Message): Boolean{
        return message.messageThreadId == 5
    }

}