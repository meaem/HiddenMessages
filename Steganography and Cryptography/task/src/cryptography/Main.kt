package cryptography

import java.io.File
import javax.imageio.ImageIO

val choiceList = listOf("hide", "show", "exit")
val functionsList = listOf(::hide, ::show, ::exit)
fun printMenuAndGetChoice(): String {
    var choice = ""
    println("Task (hide, show, exit):")
    choice = readln()
    while (choice !in choiceList) {
        println("Wrong task: $choice")
        choice = readln()
    }
    return choice
}

fun hide() {
    println("Input image file:")
    val inFileName = readln()
    println("Output image file:")
    val outFileName = readln()

    try {
        val inFile = File(inFileName)
        val image = ImageIO.read(inFile)
        for (row in 0 until image.height) {
            for (col in 0 until image.width) {
                image.setRGB(col, row, image.getRGB(col, row) or 0x010101)
            }
        }
        val outFile = File(outFileName)
        ImageIO.write(image, "PNG", outFile)
        println(
            "Input Image: $inFileName\n" +
                    "Output Image: $outFileName\n" +
                    "Image $outFileName is saved."
        )
    } catch (ex: Exception) {
//        println(ex.message)
        println("Can't read input file!")
    }
}

fun show() {
    println("Obtaining message from image.")
}

fun exit() {
    println("Bye!")
}

fun mapChoiceToAFunction(choice: String): () -> Unit {
    return functionsList[choiceList.indexOf(choice)]
}

fun main() {

    var choice = printMenuAndGetChoice()
    while (true) {
        val action = mapChoiceToAFunction(choice)
        action()

        if (action == ::exit) {
            break
        }
        choice = printMenuAndGetChoice()
    }

}

