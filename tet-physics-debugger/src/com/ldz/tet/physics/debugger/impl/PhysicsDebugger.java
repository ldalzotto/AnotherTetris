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

import com.badlogic.gdx.Gdx;
import com.ldz.tet.physics.debugger.itf.IPhysicsDebugger;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * {@see IPhysicsDebugger}
 */
public final class PhysicsDebugger implements IPhysicsDebugger {

    /**
     * <p>L'instance de l'objet</p>
     */
    private static IPhysicsDebugger instance;

    /**
     * <p>Le TAG pour log</p>
     */
    private static final String TAG = PhysicsDebugger.class.getSimpleName();

    /**
     * <p>Le délimiteur utilié pour séparer les clés/valeur dans le fichier
     * de debug</p>
     */
    private static final String KEY_DELIMITER = "=";

    /**
     * <p>Le path name du fichier de debug</p>
     */
    private static String filePathName =
            "tet-physics-debugger/resources/physics-debug" +
                    "-configuration";

    /**
     * <p>Le conteneur du fichier de debug</p>
     */
    private final DebugFileKeys debugFileKeys = new DebugFileKeys();

    /**
     * <p>Constructor initilizing {@link #debugFileKeys} from debug file.
     * Throws error if file cannot be read.</p>
     */
    @SuppressWarnings("findsecbugs:PATH_TRAVERSAL_IN")
    private PhysicsDebugger() {
        try (
                BufferedReader bufferedReader = PhysicsDebuggerUtil.getBufferedReaderFromFilePath(filePathName)
        ) {
            //begin convertion des clés
            debugFileKeys.alimentDebugKeysParameters(bufferedReader, KEY_DELIMITER);
            //end convertion des clés
        } catch (IOException | PhysicsDebugFileReadException e) {
            Gdx.app.error(TAG, e.getMessage(), e);
            throw new PhysicsDebugFileReadException(e.getMessage(), e);
        }


    }

    /**
     * get instance
     *
     * @return {@link #instance}
     */
    public static IPhysicsDebugger getInstance() {
        synchronized (PhysicsDebugger.class) {
            if (instance == null) {
                instance = new PhysicsDebugger();
            }
            return instance;
        }
    }

    /**
     * {@link IPhysicsDebugger#isDebugEnabled()}
     */
    @Override
    public boolean isDebugEnabled() {
        return this.debugFileKeys.getValue("isDebugEnabled", Boolean
                .class);
    }

    /**
     * @return debugFileKeys
     */
    public DebugFileKeys getDebugFileKeys() {
        return debugFileKeys;
    }
}
