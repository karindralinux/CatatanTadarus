package id.firodev.catatantadarus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public TadarusAdapter mAdapter;
    private Realm mRealm;
    private LayoutInflater mInflater;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();




        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCatatan();
            }
        });


    }

    private void addCatatan() {
        mInflater = MainActivity.this.getLayoutInflater();
        View content = mInflater.inflate(R.layout.edit_item,null);
        final EditText mEditAyat = (EditText)content.findViewById(R.id.input_ayat);
        final EditText mEditKegiatan = (EditText)content.findViewById(R.id.input_kegiatan);
        final Spinner mSpinnerSurah = (Spinner)content.findViewById(R.id.spinner_surah);

        List<String> list_surah = Arrays.asList(getResources().getStringArray(R.array.list_surah));

        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list_surah);

        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerSurah.setAdapter(adapter_spinner);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(content)
                .setTitle("Add Catatan")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Tadarus tadarus = new Tadarus();

                        SimpleDateFormat tanggal = new SimpleDateFormat("EEE, d MMM yyyy");
                        String tanggal_dibuat = tanggal.format(new Date());


                        SimpleDateFormat waktu = new SimpleDateFormat("h:mm a");
                        String jam_dibuat = waktu.format(new Date());



                        tadarus.setPosisi_spinner(mSpinnerSurah.getSelectedItemPosition());
                        tadarus.setId(RealmController.getInstance().getTadarus().size() + System.currentTimeMillis());
                        tadarus.setAyat(mEditAyat.getText().toString());
                        tadarus.setKeterangan(mEditKegiatan.getText().toString());
                        tadarus.setSurah(mSpinnerSurah.getSelectedItem().toString());
                        tadarus.setTanggal(tanggal_dibuat);
                        tadarus.setWaktu(jam_dibuat);

                        mRealm.beginTransaction();
                        mRealm.copyToRealm(tadarus);
                        mRealm.commitTransaction();

                        mAdapter.notifyDataSetChanged();

                        mRecyclerView.scrollToPosition(RealmController.getInstance().getTadarus().size() - 1);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_list_tadarus);
        this.mRealm = RealmController.with(this).getRealm();

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        setupRecycler();

        if (!Prefs.with(this).getPreLoad()){
            setRealmData();
        }

        RealmController.with(this).refresh();

        setRealmAdapter(RealmController.with(this).getTadarus());

    }

    private void setRealmData() {

    }

    private void setupRecycler() {
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TadarusAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }


    public void setRealmAdapter(RealmResults<Tadarus> tadarus) {
        RealmTadarusAdapter realmAdapter = new RealmTadarusAdapter(this.getApplicationContext(), tadarus,true);

        mAdapter.setRealmAdapter(realmAdapter);
        mAdapter.notifyDataSetChanged();

    }


}
