package userInterface.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import db.dto.questions.QuestionOutputDto
import db.repository.questions.QuestionsRepository
import teleBot.AdminBot
import util.observer.BotObserver

class Chat {
    @Composable
    fun init(
        bot: AdminBot,
        questions: MutableState<List<QuestionOutputDto>>,) {


        LaunchedEffect(Unit) {
            bot.addObserver(object : BotObserver {
                override fun onNewMessage() {
                    println("вопрос получен")
                    // Получаем все вопросы из репозитория
                    questions.value = QuestionsRepository().getAll()
                    // Обновляем список сообщений
                }
            })
        }

        // Отображаем список вопросов
        Box(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.LightGray)
                .padding(top = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(alignment = Alignment.BottomStart),
                verticalArrangement = Arrangement.Bottom
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Корректное использование items с коллекцией
                    items(questions.value) { questionDto ->
                        ChatMessage().init(questionDto)
                    }
                }
            }
        }
    }
}
