package id.firodev.catatantadarus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Ariaseta on 06/05/2017.
 */

public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter {

    public RealmModelAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
