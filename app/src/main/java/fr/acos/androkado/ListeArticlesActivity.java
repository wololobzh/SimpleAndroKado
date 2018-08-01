package fr.acos.androkado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import fr.acos.androkado.adapters.ArticleAdapter;
import fr.acos.androkado.bo.Article;
import fr.acos.androkado.dao.ArticlesDAO;

public class ListeArticlesActivity extends AppCompatActivity
{
    private static final String TAG = "ACOS";
    //Objet représentant le recyclerView
    private RecyclerView mRecyclerView;
    //Objet représentant l"adapter remplissant le recyclerView
    private RecyclerView.Adapter mAdapter;
    //Objet permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;
    //Liste bouchon
    private ArrayList<Article> articles = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Charge le menu décrit du fichier action_bar_liste.xml
        getMenuInflater().inflate(R.menu.action_bar_liste,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.i("XXX","onOptionsItemSelected : " + item.getItemId());
        switch (item.getItemId())
        {
            case R.id.item_configuration :
                Intent intentConfig = new Intent(this,ConfigurationActivity.class);
                startActivity(intentConfig);
                break;
            case R.id.item_ajout :
                Intent intentAjout = new Intent(this,AjoutEditActivity.class);
                startActivity(intentAjout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Définition de l'action du clic sur un item.
     */
    private View.OnClickListener monClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            int position = Integer.parseInt(view.getTag().toString());
            Log.i(TAG,"POSITION : " + view.getTag());
            Intent intent = new Intent(ListeArticlesActivity.this,MainActivity.class);
            intent.putExtra("article",articles.get(position));
            startActivity(intent);
        }
    };

    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_articles);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        chargementDonnees();
        chargementRecycler();
    }

    /**
     * Permet de charger le recycler view.
     */
    private void chargementRecycler()
    {
        //Lie le recyclerView aux fonctionnalité offerte par le linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Liaison permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
        mAdapter = new ArticleAdapter(articles,monClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Permet de charger le bouchon.
     */
    private void chargementDonnees()
    {
        ArticlesDAO dao = new ArticlesDAO(this);
        SharedPreferences spIntra = getSharedPreferences("configuration",MODE_PRIVATE);
        Boolean valeurTri = spIntra.getBoolean(ConfigurationActivity.CLE_TRI,false);
        articles = dao.get(valeurTri);
    }
}
