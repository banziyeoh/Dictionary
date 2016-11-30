package nev.com.dictionary.LongmanDictionary;


public class DictionaryEntry {
    private final String word;
    private  final String defenition;

    public DictionaryEntry(String word,String defenition){
        this.word = word;
        this.defenition = defenition;
    }
    public String getWord(){return word;}

    public String getDefenition(){return  defenition;}

    public String toString(){return word+": "+defenition;}

}

