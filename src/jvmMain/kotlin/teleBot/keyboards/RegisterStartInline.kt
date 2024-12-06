package teleBot.keyboards

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import teleBot.keyboards.builders.InlineKeyboardBuilder

class RegisterStartInline {
    fun createKeyboard(): InlineKeyboardMarkup {
        return InlineKeyboardBuilder()
            .addButton("Зарегистрироваться", "register")
            .build()
    }
}