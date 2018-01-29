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

package com.ldz.tet.physics.debugger.impl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.ldz.tet.physics.debugger.itf.IPhysicsDebugger;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class PhysicsDebuggerTest {

    private static final String NULLITY_SHAPE_DEBUGGER_MESSAGE = "Shape " +
            "debugger is null";

    @BeforeClass
    public static void init() {
        Gdx.app = Mockito.mock(Application.class);
    }

    @Before
    public void before() {
        try {
            Field instance = PhysicsDebugger.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetInstance() {

        try {
            Field filePathname = PhysicsDebugger.class.getDeclaredField
                    ("filePathName");
            filePathname.setAccessible(true);

            filePathname.set(null,
                    "test/com/ldz/tet/physics/debugger/impl/physics-debug" +
                            "-configuration");
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
        }

        IPhysicsDebugger iPhysicsDebugger = PhysicsDebugger.getInstance();
        Assert.assertNotNull(NULLITY_SHAPE_DEBUGGER_MESSAGE, iPhysicsDebugger);
        Assert.assertEquals("Physics debugger is not an instance of " +
                        "PhysicsDebugger.class", PhysicsDebugger.class,
                iPhysicsDebugger.getClass());
        IPhysicsDebugger iPhysicsDebugger2 = PhysicsDebugger.getInstance();
        Assert.assertNotNull(NULLITY_SHAPE_DEBUGGER_MESSAGE, iPhysicsDebugger2);
    }

    @Test
    public void testIsDebugEnabled() {
        try {
            Field filePathname = PhysicsDebugger.class.getDeclaredField
                    ("filePathName");
            filePathname.setAccessible(true);

            filePathname.set(null,
                    "test/com/ldz/tet/physics/debugger/impl/physics-debug" +
                            "-configuration");
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
        }

        IPhysicsDebugger iPhysicsDebugger = PhysicsDebugger.getInstance();
        Assert.assertNotNull(NULLITY_SHAPE_DEBUGGER_MESSAGE, iPhysicsDebugger);

        boolean isDebugEnabled = iPhysicsDebugger.isDebugEnabled();
        Assert.assertTrue("Debug is not enabled while config file was " +
                "correct", isDebugEnabled);
        boolean isDebugEnabled2 = iPhysicsDebugger.getAsBoolean();
        Assert.assertTrue("Debug is not enabled while config file was " +
                "correct", isDebugEnabled2);
    }

    @Test
    public void testIsNotDebugEnabled() {
        try {
            Field filePathname = PhysicsDebugger.class.getDeclaredField
                    ("filePathName");
            filePathname.setAccessible(true);

            filePathname.set(null,
                    "test/com/ldz/tet/physics/debugger/impl/physics-debug" +
                            "-configuration-false");
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
        }

        IPhysicsDebugger iPhysicsDebugger = PhysicsDebugger.getInstance();
        Assert.assertNotNull(NULLITY_SHAPE_DEBUGGER_MESSAGE, iPhysicsDebugger);

        boolean isDebugEnabled = iPhysicsDebugger.isDebugEnabled();
        Assert.assertTrue("Debug is enabled while config file was incorrect",
                !isDebugEnabled);
    }

    @Test
    public void testGetter() {
        try {
            Field filePathname = PhysicsDebugger.class.getDeclaredField
                    ("filePathName");
            filePathname.setAccessible(true);

            filePathname.set(null,
                    "test/com/ldz/tet/physics/debugger/impl/physics-debug" +
                            "-configuration");
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
        }

        PhysicsDebugger iPhysicsDebugger = (PhysicsDebugger) PhysicsDebugger
                .getInstance();
        Assert.assertNotNull(NULLITY_SHAPE_DEBUGGER_MESSAGE, iPhysicsDebugger);

        DebugFileKeys debugFileKeys = iPhysicsDebugger.getDebugFileKeys();
        Assert.assertNotNull("Debug keys sould not be null if informations " +
                "are present in file.", debugFileKeys);
        Assert.assertNotNull("Debug keys sould not be null if informations " +
                "are present in file.", debugFileKeys.getKeysFromFile());
        Assert.assertEquals("There should be two properties in contained map",
                2,
                debugFileKeys.getKeysFromFile().size());
    }

    @Test(expected = PhysicsDebugFileReadException.class)
    public void testErrorWhileReading() {
        try {
            Field filePathname = PhysicsDebugger.class.getDeclaredField
                    ("filePathName");
            filePathname.setAccessible(true);

            filePathname.set(null,
                    "grgjzugjoizgzkpok");
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
        }
        PhysicsDebugger iPhysicsDebugger = (PhysicsDebugger) PhysicsDebugger
                .getInstance();
    }

    @Test(expected = PhysicsDebugFileReadException.class)
    public void testLimiterError() {
        try {
            Field filePathname = PhysicsDebugger.class.getDeclaredField
                    ("filePathName");
            filePathname.setAccessible(true);

            filePathname.set(null,
                    "test/com/ldz/tet/physics/debugger/impl/physics-debug-configuration-limiter-error");
        } catch (Exception e) {
            Assert.assertFalse(e.getMessage(), e != null);
        }
        PhysicsDebugger iPhysicsDebugger = (PhysicsDebugger) PhysicsDebugger.getInstance();
    }

}