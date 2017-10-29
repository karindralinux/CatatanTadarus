package id.firodev.catatantadarus;

import android.content.Context;

import io.realm.RealmResults;

/**
 * Created by Ariaseta on 06/05/2017.
 */

public class RealmTadarusAdapter extends RealmModelAdapter<Tadarus> {

    public RealmTadarusAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
