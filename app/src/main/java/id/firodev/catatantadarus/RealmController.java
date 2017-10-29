package id.firodev.catatantadarus;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ariaseta on 06/05/2017.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment){
        if (instance == null){
            instance =new RealmController(fragment.getActivity().getApplication());

        }
        return instance;
    }

    public static RealmController with(Activity activity){
        if (instance == null){
            instance = new RealmController(activity.getApplication());
        }

        return instance;
    }

    public static RealmController with(Application application){
        if (instance == null){
            instance = new RealmController(application);
        }

        return instance;
    }


    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm(){
        return realm;
    }

    public void refresh(){
        realm.refresh();
    }

    public void clearAll(){
        realm.beginTransaction();
        realm.clear(Tadarus.class);
        realm.commitTransaction();
    }

    public RealmResults<Tadarus> getTadarus(){
        return realm.where(Tadarus.class).findAll();
    }

    public Tadarus getTadarus(String id){
        return realm.where(Tadarus.class).equalTo("id",id).findFirst();
    }

    public boolean hasTadarus(){
        return !realm.allObjects(Tadarus.class).isEmpty();
    }

    public RealmResults<Tadarus> queryedBook(){
        return realm.where(Tadarus.class)
                .contains("ayat","20")
                .or()
                .contains("surah","Al-Baqarah")
                .findAll();
    }



}
