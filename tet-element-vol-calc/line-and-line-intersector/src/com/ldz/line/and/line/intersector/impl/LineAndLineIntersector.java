package com.ldz.line.and.line.intersector.impl;

import com.badlogic.gdx.math.Vector2;
import com.ldz.line.and.line.intersector.itf.ILineAndLineIntersector;
import com.ldz.tet.element.vol.calc.domain.IntersectedPoint;
import com.ldz.tet.element.vol.calc.domain.IntersectingLine;

public class LineAndLineIntersector implements ILineAndLineIntersector {


    private IntersectingLine intersectingLine1;
    private IntersectingLine intersectingLine2;

    public static ILineAndLineIntersector createInstance(IntersectingLine intersectingLine1, IntersectingLine intersectingLine2){
        return new LineAndLineIntersector(intersectingLine1, intersectingLine2);
    }

    private LineAndLineIntersector(IntersectingLine intersectingLine1, IntersectingLine intersectingLine2) {
        this.intersectingLine1 = intersectingLine1;
        this.intersectingLine2 = intersectingLine2;
    }

    @Override
    public IntersectedPoint getIntersectionPoint() {

        Vector2 beginPoint1 = intersectingLine1.extractBeginPoint();
        Vector2 endPoint1 = intersectingLine1.extractEndPoint();

        Vector2 beginPoint2 = intersectingLine2.extractBeginPoint();
        Vector2 endPoint2 = intersectingLine2.extractEndPoint();

        return new IntersectedPoint(getIntersectionPoint(beginPoint1, endPoint1, beginPoint2, endPoint2));
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
