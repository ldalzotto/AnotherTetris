package com.ldz.itf;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

public class RewardLine {

    private Vector2 beginUpperPoint;
    private Vector2 endUpperPoint;

    private Vector2 beginCenterPoint;
    private Vector2 endCenterPoint;

    private Vector2 beginLowerPoint;
    private Vector2 endLowerPoint;


    public Vector2 getBeginCenterPoint() {
        return beginCenterPoint;
    }

    public void setBeginCenterPoint(Vector2 beginCenterPoint) {
        this.beginCenterPoint = beginCenterPoint;
    }

    public Vector2 getEndCenterPoint() {
        return endCenterPoint;
    }

    public void setEndCenterPoint(Vector2 endCenterPoint) {
        this.endCenterPoint = endCenterPoint;
    }

    public Vector2 getBeginUpperPoint() {
        return beginUpperPoint;
    }

    public void setBeginUpperPoint(Vector2 beginUpperPoint) {
        this.beginUpperPoint = beginUpperPoint;
    }

    public Vector2 getEndUpperPoint() {
        return endUpperPoint;
    }

    public void setEndUpperPoint(Vector2 endUpperPoint) {
        this.endUpperPoint = endUpperPoint;
    }

    public Vector2 getBeginLowerPoint() {
        return beginLowerPoint;
    }

    public void setBeginLowerPoint(Vector2 beginLowerPoint) {
        this.beginLowerPoint = beginLowerPoint;
    }

    public Vector2 getEndLowerPoint() {
        return endLowerPoint;
    }

    public void setEndLowerPoint(Vector2 endLowerPoint) {
        this.endLowerPoint = endLowerPoint;
    }

    public Polyline getUpperLineAsPolyLine(){
        return new Polyline(new float[]{
                this.beginUpperPoint.x, this.beginUpperPoint.y,
                this.endUpperPoint.x, this.endUpperPoint.y
        });
    }


    public Polyline getLowerLineAsPolyLine(){
        return new Polyline(new float[]{
                this.beginLowerPoint.x, this.beginLowerPoint.y,
                this.endLowerPoint.x, this.endLowerPoint.y
        });
    }

}
