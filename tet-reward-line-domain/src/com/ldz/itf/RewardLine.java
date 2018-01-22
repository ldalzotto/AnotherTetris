package com.ldz.itf;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

public class RewardLine {

    private Vector2 beginPoint;
    private Vector2 endPoint;

    public Vector2 getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint(Vector2 beginPoint) {
        this.beginPoint = beginPoint;
    }

    public Vector2 getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Vector2 endPoint) {
        this.endPoint = endPoint;
    }
}
