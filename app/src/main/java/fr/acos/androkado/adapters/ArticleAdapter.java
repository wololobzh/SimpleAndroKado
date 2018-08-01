package fr.acos.androkado.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import fr.acos.androkado.R;
import fr.acos.androkado.bo.Article;

/**
 * Created by acoss on 15/05/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>
{
    ArrayList<Article> articles = null;
    private View.OnClickListener monClickListener;

    /**
     * Constructeur
     * @param articles Données à afficher.
     */
    public ArticleAdapter(ArrayList<Article> articles,View.OnClickListener monClickListener)
    {
        this.articles = articles;
        this.monClickListener = monClickListener;
    }

    /**
     * Décompresse le fichier my_article_view.xml et créé un ViewHolder qui le représente.
     * @param parent
     * @param viewType
     * @return Un objet représentant my_article_view.xml
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cards, parent, false);

        ViewHolder vh = new ViewHolder(v,monClickListener);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextViewNom.setText(articles.get(position).getNom());
        holder.mRatingBar.setRating(articles.get(position).getNote());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     * Classe interne
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextViewNom;
        public RatingBar mRatingBar;

        public ViewHolder(View v, View.OnClickListener monClickListener)
        {
            super(v);
            mTextViewNom = v.findViewById(R.id.tv_info);
            mRatingBar = v.findViewById(R.id.rating_note);

            v.setOnClickListener(monClickListener);
        }
    }



}
