package nev.com.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Yeoh on 11/26/2016.
 */

public class SettingsFragment extends PreferenceFragment{

    boolean isInstalled = false;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        PreferenceManager pm = getPreferenceManager();
        pm.setSharedPreferencesName("perfs");
        pm.setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.settings_list);

    }
}
