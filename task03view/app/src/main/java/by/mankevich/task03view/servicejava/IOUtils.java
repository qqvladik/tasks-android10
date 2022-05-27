package by.mankevich.task03view.servicejava;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class IOUtils {
    public static void closeQuietly(InputStream in){
        try{
            in.close();
        } catch (IOException e) {

        }
    }

    public static void closeQuietly(Reader reader){
        try{
            reader.close();
        } catch (IOException e) {

        }
    }
}
