package by.mankevich.task03view;

import androidx.appcompat.app.AppCompatActivity;
import by.mankevich.task03view.servicejava.DownloadImageTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SubtaskActivityTwo extends AppCompatActivity {

    private Button mButtonUpload;
    private EditText mEditTextUri;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask2);

        mButtonUpload = (Button) findViewById(R.id.button_upload);
        mEditTextUri = (EditText) findViewById(R.id.edittext_uri);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mButtonUpload.setOnClickListener((view)->{
            ///скрываем клаву при нажатии на кнопку
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditTextUri.getWindowToken(), 0);

            String imageUrl = mEditTextUri.getText().toString();
            downloadAndShowImage(mImageView, imageUrl);
        });
    }

    private boolean checkInternetConnection() {
        // Get Connectivity Manager
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Details about the currently active default data network
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Network OK", Toast.LENGTH_SHORT).show();
        return true;
    }

    // When user click on the "Download Image".
    public void downloadAndShowImage(ImageView imageView, String imageUrl) {
        Log.d("SubtaskActivityTwo", "downloadAndShowImage: download image begin");
        boolean networkOK = this.checkInternetConnection();
        if (!networkOK) {
            return;
        }
        // Create a task to download and display image.
        DownloadImageTask task = new DownloadImageTask(imageView);
        // Execute task (Pass imageUrl).
        task.execute(imageUrl);
        Log.d("SubtaskActivityTwo", "downloadAndShowImage: download image END");
    }
}