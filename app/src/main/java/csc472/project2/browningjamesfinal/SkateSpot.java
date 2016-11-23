package csc472.project2.browningjamesfinal;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Browning on 11/20/16.
 */

public class SkateSpot implements Parcelable{

    public static final Creator<SkateSpot> CREATOR = new Creator<SkateSpot>() {
        @Override
        public SkateSpot createFromParcel(Parcel in) {
            return new SkateSpot(in);
        }

        @Override
        public SkateSpot[] newArray(int size) {
            return new SkateSpot[size];
        }
    };
    protected Location location;
    private String type;
    private String name;
    private String description;
    private boolean covered;
    private float rating;
    private String accessibleTimes;
    private double lat;
    private double lon;

    public SkateSpot(String type, String name, String description,
                     boolean covered, float rating, String accessibleTimes, double lat, double lon, Location location) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.covered = covered;
        this.rating = rating;
        this.accessibleTimes = accessibleTimes;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
    }

    private SkateSpot(Parcel in) {
        type = in.readString();
        name = in.readString();
        description = in.readString();
        rating = in.readFloat();
        covered = in.readByte() != 0;
        accessibleTimes = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        //  location = in.readParcelable(Location.class.getClassLoader());
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCovered() {
        return covered;
    }

    public float getRating() {
        return rating;
    }

    public String getAccessibleTimes() {
        return accessibleTimes;
    }

    @Override
    public String toString() {
        return "SkateSpot{" +
                "type=" + type +
                ", name=" + name +
                ", description=" + description +
                ", covered=" + covered +
                ", rating=" + rating +
                ", accessibleTimes='" + accessibleTimes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkateSpot skateSpot = (SkateSpot) o;

        if (isCovered() != skateSpot.isCovered()) return false;
        if (Double.compare(skateSpot.getRating(), getRating()) != 0) return false;
        if (!getType().equals(skateSpot.getType())) return false;
        if (!getName().equals(skateSpot.getName())) return false;
        if (!getDescription().equals(skateSpot.getDescription())) return false;
        return getAccessibleTimes().equals(skateSpot.getAccessibleTimes());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getType().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + (isCovered() ? 1 : 0);
        temp = Double.doubleToLongBits(getRating());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getAccessibleTimes().hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(type);
        out.writeString(name);
        out.writeString(description);
        out.writeString(accessibleTimes);
        out.writeFloat(rating);
        out.writeByte((byte) (covered ? 1 : 0));
        out.writeDouble(lat);
        out.writeDouble(lon);
        //  out.writeParcelable(location, flags);
    }
}
