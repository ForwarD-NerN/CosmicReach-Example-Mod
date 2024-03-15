# Cosmic Example Mod

This is an example mod for Cosmic Reach game based on Fabric Mod Loader.

## How to use

1. Load the Gradle project in IntelIJ
2. Download the latest version of [Cosmic Reach](https://finalforeach.itch.io/cosmic-reach) jar
3. Extract the Cosmic-Reach.X.X.X.jar into the **runs** folder.
4. Run fabric:setupEnvironment gradle task
5. Reload the Gradle project.
6. Run "Run Client" application task in IntelIJ. It should launch the game.

## How to setup sources

1. There's currently no automatic way for it. You need to manually decompile the game jar with [Cosmic Tools](https://github.com/Y2Kwastaken/CosmicTools/) or JD-gui.
2. Then you need to choose the sources in IntelIj.

## Credits

1. EliteMasterEric for the [original template](https://github.com/EliteMasterEric/HelloWorldFabric)
2. KaboomRoads for making MixinExtras work and fixing a lot of stuff
