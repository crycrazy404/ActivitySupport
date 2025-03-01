package userInterface.charts.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ReactionMiniCard {
    @Composable
    fun init() {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                ,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Распределяем элементы по краям
                    verticalAlignment = Alignment.CenterVertically // Выравниваем элементы по вертикали
                ) {
                    Column {
                        Text(
                            text = "Name Surname",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Group",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Text(
                        text = "Rating",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Attendance",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}