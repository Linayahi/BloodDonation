package com.blooddonation.blooddonation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import dao.UserDAO;
import metier.User;

public class ProfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    String sp_email,sp_prenom,sp_nom, photo;

    private Button modifier;
    private Button supprimer;
    private EditText nom;
    private EditText prenom;
    private EditText age;
    private RadioGroup sexe;
    private RadioButton radioSexButton;
    private EditText email;
    private EditText password;
    //private TextView userid;

    User user;

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sp_email = GetString("email");
        sp_prenom = GetString("prenom");
        sp_nom = GetString("nom");
        photo = GetString("photo");

        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.username);
        TextView mail = (TextView) header.findViewById(R.id.email);
        username.setText(sp_prenom.concat(" ").concat(sp_nom));
        mail.setText(sp_email);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        age = (EditText) findViewById(R.id.age);
        //age.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
        sexe = (RadioGroup) findViewById(R.id.radioSex);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        //userid=(TextView) findViewById(R.id.userid);

        UserDAO userdao = new UserDAO(getApplicationContext());
        userdao.open();

        /****** Afficher le profil de l'utilisateur****/
        user = userdao.getUserbyEmail3(sp_email);
        if (user != null) {
            nom.setText(user.getNom());
            prenom.setText(user.getPrenom());
            age.setText(String.valueOf(user.getAge()));
            email.setText(user.getEmail());
            password.setText(user.getPassword());
            //userid.setText(String.valueOf(user.getId()));

            if (user.getSexe().equals("Masculin")) {
                radioSexButton = (RadioButton) findViewById(R.id.radioButton);
                radioSexButton.setChecked(true);
            } else {
                radioSexButton = (RadioButton) findViewById(R.id.radioButton2);
                radioSexButton.setChecked(true);

            }
        }
        userdao.close();

        supprimer = (Button) findViewById(R.id.supp);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(ProfilActivity.this)
                        .setTitle("Votre compte va être supprimé")
                        .setMessage("Voulez-vous continuer?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                UserDAO userdao = new UserDAO(getApplicationContext());
                                userdao.open();
                                userdao.deleteUser(user);
                                userdao.close();

                                Intent i = new Intent(ProfilActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        modifier = (Button) findViewById(R.id.modif);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message_nom = nom.getText().toString();
                String message_prenom = prenom.getText().toString();
                String message_age = age.getText().toString();
                String message_email = email.getText().toString();
                String message_mdp = password.getText().toString();
                int selectedId = sexe.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String message_sexe = radioSexButton.getText().toString();

                if (message_nom.equals("") || message_prenom.equals("") || message_age.equals("") || message_email.equals("") || message_mdp.equals("")) {
                    Toast.makeText(getBaseContext(), "Un ou plusieurs champs obligatoires vides", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!isValidEmail(message_email))
                    {
                        Toast.makeText(getBaseContext(), "Email invalide", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (!isValidPassword(message_mdp))
                        {
                            Toast.makeText(getBaseContext(), "Mot de passe trop court, veuillez entrer un mot de passe de longueur supérieur à 7 !", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            if (!message_email.equals(sp_email))
                            {
                                UserDAO userdao2 = new UserDAO(getApplicationContext());
                                userdao2.open();
                                if (userdao2.getUserbyEmail2(message_email)) {
                                    Toast.makeText(getBaseContext(), "Cet adresse email est déjà attribuée !", Toast.LENGTH_SHORT).show();
                                    userdao2.close();
                                }

                                else
                                {
                                    UserDAO userdao = new UserDAO(getApplicationContext());
                                    userdao.open();
                                    User u = userdao.getUserbyEmail3(sp_email);
                                    u.setNom(message_nom);
                                    u.setPrenom(message_prenom);
                                    u.setAge(Integer.parseInt(message_age));
                                    u.setSexe(message_sexe);
                                    u.setEmail(message_email);
                                    u.setPassword(message_mdp);

                                    int resultat = userdao.updateUser(u);
                                    if (resultat > 0) {
                                        SaveString("email", u.getEmail());
                                        SaveString("nom", u.getNom());
                                        SaveString("prenom", u.getPrenom());
                                        SaveString("photo", u.getPhoto());

                                        Toast.makeText(getBaseContext(), "Votre compte vient d'être modifié", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfilActivity.this, ProfilActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getBaseContext(), "Aucune modification à enregistrer", Toast.LENGTH_SHORT).show();

                                    }

                                    Log.i("résultat ", String.valueOf(resultat));

                                    userdao.close();

                                }
                            }
                            else
                            {
                                UserDAO userdao = new UserDAO(getApplicationContext());
                                userdao.open();
                                User u = userdao.getUserbyEmail3(sp_email);
                                u.setNom(message_nom);
                                u.setPrenom(message_prenom);
                                u.setAge(Integer.parseInt(message_age));
                                u.setSexe(message_sexe);
                                u.setEmail(message_email);
                                u.setPassword(message_mdp);

                                int resultat = userdao.updateUser(u);
                                if (resultat > 0) {
                                    SaveString("email", u.getEmail());
                                    SaveString("nom", u.getNom());
                                    SaveString("prenom", u.getPrenom());
                                    SaveString("photo", u.getPhoto());

                                    Toast.makeText(getBaseContext(), "Votre compte vient d'être modifié", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProfilActivity.this, ProfilActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), "Aucune modification à enregistrer", Toast.LENGTH_SHORT).show();

                                }

                                Log.i("résultat ", String.valueOf(resultat));

                                userdao.close();
                            }
                        }

                    }


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil)
        {
            Intent i = new Intent(ProfilActivity.this, ProfilActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_map)
        {
            Intent i = new Intent(ProfilActivity.this, MenuActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_image)
        {
            Intent i = new Intent(ProfilActivity.this, ScannActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_quizz)
        {
            Intent i = new Intent(ProfilActivity.this, QuizzActivity.class);
            startActivity(i);
        }

        else if (id == R.id.nav_deco)
        {
            Intent i = new Intent(ProfilActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String GetString(String key)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(key, "");
    }


    public static boolean isValidEmail(String email)
    {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    public static boolean isValidPassword(String password)
    {
        if(password.length() > 7)   return true;
        else   return false;
    }

    public void SaveString(String key, String value)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
