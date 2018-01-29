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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Utlity class of {@link PhysicsDebugger}</p>
 */
public final class PhysicsDebuggerUtil {

    /**
     * constructor
     */
    private PhysicsDebuggerUtil() {

    }

    /**
     * <p>
     * Permet de caster la veur souhaitée
     * </p>
     *
     * @param value la valeur que l'on souhaite caster
     * @param <T>   le type souhaité
     * @return la valeur castée
     */
    public static <T> T castValue(final Object value) {
        return (T) value;
    }

    /**
     * <p>Création du file en fonction du path renseigné en entrée</p>
     *
     * @param path Le path du fichier
     * @return le file
     */
    @SuppressWarnings("findsecbugs:PATH_TRAVERSAL_IN")
    public static File getFileFromPath(final String path) {
        return new File(path);
    }

    /**
     * <p>
     *     Creation d'un buffered reader à partir d'un file path.
     * </p>
     * @param filePath le chemin de fichier que l'on souhaite lire
     * @return le buffered reader associé
     * @throws IOException IOException
     */
    @SuppressWarnings("pmd:LawOfDemeter")
    public static BufferedReader getBufferedReaderFromFilePath(final String filePath) throws IOException {
        final File configurationFile = PhysicsDebuggerUtil.getFileFromPath
                (filePath);

        //begin lecture du fichier
        Path path = configurationFile.toPath();
        InputStream inputStream = Files.newInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(inputStreamReader);
        //end lecture du fichier
    }


}
