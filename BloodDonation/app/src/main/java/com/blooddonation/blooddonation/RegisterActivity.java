package com.blooddonation.blooddonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

import dao.LieuDAO;
import dao.UserDAO;
import metier.LieuDon;
import metier.User;

public class RegisterActivity extends AppCompatActivity {

    private Button redirection;
    private Button register;
    private EditText nom;
    private EditText prenom;
    private EditText age;
    private RadioGroup sexe;
    private RadioButton radioSexButton;
    private EditText email;
    private EditText password;
    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    );

    List<LieuDon> lieux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        age = (EditText) findViewById(R.id.age);
        age.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
        sexe = (RadioGroup) findViewById(R.id.radioSex);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Log.i("Starting: ", "Starting..");
//                LieuDAO lieudao = new LieuDAO(getApplicationContext());
//                Log.i("Opening: ", "Opening..");
//                lieudao.open();
//                // Reading all contacts
//                 Log.i("Reading: ", "Reading all rows..");
//                 lieux = lieudao.getAllLieux();
//
//                for (LieuDon l : lieux) {
//                    String log = "Id: " + l.getId() + "Nom: " + l.getNom() + " ,Adresse: " + l.getAdresse() +" ,Latitude: " + l.getLatitude() + ", Logintude: "+ l.getLongitude()+ " , Description : "+ l.getDesc();
//                    Log.i("Lieu: ", log);
//                }
//                lieudao.close();

                String message_nom = nom.getText().toString();
                String message_prenom = prenom.getText().toString();
                String message_age = age.getText().toString();
                String message_email = email.getText().toString();
                String message_mdp = password.getText().toString();
                int selectedId = sexe.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String message_sexe= radioSexButton.getText().toString();

                if (message_nom.equals("")||message_prenom.equals("") ||message_age.equals("")||message_email.equals("")||message_mdp.equals(""))
                {
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
                            User u = new User(message_nom,
                               message_prenom,
                               Integer.parseInt(message_age),
                               message_sexe,
                               message_email,
                               message_mdp);

                            UserDAO userdao = new UserDAO(getApplicationContext());
                            userdao.open();
                            Long resultat = userdao.addUser(u);
                            if (resultat>0)
                            {
                                Toast.makeText(getBaseContext(), "Félcitations! Votre compte vient d'être créé", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }

                            Log.i("résultat ",String.valueOf(userdao.addUser(u)));

                            userdao.close();


                        }
                   }
                }






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

    public static boolean isValidEmail(String email)
    {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    public static boolean isValidPassword(String password)
    {
        if(password.length() > 7)   return true;
        else   return false;
    }
}
