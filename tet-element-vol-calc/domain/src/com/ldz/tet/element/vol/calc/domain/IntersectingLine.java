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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IntersectingLine {

    private static final String BEGIN_POINT_KEY = "BEGIN_POINT_KEY";
    private static final String END_POINT_KEY = "END_POINT_KEY";

    private Polyline polyline;

    public IntersectingLine(Polyline polyline) {
        this.polyline = polyline;
    }

    public IntersectingLine(Vector2 begin, Vector2 end) {
        float[] vertices = new float[]{
                begin.x, begin.y, end.x, end.y
        };
        polyline = new Polyline(vertices);
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    private float[] getVertices() {
        return polyline.getVertices();
    }

    private Map<String, Vector2> extractBeginAndEndPoints() {
        float[] lineVertices = getVertices();
        Vector2 begin = new Vector2(lineVertices[0], lineVertices[1]);
        Vector2 end = new Vector2(lineVertices[2], lineVertices[3]);

        Map<String, Vector2> output = new HashMap<>();
        output.put(BEGIN_POINT_KEY, begin);
        output.put(END_POINT_KEY, end);
        return output;
    }

    public boolean isALine() {
        float[] vertices = getVertices();
        return (vertices.length == 4);
    }

    public void displayDebug(IShapeDebugger iShapeDebugger) {

        Map<String, Vector2> beginAndEndPoints = extractBeginAndEndPoints();

        if (iShapeDebugger.isEnabled()) {
            iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
                @Override
                public Void apply(ShapeRenderer shapeRenderer) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.WHITE);

                    shapeRenderer.line(beginAndEndPoints.get(BEGIN_POINT_KEY), beginAndEndPoints.get(END_POINT_KEY));
                    shapeRenderer.end();
                    return null;
                }
            }, Gdx.graphics.getDeltaTime());
        }

    }

    public Vector2 extractBeginPoint() {
        Map<String, Vector2> points = extractBeginAndEndPoints();
        return points.get(BEGIN_POINT_KEY);
    }

    public Vector2 extractEndPoint() {
        Map<String, Vector2> points = extractBeginAndEndPoints();
        return points.get(END_POINT_KEY);
    }
}
