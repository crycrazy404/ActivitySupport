package view.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

class Notes {
    @Composable
    fun init(text: String){
        Box(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = true)
                .background(Color.LightGray)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Text("Заметки",  modifier = Modifier
                .align(Alignment.TopCenter) // Размещает заголовок в центре по горизонтали и сверху по вертикали
                .padding(top = 16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6)
                        Text(
                text = text.ifBlank { "Заметок нет" },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .padding(top = 40.dp) // Отступ, чтобы оставить место для заголовка
            )
        }
    }
}