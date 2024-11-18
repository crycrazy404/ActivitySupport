package parser

import org.apache.poi.xslf.usermodel.XSLFSlide

interface Iterator {
    fun getSlide(index: Int): XSLFSlide
    fun getSlides(): List<XSLFSlide>
    fun hasNext(): Boolean
    fun hasPrev(): Boolean
    fun next(): Int
    fun prev(): Int
    fun getCurrent(): Int
    fun getSlideNotes(): String
}