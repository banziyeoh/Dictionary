package nev.com.dictionary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import nev.com.dictionary.Service.ClipboardService;

/**
 * Created by Yeoh on 11/26/2016.
 */

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Service Stops","OHHHHH");
        context.startService(new Intent(context, ClipboardService.class));

    }
}
