package fr.acos.androkado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import fr.acos.androkado.bo.Article;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ACOS";
    Article article = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Charge le menu décrit du fichier action_bar_details.xml
        getMenuInflater().inflate(R.menu.action_bar_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_modifier:
                editerArticle();
                break;
            case R.id.item_envoyer:
                envoyerMessage();
                break;
        }
        return true;
    }

    private void editerArticle() {
        // Envoi à l'activité d'affichage du détail
        Intent intention = new Intent(this, AjoutEditActivity.class);
        intention.putExtra("fr.eni.android.androcado.ArticleSelectionne", article);
        startActivity(intention);
    }

    private void envoyerMessage()
    {
        Intent intention = new Intent(this,ListeContactActivity.class);
        intention.putExtra("fr.eni.android.androcado.ArticleSelectionne", article);
        startActivity(intention);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        article = getIntent().getParcelableExtra("article");

        Log.i("XXX","onCreate() : " + article.toString());

        TextView tvNom =  findViewById(R.id.tv_article);
        TextView tvDescription = findViewById(R.id.tv_description);
        TextView tvPrix = findViewById(R.id.tv_prix);
        RatingBar rating = findViewById(R.id.rating_article);
        ToggleButton toggle = findViewById(R.id.btn_achete);

        if(article!=null)
        {
            tvNom.setText(article.getNom());
            tvDescription.setText(article.getDescription());
            tvPrix.setText(String.valueOf(article.getPrix()));
            rating.setRating(article.getNote());
            toggle.setChecked(article.isAchete());
        }
    }

    public void onClickUrl(View view)
    {
        Log.i(TAG,"Lancement de l'activité InfoUrlActivity");
        Intent intent = new Intent(this, InfoUrlActivity.class);
        intent.putExtra("article",article);
        startActivity(intent);
    }

    public void onClickAchat(View view)
    {
        article.setAchete(!article.isAchete());
        Log.i(TAG,"Valeur achat " + article.isAchete());
    }
}
