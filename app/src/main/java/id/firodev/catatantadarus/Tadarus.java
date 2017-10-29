package id.firodev.catatantadarus;

import io.realm.RealmObject;

/**
 * Created by Ariaseta on 06/05/2017.
 */

public class Tadarus extends RealmObject {

    private long id;

    private int posisi_spinner;
    private String surah;
    private String ayat;
    private String keterangan;
    private String tanggal;
    private String waktu;

    public String getSurah() {
        return surah;
    }

    public void setSurah(String surah) {
        this.surah = surah;
    }

    public String getAyat() {
        return ayat;
    }

    public void setAyat(String ayat) {
        this.ayat = ayat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public int getPosisi_spinner() {
        return posisi_spinner;
    }

    public void setPosisi_spinner(int posisi_spinner) {
        this.posisi_spinner = posisi_spinner;
    }
}
