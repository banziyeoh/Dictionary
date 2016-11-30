package nev.com.dictionary;


import android.content.Context;
import android.preference.ListPreference;
import android.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.ListAdapter;

import nev.com.dictionary.Adapter.ModeArrayAdapter;

public class DemoListPreference extends ListPreference{
    public DemoListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    protected  void onPrepareDialogBuilder(AlertDialog.Builder builder){
        ListAdapter listAdapter = new ModeArrayAdapter(getContext(),android.R.layout.select_dialog_singlechoice,getEntries());
        builder.setAdapter(listAdapter,this);
        super.onPrepareDialogBuilder(builder);
    }
}
