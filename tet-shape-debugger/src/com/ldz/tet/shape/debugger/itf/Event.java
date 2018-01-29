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

package com.ldz.tet.shape.debugger.itf;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.function.Function;

public class Event {

    private static final Float NO_TIME_LEFT = 0f;

    public Event() {
        //do nothing
    }

    private Function<ShapeRenderer, Void> eventProcess;
    private float timeLeft;
    private ShapeRenderer shapeRendererReference;

    public Function<ShapeRenderer, Void> getEventProcess() {
        return eventProcess;
    }

    public void setEventProcess(Function<ShapeRenderer, Void> eventProcess) {
        this.eventProcess = eventProcess;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }

    public ShapeRenderer getShapeRendererReference() {
        return shapeRendererReference;
    }

    public void setShapeRendererReference(ShapeRenderer shapeRendererReference) {
        this.shapeRendererReference = shapeRendererReference;
    }

    public void applyEvent() {
        this.eventProcess.apply(this.shapeRendererReference);
    }

    public boolean noTimeLeft() {
        return this.getTimeLeft() <= NO_TIME_LEFT;
    }

    public void makeTimeElapse(final float delta) {
        this.setTimeLeft(this.getTimeLeft() - delta);
    }
}
