package com.ldz.tet.element.vol.calc.impl;

import com.badlogic.gdx.math.*;
import com.ldz.polygon.and.line.intersector.impl.PolygonAndLineIntersector;
import com.ldz.polygon.and.line.intersector.itf.IPolygonAndLineIntersector;
import com.ldz.tet.element.vol.calc.domain.IntersectingLine;
import com.ldz.tet.element.vol.calc.domain.VolumePolygon;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;
import com.ldz.tet.element.vol.calc.itf.ITetElementVolumeCalculation;
import com.ldz.tet.shape.debugger.impl.ShapeDebugger;

import java.util.ArrayList;
import java.util.List;

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

            IPolygonAndLineIntersector iPolygonAndLineIntersector = PolygonAndLineIntersector.createInstance(intersectingLine, volumePolygon);

            if (iPolygonAndLineIntersector.isIntersecting()) {

                List<VolumePolygon> cuttedPolygons = iPolygonAndLineIntersector.getCuttedPolygons();

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
