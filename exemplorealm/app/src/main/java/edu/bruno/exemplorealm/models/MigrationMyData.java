package edu.bruno.exemplorealm.models;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by bruno.oliveira on 6/13/16.
 */
public class MigrationMyData implements RealmMigration {

    public static final Long VERSION = 1L;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.get("Guitar")
                    .addField("strings", String.class);
            oldVersion++;
        }
    }
}
