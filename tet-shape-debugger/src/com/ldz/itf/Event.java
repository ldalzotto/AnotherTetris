package com.ldz.itf;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.function.Function;

public class Event {

    private Function<ShapeRenderer, Void> event;
    private float timeLeft = 0f;

    public Function<ShapeRenderer, Void> getEvent() {
        return event;
    }

    public void setEvent(Function<ShapeRenderer, Void> event) {
        this.event = event;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }
}
