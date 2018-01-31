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
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class VolumePolygon {

    private Polygon polygon;

    public VolumePolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public List<Vector2> getVerticesAsVector() {
        float[] vertices = polygon.getTransformedVertices();
        List<Vector2> polygonPoints = new ArrayList<>();
        for (int i = 0; i < vertices.length; i += 2) {
            polygonPoints.add(new Vector2(vertices[i], vertices[i + 1]));
        }

        return new ArrayList<>(polygonPoints);
    }

    public void displayDebug(IShapeDebugger iShapeDebugger, int index) {
        if (iShapeDebugger.isEnabled()) {
            iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
                @Override
                public Void apply(ShapeRenderer shapeRenderer) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    if (index == 1) {
                        shapeRenderer.setColor(Color.CORAL);
                    } else {
                        shapeRenderer.setColor(Color.BLUE);
                    }
                    float[] vertices = polygon.getTransformedVertices();
                    List<Vector2> polygonPoints = new ArrayList<>();
                    for (int i = 0; i < vertices.length; i += 2) {
                        polygonPoints.add(new Vector2(vertices[i], vertices[i + 1]));
                    }

                    for (int i = 0; i < polygonPoints.size(); i++) {
                        if (i != polygonPoints.size() - 1) {
                            shapeRenderer.line(polygonPoints.get(i), polygonPoints.get(i + 1));
                        } else {
                            shapeRenderer.line(polygonPoints.get(i), polygonPoints.get(0));
                        }
                    }
                    shapeRenderer.end();

                    return null;
                }
            }, Gdx.graphics.getDeltaTime());
        }
    }

}
