package view.mainPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import parser.Iterator

class PptxView {

    @Composable
    fun init(mainImagesBitmap: List<ImageBitmap>, miniImageBitmap: List<ImageBitmap>, iter: Iterator) {
        val visibleItemsCount = 3 // количество видимых элементов
        val halfVisibleItems = visibleItemsCount / 2 // половина видимых элементов
        var currentIndex by remember { mutableStateOf(iter.getCurrent()) }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(540.dp) // фиксированная высота для Row
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
                        bitmap = mainImagesBitmap[currentIndex],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize() // заполняет всю доступную высоту и ширину Box
                            .clip(RoundedCornerShape(16.dp))
                    )

                    // Кнопки по бокам
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                currentIndex = iter.prev()
                            },
                            enabled = iter.hasPrev()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Previous"
                            )
                        }

                        IconButton(
                            onClick = {
                                currentIndex = iter.next()
                            },
                            enabled = iter.hasNext()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next"
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Notes().init(iter.getSlideNotes())

                    }
                    Spacer(modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth())
                    Row(
                        modifier = Modifier
                        .weight(2f)
                    ) {
                        Chat().init()

                    }
                }
            }

            // Центрируем LazyRow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), // отступ сверху
                horizontalArrangement = Arrangement.Center // Центрируем LazyRow
            ) {
                LazyRow(
                    modifier = Modifier
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                        .background(color = Color.LightGray),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Определяем индексы для отображения
                    val startIndex = maxOf(0, currentIndex - halfVisibleItems)
                    val endIndex = minOf(miniImageBitmap.size - 1, currentIndex + halfVisibleItems)
                    val indicesToShow = (startIndex..endIndex).map { it.coerceIn(0, miniImageBitmap.size - 1) }

                    items(indicesToShow) { index ->
                        val isSelected = index == currentIndex
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color.Blue else Color.Transparent
                                )
                                .clickable {
                                    currentIndex = index
                                }
                        ) {
                            Image(
                                bitmap = miniImageBitmap[index],
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}