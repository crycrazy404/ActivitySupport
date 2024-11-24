package teleBot.handlers

import models.user.UserEntity
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import repository.UserRepository
import teleBot.utils.RegistrationState

class RegistrationHandler(
    private val sendMessageFunction: (SendMessage) -> Unit,
) {
    // Словарь для хранения состояния регистрации пользователей
    // Словарь для хранения состояния регистрации пользователей
    private val registrationStates = mutableMapOf<Long, RegistrationState>()

    // Начинаем процесс регистрации
    fun startRegistration(userId: Long, userName: String) {
        // Проверяем, зарегистрирован ли пользователь
        if (UserRepository().isUserRegistered(userId)) {
            sendMessage(userId, "Вы уже зарегистрированы!")
            return
        }

        println("Начинаем регистрацию для пользователя $userId")
        registrationStates[userId] = RegistrationState(step = 1)
        sendMessage(userId, "Привет, $userName! Давайте начнем процесс регистрации. Как ваше имя?")
    }

    // Обрабатываем шаги регистрации в зависимости от ввода пользователя
    fun handleUserRegistrationInput(userId: Long, input: String) {
        val registrationState = registrationStates[userId]
        if (registrationState != null) {
            when (registrationState.step) {
                1 -> {
                    // Шаг 1: Сбор имени
                    registrationState.firstName = input
                    registrationState.step = 2
                    sendMessage(userId, "Спасибо! Ваше имя: ${registrationState.firstName}. Теперь, пожалуйста, введите вашу фамилию.")
                }
                2 -> {
                    // Шаг 2: Сбор фамилии
                    registrationState.lastName = input
                    registrationState.step = 3
                    sendMessage(userId, "Спасибо! Ваша фамилия: ${registrationState.lastName}. Теперь, пожалуйста, введите вашу группу.")
                }
                3 -> {
                    // Шаг 3: Сбор группы
                    registrationState.group = input
                    registrationState.step = 4
                    sendMessage(userId, "Спасибо! Ваша группа: ${registrationState.group}.")
                    showConfirmation(userId, registrationState) // Переход на подтверждение
                }
                4 -> {
                    // Шаг 4: Подтверждение данных
                    if (input.equals("/confirm", ignoreCase = true)) {
                        confirmRegistration(userId)
                        sendMessage(userId, "Регистрация завершена успешно!")
                    } else {
                        sendMessage(userId, "Пожалуйста, укажите, что нужно изменить: имя, фамилию или группу.")
                    }
                }
                else -> {
                    sendMessage(userId, "Неизвестный шаг. Попробуйте начать регистрацию заново.")
                }
            }
        } else {
            sendMessage(userId, "Регистрация не начата. Используйте команду /register для начала.")
        }
    }

    // Метод для отправки собранных данных и клавиатуры редактирования
    private fun showConfirmation(userId: Long, registrationState: RegistrationState) {
        val confirmationText = """
        Ваши данные:
        Имя: ${registrationState.firstName}
        Фамилия: ${registrationState.lastName}
        Группа: ${registrationState.group}
        
        Если всё верно, отправьте /confirm. Если нужно что-то изменить, выберите нужное поле:
    """.trimIndent()

        val keyboard = InlineKeyboardMarkup().apply {
            val row1 = listOf(
                InlineKeyboardButton().apply {
                    text = "Изменить имя"
                    callbackData = "edit_first_name"
                },
                InlineKeyboardButton().apply {
                    text = "Изменить фамилию"
                    callbackData = "edit_last_name"
                },
                InlineKeyboardButton().apply {
                    text = "Изменить группу"
                    callbackData = "edit_group"
                }
            )
            val row2 = listOf(
                InlineKeyboardButton().apply {
                    text = "Начать заново"
                    callbackData = "restart_registration"
                }
            )
            keyboard = listOf(row1, row2)
        }

        sendMessageWithKeyboard(userId, confirmationText, keyboard)
    }

    // Метод для отправки сообщения с клавиатурой
    private fun sendMessageWithKeyboard(userId: Long, text: String, keyboard: InlineKeyboardMarkup) {
        val message = SendMessage().apply {
            chatId = userId.toString()
            this.text = text
            replyMarkup = keyboard
        }
        sendMessageFunction(message)  // Отправка сообщения с клавиатурой
    }

    // Метод для подтверждения регистрации и сохранения данных
    fun confirmRegistration(userId: Long) {
        val registrationState = registrationStates[userId]
        if (registrationState != null) {
            val user = registrationState.firstName?.let {
                registrationState.lastName?.let { it1 ->
                    registrationState.group?.let { it2 ->
                        UserEntity.new {
                            this.telegramId = userId
                            this.firstName = it
                            this.secondName = it1
                            this.group = it2
                        }
                    }
                }
            }

            if (user != null) {
                UserRepository().create(user)
            } // Сохранение в БД

            // Завершаем процесс регистрации
            registrationStates.remove(userId)
        }
    }

    // Метод для отправки сообщения пользователю
    fun sendMessage(userId: Long, text: String) {
        val privateMessage = SendMessage().apply {
            chatId = userId.toString()
            this.text = text
        }

        try {
            sendMessageFunction(privateMessage)
        } catch (e: Exception) {
            println("Ошибка отправки личного сообщения: ${e.message}")
        }
    }

    // Метод для обработки сообщений с callback'ами
    fun handleCallbackQuery(userId: Long, callbackData: String) {
        val registrationState = registrationStates[userId]
        if (registrationState != null) {
            when (callbackData) {
                "edit_first_name" -> {
                    // Переход на редактирование имени
                    registrationState.step = 1
                    sendMessage(userId, "Пожалуйста, введите ваше имя заново.")
                }
                "edit_last_name" -> {
                    // Переход на редактирование фамилии
                    registrationState.step = 2
                    sendMessage(userId, "Пожалуйста, введите вашу фамилию заново.")
                }
                "edit_group" -> {
                    // Переход на редактирование группы
                    registrationState.step = 3
                    sendMessage(userId, "Пожалуйста, введите вашу группу заново.")
                }
                "restart_registration" -> {
                    // Полностью перезапускаем процесс регистрации
                    registrationStates[userId] = RegistrationState(step = 1)
                    sendMessage(userId, "Регистрация начата заново. Пожалуйста, введите ваше имя.")
                }
                "confirm" -> {
                    confirmRegistration(userId)
                    sendMessage(userId, "Регистрация завершена успешно!")
                }
                else -> {
                    sendMessage(userId, "Неизвестная команда.")
                }
            }
        }

        else {
            sendMessage(userId, "Регистрация не начата. Используйте команду /register для начала.")
        }
    }

    // Метод для проверки, находится ли пользователь в процессе регистрации
    fun isUserInRegistration(userId: Long): Boolean {
        return registrationStates.containsKey(userId)
    }
}

