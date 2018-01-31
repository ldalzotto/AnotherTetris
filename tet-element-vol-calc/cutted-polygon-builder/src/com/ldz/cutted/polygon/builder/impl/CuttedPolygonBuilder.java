package com.ldz.cutted.polygon.builder.impl;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.ldz.cutted.polygon.builder.itf.ICuttedPolygonBuilder;
import com.ldz.tet.element.vol.calc.domain.CuttedPolyonVertices;

import java.util.ArrayList;
import java.util.List;

public class CuttedPolygonBuilder implements ICuttedPolygonBuilder {

    private CuttedPolyonVertices cuttedPolyonVertices;

    public static ICuttedPolygonBuilder createInstance(CuttedPolyonVertices cuttedPolyonVertices){
        return new CuttedPolygonBuilder(cuttedPolyonVertices);
    }

    private CuttedPolygonBuilder(CuttedPolyonVertices cuttedPolyonVertices) {
        this.cuttedPolyonVertices = cuttedPolyonVertices;
    }


    @Override
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
