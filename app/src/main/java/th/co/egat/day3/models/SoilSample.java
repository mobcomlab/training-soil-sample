package th.co.egat.day3.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SoilSample
        extends RealmObject {

    //region Fields
    @PrimaryKey
    private String id = null;

    private double xCoord = 0.0;
    private double yCoord = 0.0;
    private Date date = null;
    private float ph = 7.0f;
    private int cd = -1;
    //endregion

    //region Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(final double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(final double yCoord) {
        this.yCoord = yCoord;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(final float ph) {
        this.ph = ph;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(final int cd) {
        this.cd = cd;
    }
    //endregion

}
