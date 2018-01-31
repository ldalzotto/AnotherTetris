package com.ldz.polygon.and.line.intersector.impl;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ldz.cutted.polygon.builder.impl.CuttedPolygonBuilder;
import com.ldz.cutted.polygon.builder.itf.ICuttedPolygonBuilder;
import com.ldz.polygon.and.line.intersector.itf.IPolygonAndLineIntersector;
import com.ldz.tet.element.vol.calc.domain.CuttedPolyonVertices;
import com.ldz.tet.element.vol.calc.domain.IntersectingLine;
import com.ldz.tet.element.vol.calc.domain.VolumePolygon;
import com.ldz.tet.element.vol.calc.polygon.cutter.impl.PolygonCutter;
import com.ldz.tet.element.vol.calc.polygon.cutter.itf.IPolygonCutter;

import java.util.ArrayList;
import java.util.List;

public class PolygonAndLineIntersector implements IPolygonAndLineIntersector {

    private IntersectingLine intersectingLine;
    private VolumePolygon volumePolygon;

    private PolygonAndLineIntersector(IntersectingLine intersectingLine, VolumePolygon volumePolygon) {
        this.intersectingLine = intersectingLine;
        this.volumePolygon = volumePolygon;
    }


    public static IPolygonAndLineIntersector createInstance(IntersectingLine intersectingLine, VolumePolygon volumePolygon) {
        return new PolygonAndLineIntersector(intersectingLine, volumePolygon);
    }

    public boolean isIntersecting() {
        Vector2 beginPoint = intersectingLine.extractBeginPoint();
        Vector2 endPoint = intersectingLine.extractEndPoint();
        Polygon polygon = volumePolygon.getPolygon();
        return Intersector.intersectLinePolygon(beginPoint, endPoint, polygon);
    }

    public List<VolumePolygon> getCuttedPolygons() {

        IPolygonCutter polygonCutter = PolygonCutter.createInstance(volumePolygon, intersectingLine);
        CuttedPolyonVertices cuttedPolyonVertices = polygonCutter.executeCuttedVertices();

        if (cuttedPolyonVertices != null) {

            ICuttedPolygonBuilder iCuttedPolygonBuilder = CuttedPolygonBuilder.createInstance(cuttedPolyonVertices);
            List<Polygon> polygons = iCuttedPolygonBuilder.getSplitedPolygons();

            List<VolumePolygon> volumePolygons = new ArrayList<>();
            for (Polygon polygon :
                    polygons) {
                volumePolygons.add(new VolumePolygon(polygon));
            }
            return volumePolygons;

        } else {
            return new ArrayList<>();
        }

    }
}
