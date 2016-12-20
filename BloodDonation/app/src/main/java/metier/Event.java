package metier;

/**
 * Created by lina on 13/12/16.
 */
public class Event {
    private int id;
    private int user_id;
    private int lieu_id;
    private String date;

    public Event(int id, int user_id, int lieu_id, String date) {
        this.id = id;
        this.user_id = user_id;
        this.lieu_id = lieu_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLieu_id() {
        return lieu_id;
    }

    public void setLieu_id(int lieu_id) {
        this.lieu_id = lieu_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
