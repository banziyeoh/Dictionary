package nev.com.dictionary.Service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import nev.com.dictionary.BrowserDisp;
import nev.com.dictionary.PopupMeaning;
import nev.com.dictionary.ToastDisp;

public class ClipboardService extends Service{
    private final String tag = "[[ClipboardListenerService]] ";
    PopupMeaning p ;
    ToastDisp t ;
    BrowserDisp b;
    private ClipboardManager.OnPrimaryClipChangedListener listener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            performClipboardCheck();

        }
    };
    @Override
    public void onCreate(){
        ((ClipboardManager)getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startid){

        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void performClipboardCheck(){
        ClipboardManager cm = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        if(cm.hasPrimaryClip()){
            ClipData cd = cm.getPrimaryClip();
            if(cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                String word = cd.getItemAt(0).getText().toString();
                SharedPreferences sp = this.getSharedPreferences("perfs", Context.MODE_WORLD_READABLE);
                int text = Integer.parseInt(sp.getString(
                        "displayModeVal", "0"));
                switch (text) {
                    case 1:
                        b= new BrowserDisp(word,getApplicationContext());
                        b.show();
                        break;
                    case 2:
                        p = new PopupMeaning(word,getApplicationContext());
                        p.show();
                        break;
                    default:
                        t = new ToastDisp(word, getApplicationContext());
                        t.show();
                        break;
                }







                    }}





            }
    public void onDestroy(){
        Toast.makeText(this, "Service Stopped!", Toast.LENGTH_SHORT).show();
    }
        }







