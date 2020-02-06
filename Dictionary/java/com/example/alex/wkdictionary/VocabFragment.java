package com.example.alex.wkdictionary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class VocabFragment extends Fragment
{
    public VocabFragment()
    {
    }
    static String vocabUrlString = "https://www.wanikani.com/api/user/1f0220ad606e1abb782de9b74d8a8337/vocabulary/50,51,52";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        super.onCreate(savedInstanceState);

        downloadAsyncTask task = new downloadAsyncTask();
        task.execute();

        ArrayList<Kanji> kanji = GetJson.extractVocab();
        JapAdapter adapter = new JapAdapter(getActivity(), kanji, R.color.category_vocab);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }

    private class downloadAsyncTask extends AsyncTask<URL, Void, String>
    {
        @Override
        protected String doInBackground(URL... urls)
        {
            URL url = createUrl(vocabUrlString);
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e)
            {

            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonResponse)
        {
            GetJson.setJsonResponseVocab(jsonResponse);
            smthng();

        }

        public void smthng ()
        {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.detach(VocabFragment.this).attach(VocabFragment.this).commit();
        }

        private URL createUrl(String stringUrl)
        {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException
        {
            String jsonResponse = "";
            if (url == null)
            {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try
            {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200)
                {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else
                {
                    Log.wtf(LOG_TAG, "Server responded with error: " + urlConnection.getResponseCode());
                }
            } catch (IOException e)
            {
                Log.e(LOG_TAG, "Didn't recived supposed JSON results");

            } finally
            {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
                if (inputStream != null)
                {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException
        {
            StringBuilder output = new StringBuilder();
            if (inputStream != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null)
                {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
    }
}
