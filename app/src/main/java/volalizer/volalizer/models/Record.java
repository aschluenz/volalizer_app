package volalizer.volalizer.models;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by andyschlunz on 20.06.16.
 */
public class Record {

    private int id;
    private char user_Id;
    private String comment;
    private double latitude;
    private double longitude;
    private Boolean isIndoor;
    private Date date;

    public Record(char user_Id, String comment, double latitude, double longitude, Boolean isIndoor, Date date) {
        this.user_Id = user_Id;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isIndoor = isIndoor;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(char user_Id) {
        this.user_Id = user_Id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Boolean getIndoor() {
        return isIndoor;
    }

    public void setIndoor(Boolean indoor) {
        isIndoor = indoor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
