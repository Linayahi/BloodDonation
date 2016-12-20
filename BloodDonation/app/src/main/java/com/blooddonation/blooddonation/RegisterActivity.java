package com.blooddonation.blooddonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import dao.UserDAO;
import metier.User;

public class RegisterActivity extends AppCompatActivity {

    private Button redirection;
    private Button register;
    private EditText nom;
    private EditText prenom;
    private EditText age;
    private RadioGroup sexe;
    private RadioButton radioButton;
    private EditText email;
    private EditText password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        age = (EditText) findViewById(R.id.age);
        sexe = (RadioGroup) findViewById(R.id.sexe);
        final int selectedId = sexe.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              User u = new User(nom.getText().toString(),
//                                prenom.getText().toString(),
//                                Integer.parseInt(age.getText().toString()),
//                                radioButton.getText().toString(),
//                                email.getText().toString(),
//                                password.getText().toString());
//
//                UserDAO userdao = new UserDAO(getApplicationContext());
//                userdao.addUser(u);
                Log.i("TAG",radioButton.getText().toString() );

            }
        });

        redirection = (Button) findViewById(R.id.redirection);
        redirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
