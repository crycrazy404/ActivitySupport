package view.mainPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ChatMessage {
    @Composable
    fun init(message: String, name: String, surname: String) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation =4.dp
        ) {
            Column(modifier = Modifier
                .padding(12.dp)
            ){
                Text(
                    text = "$name $surname",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}