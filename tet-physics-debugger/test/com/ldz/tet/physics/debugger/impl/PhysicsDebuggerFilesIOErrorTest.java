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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhysicsDebuggerUtil.class})
public class PhysicsDebuggerFilesIOErrorTest {

    @BeforeClass
    public static void init() {
        Gdx.app = Mockito.mock(Application.class);
    }

    @Before
    public void before() {
        try {
            PowerMockito.mockStatic(PhysicsDebuggerUtil.class);
            PowerMockito.when(PhysicsDebuggerUtil.getBufferedReaderFromFilePath
                    ("test/com/ldz/tet/physics/debugger/impl/physics-debug-configuration")).thenThrow(new IOException
                    ());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Field instance = PhysicsDebugger.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(expected = PhysicsDebugFileReadException.class)
    public void testIOExceptionWhileReadingFile() {
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
    }

}
