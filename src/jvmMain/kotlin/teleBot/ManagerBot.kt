package teleBot

import io.github.cdimascio.dotenv.dotenv
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

class ManagerBot {

    private val env = dotenv{
        directory = "./"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

    private val bot: TelegramLongPollingBot by lazy {
        val botName: String = env["BOT_NAME"] ?: throw IllegalStateException("BOT_NAME is not set in .env")
        val botToken: String = env["BOT_TOKEN"] ?: throw IllegalStateException("BOT_TOKEN is not set in .env")
        AdminBot(botName, botToken)
    }

    fun managerBot(){
        try {
            val botAPI = TelegramBotsApi(DefaultBotSession::class.java)
            botAPI.registerBot(bot)
        } catch (e: TelegramApiException) {
            println("Bot initialization failed: ${e.message}")
        }
    }
}