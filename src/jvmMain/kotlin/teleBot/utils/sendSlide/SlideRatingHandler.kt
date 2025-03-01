package teleBot.utils.sendSlide

import org.telegram.telegrambots.meta.api.objects.CallbackQuery

class SlideRatingHandler {

    fun handelSlideMark(callback: CallbackQuery) {
        println(callback.toString())
    }

}