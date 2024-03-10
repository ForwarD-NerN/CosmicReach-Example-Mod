package ru.nern.modloader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.entrypoint.EntrypointUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class CosmicHooks {
    public static final String INTERNAL_NAME = CosmicHooks.class.getName().replace('.', '/');
    /** This hook runs Fabric's ModInitializer.onInitialize() from where it is called.
     *  It's recommended that you call them from as late into the game's execution as you can while still being before the game loop,
     *  to allow ModInitializer to allow as many game alterations as possible.
     *
     *
     */
    public static void init() {
        Path runDir = Paths.get(".");
        FabricLoaderImpl floader = FabricLoaderImpl.INSTANCE;

        //We don't have the client and server yet, so we just run the main mod initializer
        FabricLoaderImpl.INSTANCE.prepareModInit(runDir, floader.getGameInstance());
        EntrypointUtils.invoke("main", ModInitializer.class, ModInitializer::onInitialize);
        //EntrypointUtils.invoke("client", ClientModInitializer.class, ClientModInitializer::onInitializeClient);
    }
}