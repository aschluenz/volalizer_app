package volalizer.volalizer.models;

/**
 * Created by andyschlunz on 20.06.16.
 */
public class Record {

    private int ID;
    private String USER_ID;
    private double LATITUDE;
    private double LONGITUDE;
    private String TIME;
    private String COMMENT;
    private int IS_INDOOR;
    private double DB_VALUE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getCOMMENT() {
        return COMMENT;
    }

    public void setCOMMENT(String COMMENT) {
        this.COMMENT = COMMENT;
    }

    public int getIS_INDOOR() {
        return IS_INDOOR;
    }

    public void setIS_INDOOR(int IS_INDOOR) {
        this.IS_INDOOR = IS_INDOOR;
    }

    public double getDB_VALUE() {
        return DB_VALUE;
    }

    public void setDB_VALUE(double DB_VALUE) {
        this.DB_VALUE = DB_VALUE;
    }
}
