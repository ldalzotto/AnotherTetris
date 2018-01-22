package com.ldz.itf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;

import java.util.List;

public interface ITetElementVolumeCalculation {

    List<Polygon> splitPolygonFromLine(Polygon polygon, Polyline polyline, Color color1, Color color2);

    List<Float> calculatePolygonVolumes(List<Polygon> polygons);

}
