package fr.acos.androkado.dao;

import android.provider.BaseColumns;

/**
 * Created by acoss on 14/06/2018.
 */
public class GestionArticlesContract
{
    // Structure de la table ARTICLES
    public abstract static class Articles implements BaseColumns
    {
        // BaseColumns déclare déjà la colonne "_id"
        public static final String TABLE_NAME 		= "articles";
        public static final String COL_NAME_NOM 	= "nom";
        public static final String COL_NAME_PRIX 	= "prix";
        public static final String COL_NAME_DESC 	= "description";
        public static final String COL_NAME_NOTE = "note";
        public static final String COL_NAME_URL 	= "url";
        public static final String COL_NAME_ACHETE	= "achete";

        public static final int VALUE_ACHETE_TRUE = 1;
        public static final int VALUE_ACHETE_FALSE = 0;
    }
}
