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

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * Permet de récupérer et stocker les paramètres les paramètres du fichier de
 * configuration de debug du debuggage du monde physique.
 * </p>
 */
public class DebugFileKeys {

    /**
     * <p>The number of pair in key/value</p>
     */
    private static final Integer KEY_VALUE_NB = 2;

    /**
     * <p>The container of debug parameters.</p>
     */
    private final Map<String, String> keysFromFile = new ConcurrentHashMap<>();

    /**
     * <p>Constructor</p>
     */
    public DebugFileKeys() {
        super();
    }

    /**
     * <p>
     * Récupère la clé contenue dans {@link #keysFromFile}.
     * </p>
     *
     * @param key        La clé désirée
     * @param outputType la classe de retour de la clé
     * @param <T>        Le type de retour de la clé
     * @return La valeur de la clé désirée
     */
    public <T> T getValue(final String key, final Class<T> outputType) {
        if (Boolean.class == outputType) {
            final Boolean booleanValue = this.getBooleanValue(key);
            if (booleanValue != null) {
                return PhysicsDebuggerUtil.castValue(booleanValue);
            }
        }
        return null;
    }

    /**
     * <p>
     * Récupère la valeur brute contenue dans {@link #keysFromFile}
     * </p>
     *
     * @param key La clé désirée
     * @return La valeur brute retournée
     */
    private String getValue(final String key) {
        return this.keysFromFile.get(key);
    }

    /**
     * <p>
     * Récupère une valeur du conteneur {@link #keysFromFile} en tant que
     * booléen.
     * </p>
     *
     * @param key La clé souhaitée
     * @return la valeur booléenne retournée
     */
    private Boolean getBooleanValue(final String key) {
        final String currentValue = this.getValue(key);
        if (currentValue != null) {
            return Boolean.valueOf(currentValue);
        }
        return Boolean.FALSE;
    }

    /**
     * <p>
     * Ajout une clé dna {@link #keysFromFile}
     * </p>
     *
     * @param key   La clé que l'on souhaite ajouter
     * @param value La valeur que l'on souhaite ajouter
     */
    public void addKey(final String key, final String value) {
        this.keysFromFile.put(key, value);
    }

    /**
     * <p>
     * Retourne {@link #keysFromFile}
     * </p>
     *
     * @return {@link #keysFromFile}
     */
    public Map<String, String> getKeysFromFile() {
        return keysFromFile;
    }

    /**
     * <p>
     * Permet d'extraire et renseigner les paramètres du fichier de debug
     * au sein du debugFileKeys.
     * </p>
     *
     * @param bufferedReader Le reader actuel du fichier
     * @param keyDelimiter   le délimiteur des paramètres
     */
    @SuppressWarnings("pmd:LawOfDemeter")
    public void alimentDebugKeysParameters(final BufferedReader bufferedReader, final String keyDelimiter) {
        final List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        for (final Iterator<String> iter = lines.iterator(); iter.hasNext(); ) {
            final String currentLine = iter.next();
            final String[] splittedEntry = currentLine.split(keyDelimiter);
            if (splittedEntry.length == KEY_VALUE_NB) {
                this.addKey(splittedEntry[0], splittedEntry[1]);
            } else {
                throw new PhysicsDebugFileReadException("An error has occured while reading debug file");
            }
        }
    }
}
