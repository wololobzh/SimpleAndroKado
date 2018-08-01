package fr.acos.androkado.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by acoss on 14/06/2018.
 */

public class GestionArticlesHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "GestionArticles.db";
    private static final int DATABASE_VERSION = 1;

    public GestionArticlesHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ARTICLES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_ARTICLES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion); // Idem onUpgrade()
    }


    private static final String TYPE_TEXT = " TEXT ";
    private static final String TYPE_INT = " INTEGER ";
    private static final String TYPE_FLOAT = " REAL ";
    private static final String VIRGULE = ", ";

    private static final String SQL_CREATE_TABLE_ARTICLES =
            "CREATE TABLE " + GestionArticlesContract.Articles.TABLE_NAME + " (" +
                    GestionArticlesContract.Articles._ID + " INTEGER PRIMARY KEY," +
                    GestionArticlesContract.Articles.COL_NAME_NOM + TYPE_TEXT + VIRGULE +
                    GestionArticlesContract.Articles.COL_NAME_PRIX + TYPE_FLOAT + VIRGULE +
                    GestionArticlesContract.Articles.COL_NAME_DESC + TYPE_TEXT + VIRGULE +
                    GestionArticlesContract.Articles.COL_NAME_NOTE + TYPE_FLOAT + VIRGULE +
                    GestionArticlesContract.Articles.COL_NAME_ACHETE + TYPE_INT + VIRGULE +
                    GestionArticlesContract.Articles.COL_NAME_URL + TYPE_TEXT +
                    " )";

    private static final String SQL_DROP_TABLE_ARTICLES = "DROP TABLE IF EXISTS " + GestionArticlesContract.Articles.TABLE_NAME;
}
