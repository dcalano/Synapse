package xyz.imaginatrix.synapse.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;


public class RealmString extends RealmObject implements Parcelable {

    public String value = null;

    public RealmString() { }

    public RealmString(String value) {
        this.value = value;
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) { dest.writeString(this.value); }

    protected RealmString(Parcel in) { this.value = in.readString(); }

    public static final Parcelable.Creator<RealmString> CREATOR = new Parcelable.Creator<RealmString>() {
        @Override public RealmString createFromParcel(Parcel source) { return new RealmString(source); }
        @Override public RealmString[] newArray(int size) { return new RealmString[size]; }
    };
}
