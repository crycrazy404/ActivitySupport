
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import parser.Builder
import table.DBManager
import teleBot.ManagerBot
import view.mainPage.MainPageBuilder

@Composable
@Preview

fun App() {
    DBManager().initDatabase("h2")
    DBManager().createTables()
    val iter = Builder("C:\\Users\\OMEN 16\\Desktop\\diplom\\Activity\\src\\jvmMain\\kotlin\\example.pptx").getIterator()
    val viewMain = MainPageBuilder()
    println(ManagerBot().managerBot())
    viewMain.init(iter)

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
