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

public class IntersectedPoint {

    private Vector2 point;

    public IntersectedPoint(Vector2 point) {
        this.point = point;
    }

    public Vector2 getPoint() {
        return point;
    }

    public void setPoint(Vector2 point) {
        this.point = point;
    }

    public boolean isContainedInLine(IntersectingLine intersectingLine) {
        Vector2 begin = intersectingLine.extractBeginPoint();
        Vector2 end = intersectingLine.extractEndPoint();
        if ((point.x >= begin.x && point.x <= end.x) ||
                (point.x >= end.x && point.x <= begin.x)) {
            if ((point.y >= begin.y && point.y <= end.y) ||
                    (point.y >= end.y && point.y <= begin.y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPointDefined() {
        return !Float.isInfinite(point.x) && !Float.isNaN(point.x);
    }

}
