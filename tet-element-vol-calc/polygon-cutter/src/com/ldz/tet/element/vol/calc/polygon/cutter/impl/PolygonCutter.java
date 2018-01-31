package com.ldz.tet.element.vol.calc.polygon.cutter.impl;

import com.badlogic.gdx.math.Vector2;
import com.ldz.line.and.line.intersector.impl.LineAndLineIntersector;
import com.ldz.line.and.line.intersector.itf.ILineAndLineIntersector;
import com.ldz.tet.element.vol.calc.domain.CuttedPolyonVertices;
import com.ldz.tet.element.vol.calc.domain.IntersectedPoint;
import com.ldz.tet.element.vol.calc.domain.IntersectingLine;
import com.ldz.tet.element.vol.calc.domain.VolumePolygon;
import com.ldz.tet.element.vol.calc.polygon.cutter.itf.IPolygonCutter;

import java.util.ArrayList;
import java.util.List;

public class PolygonCutter implements IPolygonCutter {

    private VolumePolygon volumePolygon;
    private IntersectingLine intersectingLine;

    private PolygonCutter(VolumePolygon volumePolygon, IntersectingLine intersectingLine) {
        this.volumePolygon = volumePolygon;
        this.intersectingLine = intersectingLine;
    }


    public static IPolygonCutter createInstance(VolumePolygon volumePolygon, IntersectingLine intersectingLine) {
        return new PolygonCutter(volumePolygon, intersectingLine);
    }

    @Override
    public CuttedPolyonVertices executeCuttedVertices() {
        List<Vector2> polygonPoints = volumePolygon.getVerticesAsVector();

        List<Vector2> polygonPointsWithCut = new ArrayList<>(polygonPoints);
        List<Vector2> onlyCuttedVertices = new ArrayList<>();

        for (int i = 0; i < polygonPoints.size(); i++) {

            IntersectedPoint intersectionPoint;
            IntersectingLine polygonFace;

            if (i == polygonPoints.size() - 1) {
                polygonFace = new IntersectingLine(polygonPoints.get(i),
                        polygonPoints.get(0));
            } else {
                polygonFace = new IntersectingLine(polygonPoints.get(i),
                        polygonPoints.get(i + 1));
            }

            ILineAndLineIntersector iLineAndLineIntersector = LineAndLineIntersector.createInstance(polygonFace, intersectingLine);
            intersectionPoint = iLineAndLineIntersector.getIntersectionPoint();

            if (intersectionPoint.isPointDefined()) {
                if (intersectionPoint.isContainedInLine(polygonFace)) {

                    Vector2 verticeToAdd = intersectionPoint.getPoint();

                    if (i == polygonPoints.size() - 1) {

                        if (polygonPointsWithCut.size() == polygonPoints.size()) {
                            polygonPointsWithCut.add(verticeToAdd);
                        } else {
                            //insertion in order
                            polygonPointsWithCut.add(polygonPoints.size() + 1, verticeToAdd);
                        }

                    } else {

                        if (onlyCuttedVertices.isEmpty()) {
                            //insertion in order
                            polygonPointsWithCut.add(i + 1, verticeToAdd);
                        } else {
                            polygonPointsWithCut.add(i + 2, verticeToAdd);
                        }

                    }

                    onlyCuttedVertices.add(verticeToAdd);

                }
            }
        }

        if (onlyCuttedVertices.size() == 2) {
            return new CuttedPolyonVertices(polygonPointsWithCut,
                    onlyCuttedVertices.get(0), onlyCuttedVertices.get(1));
        }

        return null;
    }
}
