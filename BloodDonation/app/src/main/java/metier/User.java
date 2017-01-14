package metier;

import java.io.ByteArrayInputStream;

/**
 * Created by lina on 13/12/16.
 */
public class User {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String sexe;
    private String email;
    private String password;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User(String nom, String prenom, int age, String sexe, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;

        this.sexe = sexe;
        this.email = email;
        this.password = password;
        this.photo = "";
    }

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
