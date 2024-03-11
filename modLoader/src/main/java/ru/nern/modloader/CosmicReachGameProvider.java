package ru.nern.modloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.fabricmc.loader.impl.FormattedException;
import net.fabricmc.loader.impl.game.GameProvider;
import net.fabricmc.loader.impl.game.GameProviderHelper;
import net.fabricmc.loader.impl.game.patch.GameTransformer;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.metadata.BuiltinModMetadata;
import net.fabricmc.loader.impl.metadata.ContactInformationImpl;
import net.fabricmc.loader.impl.util.Arguments;
import net.fabricmc.loader.impl.util.SystemProperties;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import ru.nern.modloader.patch.CRInitPatch;

/*
 * A custom GameProvider which grants Fabric Loader the necessary information to launch the app.
 */
public class CosmicReachGameProvider implements GameProvider {
    public static final String[] ENTRYPOINTS = new String[]{"finalforeach.cosmicreach.lwjgl3.Lwjgl3Launcher"};
    public static final String PROPERTY_GAME_DIRECTORY = "appDirectory";

    private Arguments arguments;
    private List<Path> libraries;
    private Path gameJar;
    private CRVersion version;
    private String entrypoint;

    private static final GameTransformer TRANSFORMER = new GameTransformer(new CRInitPatch());

    /*
     * Display an identifier for the app.
     */ @Override
    public String getGameId() {
        return "cosmic_reach";
    }

    /*
     * Display a readable name for the app.
     */ @Override
    public String getGameName() {
        return "Cosmic Reach";
    }

    /*
     * Display a raw version string that may include build numbers or git hashes.
     */ @Override
    public String getRawGameVersion() {
        return version.getVersion();
    }

    /*
     * Display a clean version string for display.
     */ @Override
    public String getNormalizedGameVersion() {
        return version.getVersion();
    }

    /*
     * Provides built-in mods, for example a mod that represents the app itself so
     * that mods can depend on specific versions.
     */
    @Override
    public Collection<BuiltinMod> getBuiltinMods() {
        HashMap<String, String> contactMap = new HashMap<>();
        contactMap.put("homepage", "https://finalforeach.itch.io/cosmic-reach");
        contactMap.put("wiki", "https://finalforeach.itch.io/cosmic-reach");


        BuiltinModMetadata.Builder modMetadata = new BuiltinModMetadata.Builder(getGameId(), getNormalizedGameVersion())
            .setName(getGameName())
            .addAuthor("FinalForEach", contactMap)
            .setContact(new ContactInformationImpl(contactMap))
            .setDescription("The base game");

        return Collections.singletonList(new BuiltinMod(Collections.singletonList(gameJar), modMetadata.build()));
    }

    /*
     * Provides the full class name of the app's entrypoint.
     */
    @Override
    public String getEntrypoint() {
        return entrypoint;
    }

    /*
     * Provides the directory path where the app's resources (such as config) should
     * be located
     * This is where the `mods` folder will be located.
     */
    @Override
    public Path getLaunchDirectory() {
        if (arguments == null) {
            return Paths.get(".");
        }
        return getLaunchDirectory(arguments);
    }

    private static Path getLaunchDirectory(Arguments arguments) {
        return Paths.get(arguments.getOrDefault(PROPERTY_GAME_DIRECTORY, "."));
    }

    /*
     * Return true if the app needs to be deobfuscated.
     */
    @Override
    public boolean isObfuscated() {
        return false;
    }

    @Override
    public boolean requiresUrlClassLoader() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
     * Parse the arguments, locate the game directory, and return true if the game
     * directory is valid.
     */
    @Override
    public boolean locateGame(FabricLauncher launcher, String[] args) {
        this.arguments = new Arguments();
        this.arguments.parse(args);

        // Build a list of possible locations for the app JAR.
        List<String> appLocations = new ArrayList<>();
        // Respect "fabric.gameJarPath" if it is set.
        if (System.getProperty(SystemProperties.GAME_JAR_PATH) != null) {
            appLocations.add(System.getProperty(SystemProperties.GAME_JAR_PATH));
        }

        // List out default locations.
        appLocations.add("./cosmic-reach.jar");
        appLocations.add("./game/cosmic-reach.jar");

        // Filter the list of possible locations based on whether the file exists.
        List<Path> existingAppLocations = appLocations.stream().map(p -> Paths.get(p).toAbsolutePath().normalize())
                .filter(Files::exists).toList();

        // Filter the list of possible locations based on whether they contain the required entrypoints
        GameProviderHelper.FindResult result = GameProviderHelper.findFirst(existingAppLocations, new HashMap<>(), true, ENTRYPOINTS);

        if (result == null || result.path == null) {
            // Tell the user we couldn't find the app JAR.
            String appLocationsString = appLocations.stream().map(p -> (String.format("* %s", Paths.get(p).toAbsolutePath().normalize())))
                .collect(Collectors.joining("\n"));
            
            Log.error(LogCategory.GAME_PROVIDER, "Could not locate the application JAR! We looked in: \n" + appLocationsString);

            return false;
        }

        this.entrypoint = result.name;
        this.version = new CRVersion(result.path);
        this.gameJar = result.path;
        this.libraries = new ArrayList<>();

        //We can probably use the classifier here, but this is just simpler.
        try (Stream<Path> paths = Files.walk(result.path.resolveSibling("deps"))) {
            paths.filter(Files::isRegularFile)
                    .forEach(libraries::add);
        }catch (IOException e){
            e.printStackTrace();
        }


        return true;
    }

    /*
     * Add additional configuration to the FabricLauncher, but do not launch your
     * app.
     */
    @Override
    public void initialize(FabricLauncher launcher) {
        try {
            launcher.setValidParentClassPath(Collections.singletonList(Path.of(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI())));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        TRANSFORMER.locateEntrypoints(launcher, Collections.singletonList(gameJar));
    }

    /*
     * Return a GameTransformer that does extra modification on the app's JAR.
     */
    @Override
    public GameTransformer getEntrypointTransformer() {
        return TRANSFORMER;
    }

    /*
     * Called after transformers were initialized and mods were detected and loaded
     * (but not initialized).
     */
    @Override
    public void unlockClassPath(FabricLauncher launcher) {
        this.libraries.forEach(launcher::addToClassPath);
        launcher.addToClassPath(gameJar);
    }

    /*
     * Launch the app in this function. This MUST be done via reflection.
     */
    @Override
    public void launch(ClassLoader loader) {
        String targetClass = entrypoint;
        try {
            Class<?> main = loader.loadClass(targetClass);
            Method method = main.getMethod("main", String[].class);
            method.invoke(null, (Object) this.arguments.toArray());
        } catch (InvocationTargetException e) {
            throw new FormattedException("The game has crashed!", e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new FormattedException("Failed to launch the game", e);
        }
    }

    @Override
    public Arguments getArguments() {
        return this.arguments;
    }

    @Override
    public String[] getLaunchArguments(boolean sanitize) {
        if (arguments == null) return new String[0];

        String[] ret = arguments.toArray();
        return ret;
    }
}
