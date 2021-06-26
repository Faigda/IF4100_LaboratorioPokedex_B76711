package com.example.if4100_laboratoriopokedex_b76711.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {

    private int number;
    private String name;
    private String url;

    public String getName() {
        char[] arr = name.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
