/*
 *  (C) Copyright 2018 LDZCorp and others.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 */

package com.ldz.impl;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PolygonCutter {

    private VolumePolygon volumePolygon;
    private IntersectingLine intersectingLine;

    public PolygonCutter(VolumePolygon volumePolygon, IntersectingLine intersectingLine) {
        this.volumePolygon = volumePolygon;
        this.intersectingLine = intersectingLine;
    }

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

            IntersectorLine intersectorLine = new IntersectorLine(polygonFace, intersectingLine);
            intersectionPoint = intersectorLine.getIntersectionPoint();

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

    private boolean isPointDefined(Vector2 point) {
        return !Float.isInfinite(point.x) && !Float.isNaN(point.x);
    }
}