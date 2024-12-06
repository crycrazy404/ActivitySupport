package teleBot.keyboards.builders

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

class InlineKeyboardBuilder {
    private val rows: MutableList<List<InlineKeyboardButton>> = mutableListOf()
    fun addButton(text: String, callbackData: String): InlineKeyboardBuilder {
        val button = InlineKeyboardButton(text).apply {
            this.callbackData = callbackData
        }
        rows.add(listOf(button))
        return this
    }
    fun addButtonToRow(text: String, callbackData: String, rowIndex: Int): InlineKeyboardBuilder {
        if (rowIndex >= rows.size) {
            throw IndexOutOfBoundsException("Строки с индексом $rowIndex не существует")
        }
        val button = InlineKeyboardButton(text).apply {
            this.callbackData = callbackData
        }
        rows[rowIndex] = rows[rowIndex] + button
        return this
    }
    fun build(): InlineKeyboardMarkup {
        val markup = InlineKeyboardMarkup()
        markup.keyboard = rows
        return markup
    }
}