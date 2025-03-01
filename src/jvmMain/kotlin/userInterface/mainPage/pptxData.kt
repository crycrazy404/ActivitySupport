package userInterface.mainPage

import androidx.compose.ui.graphics.ImageBitmap
import teleBot.AdminBot
import util.parser.Iterator

data class pptxData(
    var iter: Iterator? = null,
    var mainImagesBitmap: List<ImageBitmap> = emptyList(),
    var miniImageBitmap: List<ImageBitmap> = emptyList(),
    var slideNotes: String = "",
    var bot: AdminBot? = null,
    var currentIndex: Int = 0
)