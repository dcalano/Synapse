package xyz.imaginatrix.synapse.data.models;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Subject extends RealmObject {

    private String key;
    private String name;
    private RealmList<Category> categories;

    public Subject() {
        this.key = null;
        this.name = null;
        this.categories = new RealmList<>();
    }

    public Subject(String key, String name, RealmList<Category> categories) {
        this.key = key;
        this.name = name;
        this.categories = categories;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public RealmList<Category> getCategories() { return categories; }
    public void setCategories(RealmList<Category> categories) { this.categories = categories; }
}
