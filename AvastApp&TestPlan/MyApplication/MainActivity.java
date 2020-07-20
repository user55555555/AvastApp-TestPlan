package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private Button getBtn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://blog.avast.com").get();
                    String title = doc.title();
                    Elements links = doc.select("h3");

                    builder.append(title).append("\n");

                    int i = 0;
                    for (Element link : links) {
                        if(i == 5)
                            break;
                        builder.append("\n").append("Title ").append(i + 1).append(": ").append(link.text()).append("\n");
                        i++;
                    }

                    Elements links2 = doc.select("div.article-item > a");
                    i = 0;
                    for (Element link : links2) {
                        if(i == 6)
                            break;
                        if(i!=0)
                            builder.append("\n").append("URL ").append(i).append(": ").append(link.attr("href")).append("\n");
                        i++;
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}