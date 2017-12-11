package xyz.imaginatrix.synapse.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Classification extends RealmObject implements Parcelable {

    private String subjectName = null;
    private String subjectKey = null;
    private Category category = null;

    public Classification() { }

    public Classification(String subjectName, String subjectKey, Category category) {
        this.subjectName = subjectName;
        this.subjectKey = subjectKey;
        this.category = category;
    }

    public String getClassification() {
        StringBuilder sb = new StringBuilder();
        if (subjectKey == null) {
            return null;
        } else {
            sb.append(subjectKey);

            if (category != null) {
                sb.append(".");
                sb.append(category.getKey());
            }
        }

        return sb.toString();
    }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getSubjectKey() { return subjectKey; }
    public void setSubjectKey(String subjectKey) { this.subjectKey = subjectKey; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String toString() {
        if (subjectKey == null)
            return null;

        StringBuilder sb = new StringBuilder();
        sb.append(subjectKey);
        if (category != null)
            sb.append(".").append(category.getKey());

        return sb.toString();
    }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subjectName);
        dest.writeString(this.subjectKey);
        dest.writeParcelable(this.category, flags);
    }

    protected Classification(Parcel in) {
        this.subjectName = in.readString();
        this.subjectKey = in.readString();
        this.category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Parcelable.Creator<Classification> CREATOR = new Parcelable.Creator<Classification>() {
        @Override
        public Classification createFromParcel(Parcel source) {
            return new Classification(source);
        }

        @Override
        public Classification[] newArray(int size) {
            return new Classification[size];
        }
    };
}