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

public class IntersectorLine {

    private IntersectingLine intersectingLine1;
    private IntersectingLine intersectingLine2;

    public IntersectorLine(IntersectingLine intersectingLine1, IntersectingLine intersectingLine2) {
        this.intersectingLine1 = intersectingLine1;
        this.intersectingLine2 = intersectingLine2;
    }

    public IntersectedPoint getIntersectionPoint() {

        Vector2 beginPoint1 = intersectingLine1.extractBeginPoint();
        Vector2 endPoint1 = intersectingLine1.extractEndPoint();

        Vector2 beginPoint2 = intersectingLine2.extractBeginPoint();
        Vector2 endPoint2 = intersectingLine2.extractEndPoint();

        return new IntersectedPoint(getIntersectionPoint(beginPoint1, endPoint1, beginPoint2, endPoint2));
    }

    public IntersectingLine getIntersectingLine1() {
        return intersectingLine1;
    }

    public void setIntersectingLine1(IntersectingLine intersectingLine1) {
        this.intersectingLine1 = intersectingLine1;
    }

    public IntersectingLine getIntersectingLine2() {
        return intersectingLine2;
    }

    public void setIntersectingLine2(IntersectingLine intersectingLine2) {
        this.intersectingLine2 = intersectingLine2;
    }

    private Vector2 getIntersectionPoint(Vector2 beginPoly, Vector2 endPoly, Vector2 beginLine, Vector2 endLine) {
        float x1 = beginPoly.x;
        float y1 = beginPoly.y;
        float x2 = endPoly.x;
        float y2 = endPoly.y;

        float x3 = beginLine.x;
        float y3 = beginLine.y;
        float x4 = endLine.x;
        float y4 = endLine.y;

        return new Vector2(
                (((((x1 * y2) - (y1 * x2)) * (x3 - x4)) - ((x1 - x2) * ((x3 * y4) - (y3 * x4))))
                        / (((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4)))),
                (((((x1 * y2) - (y1 * x2)) * (y3 - y4)) - ((y1 - y2) * ((x3 * y4) - (y3 * x4))))
                        / (((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4))))
        );

    }

}
