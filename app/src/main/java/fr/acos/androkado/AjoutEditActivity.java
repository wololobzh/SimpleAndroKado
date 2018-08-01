package fr.acos.androkado;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import fr.acos.androkado.bo.Article;
import fr.acos.androkado.dao.ArticlesDAO;

public class AjoutEditActivity extends AppCompatActivity {

    private final static String TAG = AjoutEditActivity.class.getName();

    private Article articleModifie;
    private ArticlesDAO dao = null;
    TextView prix = null;
    EditText nom =null;
    EditText desc =null;
    RatingBar note =null;
    TextView url =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_edit);

        prix = (EditText) findViewById(R.id.edit_prix);
        nom = (EditText) findViewById(R.id.edit_nom);
        desc = (EditText) findViewById(R.id.edit_description);
        note = (RatingBar) findViewById(R.id.edit_envie);
        url = (TextView) findViewById(R.id.edit_url);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Initialisation du DAO
        dao = new ArticlesDAO(this);
        // Récupérer l'intention qui a déclenché cette activité
        Intent intention = getIntent();
        Article a = intention.getParcelableExtra("fr.eni.android.androcado.ArticleSelectionne");
        // Traitement de l'article (potentiellement) reçu : affichage
        if (a != null)
        {
            setDetailArticle(a);
        }
        else
        {
            SharedPreferences spIntra = getSharedPreferences("configuration",MODE_PRIVATE);
            String valeurPrixDefaut = spIntra.getString(ConfigurationActivity.CLE_PRIX_DEFAUT,"");
            prix.setText(valeurPrixDefaut);
        }
    }

    private void validerModification() {

        Log.i("XXX","Entrée dans validerModification()");
        Log.i("XXX","articleModifie : " + articleModifie);
        // Mettre à jour le modèle
        if(articleModifie == null || articleModifie.getId() == 0)
        {
            articleModifie = new Article();
            Log.i("XXX","validerModification() : insertion : " + articleModifie.toString());
            doModifications();
            dao.insert(articleModifie);
            this.finish();
        }
        else
        {
            Log.i("XXX","validerModification() : modification : " + articleModifie.toString());
            doModifications();
            dao.update(articleModifie);
            // Retour au détail de l'article (en consultation seulement)
            goDetailArticle();
        }
    }

    private void goDetailArticle() {
        // Envoi à l'activité d'affichage du détail
        Intent intention = new Intent(AjoutEditActivity.this, ListeArticlesActivity.class);
        intention.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // On n'empile pas de nouvelle instance de DetailArticleActivity, on réutilise la même
        intention.putExtra("fr.eni.android.androcado.ArticleSelectionne", articleModifie);
        startActivity(intention);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_detail_barre_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Actions de la barre d'action
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save:
                validerModification();
                return true;
        }
        return true;
    }

    /**
     * Mettre à jour le modele à partir de la vue
     */
    protected void doModifications()
    {
        articleModifie.setNom(nom.getText().toString());
        articleModifie.setDescription(desc.getText().toString());
        articleModifie.setPrix(Float.parseFloat(prix.getText().toString()));
        articleModifie.setNote(note.getRating() + 1);
        articleModifie.setUrl(url.toString());
    }
    /**
     * Mettre à jour la vue avec les valeurs de l'article a
     *
     * @param article Article à afficher
     */
    protected void setDetailArticle(Article article)
    {
        articleModifie = article;
        nom.setText(article.getNom());
        desc.setText(article.getDescription());
        prix.setText(Float.toString(article.getPrix()));
        note.setRating(article.getNote());
    }
}
