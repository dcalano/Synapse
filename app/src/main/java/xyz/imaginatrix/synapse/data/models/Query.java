package xyz.imaginatrix.synapse.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Query implements Parcelable {

    private String query = null;
    private Classification classification = null;
    private String filter = "all";
    private String sortBy = "relevance";
    private String sortOrder = "descending";
    private int currentStartIndex = 0;
    private int totalResults = -1;

    public Query() { }

    public String getRawQuery() { return query; }
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        // Parse query
        if (this.query != null) {
            String formattedQuery = "\""
                    + query.trim()
                        .replace("  ", " ")
                        .replace(" ", "+")
                    + "\"";

            sb.append(this.filter)
                    .append(":")
                    .append(formattedQuery)
                    .append("+AND+");
        }

        // Parse classification
        if (this.classification != null && this.classification.getSubjectKey() != null) {
            sb.append("cat:")
                    .append(this.classification.getSubjectKey());

            if (this.classification.getCategory() != null && this.classification.getCategory().getKey() != null) {
                sb.append(".").append(this.classification.getCategory().getKey());
            }
        }

        return sb.toString();
    }
    public void setQuery(String query) {
        this.query = query;
    }

    public Classification getClassification() { return this.classification; }
    public void setClassification(Classification classification) { this.classification = classification; }

    public String getFilter() { return filter; }
    public void setFilter(String filter) {
        if (filter.equals("ti") || filter.equals("au") || filter.equals("abs")) {
            this.filter = filter;
        } else {
            this.filter = "all";
        }
    }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) {
        if (sortBy.equals("relevance") || sortBy.equals("lastUpdatedDate") || sortBy.equals("submittedDate")) {
            this.sortBy = sortBy;
        } else {
            this.sortBy = "relevance";
        }
    }

    public String getSortOrder() { return sortOrder; }
    public void setSortOrder(String sortOrder) {
        if (sortOrder.equals("ascending") || sortOrder.equals("descending")) {
            this.sortOrder = sortOrder;
        } else {
            this.sortOrder = "descending";
        }
    }

    public int getCurrentStartIndex() { return currentStartIndex; }
    public void setCurrentStartIndex(int currentStartIndex) { this.currentStartIndex = currentStartIndex; }

    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.query);
        dest.writeParcelable(this.classification, flags);
        dest.writeString(this.filter);
        dest.writeString(this.sortBy);
        dest.writeString(this.sortOrder);
        dest.writeInt(this.currentStartIndex);
        dest.writeInt(this.totalResults);
    }

    protected Query(Parcel in) {
        this.query = in.readString();
        this.classification = in.readParcelable(Classification.class.getClassLoader());
        this.filter = in.readString();
        this.sortBy = in.readString();
        this.sortOrder = in.readString();
        this.currentStartIndex = in.readInt();
        this.totalResults = in.readInt();
    }

    public static final Parcelable.Creator<Query> CREATOR = new Parcelable.Creator<Query>() {
        @Override public Query createFromParcel(Parcel source) { return new Query(source); }
        @Override public Query[] newArray(int size) { return new Query[size]; }
    };
}
