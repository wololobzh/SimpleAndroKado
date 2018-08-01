package fr.acos.androkado;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class ConfigurationActivity extends AppCompatActivity {

    public final static String CLE_TRI = "CLE_TRI";
    public final static String CLE_PRIX_DEFAUT = "CLE_PRIX_DEFAUT";

    EditText etPrixDefaut;
    Switch tri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        setTitle("Configuration");

        SharedPreferences spIntra = getSharedPreferences("configuration",MODE_PRIVATE);
        String valeurPrixDefaut = spIntra.getString(CLE_PRIX_DEFAUT,"");
        Boolean valeurTri = spIntra.getBoolean(CLE_TRI,false);

        etPrixDefaut = findViewById(R.id.et_prix_defaut);
        tri =  findViewById(R.id.s_tri_prix);

        etPrixDefaut.setText(valeurPrixDefaut);
        tri.setChecked(valeurTri);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sp = getSharedPreferences("configuration",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CLE_PRIX_DEFAUT,etPrixDefaut.getText().toString());
        editor.putBoolean(CLE_TRI,tri.isChecked());
        editor.commit();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
