package id.firodev.catatantadarus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Edit extends AppCompatActivity {

    private Realm realm;
    private int posisi;

    TadarusAdapter mAdapter;

    Spinner mSpinnerSurah;
    EditText mEditAyat;
    EditText mEditKegiatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = RealmController.getInstance().getRealm();

        mAdapter = new TadarusAdapter(this);


        Intent ngedit = getIntent();
        posisi = ngedit.getIntExtra("posisi", 0);

        RealmResults<Tadarus> results = realm.where(Tadarus.class).findAll();
        Tadarus tadarus = results.get(posisi);

        mSpinnerSurah = (Spinner) findViewById(R.id.spinner_surah);


        List<String> list_surah = Arrays.asList(getResources().getStringArray(R.array.list_surah));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list_surah);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerSurah.setAdapter(adapter);

        mEditAyat = (EditText) findViewById(R.id.input_ayat);
        mEditKegiatan = (EditText) findViewById(R.id.input_kegiatan);

        mSpinnerSurah.setSelection(tadarus.getPosisi_spinner());

        mEditAyat.setText(tadarus.getAyat());
        mEditKegiatan.setText(tadarus.getKeterangan());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.centang, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_siap) {
            RealmResults<Tadarus> results = realm.where(Tadarus.class).findAll();

            realm.beginTransaction();
            results.get(posisi).setAyat(mEditAyat.getText().toString());
            results.get(posisi).setKeterangan(mEditKegiatan.getText().toString());
            results.get(posisi).setSurah(mSpinnerSurah.getSelectedItem().toString());

            realm.commitTransaction();
            mAdapter.notifyDataSetChanged();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);


    }
}
