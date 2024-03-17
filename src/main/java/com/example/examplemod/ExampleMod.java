package com.example.examplemod;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";


    @Override
    public void onInitialize() {
        System.out.println("Hello non Cosmic world!");
    }
}
