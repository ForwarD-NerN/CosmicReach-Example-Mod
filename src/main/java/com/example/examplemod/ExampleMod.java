package com.example.examplemod;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello non Cosmic world!");
    }
}
