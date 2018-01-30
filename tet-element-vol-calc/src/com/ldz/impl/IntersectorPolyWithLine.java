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

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class IntersectorPolyWithLine {

    private IntersectingLine intersectingLine;
    private VolumePolygon volumePolygon;

    public IntersectorPolyWithLine(IntersectingLine intersectingLine, VolumePolygon volumePolygon) {
        this.intersectingLine = intersectingLine;
        this.volumePolygon = volumePolygon;
    }

    public boolean isIntersecting() {
        Vector2 beginPoint = intersectingLine.extractBeginPoint();
        Vector2 endPoint = intersectingLine.extractEndPoint();
        Polygon polygon = volumePolygon.getPolygon();
        return Intersector.intersectLinePolygon(beginPoint, endPoint, polygon);
    }

    public List<VolumePolygon> getCuttedPolygons() {

        PolygonCutter polygonCutter = new PolygonCutter(volumePolygon, intersectingLine);
        CuttedPolyonVertices cuttedPolyonVertices = polygonCutter.executeCuttedVertices();

        if (cuttedPolyonVertices != null) {

            CuttedPolygonBuilder cuttedPolygonBuilder = new CuttedPolygonBuilder(cuttedPolyonVertices);
            List<Polygon> polygons = cuttedPolygonBuilder.getSplitedPolygons();
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
