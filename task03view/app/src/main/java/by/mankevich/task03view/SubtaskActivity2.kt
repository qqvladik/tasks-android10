package by.mankevich.task03view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.mankevich.task03view.servicejava.DownloadImageTask


class SubtaskActivity2 : AppCompatActivity() {

    private lateinit var mButtonUpload: Button
    private lateinit var mEditTextUri: EditText
    private lateinit var mImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtask2)

        mButtonUpload = findViewById(R.id.button_upload)
        mEditTextUri = findViewById(R.id.edittext_uri)
        mImageView = findViewById(R.id.imageView)

        mButtonUpload.setOnClickListener{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mEditTextUri.windowToken, 0)

            val imageUrl = mEditTextUri.text.toString()
            downloadAndShowImage(mImageView, imageUrl)
        }
    }

    private fun checkInternetConnection(): Boolean {
        // Get Connectivity Manager
        val connManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        // Details about the currently active default data network
        val networkInfo = connManager.activeNetworkInfo
        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show()
            return false
        }
        if (!networkInfo.isConnected) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show()
            return false
        }
        if (!networkInfo.isAvailable) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show()
            return false
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_SHORT).show()
        return true
    }

    // When user click on the "Download Image".
    fun downloadAndShowImage(imageView: ImageView?, imageUrl: String?) {
        Log.d("SubtaskActivityTwo", "downloadAndShowImage: download image begin")
        val networkOK = checkInternetConnection()
        if (!networkOK) {
            return
        }
        // Create a task to download and display image.
        val task = DownloadImageTask(imageView)
        // Execute task (Pass imageUrl).
        task.execute(imageUrl)
        Log.d("SubtaskActivityTwo", "downloadAndShowImage: download image END")
    }
}