package nev.com.dictionary.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import nev.com.dictionary.BrowserDisp;
import nev.com.dictionary.LongmanDictionary.DictionaryEntry;
import nev.com.dictionary.LongmanDictionary.Remote.LongmanAPIHelper;
import nev.com.dictionary.LongmanDictionary.Settings;
import nev.com.dictionary.MainDictionary;
import nev.com.dictionary.PopupMeaning;
import nev.com.dictionary.R;
import nev.com.dictionary.ShutdownService;
import nev.com.dictionary.ToastDisp;
import nev.com.dictionary.WordsFragment;

public class ClipboardService extends Service{
    private final String tag = "[[ClipboardListenerService]] ";
    PopupMeaning p ;
    ToastDisp t ;
    BrowserDisp b;
    WordsFragment w;
    private ProgressDialog progressDialog;
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


        // Build notification
        // Actions are just fake
        Intent intents = new Intent(this, ShutdownService.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intents, 0);
        Notification noti = new Notification.Builder(this)
                .setTicker("Ticker Title")
                .setContentTitle("Dictionary Service Running")
                .setContentText("Click to disable")
                .setSmallIcon(R.drawable.ic_settings_applications_black_18dp)
                .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        noti.flags=Notification.FLAG_ONGOING_EVENT;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);

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
                        //testing
                        Bundle bundle = new Bundle();
                        bundle.putString("hi", word);
                        Intent i = new Intent(this, MainDictionary.class);
                        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);




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
    private void completeEntryLoad(DictionaryEntry entry) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (entry != null) {
            Toast.makeText(this, ""+entry.toString(), Toast.LENGTH_SHORT).show();

            Toast.makeText(this, "Formatting error in returned response. Please try again.", Toast.LENGTH_SHORT).show();

        }
    }

    private void showProgressDialog() {

        progressDialog = new ProgressDialog(this.getApplicationContext());
        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
        progressDialog.show(this, "", "Getting Random Word...", true);
    }


    /**
     * AsyncTask for retrieval of RandomWord.
     */
    class RetrieveDictionaryEntryTask extends AsyncTask<Void, Void, DictionaryEntry> {
        @Override
        protected DictionaryEntry doInBackground(Void... arg0) {
            try {
                return new LongmanAPIHelper().getRandomDictionaryEntry();
            } catch (Exception e) {
                Log.w(Settings.LOG_TAG, e.getClass().getSimpleName() + ", " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(DictionaryEntry entry) {
            completeEntryLoad(entry);
        }

    }

}








