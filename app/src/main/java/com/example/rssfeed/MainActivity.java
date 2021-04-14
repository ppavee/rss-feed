package com.example.rssfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Interface views
    ListView lvItems;
    Button btnRefresh;

    // Other variables
    ArrayList<String> newsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = findViewById(R.id.lvItems);
        btnRefresh = findViewById(R.id.btnRefresh);

        newsItems = new ArrayList<String>();


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetFeedAsync().execute();
            }
        });
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            return null;
        }
    }

    public class GetFeedAsync extends AsyncTask<Void, Integer, String>
    {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Refresh");
            dialog.setMessage("Please wait. Updating the feed items.");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Exception exception = null;

            try
            {
                URL url = new URL("https://feeds.yle.fi/uutiset/v1/recent.rss?publisherIds=YLE_UUTISET");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;
                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equalsIgnoreCase("item"))
                        {
                           // insideItem = true;
                            NewsItem ni = new NewsItem();

                            while(true)
                            {
                                String name = xpp.getName();
                                if(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                                {
                                    break;
                                }
                                if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("title"))
                                {
                                    ni.setTitle(xpp.nextText());
                                }
                                else if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("description"))
                                {
                                    ni.setDescription(xpp.nextText());
                                }
                                else if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("link"))
                                {
                                    ni.setLink(xpp.nextText());
                                }
                                else if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase("pubdate"))
                                {
                                    ni.setDate(new Date(xpp.nextText()));
                                }

                                if(ni.hasAllProperties())
                                {
                                    newsItems.add(ni.getTitle());
                                    break;
                                }
                                
                                eventType = xpp.next();
                            }

                        }
                    }
                  //  else if(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                  //  {
                  //      insideItem = false;
                 //   }
                    eventType = xpp.next();
                }

            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }
            catch (Exception e)
            {
                exception = e;
            }


            return (exception != null) ? exception.getMessage() : null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, newsItems);
            lvItems.setAdapter(adapter);

            dialog.dismiss();
        }
    }
}