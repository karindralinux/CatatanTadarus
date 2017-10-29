package id.firodev.catatantadarus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ariaseta on 06/05/2017.
 */

public class TadarusAdapter extends RealmRecyclerViewAdapter<Tadarus> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public TadarusAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TadarusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tes,parent,false);
        return new TadarusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        final Tadarus tadarus = getItem(position);

        final TadarusViewHolder holder = (TadarusViewHolder) viewHolder;

        holder.mSurah.setText(tadarus.getSurah());
        holder.mAyat.setText(tadarus.getAyat());
        holder.mKeterangan.setText(tadarus.getKeterangan());


        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LayoutInflater inflater2;
                inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content_pilih = inflater2.inflate(R.layout.longclick,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content_pilih)
                        .setTitle("");

                final AlertDialog dialog = builder.create();
                dialog.show();

                final TextView edit = (TextView)content_pilih.findViewById(R.id.editClick);
                final TextView delete = (TextView)content_pilih.findViewById(R.id.deleteClick);


                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent edit = new Intent(context,Edit.class);
                        edit.putExtra("posisi",position);
                        context.startActivity(edit);
                        dialog.dismiss();
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RealmResults<Tadarus> results = realm.where(Tadarus.class).findAll();

                        Tadarus t = results.get(position);
                        String kegiatan = t.getKeterangan();

                        realm.beginTransaction();

                        results.remove(position);
                        realm.commitTransaction();

                        if (results.size() == 0) {
                            Prefs.with(context).setPreLoad(false);
                        }

                        notifyDataSetChanged();

                        Toast.makeText(context, kegiatan + " Dihapus Dari Catatan", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });





                return false;
            }
        });

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View content = inflater.inflate(R.layout.longclick,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("");

                final AlertDialog dialog = builder.create();
                dialog.show();

                final TextView edit = (TextView)content.findViewById(R.id.editClick);
                final TextView delete = (TextView)content.findViewById(R.id.deleteClick);


                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        Intent edit = new Intent(context,Edit.class);
                        edit.putExtra("posisi",position);
                        context.startActivity(edit);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RealmResults<Tadarus> results = realm.where(Tadarus.class).findAll();

                        Tadarus t = results.get(position);
                        String kegiatan = t.getKeterangan();

                        realm.beginTransaction();

                        results.remove(position);
                        realm.commitTransaction();

                        if (results.size() == 0) {
                            Prefs.with(context).setPreLoad(false);
                        }

                        notifyDataSetChanged();

                        Toast.makeText(context, kegiatan + " Dihapus Dari Catatan", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });


//                final Spinner mSpinnerSurah = (Spinner)content.findViewById(R.id.spinner_surah);
//                final EditText mEditAyat = (EditText)content.findViewById(R.id.input_ayat);
//                final EditText mEditKegiatan = (EditText)content.findViewById(R.id.input_kegiatan);
//
//
//                mEditAyat.setText(tadarus.getAyat());
//                mEditKegiatan.setText(tadarus.getKeterangan());
//
//                //spinner surahnya belum
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(content)
//                        .setTitle("Edit Catatan")
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                RealmResults<Tadarus> results = realm.where(Tadarus.class).findAll();
//
//                                realm.beginTransaction();
//                                results.get(position).setAyat(mEditAyat.getText().toString());
//                                results.get(position).setKeterangan(mEditKegiatan.getText().toString());
//                                results.get(position).setSurah(mSpinnerSurah.getSelectedItem().toString());
//
//                                realm.commitTransaction();
//                                notifyDataSetChanged();
//                            }
//                        })
//
//                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();

            }
        });

    }


    @Override
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }

        return 0;
    }

    public static class TadarusViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mAyat;
        public TextView mSurah;
        public TextView mKeterangan;
//        public TextView mTanggal;
//        public TextView mWaktu;

        public TadarusViewHolder(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mAyat = (TextView) itemView.findViewById(R.id.ayat);
            mSurah = (TextView) itemView.findViewById(R.id.surah);
            mKeterangan = (TextView) itemView.findViewById(R.id.kegiatan);
//            mTanggal = (TextView) itemView.findViewById(R.id.tanggal);
//            mWaktu = (TextView) itemView.findViewById(R.id.waktu);

        }
    }
}
