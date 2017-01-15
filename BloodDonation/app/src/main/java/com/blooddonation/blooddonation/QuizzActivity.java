package com.blooddonation.blooddonation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizzActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<RadioButton> rb;
    public ArrayList<RadioGroup> rg;
    public Button result;
    public TextView text;
    SharedPreferences sharedPreferences;
    String email,prenom,nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        email = GetString("email");
        prenom = GetString("prenom");
        nom = GetString("nom");

        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.username);
        TextView mail = (TextView) header.findViewById(R.id.email);
        username.setText(prenom.concat(" ").concat(nom));
        mail.setText(email);

        text = (TextView) findViewById(R.id.text);
        //        int selectedId = sexe.getCheckedRadioButtonId();
//        radioSexButton = (RadioButton) findViewById(selectedId);
        rb = new ArrayList<RadioButton>();
        rg = new ArrayList<RadioGroup>();
        rg.add((RadioGroup) findViewById(R.id.r1));
        rg.add((RadioGroup) findViewById(R.id.r2));
        rg.add((RadioGroup) findViewById(R.id.r3));
        rg.add((RadioGroup) findViewById(R.id.r4));
        rg.add((RadioGroup) findViewById(R.id.r5));
        rg.add((RadioGroup) findViewById(R.id.r6));
        rg.add((RadioGroup) findViewById(R.id.r7));
        rg.add((RadioGroup) findViewById(R.id.r8));
        rg.add((RadioGroup) findViewById(R.id.r9));





//        rb[0] = (RadioButton) findViewById(R.id.r_1_1); //vrai 0
//        rb[1] = (RadioButton) findViewById(R.id.r_1_2); //faux 1
//        rb[2] = (RadioButton) findViewById(R.id.r_2_1); //v 2
//        rb[3] = (RadioButton) findViewById(R.id.r_2_2); //f 3
//        rb[4] = (RadioButton) findViewById(R.id.r_3_1); //f 4
//        rb[5] = (RadioButton) findViewById(R.id.r_3_2); //v 5
//        rb[6] = (RadioButton) findViewById(R.id.r_4_1); //f 6
//        rb[7] = (RadioButton) findViewById(R.id.r_4_2); //v 7
//        rb[8] = (RadioButton) findViewById(R.id.r_5_1); // v 8
//
//        rb[9] = (RadioButton) findViewById(R.id.r_5_2); // f 9
//        rb[10] = (RadioButton) findViewById(R.id.r_6_1); // f 10
//        rb[11] = (RadioButton) findViewById(R.id.r_6_2); // v 11
//        rb[12] = (RadioButton) findViewById(R.id.r_7_1); // f 12
//        rb[13] = (RadioButton) findViewById(R.id.r_7_2);// v 13
//        rb[14] = (RadioButton) findViewById(R.id.r_8_1); // f 14
//        rb[15] = (RadioButton) findViewById(R.id.r_8_2);// v 15
//        rb[16] = (RadioButton) findViewById(R.id.r_9_1);// f 16
//        rb[17] = (RadioButton) findViewById(R.id.r_9_2) ;// v 17
//
//
//        // 1 1 2 2 1 2 2 2 2
//
//
//
        result = (Button) findViewById(R.id.result);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected1 = rg.get(0).getCheckedRadioButtonId();
                int selected2 = rg.get(1).getCheckedRadioButtonId();
                int selected3 = rg.get(2).getCheckedRadioButtonId();
                int selected4 = rg.get(3).getCheckedRadioButtonId();
                int selected5 = rg.get(4).getCheckedRadioButtonId();
                int selected6 = rg.get(5).getCheckedRadioButtonId();
                int selected7 = rg.get(6).getCheckedRadioButtonId();
                int selected8 = rg.get(7).getCheckedRadioButtonId();
                int selected9 = rg.get(8).getCheckedRadioButtonId();

                rb.add((RadioButton) findViewById(selected1)); //vrai 0
                rb.add((RadioButton) findViewById(selected2)); //faux 1
                rb.add((RadioButton) findViewById(selected3)); //v 2
                rb.add((RadioButton) findViewById(selected4)); //f 3
                rb.add((RadioButton) findViewById(selected5)); //f 4
                rb.add((RadioButton) findViewById(selected6)); //v 5
                rb.add((RadioButton) findViewById(selected7)); //f 6
                rb.add((RadioButton) findViewById(selected8)); //v 7
                rb.add((RadioButton) findViewById(selected9)); // v 8

                if(selected1 == -1 && selected2==-1 && selected2==-1 && selected3==-1 &&
                selected4==-1 && selected5==-1 && selected6==-1 && selected7==-1 &&
                        selected8==-1 && selected3==-1)
                {
                    text.setText("Vous n'avez coché aucune réponse !");
                    text.setTextColor(0xffff0000);
                }
                else {


                    int right = 0;
                    int wrong = 0;
                    if(selected1 != -1)
                    {
                        if (rb.get(0).getText().equals("18")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(0).setTextColor(0xffff0000);
                        }
                    }

                    if(selected2 != -1) {
                        if (rb.get(1).getText().equals("50")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(1).setTextColor(0xffff0000);
                        }

                    }
                    if(selected3 != -1) {
                        if (rb.get(2).getText().equals("8 semaines")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(2).setTextColor(0xffff0000);
                        }
                    }
                    if(selected4 != -1) {
                        if (rb.get(3).getText().equals("15")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(3).setTextColor(0xffff0000);
                        }
                    }
                    if(selected5 != -1) {
                        if (rb.get(4).getText().equals("4 denriers mois")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(4).setTextColor(0xffff0000);
                        }
                    }
                    if(selected6 != -1) {
                        if (rb.get(5).getText().equals("Non")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(5).setTextColor(0xffff0000);
                        }
                    }
                    if(selected7 != -1) {
                        if (rb.get(6).getText().equals("Non")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(6).setTextColor(0xffff0000);
                        }
                    }

                    if(selected8 != -1) {
                        if (rb.get(7).getText().equals("Non")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(7).setTextColor(0xffff0000);
                        }
                    }

                    if(selected9 != -1) {
                        if (rb.get(8).getText().equals("Non")) {
                            right++;
                        } else {
                            wrong++;
                            rb.get(8).setTextColor(0xffff0000);
                        }
                    }
                    if(right < wrong)
                    {
                        text.setText(Html.fromHtml(":( <br> Vous avez eu " + right + " bonne(s) réponse(s) et " + wrong + " mauvaise(s) réponse(s)"));
                        text.setTextColor(0xffff0000);
                    }
                    else if(right>=wrong)
                    {
                        text.setText(Html.fromHtml(":) <br> Vous avez eu " + right + " bonne(s) réponse(s) et " + wrong + " mauvaise(s) réponse(s)"));
                        text.setTextColor(0xff00ff00);
                    }

                }
//                for(int j=0;j<rg.size();j++)
//                    rg.get(j).setEnabled(false);
                result.setEnabled(false);
                result.setBackgroundColor(0xff888888);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quizz, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            if (id == R.id.nav_profil)
            {
            }
            else if (id == R.id.nav_map)
            {
                Intent i = new Intent(QuizzActivity.this, MenuActivity.class);
                startActivity(i);

            }
            else if (id == R.id.nav_image)
            {
                Intent i = new Intent(QuizzActivity.this, ScanActivity.class);
                startActivity(i);
            }
            else if (id == R.id.nav_quizz)
            {
                Intent i = new Intent(QuizzActivity.this, QuizzActivity.class);
                startActivity(i);
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
}
