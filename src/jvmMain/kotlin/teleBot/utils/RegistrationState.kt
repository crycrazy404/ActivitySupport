package teleBot.utils

data class RegistrationState(
    var step: Int = 1, // Начальный шаг
    var firstName: String? = null,
    var lastName: String? = null,
    var group: String? = null
)
