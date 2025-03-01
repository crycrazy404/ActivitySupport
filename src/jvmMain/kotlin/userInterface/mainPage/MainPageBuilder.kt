package userInterface.mainPage

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import db.dto.questions.QuestionOutputDto
import db.dto.reactions.ReactionDTO
import teleBot.AdminBot
import userInterface.charts.ReactionsMainTab
import util.parser.Builder
import util.parser.Converter
import util.parser.Iterator
import java.io.File
import java.io.FileNotFoundException
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class MainPageBuilder() {
    private var selectedTabIndex by mutableStateOf(0)
    private var isPptxSelected by mutableStateOf(false)
    private lateinit var builder: Builder;
    private lateinit var bot: AdminBot;

    constructor(builder: Builder, bot: AdminBot) : this() {
        this.builder = builder;
        this.bot = bot;
    }


    @Composable
    fun init() {
        val mainPicture = remember { mutableStateOf(emptyList<ImageBitmap>()) }
        val miniPicture = remember { mutableStateOf(emptyList<ImageBitmap>()) }
        val questions = remember { mutableStateOf(emptyList<QuestionOutputDto>()) }
        val iter = remember { mutableStateOf( builder.getIterator()) }
        val reactions = remember { mutableStateOf(emptyList<ReactionDTO>()) }

        val tabs = listOf("Главная", "Дополнительно", "Настройки")
        var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

        Scaffold(
            topBar = {
                Column {
                    TopAppBar(elevation = 4.dp) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { selectedTabIndex = index },
                                    text = { Text(title) }
                                )
                            }
                        }
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sizeIn(maxHeight = 900.dp)
                        .defaultMinSize(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        when (selectedTabIndex) {
                            0 -> mainContent(mainPicture, miniPicture, iter, questions)
                            1 -> additionalContent(reactions, miniPicture.value)
                            2 -> settingsContent()
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun mainContent(
        mainPicture: MutableState<List<ImageBitmap>>,
        miniPicture: MutableState<List<ImageBitmap>>,
        iter: MutableState<Iterator>,
        questions: MutableState<List<QuestionOutputDto>>,
    ) {


        if (iter.value.getSlides().isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {
                        selectFile { file ->
                            val path = file.absolutePath
                            try {
                                iter.value = getIterator(path)
                                val slides = iter.value.getSlides()
                                if (slides.isNotEmpty()) {
                                    mainPicture.value = Converter().slideScaledImage(slides, 1920, 1080)
                                    miniPicture.value = Converter().slideScaledImage(slides, 640, 360)
                                    bot.setIterator(iter.value)
                                } else {
                                    println("Ошибка: Слайды не загружены")
                                }
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            }
                        }
                    }) {
                        Text("Выбрать файл PPTX")
                    }
                }
            }
        } else {
            if (mainPicture.value.isNotEmpty() && miniPicture.value.isNotEmpty()) {
                PptxView().init(bot, iter.value, mainPicture.value, miniPicture.value, questions)
            } else {
                println("Ошибка: Изображения слайдов не загружены")
            }
        }
    }
    private fun selectFile(onFileSelected: (File) -> Unit) {
        val fileChooser = JFileChooser().apply {
            fileFilter = FileNameExtensionFilter("PPTX файлы", "pptx")
            dialogTitle = "Выберите файл PPTX"
        }
        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            onFileSelected(fileChooser.selectedFile)
        }
    }

    private fun getIterator(path: String): Iterator {
        builder.loadPresentation(path)
        return builder.getIterator()
    }

    @Composable
    fun additionalContent(reactions: MutableState<List<ReactionDTO>>, miniPicture: List<ImageBitmap>) {
        ReactionsMainTab().init(reactions, miniPicture)
    }

    @Composable
    fun settingsContent() {
        Text("Настройки")
    }
}
