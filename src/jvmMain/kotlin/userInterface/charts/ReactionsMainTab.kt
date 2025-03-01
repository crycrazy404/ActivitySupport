package userInterface.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import db.dto.reactions.ReactionDTO
import db.repository.reactions.ReactionsRepository
import org.jetbrains.skia.Paint
import org.jetbrains.skia.PaintMode
import org.jetbrains.skia.Rect

class ReactionsMainTab {
    private val repository = ReactionsRepository()

    @Composable
    fun init(reactions: MutableState<List<ReactionDTO>>, miniPictures: List<ImageBitmap>) {
        if (miniPictures.isEmpty()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Презентация не выбрана", style = MaterialTheme.typography.h6)
            }
        } else {
            Box(
                modifier = Modifier
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                    .background(Color.LightGray)
                    .fillMaxSize(),
            ) {
                Column {
                    Text(
                        text = "Обзор",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        itemsIndexed(miniPictures) { index, picture ->
                            slideRatingCard(index, picture, reactions.value)
                        }
                    }

                    IconButton(
                        onClick = {
                            updateRating(reactions)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun slideRatingCard(slideId: Int, picture: ImageBitmap, reactions: List<ReactionDTO>) {
        val slideReactions = reactions.filter { it.slideId == slideId }
        val ratings = slideReactions.groupingBy { it.mark }.eachCount()
        val averageRating = if (slideReactions.isNotEmpty()) {
            slideReactions.map { it.mark }.average().toString().take(3)
        } else {
            "N/A"
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = BitmapPainter(picture),
                    contentDescription = "Миниатюра слайда",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                ) {
                    barChart(ratings)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Средняя: $averageRating",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun barChart(ratings: Map<Int, Int>) {
        val maxRating = (ratings.values.maxOrNull() ?: 1).toFloat()
        val barWidth = 30f

        var tooltipText by remember { mutableStateOf<String?>(null) }
        var tooltipPosition by remember { mutableStateOf<Pair<Float, Float>?>(null) }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerMoveFilter(
                onMove = { offset ->
                    val barSpacing = 20f
                    (1..5).forEach { mark ->
                        val left = (mark - 1) * (barWidth + barSpacing)
                        val right = left + barWidth
                        if (offset.x in left..right) {
                            val count = ratings[mark] ?: 0
                            tooltipText = "Оценка $mark: $count шт."
                            tooltipPosition = offset.x to offset.y
                        }
                    }
                    true
                },
                onExit = {
                    tooltipText = null
                    tooltipPosition = null
                    true
                }
            )
        ) {
            val colors = listOf(
                Color.Red,          // 1
                Color(0xFFFFA500),  // 2 (оранжевый)
                Color.Yellow,       // 3
                Color(0xFFADFF2F),  // 4 (желто-зеленый)
                Color.Green         // 5
            )

            val paint = Paint().apply {
                mode = PaintMode.FILL
            }

            (1..5).forEach { mark ->
                val count = ratings[mark] ?: 0
                val left = (mark - 1) * (barWidth + 20)
                val top = size.height - (size.height * (count / maxRating))
                val right = left + barWidth
                val bottom = size.height

                paint.color = colors[mark - 1].toArgb()
                val rect = Rect.makeLTRB(left, top, right, bottom)
                drawContext.canvas.nativeCanvas.drawRect(rect, paint)
            }
        }

        if (tooltipText != null && tooltipPosition != null) {
            Box(
                modifier = Modifier
                    .absoluteOffset(x = tooltipPosition!!.first.dp, y = tooltipPosition!!.second.dp)
                    .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(4.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = tooltipText ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

    private fun updateRating(reactions: MutableState<List<ReactionDTO>>) {
        reactions.value = repository.getAll()
    }
}
