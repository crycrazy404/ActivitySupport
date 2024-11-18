
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

class ViewMain{
    @Composable
    @Preview
    fun svgFromByteArray(svgImagesBitmap: List<ImageBitmap>) {
        var currentIndex by remember { mutableStateOf(0) }
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 4.dp,
                    title = { Text("Чат") },
                    navigationIcon = {
                        IconButton(onClick = { /* Обработчик для кнопки навигации */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Обработчик для кнопки поиска */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Поиск")
                        }
                        IconButton(onClick = { /* Обработчик для меню */ }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                        }
                    }
                )
            },
            content = { padding ->

            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 65.dp)
                .defaultMinSize(400.dp)
                .border(width = 2.dp, color = Color.Black),
                contentAlignment = Alignment.Center

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .height(540.dp) // фиксированная высота для Row
                ) {
                    Box(
                        modifier = Modifier
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                            .background(color = Color.LightGray)
                            .fillMaxHeight()
                            .weight(2f) // 2/3 ширины
                    ) {
                        // Основное изображение
                        Image(
                            bitmap = svgImagesBitmap[currentIndex],
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize() // заполняет всю доступную высоту и ширину Box
                                .clip(RoundedCornerShape(16.dp))
                        )

                        // Кнопки по бокам
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                if (currentIndex > 0) currentIndex -= 1
                            },
                                enabled = currentIndex > 0) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Previous"
                                )
                            }

                            IconButton(onClick = {
                                if (currentIndex < svgImagesBitmap.size - 1) currentIndex += 1
                            },
                                enabled = currentIndex < svgImagesBitmap.size - 1) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Next"
                                )
                            }
                        }

                        // Строка внизу для просмотра всей коллекции
                        Text(
                            text = "Image",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(8.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.body2
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight() // заполняет всю доступную высоту внутри Row
                            .weight(1f), // 1/3 ширины
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                                .background(color = Color.LightGray)
                                .fillMaxWidth() // заполняет всю доступную ширину Column
                                .weight(1f) // 1/2 высоты Column
                                .padding(8.dp), // отступы внутри LazyColumn
                            reverseLayout = true,
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                Text("Чат", modifier = Modifier.padding(8.dp))
                            }
                        }

                        Box(
                            modifier = Modifier
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                                .background(Color.LightGray)
                                .fillMaxWidth() // заполняет всю доступную ширину Column
                                .weight(1f) // 1/2 высоты Column
                        ) {
                            Text("Заметки", modifier = Modifier.padding(8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
