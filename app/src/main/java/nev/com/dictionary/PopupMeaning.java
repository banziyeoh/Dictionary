package nev.com.dictionary;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PopupMeaning {
    String search_word;
    String meaning_text;
    Context context;
    StringBuffer m;
    DictSearch dict;

    public PopupMeaning(String search_word,Context context){
        this.search_word = search_word;
        this.context = context;
        dict = new DictSearch();
    }
    public void show(){
        if(!dict.exists()){
            Toast.makeText(context, "Dictionary not found!", Toast.LENGTH_SHORT).show();
            return;

        }
        int width = context.getResources().getDisplayMetrics().widthPixels;
        LinearLayout layout = new LinearLayout(context);
        TextView attr = new TextView(context);
        TextView word = new TextView(context);
        TextView meaning = new TextView(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(40,0,40,0);
        attr.setTextSize(12);
        attr.setTypeface(null, Typeface.ITALIC);
        attr.setPadding(0,40,0,0);
        attr.setText("~ from Wiktionary, Creative Commons Attribution/Share-Alike License");

        word.setTextSize(17);
        word.setLayoutParams(params);
        word.setTypeface(null,Typeface.BOLD);
        word.setPadding(0,40,0,0);
        word.setText(search_word.toLowerCase(Locale.getDefault()));

        meaning.setTextSize(15);
        meaning.setSingleLine(false);
        meaning.setLayoutParams(params);
        meaning.setPadding(0,0,0,40);
        if(meaning_text.equals("")){
            meaning.setText("No definition found");

        }
        else{
            meaning.setText(dict.getAllGlosses(search_word));

        }
        meaning.setMovementMethod(new ScrollingMovementMethod());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(attr);
        layout.addView(word);
        layout.addView(meaning);

        Dialog d = new Dialog(context);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.addContentView(layout,new LinearLayout.LayoutParams(4 * width/5, ViewGroup.LayoutParams.WRAP_CONTENT));
        d.setCanceledOnTouchOutside(true);
        d.show();
    }

}