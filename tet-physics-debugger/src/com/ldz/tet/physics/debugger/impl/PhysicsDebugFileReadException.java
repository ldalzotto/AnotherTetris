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

/**
 * <p>
 * This exception occurs when an error has occured while reading debug
 * file in {@link PhysicsDebugger}.
 * </p>
 */
public class PhysicsDebugFileReadException extends RuntimeException {

    /**
     * @param message message
     */
    public PhysicsDebugFileReadException(final String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause   message
     */
    public PhysicsDebugFileReadException(final String message, final Throwable
            cause) {
        super(message, cause);
    }
}
