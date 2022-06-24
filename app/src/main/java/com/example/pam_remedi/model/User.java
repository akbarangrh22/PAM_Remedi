package com.example.pam_remedi.model;

public class User {

    private String id, nama, subuh, dzuhur, ashar, maghrib, isya;

    public User(String nama, String subuh, String dzuhur, String ashar, String maghrib, String isya) {
        this.nama = nama;
        this.subuh = subuh;
        this.dzuhur = dzuhur;
        this.ashar = ashar;
        this.maghrib = maghrib;
        this.isya = isya;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return  nama;
    }

    public void setName(String nama) {
        this.nama = nama;
    }

    public String getSubuh() {
        return subuh;
    }

    public void setSubuh(String subuh) {
        this.subuh = subuh;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public void setDzuhur(String dzuhur) {
        this.dzuhur = dzuhur;
    }

    public String getAshar() {
        return ashar;
    }

    public void setAshar(String ashar) {
        this.ashar = ashar;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }
}

