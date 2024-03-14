# Cosmic Example Mod

This is an example mod for Cosmic Reach game based on Fabric Mod Loader.

## How to setup

1. Load the Gradle project in IntelIJ
2. Download the Cosmic Reach game jar and put it into the /run/ folder. 
3. Rename it to "cosmic-reach.jar"
4. Download the [latest mod loader](https://github.com/ForwarD-NerN/CosmicReach-Mod-Loader/releases/latest) archive
5. Unzip it in the /run/ folder
6. Reload the Gradle project.
7. Run "Run Client" application task in IntelIJ. It should launch the game.

## How to setup sources

1. There's currently no automatic way for it. You need to manually decompile the game jar with [Cosmic Tools](https://github.com/Y2Kwastaken/CosmicTools/) or JD-gui.
2. Then you need to choose the sources in IntelIj.

## Credits

1. EliteMasterEric for the [original template](https://github.com/EliteMasterEric/HelloWorldFabric)
2. KaboomRoads for making MixinExtras work and fixing a lot of stuff
