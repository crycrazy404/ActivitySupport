package parser

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.apache.batik.dom.GenericDOMImplementation
import org.apache.batik.svggen.SVGGeneratorContext
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.apache.poi.xslf.usermodel.XSLFSlide
import org.apache.poi.xslf.usermodel.XSLFTextShape
import java.awt.Dimension
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import javax.imageio.ImageIO

class Converter {
    fun slideScaledImage(slides: List<XSLFSlide>, targetWeight: Int, targetHeight: Int): List<ImageBitmap> {
        val domImpl = GenericDOMImplementation.getDOMImplementation()
        val svgNS = "http://www.w3.org/2000/svg"
        val document = domImpl.createDocument(svgNS, "svg", null)
        val svgGeneratorContext = SVGGeneratorContext.createDefault(document)
        svgGeneratorContext.isEmbeddedFontsOn = true  // Встраивание шрифтов

        val svgGraphics = SVGGraphics2D(svgGeneratorContext, false)
        val weight = 960
        val height = 540

        val scaleWeight = targetWeight.toDouble() / weight.toDouble()
        val scaleHeight = targetHeight.toDouble() / height.toDouble()

        svgGraphics.scale(scaleWeight, scaleHeight)
        svgGraphics.svgCanvasSize = Dimension(targetWeight, targetHeight)

        val imageList = mutableListOf<ImageBitmap>()
        val transcoder = PNGTranscoder()

        for (slide in slides) {
            val shapes = slide.shapes
            for(shape in shapes){
                when(shape){
                    is XSLFTextShape -> {
                        val currentFontSize =
                            shape.textParagraphs.flatMap { it.textRuns }.map { it.fontSize }.firstOrNull()
                                ?: 12.0
                        shape.textParagraphs.forEach { paragraph ->
                            paragraph.textRuns.forEach { run ->
                                run.fontSize =
                                    currentFontSize *  0.8
                                run.characterSpacing *= 0.8
                            }
                        }
                    }
                }
            }

            slide.draw(svgGraphics)

            val svgByteStream = ByteArrayOutputStream()
            val writer = OutputStreamWriter(svgByteStream, Charsets.UTF_8)
            svgGraphics.stream(writer, true)
            writer.flush()
            writer.close()

            val input = TranscoderInput(ByteArrayInputStream(svgByteStream.toByteArray()))
            val outputStream = ByteArrayOutputStream()
            val output = TranscoderOutput(outputStream)
            transcoder.transcode(input, output)
            outputStream.flush()

            val imageBitmap = ImageIO.read(ByteArrayInputStream(outputStream.toByteArray())).toComposeImageBitmap()
            imageList.add(imageBitmap)

            svgByteStream.close()
            outputStream.close()
        }

        return imageList
    }
}
