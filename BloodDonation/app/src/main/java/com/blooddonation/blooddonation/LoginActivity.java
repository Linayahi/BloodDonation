package com.blooddonation.blooddonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dao.UserDAO;
import metier.User;

public class LoginActivity extends AppCompatActivity {
    private Button redirection;
    private Button login;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    if (userdao.getUserbyEmail(message_email, message_mdp))
                    {
                        Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                        //Intent intent = new Intent(LoginActivity.this,EventActivity.class);

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
}
