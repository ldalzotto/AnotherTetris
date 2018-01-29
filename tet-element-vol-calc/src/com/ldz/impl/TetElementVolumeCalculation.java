package com.ldz.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;
import com.ldz.itf.ITetElementVolumeCalculation;
import com.ldz.tet.shape.debugger.impl.ShapeDebugger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TetElementVolumeCalculation implements ITetElementVolumeCalculation {

    private static ITetElementVolumeCalculation instance;
    public static ITetElementVolumeCalculation getInstance(){
        if(instance==null){
            instance=new TetElementVolumeCalculation();
        }
        return instance;
    }

    private IShapeDebugger iShapeDebugger;

    private TetElementVolumeCalculation()  {
        this.iShapeDebugger = ShapeDebugger.getInstance();
    }

    @Override
    public List<Polygon> splitPolygonFromLine(Polygon polygon, Polyline polyline) {
        List<Polygon> returnedPolygons = new ArrayList<>();
        float[] lineVertices = polyline.getVertices();
        if (lineVertices.length == 4){
            Vector2 begin = new Vector2(lineVertices[0], lineVertices[1]);
            Vector2 end = new Vector2(lineVertices[2], lineVertices[3]);
            if(this.iShapeDebugger.isEnabled()){
                this.iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
                    @Override
                    public Void apply(ShapeRenderer shapeRenderer) {
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                        shapeRenderer.setColor(Color.WHITE);

                        shapeRenderer.line(begin, end);
                        shapeRenderer.end();
                        return null;
                    }
                }, Gdx.graphics.getDeltaTime());
            }

           if(Intersector.intersectLinePolygon(begin, end, polygon)){
               List<Polygon> intersectedPolygons = getIntersectionsPoints(polygon, begin, end);
               for (Polygon intersectedPolygon :
                       intersectedPolygons) {
                   if(this.iShapeDebugger.isEnabled()){
                       this.iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
                           @Override
                           public Void apply(ShapeRenderer shapeRenderer) {
                               shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                               if(intersectedPolygons.indexOf(intersectedPolygon)==1){
                                   shapeRenderer.setColor(Color.CORAL);
                               }else {
                                   shapeRenderer.setColor(Color.BLUE);
                               }
                               float[] vertices = intersectedPolygon.getTransformedVertices();
                               List<Vector2> polygonPoints = new ArrayList<>();
                               for (int i = 0; i<vertices.length; i+=2){
                                   polygonPoints.add(new Vector2(vertices[i], vertices[i+1]));
                               }

                               for (int i =0; i<polygonPoints.size(); i++){
                                   if(i!=polygonPoints.size()-1){
                                       shapeRenderer.line(polygonPoints.get(i), polygonPoints.get(i+1));
                                   }else {
                                       shapeRenderer.line(polygonPoints.get(i), polygonPoints.get(0));
                                   }
                               }
                               shapeRenderer.end();

                               return null;
                           }
                       }, Gdx.graphics.getDeltaTime());
                   }
                   returnedPolygons.add(intersectedPolygon);
               }

           }

        }
        return returnedPolygons;
    }

    private List<Polygon> getIntersectionsPoints(Polygon polygon, Vector2 begin, Vector2 end){

        List<Vector2> intersectionPoints = new ArrayList<>();

        float[] vertices = polygon.getTransformedVertices();
        List<Vector2> polygonPoints = new ArrayList<>();
        for (int i = 0; i<vertices.length; i+=2){
            polygonPoints.add(new Vector2(vertices[i], vertices[i+1]));
        }

        List<Vector2> polygonPointsWithCut = new ArrayList<>(polygonPoints);

        Integer firtIntersectionIndex = null;
        Integer secondIntersectionIndex = null;

        for (int i =0; i<polygonPoints.size(); i++){
            Vector2 intersectionPoint;
            if(i==polygonPoints.size()-1){
                intersectionPoint = this.getIntersectionPoint(polygonPoints.get(i), polygonPoints.get(0), begin, end);
                if(!Float.isInfinite(intersectionPoint.x) && !Float.isNaN(intersectionPoint.x)){
                    if(this.checkIfPointContainedInLine(intersectionPoint, polygonPoints.get(i), polygonPoints.get(0))){

                        if(firtIntersectionIndex == null){
                            if(polygonPointsWithCut.size() == polygonPoints.size()){
                                polygonPointsWithCut.add(intersectionPoint);
                            } else {
                                polygonPointsWithCut.add(polygonPoints.size()+1, intersectionPoint);
                            }
                            firtIntersectionIndex = polygonPoints.size()+1;
                        }else {
                            if(polygonPointsWithCut.size() == polygonPoints.size()) {
                                polygonPointsWithCut.add(intersectionPoint);
                            } else {
                                polygonPointsWithCut.add(polygonPoints.size()+1, intersectionPoint);
                            }
                            secondIntersectionIndex = polygonPoints.size()+1;
                        }
                    }
                }
            }else {
                intersectionPoint = this.getIntersectionPoint(polygonPoints.get(i), polygonPoints.get(i+1), begin, end);
                if(!Float.isInfinite(intersectionPoint.x) && !Float.isNaN(intersectionPoint.x)){
                    if(this.checkIfPointContainedInLine(intersectionPoint, polygonPoints.get(i), polygonPoints.get(i+1))) {

                        if(firtIntersectionIndex == null){
                            polygonPointsWithCut.add(i+1, intersectionPoint);
                            firtIntersectionIndex = i+1;
                        }else {
                            polygonPointsWithCut.add(i+2, intersectionPoint);
                            secondIntersectionIndex = i+2;
                        }
                    }
                }
            }
        }

        //polygon creations
        if(firtIntersectionIndex != null && secondIntersectionIndex != null){
            List<Polygon> polygons = new ArrayList<>();

            List<Vector2> polygonVertices = new ArrayList<>();
            for (int i = firtIntersectionIndex; i <= secondIntersectionIndex; i++){
                polygonVertices.add(polygonPointsWithCut.get(i));
            }
            float[] finalPolygonVertices = new float[polygonVertices.size()*2];
            for (int i = 0; i<polygonVertices.size(); i++){
                finalPolygonVertices[i*2] = polygonVertices.get(i).x;
                finalPolygonVertices[(i*2)+1] = polygonVertices.get(i).y;
            }
            polygons.add(new Polygon(finalPolygonVertices));

            //second polygon
            polygonVertices.clear();
            for(int i = secondIntersectionIndex; i<polygonPointsWithCut.size(); i++){
                polygonVertices.add(polygonPointsWithCut.get(i));
            }
            for (int i = 0; i<=firtIntersectionIndex; i++){
                polygonVertices.add(polygonPointsWithCut.get(i));
            }

            float[] finalPolygonVertices2 = new float[polygonVertices.size()*2];
            for (int i = 0; i<polygonVertices.size(); i++){
                finalPolygonVertices2[i*2] = polygonVertices.get(i).x;
                finalPolygonVertices2[(i*2)+1] = polygonVertices.get(i).y;
            }
            polygons.add(new Polygon(finalPolygonVertices2));
            return polygons;
        } else {
            return Arrays.asList(polygon);
        }
    }

    private boolean checkIfPointContainedInLine(Vector2 point, Vector2 begin, Vector2 end){
        if((point.x >= begin.x && point.x <= end.x) ||
                (point.x >= end.x && point.x <= begin.x)  ){
            if((point.y >= begin.y && point.y <= end.y) ||
                    (point.y >= end.y && point.y <= begin.y)  ){
                return true;
            }
        }
        return false;
    }

    private Vector2 getIntersectionPoint(Vector2 beginPoly, Vector2 endPoly, Vector2 beginLine, Vector2 endLine){
        float x1 = beginPoly.x;
        float y1 = beginPoly.y;
        float x2 = endPoly.x;
        float y2 = endPoly.y;

        float x3 = beginLine.x;
        float y3 = beginLine.y;
        float x4 = endLine.x;
        float y4 = endLine.y;

        return new Vector2(
                (( (((x1*y2)-(y1*x2))*(x3-x4)) - ((x1-x2)*((x3*y4)-(y3*x4))) )
                        / (((x1-x2)*(y3-y4)) - ((y1-y2)*(x3-x4)))),
                (( (((x1*y2)-(y1*x2))*(y3-y4)) - ((y1-y2)*((x3*y4)-(y3*x4))) )
                        / (((x1-x2)*(y3-y4)) - ((y1-y2)*(x3-x4))))
        );

    }

    @Override
    public List<Float> calculatePolygonVolumes(List<Polygon> polygons) {
        List<Float> areas = new ArrayList<>();
        for (Polygon polygon :
                polygons) {
            areas.add(polygon.area());
        }
        return areas;
    }
}
