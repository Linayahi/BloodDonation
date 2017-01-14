package com.blooddonation.blooddonation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dao.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private Button redirection;
    private Button login;
    private EditText email;
    private EditText password;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar=getSupportActionBar();
        actionBar.show();
        actionBar.setTitle(Html.fromHtml("<font color=\"white\">" + "Connexion" + "</font>"));

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message_email = email.getText().toString();
                String message_mdp = password.getText().toString();
                if (message_email.equals("")||message_mdp.equals(""))
                {
                    Toast.makeText(getBaseContext(), "Un ou plusieurs champs obligatoires vides", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    UserDAO userdao = new UserDAO(getApplicationContext());
                    userdao.open();
                    User user = userdao.getUserbyEmail(message_email, message_mdp);
                    if (user!=null)
                    {
<<<<<<< HEAD
                        SaveString("email", user.getEmail());
                        SaveString("nom", user.getNom());
                        SaveString("prenom", user.getPrenom());

                        Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
=======
                        Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                        //Intent intent = new Intent(LoginActivity.this,EventActivity.class);
                       // Intent intent = new Intent(LoginActivity.this,MenuActivity.class);


                        Intent intent = new Intent(LoginActivity.this,QuizActivity.class);
>>>>>>> fd1758df35b866c192b7f20f239f6f1ebe2b0152
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Email ou mot de pase incorrect", Toast.LENGTH_SHORT).show();

                    }
                    userdao.close();
                }



            }
        });

        redirection = (Button) findViewById(R.id.redirection);
        redirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    public void SaveString(String key, String value)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
