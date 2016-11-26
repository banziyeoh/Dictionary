package nev.com.dictionary;

import android.content.*;

import nev.com.dictionary.Service.ClipboardService;

/**
 * Created by Yeoh on 11/26/2016.
 */

public class BootReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
            context.startService(new Intent(context, ClipboardService.class));
        }

    }
}
