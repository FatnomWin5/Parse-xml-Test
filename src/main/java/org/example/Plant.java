package org.example;

public class Plant {

    private String common;
    private String botanical;
    private int zone;
    private String light;
    private float price;
    private int availability;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getBotanical() {
        return botanical;
    }

    public void setBotanical(String botanical) {
        this.botanical = botanical;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String plantToString() {
        return "COMMON: " + this.common + '\n' +
                "BOTANICAL: " + this.botanical + '\n' +
                "ZONE: " + this.zone + '\n' +
                "LIGHT: " + this.light + '\n' +
                "PRICE: " + this.price + '\n' +
                "AVAILABILITY: " + this.availability + '\n';
    }
}
