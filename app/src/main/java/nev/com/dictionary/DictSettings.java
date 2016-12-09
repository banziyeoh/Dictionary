package nev.com.dictionary;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import nev.com.dictionary.Service.ClipboardService;



public class DictSettings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();

        CopyDict c = new CopyDict(this,getAssets());
        c.execute();
        startService(new Intent(this,ClipboardService.class));
        Intent intents = new Intent(this, ShutdownService.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_CANCEL_CURRENT);
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





        ActivityCompat.requestPermissions(DictSettings.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CopyDict c = new CopyDict(this,getAssets());
                    c.execute();
                    startService(new Intent(this,ClipboardService.class));
                    Intent intents = new Intent(this, ShutdownService.class);
                    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_CANCEL_CURRENT);
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

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(DictSettings.this, "Permission denied to write your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onPause(){
        super. onPause();

    }
    @Override
    public void onStop(){
        super.onStop();
        finish();
    }

}
