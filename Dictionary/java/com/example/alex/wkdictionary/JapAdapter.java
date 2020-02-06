package com.example.alex.wkdictionary;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class JapAdapter extends ArrayAdapter<Kanji>
{
    private int mColorResourceId;

    public JapAdapter(Activity context, ArrayList<Kanji> kanji, int colorResourceId)
    {
        super(context, 0, kanji);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.jap_list_item, parent, false);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        Kanji currentKanji = getItem(position);
        TextView onyomi = (TextView) listItemView.findViewById(R.id.onyomi);
        onyomi.setText(currentKanji.getOnyomi());
        TextView meaning = (TextView) listItemView.findViewById(R.id.meaning);
        meaning.setText(currentKanji.getMeaning());
        TextView kanji = (TextView) listItemView.findViewById(R.id.kanji);
        kanji.setText(currentKanji.getKanji());
        return listItemView;
    }
}
