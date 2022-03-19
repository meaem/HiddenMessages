package cryptography

import java.io.File
import javax.imageio.ImageIO
import kotlin.experimental.xor

val choiceList = listOf("hide", "show", "exit")
val functionsList = listOf(::hide, ::show, ::exit)
val stopMarker = "000000000000000000000011"

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

fun encryptMsg(msg: String, password: String): String {
    val p = password.encodeToByteArray()
    val l = p.size
    return msg.encodeToByteArray().mapIndexed { index, byte -> (byte xor p[index % l]).bitsString() }.joinToString("")
}

fun hide() {
    println("Input image file:")
    val inFileName = readln()
    println("Output image file:")
    val outFileName = readln()

    println("Message to hide:")
    val message = readln()
    println("password:")
    val password = readln()
    val messageInBits = encryptMsg(message, password) + stopMarker
//    println(messageInBits)
    try {
        val inFile = File(inFileName)
        val image = ImageIO.read(inFile)
        var idx = 0
//        println(messageInBits)
        if (image.width * image.height / 3 >= messageInBits.length) {
            outer@ for (row in 0 until image.height) {
                for (col in 0 until image.width) {
//
//                idx++
//                println(image.getRGB(col, row).bitsString())
//                val clr = Color(image.getRGB(col, row))
//                println(messageInBits[idx])
                    image.setRGB(
                        col,
                        row,
                        image.getRGB(col, row).toUInt().setBit24(messageInBits[idx++] == '1').toInt()
                    )
//                val clr2 = Color(image.getRGB(col, row))
//                println("${clr.red} ${clr.green} ${clr.blue}--> ${clr2.red} ${clr2.green} ${clr2.blue}")
//                image.setRGB(col, row, Color(0,0,0).rgb)
//                 image.setRGB(col, row, image.getRGB(col, row).toUInt().setBit24(idx%2==1).toInt())

//                println(image.getRGB(col, row).bitsString())

                    if (idx == messageInBits.length)
                        break@outer
                }
            }
            val outFile = File(outFileName)
            ImageIO.write(image, "PNG", outFile)
            println("Message saved in $inFileName image.")
        } else {
            println("The input image is not large enough to hold this message")
        }
    } catch (ex: Exception) {

        println("Can't read input file!")
    }
}

fun decryptMsg(cipher: ByteArray, password: String): String {
    val p = password.encodeToByteArray()
    val l = p.size
    return cipher.mapIndexed { index, byte -> (byte xor p[index % l]) }
        .toByteArray().toString(Charsets.UTF_8)
}

fun show() {
    println("Input image file:")
    val inFileName = readln()
    println("password:")
    val password = readln()
    var message = ""
    try {
        val inFile = File(inFileName)
        val image = ImageIO.read(inFile)
        outer@ for (row in 0 until image.height) {
            for (col in 0 until image.width) {
                message += (image.getRGB(col, row) and 0x01)
                if (message.endsWith(stopMarker)) {

                    break@outer
                }
            }
        }
        val msgBytes = mutableListOf<Byte>()
        for (i in 0..message.length - 24 step 8) {
            msgBytes.add(message.substring(i, i + 8).toInt(2).toByte())
        }

        println("Message:")
        println(decryptMsg(msgBytes.toByteArray(), password ) )
//        println(message)

    } catch (ex: Exception) {
        println("Can't read input file!")
    }
}

fun exit() {
    println("Bye!")
}

fun mapChoiceToAFunction(choice: String): () -> Unit {
    return functionsList[choiceList.indexOf(choice)]
}

fun Byte.bitsString(): String {
    val list = List(8) {
        this.toInt().shr(it).and(0x01)
    }
    return list.map { it.toString() }.joinToString("").reversed()
}

fun Int.bitsString(): String {
    val list = List(32) {
        this.shr(it).and(0x01)
    }
    return list.map { it.toString() }.joinToString("")//.reversed()
}

fun UInt.bitsString(): String {
    val list = List(32) {
        this.shr(it).and(0x01u)
    }
    return list.map { it.toString() }.joinToString("")//.reversed()
}

fun UInt.setBit24(bitValue: Boolean): UInt {

    return if (bitValue) this or 0x00000001u else this and 0xFFFFFFFEu
}

fun main() {
    val b = 6u
    println(b.bitsString())
//    val c = 0xFFFFFEFFu //"0"[0].digitToInt().shl(8).toUInt()
//    println(c.bitsString())
//    val x = b and c
//    println(x.bitsString())
//    println("1:"+b.setBit8(true).bitsString())
//    println("0:"+b.setBit8(false).bitsString())
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

