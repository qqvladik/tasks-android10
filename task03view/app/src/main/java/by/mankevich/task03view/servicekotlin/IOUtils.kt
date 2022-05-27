package by.mankevich.task03view.servicekotlin

import java.io.IOException
import java.io.InputStream
import java.io.Reader

class IOUtils {
    fun closeQuietly(inputStream: InputStream) {
        try {
            inputStream.close()
        } catch (e: IOException) {
        }
    }

    fun closeQuietly(reader: Reader) {
        try {
            reader.close()
        } catch (e: IOException) {
        }
    }
}