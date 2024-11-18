package view.mainPage

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class Chat {
    @Composable
    fun init(){
        val messages = listOf(
            Triple("Hello, how are you?", "John", "Doe"),
            Triple("I'm good, thanks! How about you?", "Jane", "Smith"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
            Triple("I'm doing well too!", "John", "Doe"),
        )
        // Отображаем список сообщений
        Box(modifier = Modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.LightGray)
            .padding(top = 16.dp)
        ){
            Column(modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.BottomStart)
                , verticalArrangement = Arrangement.Bottom) {
                Box(modifier = Modifier
                    .fillMaxWidth()) {
                    val listState = rememberLazyListState()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Чат",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h6
                        )

                        Spacer(modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth())

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            state = listState  // Присвойте состояние списку
                        ) {
                            items(messages.size) { index ->
                                val (message, name, surname) = messages[index]
                                val chatMessage = ChatMessage()
                                chatMessage.init(message, name, surname)
                            }
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        adapter = rememberScrollbarAdapter(scrollState = listState)
                    )
                }
            }
        }
    }
}