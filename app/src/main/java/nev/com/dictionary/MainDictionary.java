package nev.com.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainDictionary extends ActionBarActivity implements WordsFragment.Callback{
    public boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_main);
        if(findViewById(R.id.detail_container)!=null){
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailDialogFragment()).commit();
            }
        } else {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String onlineId, String headword) {
        if(mTwoPane == true){
            Bundle args = new Bundle();
            args.putString(DetailDialog.HEADWORD_KEY, headword);
            args.putString(DetailDialog.ONLINE_ID_KEY, onlineId);
            DetailDialogFragment fragment = new DetailDialogFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment).commit();

        } else if(mTwoPane == false) {
            Intent wordToDetails = new Intent(this, DetailDialog.class);
            wordToDetails.putExtra(DetailDialog.HEADWORD_KEY, headword)
                    .putExtra(DetailDialog.ONLINE_ID_KEY, onlineId);
            startActivity(wordToDetails);
        }
    }
}
