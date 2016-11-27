package nev.com.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import nev.com.dictionary.Service.ClipboardService;

public class ShutdownService extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopService(new Intent(ShutdownService.this, ClipboardService.class));
    }
}
