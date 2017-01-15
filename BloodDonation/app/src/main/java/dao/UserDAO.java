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
 * Cette classe contient les méthodes qui permettent de gérer la table User
 */
public class UserDAO {

    public static final String USER_ID = "user_id";
    public static final String USER_NOM = "nom";
    public static final String USER_PRENOM = "prenom";
    public static final String USER_AGE = "age";
    public static final String USER_SEXE = "sexe";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHOTO = "photo";

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NOM+ " TEXT, " +
                    USER_PRENOM+ " TEXT, " +
                    USER_AGE+ " INTEGER, " +
                    USER_SEXE+ " TEXT, " +
                    USER_EMAIL+ " TEXT, "  +
                    USER_PASSWORD+ " TEXT, " +
                    USER_PHOTO+ " TEXT);";

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

    /**
     * Cette fonction permet d'insérer un User entrée en pramètre.
     * @param u
     * @return id du nouvel enregistrement inséré, ou -1 en cas d'erreur
     */
    public long addUser(User u)
    {
        ContentValues value = new ContentValues();
        value.put(UserDAO.USER_NOM, u.getNom());
        value.put(UserDAO.USER_PRENOM, u.getPrenom());
        value.put(UserDAO.USER_AGE, u.getAge());
        value.put(UserDAO.USER_SEXE, u.getSexe());
        value.put(UserDAO.USER_EMAIL, u.getEmail());
        value.put(UserDAO.USER_PASSWORD, u.getPassword());
        value.put(UserDAO.USER_PHOTO,u.getPhoto());
        return db.insert(UserDAO.USER_TABLE_NAME,null,value);

    }

     //Ajoute une image à l'utilisateur
    public long AddPhoto(String email, String photo)
    {
        ContentValues value = new ContentValues();
        value.put(UserDAO.USER_PHOTO,photo);
        return db.update(UserDAO.USER_TABLE_NAME,value,"email='"+email+"'",null);
    }

    /**
     * Cette fonction permet de récupérer un utilisateur par son email et password
     * @param email
     * @param password
     * @return
     */
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
            user.setPhoto(cursor.getString(7));
            return user;
        }
        else
        {
            cursor.close();
            return null;
        }
    }


    /**
     * Cette fonction vérifie si une adresse email n'est pas déjà attribué en base de données
     * @param email
     * @return
     */
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

    /**
     * Cette fonction récupère un User par son mail
     * @param email
     * @return
     */
    public User getUserbyEmail3(String email) {

        Cursor cursor = db.rawQuery("select * from " + USER_TABLE_NAME + " where "+USER_EMAIL+" = ?" , new String[]{email});
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
            user.setPhoto(cursor.getString(7));
            return user;
        }
        else
        {
            cursor.close();
            return null;
        }
    }

    /**
     * Cette fonction supprime un User entré en paramère
     * @param user
     */
    public void deleteUser(User user) {
        db.delete(USER_TABLE_NAME, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    /**
     * Cette fonction met à jour les modifications d'un utilisateur
     * @param u
     * @return
     */
    public int updateUser(User u) {

        ContentValues value = new ContentValues();
        value.put(UserDAO.USER_NOM, u.getNom());
        value.put(UserDAO.USER_PRENOM, u.getPrenom());
        value.put(UserDAO.USER_AGE, u.getAge());
        value.put(UserDAO.USER_SEXE, u.getSexe());
        value.put(UserDAO.USER_EMAIL, u.getEmail());
        value.put(UserDAO.USER_PASSWORD, u.getPassword());
        value.put(UserDAO.USER_PHOTO,u.getPhoto());

        return db.update(USER_TABLE_NAME, value, USER_ID + " = ?",
                new String[] { String.valueOf(u.getId()) });
    }
}
