package com.srija.lab_a1_a2_android_srija_c0813274.models;

import java.io.Serializable;


public class Provider implements Serializable {

    private int providerId;
    private String providerName;
    private String providerEmail;
    private String providerPhone;
    private double providerLat;
    private double providerLng;
    private int count;

    public Provider() {
    }

    public Provider(String providerName, String providerEmail, String providerPhone, double providerLat, double providerLng) {
        this.providerName = providerName;
        this.providerEmail = providerEmail;
        this.providerPhone = providerPhone;
        this.providerLat = providerLat;
        this.providerLng = providerLng;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    public double getProviderLat() {
        return providerLat;
    }

    public void setProviderLat(double providerLat) {
        this.providerLat = providerLat;
    }

    public double getProviderLng() {
        return providerLng;
    }

    public void setProviderLng(double providerLng) {
        this.providerLng = providerLng;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}