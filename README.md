# Cosmic Example Mod

This is an example mod for the Cosmic Reach game based on Fabric Mod Loader.

## How to use

1. Load the Gradle project in IntelIJ
2. Download the latest version of [Cosmic Reach](https://finalforeach.itch.io/cosmic-reach)
3. Create the **run** folder in the project root
4. Extract the Cosmic-Reach.X.X.X.jar from the downloaded archive into the **run** folder.
5. Run fabric:setupEnvironment gradle task
6. Reload the Gradle project after the fabric:setupEnvironment is completed.
7. Run the "Run Client" application task in IntelIJ. It should launch the game.

## How to setup sources

1. There's currently no automatic way for it. You need to manually decompile the game jar with [Cosmic Tools](https://github.com/Y2Kwastaken/CosmicTools/) or JD-gui.
2. Then you need to choose the sources in IntelIj.

## Credits

1. EliteMasterEric for the [original template](https://github.com/EliteMasterEric/HelloWorldFabric)
2. KaboomRoads for making MixinExtras work and fixing a lot of stuff
