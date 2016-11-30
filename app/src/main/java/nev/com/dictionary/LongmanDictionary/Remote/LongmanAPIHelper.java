package nev.com.dictionary.LongmanDictionary.Remote;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nev.com.dictionary.LongmanDictionary.DictionaryEntry;
import nev.com.dictionary.LongmanDictionary.Settings;
import nev.com.dictionary.LongmanDictionary.Word;

public class LongmanAPIHelper {
    private static final String URL_PREFIX="https://api.pearson.com/longman/dictionary/entry/random.json?apikey=";
    private final String LOG_TAG = LongmanAPIHelper.class.getSimpleName();

    //testing
    private final String COUNT = "count";
    private final String RESULTS = "count";
    private final String HEADWORD= "headword";
    private final String ONLINE_ID ="id";
    private final String DEFINITION="definition";
    private final String POS = "part_of_speech";
    private final String TRANSCRIPTION="ipa";
    private final String EXAMPLE="examples";
    private final String SENSES = "senses";
    private final String PRONUNCIATIONS="pronunciations";
    private final String EXAMPLE_TEXT = "text";



    private static final String KEY_ENTRY = "Entry";
    private static final String KEY_HEAD = "Head";
    private static final String KEY_WORD = "HWD";
    private static final String KEY_TEXT = "#text";
    private static final String KEY_SENSE = "Sense";

    private static final String KEY_DEFINITION = "DEF";

    public String getRandomJSON()throws Exception{
        HTTPSCall call = new HTTPSCall(URL_PREFIX + Settings.API_KEY);
        String s = call.doRemoteCall();
        return s;
        }
    public Word[] getWordsFromJSON(String wordsJson)throws JSONException{
        JSONObject receivedObject = new JSONObject(wordsJson);
        int receivedCount = receivedObject.getInt(COUNT);
        JSONArray resultsArray = receivedObject.getJSONArray(RESULTS);
        Word[]words = new Word[receivedCount];
        for(int i=0 ; i<resultsArray.length();i++){
            JSONObject result = resultsArray.getJSONObject(i);
            JSONArray senses = result.getJSONArray(SENSES);
            JSONObject sens0 = senses.getJSONObject(0);

            String headWord = result.getString(HEADWORD);
            String onlineId=result.getString(ONLINE_ID);
            String defenition0 = sens0.getJSONArray(DEFINITION).getString(0);
            String partOfSpeech = "";

            if(result.has(POS)){
                partOfSpeech = result.getString(POS);
                words[i]=new Word(onlineId,headWord,defenition0,partOfSpeech);
                if(sens0.has(EXAMPLE)){
                    JSONObject example0 = sens0.getJSONArray(EXAMPLE).getJSONObject(0);
                    String example0Text = example0.getString(EXAMPLE_TEXT);
                    words[i].setExamples(example0Text);
                }
                if(result.has(PRONUNCIATIONS)){
                    JSONObject pronounciation0=result.getJSONArray(PRONUNCIATIONS).getJSONObject(0);
                    String transcription = pronounciation0.getString(TRANSCRIPTION);
                    words[i].setTranscription(transcription);
                }
            }
        }
        return words;

    }

    public DictionaryEntry getRandomDictionaryEntry()throws Exception{
        String url = URL_PREFIX +Settings.API_KEY;
        HTTPSCall call=new HTTPSCall(url);
        Log.i(Settings.LOG_TAG,url);
        return deserializeDictionaryEntry(call.doRemoteCall());
    }
    public DictionaryEntry deserializeDictionaryEntry(String entryJSONAsString)throws Exception{
        Log.i(Settings.LOG_TAG,entryJSONAsString);
        DictionaryEntry entry;
        try{
            JSONObject entryJSON =new JSONObject(entryJSONAsString);
            entry=deserializeEntry(entryJSON);
        }catch (JSONException  e){throw new RuntimeException(e);}
        return entry;
    }


    private DictionaryEntry deserializeEntry(JSONObject entryJSON)throws Exception{
        return new DictionaryEntry(entryJSON.getJSONObject(KEY_ENTRY).
                getJSONObject(KEY_HEAD).getJSONObject(KEY_WORD).getString(KEY_TEXT),

                entryJSON.getJSONObject(KEY_ENTRY).getJSONObject(KEY_SENSE).
                        getJSONObject(KEY_DEFINITION).getString(KEY_TEXT));
    }


    }



