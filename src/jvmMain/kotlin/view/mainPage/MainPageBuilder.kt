package view.mainPage

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import parser.Converter
import parser.Iterator


class MainPageBuilder() {
    @Composable
    fun init(iter: Iterator) {
        val mainPictures = Converter().slideScaledImage(iter.getSlides(), 1920, 1080)
        val miniPictures = Converter().slideScaledImage(iter.getSlides(), 320, 180)
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
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sizeIn(maxHeight = 690.dp)
                        .defaultMinSize(400.dp)
                        .border(width = 2.dp, color = Color.Black),
                    contentAlignment = Alignment.Center
                ){
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier
                           .padding(16.dp)
                   ) {
                       Row {
                           PptxView().init(mainPictures, miniPictures, iter)
                       }

                   }
                }
            }
        )
    }
}