package fr.acos.androkado;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import fr.acos.androkado.adapters.ContactAdapter;
import fr.acos.androkado.bo.Article;
import fr.acos.androkado.bo.Contact;

public class ListeContactActivity extends AppCompatActivity {

    private static final String TAG = "ACOS";
    //Objet représentant le recyclerView
    private RecyclerView mRecyclerView;
    //Objet représentant l"adapter remplissant le recyclerView
    private RecyclerView.Adapter mAdapter;
    //Objet permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;
    //Liste bouchon
    private ArrayList<Contact> contacts = new ArrayList<>();
    Article article = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_contact);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE}, 14540);
        Intent intention = getIntent();
        article = intention.getParcelableExtra("fr.eni.android.androcado.ArticleSelectionne");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "Entrée onRequestPermissionsResult");

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Traitement effectuée pour la demande de permission ayant l'identifiant 14540
        if (requestCode == 14540) {
            //Test si l'utilisateur a donné sa permission pour la permission READ_CONTACT
            if (grantResults.length == 3
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    ) {
                //Appel de la fonction qui utilise le content provider
                contacts = getContacts();
                chargementRecycler();
            }
        }
    }

    /**
     * Définition de l'action du clic sur un item.
     */
    private View.OnClickListener monClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            Log.i(TAG, "ENVOI D'UN MESSAGE " + position);
            Contact unContact = contacts.get(position);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(unContact.getTelephone(), null, "Voici un cadeau qui me ferait plaisir : " + article.getNom(), null, null);
            finish();
        }
    };

    /**
     * Permet de charger le recycler view.
     */
    private void chargementRecycler() {
        //Lie le recyclerView aux fonctionnalité offerte par le linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Liaison permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
        mAdapter = new ContactAdapter(contacts, monClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Contact> getContacts() {
        ArrayList<Contact> liste = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Cursor contacts = resolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY}, null, null, null);

        //Itération sur tout les contacts
        while (contacts.moveToNext()) {
            Contact unContact = new Contact();

            //Récupération de l'identifiant du contact
            String contactId = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
            //Récupération de son nom principal
            String contactNom = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

            unContact.setId(contactId);
            unContact.setNom(contactNom);

            Cursor telephones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

            if (telephones.moveToNext()) {
                String numero = telephones.getString(telephones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                unContact.setTelephone(numero);
            }
            telephones.close();
            liste.add(unContact);
        }
        contacts.close();
        return liste;
    }
}
