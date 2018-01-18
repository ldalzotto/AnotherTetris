package com.ldz.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ldz.itf.Event;
import com.ldz.itf.IPhysicsDebugger;
import com.ldz.itf.IShapeDebugger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ShapeDebugger implements IShapeDebugger {

    private static IShapeDebugger instance;
    private IPhysicsDebugger iPhysicsDebugger;
    private ShapeRenderer shapeRenderer;
    private List<Event> drawEvents = new ArrayList<>();

    private ShapeDebugger() {
        this.shapeRenderer = new ShapeRenderer();
        this.iPhysicsDebugger = PhysicsDebugger.getInstance();
    }

    public static IShapeDebugger getInstance() {
        if (instance == null) {
            instance = new ShapeDebugger();
        }
        return instance;
    }

    @Override
    public void render(Camera camera, float delta) {
        if (this.iPhysicsDebugger.isDebugEnabled()) {
            this.shapeRenderer.setProjectionMatrix(camera.combined);

            for (Event event :
                    this.drawEvents) {
                event.getEvent().apply(this.shapeRenderer);
                event.setTimeLeft(event.getTimeLeft() - delta);
            }


            List<Event> tmp = new ArrayList<>();
            tmp.addAll(this.drawEvents);
            for (Event event :
                    tmp) {
                if (event.getTimeLeft() <= 0f) {
                    this.drawEvents.remove(event);
                }
            }


        }
    }

    @Override
    public boolean pushDrawEvent(Function<ShapeRenderer, Void> event, float time) {
        if (this.iPhysicsDebugger.isDebugEnabled()) {
            Event event1 = new Event();
            event1.setEvent(event);
            event1.setTimeLeft(time);
            return this.drawEvents.add(event1);
        }
        return false;
    }
}
