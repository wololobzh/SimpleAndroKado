package fr.acos.androkado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fr.acos.androkado.bo.Article;

public class InfoUrlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_url);

        Intent intent = getIntent();
        Article article = intent.getParcelableExtra("article");

        TextView tvUrl = findViewById(R.id.tv_url);
        tvUrl.setText(article.getUrl());

    }
}
