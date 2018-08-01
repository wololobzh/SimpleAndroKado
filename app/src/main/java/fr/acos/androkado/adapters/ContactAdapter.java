package fr.acos.androkado.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.acos.androkado.R;
import fr.acos.androkado.bo.Contact;

/**
 * Created by acoss on 01/08/2018.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
{
    private static final String TAG = "ACOS";
    ArrayList<Contact> contacts = null;
    private View.OnClickListener monClickListener;

    /**
     * Constructeur
     * @param contacts Données à afficher.
     */
    public ContactAdapter(ArrayList<Contact> contacts,View.OnClickListener monClickListener)
    {
        this.contacts = contacts;
        this.monClickListener = monClickListener;
        Log.i(TAG,"contacts size : " + contacts.size());
    }

    /**
     * Décompresse le fichier my_article_view.xml et créé un ViewHolder qui le représente.
     * @param parent
     * @param viewType
     * @return Un objet représentant my_article_view.xml
     */
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        ContactViewHolder vh = new ContactViewHolder(v,monClickListener);
        return vh;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position)
    {
        holder.mTextViewNom.setText(contacts.get(position).getNom());
        holder.mTextViewTelephone.setText(contacts.get(position).getTelephone());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /**
     * Classe interne
     */
    public static class ContactViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextViewNom;
        public TextView mTextViewTelephone;

        public ContactViewHolder(View v, View.OnClickListener monClickListener)
        {
            super(v);
            mTextViewNom = v.findViewById(R.id.tv_nom);
            mTextViewTelephone = v.findViewById(R.id.tv_telephone);
            v.setOnClickListener(monClickListener);
        }
    }
}
