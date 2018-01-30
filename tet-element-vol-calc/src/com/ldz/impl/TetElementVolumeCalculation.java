package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;
import com.ldz.itf.ITetElementVolumeCalculation;
import com.ldz.tet.shape.debugger.impl.ShapeDebugger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TetElementVolumeCalculation implements ITetElementVolumeCalculation {

    private static ITetElementVolumeCalculation instance;

    public static ITetElementVolumeCalculation getInstance() {
        if (instance == null) {
            instance = new TetElementVolumeCalculation();
        }
        return instance;
    }

    private IShapeDebugger iShapeDebugger;

    private TetElementVolumeCalculation() {
        this.iShapeDebugger = ShapeDebugger.getInstance();
    }

    @Override
    public List<Polygon> splitPolygonFromLine(Polygon polygon, Polyline polyline) {

        VolumePolygon volumePolygon = new VolumePolygon(polygon);
        IntersectingLine intersectingLine = new IntersectingLine(polyline);
        List<Polygon> returnedPolygons = new ArrayList<>();

        if (intersectingLine.isALine()) {
            intersectingLine.displayDebug(iShapeDebugger);

            IntersectorPolyWithLine intersectorPolyWithLine = new IntersectorPolyWithLine(intersectingLine,
                    volumePolygon);

            if (intersectorPolyWithLine.isIntersecting()) {

                List<VolumePolygon> cuttedPolygons = intersectorPolyWithLine.getCuttedPolygons();

                for (VolumePolygon intersectedPolygon :
                        cuttedPolygons) {

                    intersectedPolygon.displayDebug(iShapeDebugger, cuttedPolygons.indexOf(intersectedPolygon));
                    returnedPolygons.add(intersectedPolygon.getPolygon());

                }

            }

        }

        return returnedPolygons;
    }

    @Override
    public List<Float> calculatePolygonVolumes(List<Polygon> polygons) {
        List<Float> areas = new ArrayList<>();
        for (Polygon polygon :
                polygons) {
            areas.add(polygon.area());
        }
        return areas;
    }
}
