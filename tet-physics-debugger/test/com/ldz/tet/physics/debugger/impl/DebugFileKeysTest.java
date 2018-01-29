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

import java.util.HashMap;

import static org.junit.Assert.*;

public class DebugFileKeysTest {

    @Test
    public void testGetValuetNullValue() {

        DebugFileKeys debugFileKeys = new DebugFileKeys();

        Boolean outputValue = debugFileKeys.getValue("eefoieofij", Boolean
                .class);

        Assert.assertEquals("This unknown key should be false !",
                Boolean.FALSE, outputValue);

    }

    @Test
    public void testGetValueNullValueUnknownClass() {

        DebugFileKeys debugFileKeys = new DebugFileKeys();

        HashMap outputValue = debugFileKeys.getValue("eefoieofij", HashMap
                .class);

        Assert.assertNull("This unknown key should be null because " +
                "requested class is unknown!", outputValue);

    }
}