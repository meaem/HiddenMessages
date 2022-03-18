import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun drawLines(): BufferedImage {
    return BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB).also {
        val g = it.createGraphics()
        g.color = Color.red
        g.drawLine(0, 0, 200, 200)
    }.also {
        val g = it.createGraphics()
        g.color = Color.green
        g.drawLine(200, 0, 0, 200)
    }
}

fun drawCircles(): BufferedImage {
    val poistions = listOf(50 to 50, 50 to 75, 75 to 50, 75 to 75)
    val colors = listOf(Color.red, Color.yellow, Color.green, Color.blue)
    return BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB).also { image ->
        val g = image.createGraphics()
        repeat(4) {
            g.color = colors[it]
            g.drawOval(poistions[it].first, poistions[it].second, 100, 100)
        }
    }
}

fun drawSquare(): BufferedImage {
    return BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB).also {
        val g = it.createGraphics()
        g.color = Color.red
        g.drawRect(100, 100, 300, 300)
    }
}

fun drawStrings(): BufferedImage {
    val poistions = listOf(50 to 50, 51 to 51, 52 to 52)
    val colors = listOf(Color.red, Color.green, Color.blue)
    return BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB).also { image ->
        val g = image.createGraphics()
        repeat(3) {
            g.color = colors[it]
            g.drawString("Hello, images!", poistions[it].first, poistions[it].second)
        }
    }
}


fun main() {
//    val img = drawStrings()
//    ImageIO.write(img, "png", File("stringsImage.png"))
    println(5 or 5 +4 and  4)
}