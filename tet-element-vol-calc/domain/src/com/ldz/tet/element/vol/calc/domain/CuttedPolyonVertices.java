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

package com.ldz.tet.element.vol.calc.domain;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class CuttedPolyonVertices {

    private List<Vector2> allVertices;

    private Vector2 firstCuttedVertice;
    private Vector2 secondCuttedVertice;

    public CuttedPolyonVertices(List<Vector2> allVertices, Vector2 firstCuttedVertice, Vector2 secondCuttedVertice) {
        this.allVertices = allVertices;
        this.firstCuttedVertice = firstCuttedVertice;
        this.secondCuttedVertice = secondCuttedVertice;
    }

    private int getFirstCuttedIndex() {
        return allVertices.indexOf(firstCuttedVertice);
    }

    private int getSecondCuttedIndex() {
        return allVertices.indexOf(secondCuttedVertice);
    }


    public List<Vector2> getFirstCuttedPolygon() {
        return this.getAllVerticesBetweenIndexes(getFirstCuttedIndex(), getSecondCuttedIndex());
    }

    public List<Vector2> getSecondCuttedPolygon() {
        return this.getAllVerticesBetweenIndexes(getSecondCuttedIndex(), getFirstCuttedIndex());
    }

    private List<Vector2> getAllVerticesBetweenIndexes(int begin, int end) {

        List<Vector2> returnVertices = new ArrayList<>();

        if (end > begin) {
            for (int i = begin; i <= end; i++) {
                returnVertices.add(allVertices.get(i));
            }
        } else {
            for (int i = begin; i < allVertices.size(); i++) {
                returnVertices.add(allVertices.get(i));
            }
            for (int i = 0; i <= end; i++) {
                returnVertices.add(allVertices.get(i));
            }
        }

        return returnVertices;

    }

}
