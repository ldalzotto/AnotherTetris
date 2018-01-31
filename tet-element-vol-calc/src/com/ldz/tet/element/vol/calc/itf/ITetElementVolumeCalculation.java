package com.ldz.tet.element.vol.calc.itf;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;

import java.util.List;

public interface ITetElementVolumeCalculation {

    List<Polygon> splitPolygonFromLine(Polygon polygon, Polyline polyline);

    List<Float> calculatePolygonVolumes(List<Polygon> polygons);

}
