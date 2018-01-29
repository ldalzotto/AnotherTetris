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

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PhysicsDebuggerUtilTest {

    @Test
    public void testConstructor() {

        try {
            Constructor constructor = PhysicsDebuggerUtil.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            Assert.assertNotNull("Instance should not be null !", instance);
            Assert.assertTrue("Should be an instance of PhysicsDebuggerUtil", instance instanceof
                    PhysicsDebuggerUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
