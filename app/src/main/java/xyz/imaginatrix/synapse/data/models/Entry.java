package xyz.imaginatrix.synapse.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Entry extends RealmObject implements Parcelable {

    private String idUrl = null;
    private String publishedDate = null;
    private String updatedDate = null;
    private String title = null;
    private String summary = null;
    private RealmList<Author> authors = null;
    private RealmList<Classification> classifications = new RealmList<>();
    private String journalRef = null;
    private String comment = null;
    private String webUrl = null;
    private String pdfUrl = null;
    private String doiUrl = null;

    public Entry() { }

    public String getIdUrl() { return idUrl; }
    public void setIdUrl(String idUrl) { this.idUrl = idUrl; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary.trim(); }

    public RealmList<Author> getAuthors() { return authors; }
    public void setAuthors(RealmList<Author> authors) { this.authors = authors; }

    public RealmList<Classification> getClassifications() { return classifications; }
    public void setClassifications(RealmList<Classification> classifications) { this.classifications = classifications; }

    public String getJournalRef() {return journalRef; }
    public void setJournalRef(String journalRef) { this.journalRef = journalRef; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }

    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }

    public String getDoiUrl() { return doiUrl; }
    public void setDoiUrl(String doiUrl) { this.doiUrl = doiUrl; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idUrl);
        dest.writeString(this.publishedDate);
        dest.writeString(this.updatedDate);
        dest.writeString(this.title);
        dest.writeString(this.summary);
        dest.writeTypedList(this.authors);
        dest.writeTypedList(this.classifications);
        dest.writeString(this.journalRef);
        dest.writeString(this.comment);
        dest.writeString(this.webUrl);
        dest.writeString(this.pdfUrl);
        dest.writeString(this.doiUrl);
    }

    protected Entry(Parcel in) {
        this.idUrl = in.readString();
        this.publishedDate = in.readString();
        this.updatedDate = in.readString();
        this.title = in.readString();
        this.summary = in.readString();
        this.authors = new RealmList<>();
        this.authors.addAll(in.createTypedArrayList(Author.CREATOR));
        this.classifications = new RealmList<>();
        this.classifications.addAll(in.createTypedArrayList(Classification.CREATOR));
        this.journalRef = in.readString();
        this.comment = in.readString();
        this.webUrl = in.readString();
        this.pdfUrl = in.readString();
        this.doiUrl = in.readString();
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}
