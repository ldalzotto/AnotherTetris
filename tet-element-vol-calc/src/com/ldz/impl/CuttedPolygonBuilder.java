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

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuttedPolygonBuilder {

    private CuttedPolyonVertices cuttedPolyonVertices;


    public CuttedPolygonBuilder(CuttedPolyonVertices cuttedPolyonVertices) {
        this.cuttedPolyonVertices = cuttedPolyonVertices;
    }

    public List<Polygon> getSplitedPolygons() {

        List<Polygon> polygons = new ArrayList<>();

        //first
        List<Vector2> firstCuttedVertices = cuttedPolyonVertices.getFirstCuttedPolygon();
        polygons.add(createPolygon(firstCuttedVertices));

        //second
        List<Vector2> secondCuttedVertices = cuttedPolyonVertices.getSecondCuttedPolygon();
        polygons.add(createPolygon(secondCuttedVertices));

        return polygons;
    }

    private Polygon createPolygon(List<Vector2> polygonVertices) {
        float[] finalPolygonVertices2 = new float[polygonVertices.size() * 2];
        for (int i = 0; i < polygonVertices.size(); i++) {
            finalPolygonVertices2[i * 2] = polygonVertices.get(i).x;
            finalPolygonVertices2[(i * 2) + 1] = polygonVertices.get(i).y;
        }
        return new Polygon(finalPolygonVertices2);
    }
}
