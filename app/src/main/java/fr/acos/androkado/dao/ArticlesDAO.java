package fr.acos.androkado.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.acos.androkado.bo.Article;

/**
 * Created by acoss on 14/06/2018.
 */

public class ArticlesDAO {

    private static final String TAG = ArticlesDAO.class.getName();

    private GestionArticlesHelper helper = null;
    private SQLiteDatabase db = null;

    private static final String[] ALL_COLUMN = new String[]{GestionArticlesContract.Articles._ID,
            GestionArticlesContract.Articles.COL_NAME_NOM,
            GestionArticlesContract.Articles.COL_NAME_PRIX,
            GestionArticlesContract.Articles.COL_NAME_DESC,
            GestionArticlesContract.Articles.COL_NAME_NOTE,
            GestionArticlesContract.Articles.COL_NAME_URL,
            GestionArticlesContract.Articles.COL_NAME_ACHETE };

    public ArticlesDAO(Context contexte) {
        helper = new GestionArticlesHelper(contexte);
    }


    public void insert(Article article) {

        // Ouvrir la connexion
        db = helper.getWritableDatabase();

        // Preparer la requete
        ContentValues valeurs = new ContentValues();
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_NOM, article.getNom());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_PRIX, article.getPrix());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_DESC, article.getDescription());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_NOTE, article.getNote());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_URL, article.getUrl());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_ACHETE, article.isAchete());

        // Passer la requete
        long rowId = db.insert(GestionArticlesContract.Articles.TABLE_NAME, null, valeurs);

        if (rowId == -1)
        {
            Log.w(TAG, "L'insertion n'a pas eu lieu.");
        }
        Log.d(TAG, "rowID = " + rowId);

        // Fermer la connexion
        helper.close();

    }

    public void update(Article article)
    {
        Log.d(TAG, "Entrée dans update() = " + article.toString());
        // Ouvrir la connexion
        db = helper.getWritableDatabase();

        // Preparer la requete
        ContentValues valeurs = new ContentValues();
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_NOM, article.getNom());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_PRIX, article.getPrix());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_DESC, article.getDescription());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_NOTE, article.getNote());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_URL, article.getUrl());
        valeurs.put(GestionArticlesContract.Articles.COL_NAME_ACHETE, article.isAchete());

        String whereClause =  GestionArticlesContract.Articles._ID+" == ?";
        String[] whereParams = new String[]{Integer.toString(article.getId())};

        // Passer la requete
        int nbLignesModifiess = db.update(GestionArticlesContract.Articles.TABLE_NAME, valeurs, whereClause, whereParams);

        Log.d(TAG, "nbLignesModifiess = " + nbLignesModifiess);

        // Fermer la connexion
        helper.close();

    }

    public void delete(Article article)
    {
        // Ouvrir la connexion
        db = helper.getWritableDatabase();

        String whereClause =  GestionArticlesContract.Articles._ID+" == ?";
        String[] whereParams = new String[]{Integer.toString(article.getId())};

        // Passer la requete
        int nbLignesModifiess = db.delete(GestionArticlesContract.Articles.TABLE_NAME, whereClause, whereParams);

        // Fermer la connexion
        helper.close();
    }

    public ArrayList<Article> get(boolean tri)
    {
        Log.d(TAG, "Entrée dans get(). Tri = " + tri);
        ArrayList<Article> resultat = new ArrayList<Article>();

        // Ouvrir la connexion
        db = helper.getWritableDatabase();

        String orderBy = tri?GestionArticlesContract.Articles.COL_NAME_PRIX + " ASC":"";

        Log.d(TAG, "Dans get(). orderBy = " + orderBy);

        Cursor curseur = db.query(GestionArticlesContract.Articles.TABLE_NAME, ALL_COLUMN, null, null, null, null, orderBy);

        while (curseur.moveToNext())
        {
            Article article = new Article(curseur.getInt(0), // id
                    curseur.getString(1), // Nom
                    curseur.getString(3),// Description
                    curseur.getString(5), // URL
                    curseur.getFloat(2), // Prix
                    curseur.getFloat(4), // Envie
                    curseur.getInt(6)==GestionArticlesContract.Articles.VALUE_ACHETE_TRUE // Achete
            );
            Log.d(TAG, "Dans get(). article = " + article.toString());
            resultat.add(article);
        }

        curseur.close();

        // Fermer la connexion
        helper.close();
        return resultat;
    }

    public Article get(int id)
    {
        Article resultat = new Article();

        // Ouvrir la connexion
        db = helper.getWritableDatabase();

        String whereClause =  GestionArticlesContract.Articles._ID+" == ?";
        String[] whereParams = new String[]{Integer.toString(id)};

        Cursor curseur = db.query(GestionArticlesContract.Articles.TABLE_NAME, ALL_COLUMN, whereClause, whereParams, null, null, null);

        if (curseur.moveToNext())
        {
            resultat = new Article(curseur.getInt(0), // id
                    curseur.getString(1), // Nom
                    curseur.getString(3),// Description
                    curseur.getString(5), // URL
                    curseur.getFloat(2), // Prix
                    curseur.getFloat(4), // Envie
                    curseur.getInt(6)==GestionArticlesContract.Articles.VALUE_ACHETE_TRUE // Achete
            );
        }

        curseur.close();

        // Fermer la connexion
        helper.close();
        return resultat;
    }
}
