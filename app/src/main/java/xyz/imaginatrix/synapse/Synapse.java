package xyz.imaginatrix.synapse;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Synapse extends Application {

    @Override public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());

        // Realm
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}