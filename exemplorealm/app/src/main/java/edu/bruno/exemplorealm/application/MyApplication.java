package edu.bruno.exemplorealm.application;

import android.app.Application;

import edu.bruno.exemplorealm.models.MigrationMyData;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bruno.oliveira on 6/10/16.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .schemaVersion(MigrationMyData.VERSION)
                .migration(new MigrationMyData())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
