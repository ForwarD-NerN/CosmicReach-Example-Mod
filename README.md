# Cosmic Example Mod

This is an example mod for the Cosmic Reach game based on Fabric Mod Loader.

## How to use

1. Load the Gradle project in IntelIJ
2. Run fabric:setupEnvironment gradle task
3. Reload the Gradle project after fabric:setupEnvironment is completed.
4. Run the "Run Client" application task in IntelIJ. It should launch the game.

## How to update the game
1. Go to gradle.properties and change **cosmicreach_version** to the version you want to update to.
2. Run fabric:setupEnvironment
3. Reload the Gradle project.

## How to setup sources

1. There's currently no automatic way for it. You need to manually decompile the game jar with [Cosmic Tools](https://github.com/Y2Kwastaken/CosmicTools/) or JD-gui.
2. Then you need to choose the sources in IntelIj.

## Credits

1. EliteMasterEric for the [original template](https://github.com/EliteMasterEric/HelloWorldFabric)
2. KaboomRoads for making MixinExtras work and fixing a lot of stuff
3. Mr Zombii and Neuxs0 for their [Cosmic Reach archive](https://github.com/CRModders/CosmicArchive)
