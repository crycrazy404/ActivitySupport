package teleBot

import androidx.compose.ui.graphics.ImageBitmap
import io.github.cdimascio.dotenv.dotenv
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import teleBot.utils.questions.QuestionHandler
import teleBot.utils.rating.RateHandler
import teleBot.utils.registration.RegistrationHandler
import util.observer.BotObserver
import util.parser.Converter
import util.parser.Iterator
import java.io.ByteArrayInputStream

class AdminBot(
    private val botName: String,
    private val botToken: String
) : TelegramLongPollingBot(botToken) {
    private val observers = mutableListOf<BotObserver>()

    fun addObserver(observer: BotObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: BotObserver) {
        observers.remove(observer)
    }

    // Метод для уведомления всех наблюдателей о новом сообщении
    private fun notifyObservers() {
        observers.forEach { it.onNewMessage() }
    }
    override fun getBotUsername(): String = botName

    private val env = dotenv {
        directory = "./"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }
    private var iterator: Iterator? = null

    private val registrationHandler = RegistrationHandler { message: SendMessage ->
        execute(message)
    }

    private val ratingHandler = RateHandler{message: SendMessage -> execute(message)}

    private val questionHandler = QuestionHandler(
        sendMessage = { message: SendMessage -> execute(message) }
    )

    fun setIterator(iter: Iterator) {
        this.iterator = iter
        questionHandler.setIterator(iter)
    }

    override fun onUpdateReceived(update: Update?) {
        update?.let {
            if (it.hasCallbackQuery()) {
                when {
                    registrationHandler.isUserInRegistration(it.callbackQuery.from.id) -> {
                        registrationHandler.handleRegistrationCallBackQuery(it.callbackQuery)
                    }
                    it.callbackQuery.data.startsWith("rating_") -> {
                        ratingHandler.handleRate(it.callbackQuery)
                    }
                    else -> sendMessage(it.callbackQuery.from.id.toString(), "Нечего обрабатывать")
                }
            }

            if (it.hasMessage()) {
                val chatId = it.message.chatId.toString()
                val receivedText = it.message.text
                if (receivedText == "/start" && it.message.isUserMessage) {
                    sendMessage(chatId, "Привет, ${it.message.from.firstName}! Для начала регистрации напиши /register")
                }
                if (receivedText == "/register" && it.message.isUserMessage) {
                    registrationHandler.startRegistration(it.message.from.id)
                } else if (registrationHandler.isUserInRegistration(it.message.from.id)) {
                    registrationHandler.handelUserRegistrationQuery(it.message)
                }
                if (receivedText == "/question" && it.message.isUserMessage) {
                    questionHandler.startQuestion(it.message)
                } else if (questionHandler.isQuestion(it.message.from.id)) {
                    questionHandler.handleQuestion(it.message)
                    notifyObservers()
                }
            }
        }
    }

    private fun sendMessage(chatId: String, text: String) {
        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = text
            this.parseMode = "Markdown"
        }
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    fun sendSlide(slideID: Int, slideImage: ImageBitmap) {
        val photo = Converter().bitmapToByteArray(slideImage)
        val message = SendPhoto().apply {
            this.chatId = env["CHAT_ID"] ?: throw IllegalStateException("CHAT_ID is not set in .env")
            this.caption = "Слайд номер $slideID"
            this.parseMode = "Markdown"
            this.photo = InputFile(ByteArrayInputStream(photo), "slide.png")

            this.replyMarkup = InlineKeyboardMarkup().apply {
                this.keyboard = listOf(
                    (1..5).map { rating ->
                        InlineKeyboardButton().apply {
                            text = rating.toString()
                            callbackData = "rating_${rating}_slide_$slideID"
                        }
                    }
                )
            }
        }

        try {
            execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}

