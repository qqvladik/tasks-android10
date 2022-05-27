package by.mankevich.task03view.servicejava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private final ImageView imageView;

    public DownloadImageTask(ImageView imageView)  {
        this.imageView= imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String imageUrl = strings[0];

        InputStream in = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            } else {
                return null;
            }

            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.i("DownloadImageTask", "doInBackground: I/O exception");
        } finally {
            if(in!=null) {
                IOUtils.closeQuietly(in);
            }
        }
        return null;
    }

    // When the task is completed, this method will be called
    // Download complete. Lets update UI
    @Override
    protected void onPostExecute(Bitmap result) {
        if(result  != null){
            imageView.setImageBitmap(result);
        } else{
            Toast.makeText(imageView.getContext(), "Failed to fetch data!", Toast.LENGTH_LONG).show();
            Log.i("MyMessage", "Failed to fetch data!");
        }
    }
}
