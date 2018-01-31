package com.ldz.polygon.and.line.intersector.itf;

import com.ldz.tet.element.vol.calc.domain.IntersectingLine;
import com.ldz.tet.element.vol.calc.domain.VolumePolygon;

import java.util.List;

public interface IPolygonAndLineIntersector {

    public boolean isIntersecting();

    public List<VolumePolygon> getCuttedPolygons();

}
