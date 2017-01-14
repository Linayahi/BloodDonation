package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHandler;
import metier.LieuDon;
import metier.User;

/**
 * Created by lina, ouiza on 13/12/16.
 */
public class UserDAO {

    public static final String USER_ID = "user_id";
    public static final String USER_NOM = "nom";
    public static final String USER_PRENOM = "prenom";
    public static final String USER_AGE = "age";
    public static final String USER_SEXE = "sexe";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    //public static final byte[] USER_IMAGE = null;

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NOM+ " TEXT, " +
                    USER_PRENOM+ " TEXT, " +
                    USER_AGE+ " INTEGER, " +
                    USER_SEXE+ " TEXT, " +
                    USER_EMAIL+ " TEXT, "  +
                    USER_PASSWORD+ " TEXT);" ;

   public static final String USER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    private DatabaseHandler maBaseSQLite;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        maBaseSQLite = DatabaseHandler.getInstance(context);
    }

    //Ouverture de la table en lecture/écriture
    public void open()
    {
        db = maBaseSQLite.getWritableDatabase();
    }

    //Fermeture de l'accès à la BDD
    public void close()
    {
        db.close();
    }

    // Retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
    public long addUser(User u)
    {
        ContentValues value = new ContentValues();
        value.put(UserDAO.USER_NOM, u.getNom());
        value.put(UserDAO.USER_PRENOM, u.getPrenom());
        value.put(UserDAO.USER_AGE, u.getAge());
        value.put(UserDAO.USER_SEXE, u.getSexe());
        value.put(UserDAO.USER_EMAIL, u.getEmail());
        value.put(UserDAO.USER_PASSWORD, u.getPassword());
        //value.put(String.valueOf(UserDAO.USER_IMAGE),u.getImage());
        return db.insert(UserDAO.USER_TABLE_NAME,null,value);

    }


  /*  public boolean getUserbyEmail(String email, String password) {
        Cursor cursor = db.rawQuery("select * from " + USER_TABLE_NAME + " where " + USER_EMAIL + " = ? AND " + USER_PASSWORD + "= ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }*/

    public User getUserbyEmail(String email, String password) {

      Cursor cursor = db.rawQuery("select * from " + USER_TABLE_NAME + " where "+USER_EMAIL+" = ? AND " + USER_PASSWORD + "= ?", new String[]{email,password});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            User user = new User();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setNom(cursor.getString(1));
            user.setPrenom(cursor.getString(2));
            user.setAge(Integer.parseInt(cursor.getString(3)));
            user.setSexe(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            user.setPassword(cursor.getString(6));

            return user;
        }
        else
        {
            cursor.close();
            return null;
        }
    }

    public boolean getUserbyEmail2(String email) {
        Cursor cursor = db.rawQuery("select * from " + USER_TABLE_NAME + " where " + USER_EMAIL + " = ?  ", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> lieuList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User lieu = new User();
                lieu.setId(Integer.parseInt(cursor.getString(0)));
                lieu.setNom(cursor.getString(1));
                lieu.setPrenom(cursor.getString(2));
                lieu.setAge(Integer.parseInt(cursor.getString(3)));
                lieu.setSexe(cursor.getString(4));
                lieu.setEmail(cursor.getString(5));
                lieu.setPassword(cursor.getString(6));
                lieu.setImage(cursor.getBlob(7));

                lieuList.add(lieu);
            } while (cursor.moveToNext());
        }

        return lieuList;
    }
}
