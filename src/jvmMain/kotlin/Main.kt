
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import models.DBManager
import parser.Builder
import teleBot.ManagerBot
import view.mainPage.MainPageBuilder

@Composable
@Preview

fun app() {
    val dbManager = DBManager("sqlight")
    dbManager.createTables()
    val iter = Builder("C:\\Users\\OMEN 16\\Desktop\\diplom\\Activity\\src\\jvmMain\\kotlin\\example.pptx").getIterator()
    val viewMain = MainPageBuilder()
    ManagerBot().managerBot()
    viewMain.init(iter)
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        app()
    }
}
