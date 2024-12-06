package teleBot.utils

data class RegistrationState(
    var step: Int = 0, // Начальный шаг
    var firstName: String? = null,
    var lastName: String? = null,
    var group: String? = null,
    var changeField: UserState = UserState.REGISTRATION
)

enum class UserState {
    REGISTRATION, CONFIRMATION, EDIT_NAME, EDIT_LASTNAME, EDIT_GROUP
}