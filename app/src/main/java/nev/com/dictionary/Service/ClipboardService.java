package nev.com.dictionary.Service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
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
        Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Service Running", Toast.LENGTH_SHORT).show();

                        String text = cd.getItemAt(0).getText().toString();
                t = new ToastDisp(text, getApplicationContext());

                t.show();

                    }}





            }
    public void onDestroy(){
        Toast.makeText(this, "Service Stopped!", Toast.LENGTH_SHORT).show();
    }
        }







