package teleBot.utils

import dto.UserDTO
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import repository.user.UserRepository
import teleBot.keyboards.ChangeRegistrationFieldsKeyboard

class RegistrationHandler(
    private val sendMessage: (SendMessage) -> Unit,
    ) {
    private val userRegistrations = mutableMapOf<Long, RegistrationState>()
    private val userRepository = UserRepository()

    fun startRegistration(userId: Long){
        if(userRepository.isRegistered(userId)){
            send(userId.toString(), "Вы уже зарегистрированы!")
            return
        }
        userRegistrations[userId] = RegistrationState(step = 1, changeField = UserState.REGISTRATION)
        val text =
            """
            *Начата регистрация!*
            Введите ваше *имя*.
            """.trimIndent()
        send(userId.toString(), text)
    }

    fun handelUserRegistrationQuery(message: Message) {
        val userId = message.from.id
        val user = userRegistrations[userId]

        if (user != null) {
            when (user.changeField) {
                UserState.REGISTRATION -> handleRegistration(user, message)
                UserState.EDIT_NAME -> handleEditName(message, user)
                UserState.EDIT_LASTNAME -> handleEditLastName(message, user)
                UserState.EDIT_GROUP -> handleEditGroup(message, user)
                UserState.CONFIRMATION -> handleConfirmation(user, message.from.id)
            }
        } else {
            send(userId.toString(), "Не удалось продолжить регистрацию. Попробуйте снова начать регистрацию.")
        }
    }


    private fun handleConfirmation(user: RegistrationState, userId: Long){
        val text =
            """
                Ваши данные:
                *Имя:*  ${user.firstName}
                *Фамилия:*  ${user.lastName}
                *Группа:*  ${user.group}
                Если все верно, нажмите кнопку *закончить*. Если нужно что-то изменить, выберите нужное поле:
            """.trimIndent()
        sendWithKeyboard(userId.toString(), text, ChangeRegistrationFieldsKeyboard().createKeyboard())
    }


    private fun handleEditName(message: Message, user: RegistrationState) {
        user.firstName = message.text
        user.changeField = UserState.CONFIRMATION
        handleConfirmation(user, message.from.id)
    }

    private fun handleEditLastName(message: Message, user: RegistrationState) {
        user.lastName = message.text
        user.changeField = UserState.CONFIRMATION
        handleConfirmation(user, message.from.id)
    }

    private fun handleEditGroup(message: Message, user: RegistrationState) {
        user.group = message.text
        user.changeField = UserState.CONFIRMATION
        handleConfirmation(user, message.from.id)
    }

    private fun handleRegistration(user: RegistrationState,message: Message){
        val userId = message.from.id
        when (user.step) {
            1 -> {
                user.firstName = message.text
                user.step = 2
                send(userId.toString(),
                    """
                        Спасибо! Ваше имя: *${user.firstName}*.
                        Теперь, пожалуйста, введите вашу *фамилию*.
                        """.trimIndent())
            }
            2 -> {
                user.lastName = message.text
                user.step = 3
                send(userId.toString(),
                    """
                        Спасибо! Ваша фамилия: *${user.lastName}*.
                        Теперь, пожалуйста, введите вашу *группу*.
                        """.trimIndent())
            }
            3 -> {
                user.group = message.text
                user.changeField = UserState.CONFIRMATION
                handleConfirmation(user, message.from.id)
            }
        }
    }


    fun handleRegistrationCallBackQuery(callbackQuery: CallbackQuery) {
        val chatId = callbackQuery.from.id
        val callbackData = callbackQuery.data
        val user = userRegistrations[chatId]

        when (callbackData) {
            "change_name" -> {
                send(chatId.toString(), "Введите новое имя")
                user?.changeField = UserState.EDIT_NAME
            }
            "change_lastName" -> {
                send(chatId.toString(), "Введите новую фамилию")
                user?.changeField = UserState.EDIT_LASTNAME
            }
            "change_group" -> {
                send(chatId.toString(), "Введите новую группу")
                user?.changeField = UserState.EDIT_GROUP
            }
            "restart" -> {
                send(chatId.toString(), "Регистрация будет начата заново")
                userRegistrations.remove(chatId) // Удаляем старые данные пользователя
                startRegistration(callbackQuery.from.id)
            }
            "commit" ->
                {
                    val newUserID = userRepository.create(UserDTO(
                        telegramId = callbackQuery.from.id,
                        firstName = user?.firstName!!,
                        secondName = user.lastName!!,
                        group = user.group!!,
                    ))
                    println("User created with ID: $newUserID")

                    userRegistrations.remove(chatId)
                    send(chatId.toString(), "Регистрация успешно завершена!")
            }
        }
    }

    fun isUserInRegistration(userId: Long): Boolean =
        userRegistrations.containsKey(userId)

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
    private fun sendWithKeyboard(chatId: String, text: String, replyKeyboardMarkup: InlineKeyboardMarkup) {
        val privateMessage = SendMessage().apply {
            this.chatId = chatId
            this.text = text
            enableMarkdown(true)
        }
        privateMessage.replyMarkup = replyKeyboardMarkup
        println("Отправка сообщения")
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