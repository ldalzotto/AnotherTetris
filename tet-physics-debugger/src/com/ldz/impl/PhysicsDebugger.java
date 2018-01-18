package com.ldz.impl;

import com.ldz.itf.IPhysicsDebugger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhysicsDebugger implements IPhysicsDebugger {

    private static IPhysicsDebugger instance;

    private static String KEY_DELIMITER = "=";
    private Map<String, String> keysFromFile = new HashMap<>();

    private File configurationFile;

    private PhysicsDebugger() {
        this.configurationFile = new File("tet-physics-debugger/resources/physics-debug-configuration");
        try {
            FileReader fileReader = new FileReader(this.configurationFile.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] splittedEntry = currentLine.split(KEY_DELIMITER);
                this.keysFromFile.put(splittedEntry[0], splittedEntry[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static IPhysicsDebugger getInstance() {
        if (instance == null) {
            instance = new PhysicsDebugger();
        }
        return instance;
    }

    @Override
    public boolean isDebugEnabled() {
        return this.getKeyFromFile("isDebugEnabled", Boolean.class);
    }

    private <T> T getKeyFromFile(String key, Class<T> outputType) {
        if (Boolean.class == outputType) {
            return (T) Boolean.valueOf(this.keysFromFile.get(key));
        } else {
            return null;
        }
    }
}
