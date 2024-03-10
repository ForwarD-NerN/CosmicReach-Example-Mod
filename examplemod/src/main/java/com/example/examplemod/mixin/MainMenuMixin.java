package com.example.examplemod.mixin;

import finalforeach.cosmicreach.gamestates.MainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.example.examplemod.ExampleMod.LOGGER;

@Mixin(MainMenu.class)
public class MainMenuMixin {
    @Inject(method = "create", at = @At("HEAD"))
    private void mainmenu$init(CallbackInfo ci) {
        LOGGER.info("The game is ready!");
    }
}
