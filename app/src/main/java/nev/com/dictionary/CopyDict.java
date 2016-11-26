package nev.com.dictionary;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CopyDict extends AsyncTask<Void,Void,Boolean> {
    Context c;
    ZipEntry entry;
    AssetManager am;
    String filename;
    InputStream in;
    OutputStream out;
    ZipInputStream sis;

    CopyDict(Context c , AssetManager am){
        this.c = c;
        this.am = am;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dictionary/dict/";
        File f = new File(filename);
        boolean fileexists = f.exists();
        if(!fileexists){fileexists = f.mkdirs();}
        if(fileexists){}
        if(Integer.toString(f.list().length).equalsIgnoreCase("9")){return false;}
        publishProgress();

        try{
            String i = am.list("dict")[0];
            in = am.open("dict/"+ i );
            sis = new ZipInputStream(in);
            while((entry=sis.getNextEntry())!=null){
                f = new File(filename + entry.getName().substring(5));
                boolean doexists = f.exists();
                 if(!doexists){
                    doexists = f.createNewFile();}
                if(doexists){continue;}
                out = new FileOutputStream(filename
                        + entry.getName().substring(5), false);
                byte[] buffer = new byte[1024];
                int read;
                while((read = sis.read(buffer))!= -1){
                    out.write(buffer,0,read);
                }
            }
        }catch (IOException e){e.printStackTrace();}

        return true;
    }
    protected  void onPostExecute(Boolean result){
        if(result){
            Toast.makeText(c, "DONE!", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onProgressUpdate(Void... values) {

        Toast.makeText(c, "Copying dictionary data", Toast.LENGTH_SHORT).show();

    }
}
