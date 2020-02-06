package com.example.alex.wkdictionary;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetJson {

    private GetJson()
    {
    }

    static String radical_example_response = "{\"requested_information\":[{\"character\":\" \",\"meaning\":\"loading \",\"kana\":\" \",\"onyomi\":\"\",\"image\":\" \",\"level\":13}]}";
    static String kanji_example_response = "{\"requested_information\":[{\"character\":\" \",\"meaning\":\"loading \",\"kana\":\" \",\"onyomi\":\"\",\"image\":\" \",\"level\":13}]}";
    static String vocab_example_response = "{\"requested_information\":[{\"character\":\" \",\"meaning\":\"loading \",\"kana\":\" \",\"onyomi\":\"\",\"image\":\" \",\"level\":13}]}";

    public static void setJsonResponseRadical (String response) {radical_example_response = response;}
    public static void setJsonResponseKanji (String response) {kanji_example_response = response;}
    public static void setJsonResponseVocab (String response) {vocab_example_response = response;}

    public static ArrayList<Kanji> extractKanji()
    {
        ArrayList<Kanji> kanji = new ArrayList<>();
        try
        {
            JSONObject baseJsonResponse = new JSONObject(kanji_example_response);
            JSONArray kanjiArray = baseJsonResponse.getJSONArray("requested_information");
            for (int i = 0; i < kanjiArray.length(); i++)
            {
                JSONObject currentKanji = kanjiArray.getJSONObject(i);
                String kanjiJson = currentKanji.getString("character");
                String onyomiJson = currentKanji.getString("onyomi");
                String meaningJson = currentKanji.getString("meaning");
                if (onyomiJson != "None")
                {
                    Kanji newKanji = new Kanji(onyomiJson, meaningJson, kanjiJson);
                    kanji.add(newKanji);
                } else
                {

                }
            }
        } catch (JSONException e)
        {
            Log.e("GetJson", "JSON parsing was not successful", e);
        }
        return kanji;
    }

    public static ArrayList<Kanji> extractRadical()
    {
        ArrayList<Kanji> radical = new ArrayList<>();

        try
        {
            JSONObject baseJsonResponse = new JSONObject(radical_example_response);
            JSONArray radicalArray = baseJsonResponse.getJSONArray("requested_information");
            for (int i = 0; i < radicalArray.length(); i++)
            {
                JSONObject currentRadical = radicalArray.getJSONObject(i);
                String radicalJson = currentRadical.getString("character");
                String onyomiJson = "";
                String meaningJson = currentRadical.getString("meaning");
                if (radicalJson != "null")
                {
                    Kanji newKanji = new Kanji(onyomiJson, meaningJson, radicalJson);
                    radical.add(newKanji);
                } else
                {

                }
            }
        } catch (JSONException e)
        {
            Log.e("GetJson", "JSON parsing was not successful", e);
        }
        return radical;
    }

    public static ArrayList<Kanji> extractVocab()
    {
        ArrayList<Kanji> kanji = new ArrayList<>();

        try
        {
            JSONObject baseJsonResponse = new JSONObject(vocab_example_response);
            JSONArray kanjiArray = baseJsonResponse.getJSONArray("requested_information");
            for (int i = 0; i < kanjiArray.length(); i++)
            {
                JSONObject currentKanji = kanjiArray.getJSONObject(i);
                String vocabJson = currentKanji.getString("character");
                String kanaJson = currentKanji.getString("kana");
                String meaningJson = currentKanji.getString("meaning");
                Kanji newKanji = new Kanji(kanaJson, meaningJson, vocabJson);
                kanji.add(newKanji);
            }
        } catch (JSONException e)
        {
            Log.e("GetJson", "JSON parsing was not successful", e);
        }
        return kanji;
    }
}
