
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import db.models.DBManager
import teleBot.ManagerBot
import userInterface.mainPage.MainPageBuilder
import util.parser.Builder

@Composable
@Preview

fun app() {
    DBManager("sqlight").createTables()
    val builder = Builder()
    val bot = ManagerBot(builder)
    bot.managerBot()

    MainPageBuilder(Builder(), bot.getBot()).init()
}

fun main() = application {
    Window(onCloseRequest = {
        println("Завершение программы...")
        exitApplication()
    }) {
        app()
    }
}
