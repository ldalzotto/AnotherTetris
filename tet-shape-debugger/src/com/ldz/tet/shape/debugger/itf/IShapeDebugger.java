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

package com.ldz.tet.shape.debugger.itf;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.function.Function;

/**
 * <p>
 *     Cette interface permet d'envoyer des évènemments {@link Event} de debuggage afin qu'ils soient rendu à l'écran.
 * </p>
 */
public interface IShapeDebugger {

    /**
     * <p>
     *  Permet d'afficher les élément de debuggage
     * </p>
     * @param camera La camera pour le rendu
     * @param delta le temps passé entre deux frames
     */
    void render(Camera camera, float delta);

    /**
     * <p>
     *     Permet d'insérer un élément de debuggage dans la file d'attente. L'évènement est affiché pendant le temps
     *     défini dans {@code time}.
     * </p>
     * @param event l'évènement souhaite.
     * @param time le temps pendant lequel on souhaite afficher l'élément de debuggage.
     * @return Si l'évènement a correctement été inséré dans la file d'attente
     */
    boolean pushDrawEvent(Function<ShapeRenderer, Void> event, float time);

    /**
     * <p>Permet de savoir si le mode debug est activé ou pas.</p>
     * @return booléen de retour
     */
    boolean isEnabled();
}
