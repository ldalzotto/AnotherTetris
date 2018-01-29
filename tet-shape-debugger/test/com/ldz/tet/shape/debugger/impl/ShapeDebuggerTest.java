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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ldz.tet.shape.debugger.itf.Event;
import com.ldz.tet.shape.debugger.itf.IShapeDebugger;
import com.ldz.tet.physics.debugger.impl.PhysicsDebugger;
import com.ldz.tet.physics.debugger.itf.IPhysicsDebugger;
import com.ldz.tet.shape.debugger.impl.ShapeDebugger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhysicsDebugger.class})
public class ShapeDebuggerTest {

    private static IPhysicsDebugger iPhysicsDebuggerMock;

    @Before
    public void before() {
        Gdx.app = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {

            }

            @Override
            public void resize(int i, int i1) {

            }

            @Override
            public void render() {

            }

            @Override
            public void pause() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void dispose() {

            }
        });
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
        PowerMockito.mockStatic(PhysicsDebugger.class);

        iPhysicsDebuggerMock = Mockito.mock(IPhysicsDebugger
                .class);


        PowerMockito.when(PhysicsDebugger.getInstance()).thenReturn
                (iPhysicsDebuggerMock);
        //set null
        try {
            Field instance = ShapeDebugger.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_getInstance() {

        IShapeDebugger iShapeDebugger = ShapeDebugger.getInstance();

        Assert.assertNotNull("shapeDebugger should not be null !",
                iShapeDebugger);
        Assert.assertEquals("shapeDebugger is not correctly instanciated",
                iShapeDebugger instanceof ShapeDebugger, iShapeDebugger !=
                        null);
    }

    @Test
    public void render() {
        ShapeDebugger iShapeDebugger = (ShapeDebugger) ShapeDebugger
                .getInstance();
        Mockito.when(iPhysicsDebuggerMock.isDebugEnabled()).thenReturn(true);

        iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
            @Override
            public Void apply(ShapeRenderer shapeRenderer) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.line(new Vector2(0, 0), new Vector2(1, 1));
                shapeRenderer.end();
                return null;
            }
        }, 9);

        ImmediateModeRenderer20 immediateModeRenderer20 =
                (ImmediateModeRenderer20) iShapeDebugger.getShapeRenderer()
                        .getRenderer();

        Assert.assertNotNull("Rederer souhld not be null",
                immediateModeRenderer20);

        try {
            Field verticesField = ImmediateModeRenderer20.class
                    .getDeclaredField("vertices");
            verticesField.setAccessible(true);
            float[] vertices = (float[]) verticesField.get
                    (immediateModeRenderer20);
            Assert.assertEquals(vertices[0], 0f, 0.0001f);
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
            e.printStackTrace();
        }


        iShapeDebugger.render(new OrthographicCamera(), 8f);


        try {
            Field verticesField = ImmediateModeRenderer20.class
                    .getDeclaredField("vertices");
            verticesField.setAccessible(true);
            float[] vertices = (float[]) verticesField.get
                    (immediateModeRenderer20);
            Assert.assertNotEquals("Firt vertice must be != 0 ! ", vertices[0],
                    0f);
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
            e.printStackTrace();
        }
    }

    @Test
    public void pushDrawEvent() {


        ShapeDebugger iShapeDebugger = (ShapeDebugger) ShapeDebugger
                .getInstance();
        Mockito.when(iPhysicsDebuggerMock.isDebugEnabled()).thenReturn(true);

        iShapeDebugger.pushDrawEvent(new Function<ShapeRenderer, Void>() {
            @Override
            public Void apply(ShapeRenderer shapeRenderer) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.line(new Vector2(0, 0), new Vector2(1, 1));
                shapeRenderer.end();
                return null;
            }
        }, 9);
        iShapeDebugger.render(new OrthographicCamera(), 8f);

        List<Event> events = iShapeDebugger.getDrawEvents();

        Assert.assertNotNull(events);
        Assert.assertEquals(1, events.size());

        iShapeDebugger.render(new OrthographicCamera(), 1f);

        List<Event> events2 = iShapeDebugger.getDrawEvents();

        Assert.assertNotNull(events2);
        Assert.assertEquals("The event has not been flushed after required " +
                "time.", 0, events2.size());

    }

}