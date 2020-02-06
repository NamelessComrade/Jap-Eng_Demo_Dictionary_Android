package com.example.alex.wkdictionary;

public class Kanji {
    private String mOnyomi;
    private String mMeaning;
    private String mKanji;


    public Kanji(String onyomi, String meaning, String kanji) {
        mOnyomi = onyomi;
        mMeaning = meaning;
        mKanji = kanji;
    }


    public String getOnyomi() {
        return mOnyomi;
    }
    public String getMeaning () { return mMeaning; }
    public String getKanji () { return mKanji;}
}



