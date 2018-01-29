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
package com.ldz.tet.physics.debugger.itf;

import java.util.function.BooleanSupplier;

/**
 * <p>
 * Interface permettant d'affciher les éléments de debuggage du monde
 * physique box2D.
 * </p>
 */
@FunctionalInterface
public interface IPhysicsDebugger extends BooleanSupplier {

    /**
     * <p>
     * Vérifie si le mode debug du monde physique est activé
     * </p>
     *
     * @return Si oui ou non, le mode debug est activé
     */
    boolean isDebugEnabled();

    /**
     * Overidded method
     *
     * @return {@link #isDebugEnabled()}
     */
    @Override
    @SuppressWarnings("pmd:BooleanGetMethodName")
    default boolean getAsBoolean() {
        return this.isDebugEnabled();
    }
}
