package com.ldz.itf;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;
import java.util.List;

public class FixtureToPolygonUtil {

    public static Polygon fixtureToPolygon(Fixture fixture){
        if(fixture!=null && fixture.getShape() !=null && fixture.getShape() instanceof PolygonShape){

            //get origin transform
            Vector2 originPosition = fixture.getBody().getTransform().getPosition();

           PolygonShape polygonShape = ((PolygonShape)fixture.getShape());
           List<Float> vertices = new ArrayList<>();
           int vertexCount = polygonShape.getVertexCount();
           for (int i = 0; i<vertexCount; i++){
               Vector2 vertex = new Vector2();
               polygonShape.getVertex(i, vertex);
               vertices.add(vertex.x+originPosition.x);
               vertices.add(vertex.y+originPosition.y);
           }

           float[] verticesf = new float[vertices.size()];

           for (int i = 0; i<vertices.size(); i++){
               verticesf[i]=vertices.get(i);
           }
           return new Polygon(verticesf);

        }
     return null;
    }

}
