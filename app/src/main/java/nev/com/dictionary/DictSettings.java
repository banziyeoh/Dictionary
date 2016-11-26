package nev.com.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import nev.com.dictionary.Service.ClipboardService;



public class DictSettings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CopyDict c = new CopyDict(this,getAssets());
        c.execute();
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SettingsFragment()).commit();
        startService(new Intent(this,ClipboardService.class));
    }
    @Override
    public void onPause(){
        super. onPause();

    }

}
