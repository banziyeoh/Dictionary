package nev.com.dictionary;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class BrowserDisp {
    static int MAX_URL_LENGTH = 200;
    StringBuffer url;
    String search_word;
    Context context;
    Intent browserIntent;

    public BrowserDisp(String search_word,Context context){
        this.search_word=search_word;
        this.context = context;
    }
    public void show(){
        url = new StringBuffer(MAX_URL_LENGTH);
        url.append("http://dictionary.reference.com/browse/");
        url.append(search_word);

        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);


    }
}
