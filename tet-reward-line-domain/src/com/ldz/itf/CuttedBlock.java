package com.ldz.itf;

import com.badlogic.gdx.math.Polygon;

public class CuttedBlock {

    private Polygon upperPolygon;
    private float upperArea;

    private Polygon lowerPolygon;
    private float lowerArea;

    public Polygon getUpperPolygon() {
        return upperPolygon;
    }

    public void setUpperPolygon(Polygon upperPolygon) {
        this.upperPolygon = upperPolygon;
    }

    public float getUpperArea() {
        return upperArea;
    }

    public void setUpperArea(float upperArea) {
        this.upperArea = upperArea;
    }

    public Polygon getLowerPolygon() {
        return lowerPolygon;
    }

    public void setLowerPolygon(Polygon lowerPolygon) {
        this.lowerPolygon = lowerPolygon;
    }

    public float getLowerArea() {
        return lowerArea;
    }

    public void setLowerArea(float lowerArea) {
        this.lowerArea = lowerArea;
    }
}
