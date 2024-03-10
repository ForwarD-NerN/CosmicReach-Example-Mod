# Cosmic Example Mod

This is an example mod for Cosmic Reach game based on Fabric Mod Loader.

## How to setup
1. Load the Gradle project in IntelIJ.
2. Move the main game jar into the /run folder and rename it to "cosmic-reach.jar"
3. Run modLoader:buildAndCopy. It should create .jar file in the run folder
4. Run examplemod:runClient task. It should launch the game.

## How to setup sources
1. There's no automatic way for it currently. You need to manually decompile the game jar with JD-gui or other decompilers and export it sources.
2. Then you need to choose them with IntelIj.