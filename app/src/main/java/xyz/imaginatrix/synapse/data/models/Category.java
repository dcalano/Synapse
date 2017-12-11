package xyz.imaginatrix.synapse.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by cyberpunk on 8/4/17.
 */

public class Category extends RealmObject implements Parcelable {

    private String key = null;
    private String name = null;

    public Category() { }

    public Category(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
    }

    protected Category(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
