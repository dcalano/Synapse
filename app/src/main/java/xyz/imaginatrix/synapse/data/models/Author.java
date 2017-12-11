package xyz.imaginatrix.synapse.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Author extends RealmObject implements Parcelable {

    private String name = null;
    private RealmList<RealmString> affiliations = null;

    public Author() { }

    public Author(@NonNull String name) {
        this.name = name;
    }

    public Author(@NonNull String name, String affiliation) {
        this.name = name;
        this.setAffiliations(affiliation);
    }

    public Author(@NonNull String name, List<String> affiliations) {
        this.name = name;
        this.setAffiliations(affiliations);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public RealmList<RealmString> getAffiliations() { return this.affiliations; }
    public void setAffiliations(String affiliation) {
        this.affiliations  = new RealmList<>();
        if (affiliation != null)
            this.affiliations.add(new RealmString(affiliation));
    }
    public void setAffiliations(List<String> affiliations) {
        this.affiliations  = new RealmList<>();
        if (affiliations != null) {
            for (String affiliation : affiliations)
                this.affiliations.add(new RealmString(affiliation));
        }
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.affiliations);
    }

    protected Author(Parcel in) {
        this.name = in.readString();
        this.affiliations = new RealmList<>();
        this.affiliations.addAll(in.createTypedArrayList(RealmString.CREATOR));
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override public Author createFromParcel(Parcel source) { return new Author(source); }
        @Override public Author[] newArray(int size) { return new Author[size]; }
    };
}
