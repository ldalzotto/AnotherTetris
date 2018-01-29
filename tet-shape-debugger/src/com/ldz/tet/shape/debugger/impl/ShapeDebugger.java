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

package com.ldz.tet.shape.debugger.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ldz.tet.shape.debugger.itf.Event;
import com.ldz.tet.physics.debugger.itf.IPhysicsDebugger;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;
import com.ldz.tet.physics.debugger.impl.PhysicsDebugger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 *     Implémentation de {@link IShapeDebugger}.
 *     {@see IShapeDebugger}
 * </p>
 */
public final class ShapeDebugger implements IShapeDebugger {

    /**
     * <p>L'instance du singleton</p>
     */
    private static IShapeDebugger instance;

    /**
     * <p>L'instance du debuggeur du monde physique</p>
     */
    private final IPhysicsDebugger iPhysicsDebugger;

    /**
     * <p>L'objet permettant d'afficher le rendu des évènements de debug</p>
     */
    private final ShapeRenderer shapeRenderer;

    /**
     * <p>La file d'attente des évènement de debuggage</p>
     */
    private final List<Event> drawEvents = new ArrayList<>();

    /**
     * <p>Constructeur</p>
     */
    private ShapeDebugger() {
        this.shapeRenderer = new ShapeRenderer();
        this.iPhysicsDebugger = PhysicsDebugger.getInstance();
    }

    /**
     * <p>Récupération de l'instance statique</p>
     * @return instance statique
     */
    public static IShapeDebugger getInstance() {
        synchronized (ShapeDebugger.class) {
            if (instance == null) {
                instance = new ShapeDebugger();
            }
            return instance;
        }
    }

    @Override
    public void render(final Camera camera, final float delta) {
        if (this.iPhysicsDebugger.isDebugEnabled()) {
            this.shapeRenderer.setProjectionMatrix(camera.combined);

            for (final Iterator<Event> eventIterator = this.drawEvents.iterator(); eventIterator.hasNext(); ) {
                final Event event = eventIterator.next();
                event.applyEvent();
                event.makeTimeElapse(delta);
            }

            List<Event> tmp = new ArrayList<>(this.drawEvents);

            for (final Iterator<Event> eventIterator = tmp.iterator(); eventIterator.hasNext(); ) {
                Event event = eventIterator.next();
                if (event.noTimeLeft()) {
                    this.drawEvents.remove(event);
                }
            }

        }
    }

    @Override
    public boolean pushDrawEvent(final Function<ShapeRenderer, Void> event, final float
            time) {
        if (this.iPhysicsDebugger.isDebugEnabled()) {
            Event event1 = new Event();
            event1.setEventProcess(event);
            event1.setTimeLeft(time);
            event1.setShapeRendererReference(shapeRenderer);
            return this.drawEvents.add(event1);
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.iPhysicsDebugger.isDebugEnabled();
    }

    /**
     * <p>getDrawEvents</p>
     * @return {@link #drawEvents}
     */
    public List<Event> getDrawEvents() {
        return drawEvents;
    }

    /**
     * <p>getiPhysicsDebugger</p>
     * @return {@link #iPhysicsDebugger}
     */
    public IPhysicsDebugger getiPhysicsDebugger() {
        return iPhysicsDebugger;
    }

    /**
     * <p>getShapeRenderer</p>
     * @return {@link #shapeRenderer}
     */
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
}
