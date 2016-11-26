package nev.com.dictionary;

import android.content.Context;
import android.content.Intent;

import nev.com.dictionary.Service.ClipboardService;


public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        context.startService(new Intent(context, ClipboardService.class));

    }
}
