package util.parser

import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFSlide
import java.io.FileInputStream
import java.io.IOException

class Builder : Aggregate {

    private var slides: List<XSLFSlide> = emptyList()

    fun loadPresentation(path: String) {
        try {
            FileInputStream(path).use { fis ->
                val pptx = XMLSlideShow(fis)
                slides = pptx.slides
            }
        } catch (e: IOException) {
            e.printStackTrace()
            slides = emptyList()
        }
    }

    override fun getIterator(): Iterator {
        return PPTXIterator()
    }

    private inner class PPTXIterator : Iterator {

        private var count = 0

        override fun getSlide(index: Int): XSLFSlide {
            return slides[(index % slides.size).let { if (it < 0) slides.size - 1 else it }]
        }

        override fun getSlides(): List<XSLFSlide> {
            return slides
        }

        override fun hasNext(): Boolean {
            return count + 1 < slides.size
        }

        override fun hasPrev(): Boolean {
            return count - 1 >= 0
        }

        override fun next(): Int {
            if (!hasNext()) {
                count = 0
                return count
            }
            return ++count
        }

        override fun prev(): Int {
            if (!hasPrev()) {
                count = slides.size - 1
                return count
            }
            return --count
        }

        override fun getCurrent(): Int {
            return count
        }

        override fun setCurrent(index: Int) {
            count = index.coerceIn(0, slides.size - 1)
        }

        override fun getSlideNotes(): String {
            val slideNotes = slides.getOrNull(count)?.notes
            var notes = ""
            slideNotes?.textParagraphs?.forEach { paragraphList ->
                paragraphList.forEach { paragraph ->
                    paragraph.textRuns.forEach { textRun ->
                        notes += textRun.rawText
                    }
                    notes += "\n"
                }
            }
            return notes
        }
    }
}
