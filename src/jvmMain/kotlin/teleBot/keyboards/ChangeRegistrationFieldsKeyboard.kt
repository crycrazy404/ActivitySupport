package teleBot.keyboards

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import teleBot.keyboards.builders.InlineKeyboardBuilder

class ChangeRegistrationFieldsKeyboard {
    fun createKeyboard(): InlineKeyboardMarkup = InlineKeyboardBuilder()
            .addButton("Изменить имя", "change_name")
            .addButtonToRow("Изменить фамилию", "change_lastName", 0)
            .addButtonToRow("Изменить группу", "change_group", 0)
            .addButton("Начать заново", "restart")
            .addButtonToRow("Закончить", "commit", 1)
            .build().apply {
                println("Клавиатура создана")
            }
}