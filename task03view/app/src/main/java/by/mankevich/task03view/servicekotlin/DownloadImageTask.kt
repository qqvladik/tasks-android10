package by.mankevich.task03view.servicekotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import by.mankevich.task03view.servicejava.IOUtils
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadImageTask(private val imageView: ImageView): AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg p0: String?): Bitmap? {
        val imageUrl = p0[0]
        var inputStream: InputStream? = null
        try {
            val url = URL(imageUrl)
            val httpConn = url.openConnection() as HttpURLConnection

            httpConn.allowUserInteraction=false
            httpConn.instanceFollowRedirects=true
            httpConn.requestMethod="GET"
            httpConn.connect()
            val resCode = httpConn.responseCode

            if (resCode==HttpURLConnection.HTTP_OK){
                inputStream = httpConn.inputStream
            }else{
                return null
            }
            return BitmapFactory.decodeStream(inputStream)
        }catch (e: IOException){
            Log.i("DownloadImageTask", "doInBackground: I/O exception")
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream)
            }
        }
        return null
    }

    // When the task is completed, this method will be called
    // Download complete. Lets update UI
    override fun onPostExecute(result: Bitmap?) {
        if (result != null) {
            imageView.setImageBitmap(result)
        } else {
            Toast.makeText(imageView.getContext(), "Failed to fetch data!", Toast.LENGTH_LONG)
                .show()
            Log.i("MyMessage", "Failed to fetch data!")
        }
    }
}