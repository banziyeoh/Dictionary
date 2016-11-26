package nev.com.dictionary;

import android.Manifest;
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
        CopyDict c = new CopyDict(this,getAssets());
        c.execute();
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();
        startService(new Intent(this,ClipboardService.class));
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

}
