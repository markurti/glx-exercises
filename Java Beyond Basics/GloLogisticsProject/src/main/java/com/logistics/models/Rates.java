package com.logistics.models;

public class Rates {
    private float ground;
    private float air;
    private float ocean;

    public Rates(float ground, float air, float ocean) {
        this.ground = ground;
        this.air = air;
        this.ocean = ocean;
    }

    public float getGround() { return ground; }
    public void setGround(float ground) { this.ground = ground; }

    public float getAir() { return air; }
    public void setAir(float air) { this.air = air; }

    public float getOcean() { return ocean; }
    public void setOcean(float ocean) { this.ocean = ocean; }

    @Override
    public String toString() {
        return "Ground: $" + ground + ", Air: $" + air + ", Ocean: $" + ocean;
    }
}
