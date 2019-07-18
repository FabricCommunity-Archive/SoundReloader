package com.github.fabriccommunity.soundreloader;

import com.github.fabriccommunity.soundreloader.mixin.SoundSystemAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class SoundReloaderMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricKeyBinding binding = FabricKeyBinding.Builder
				.create(new Identifier("soundreloader", "reload_sounds"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "key.categories.misc")
				.build();

		KeyBindingRegistry.INSTANCE.register(binding);

		ClientTickCallback.EVENT.register((client) -> {
			while (binding.wasPressed()) {
				SoundSystemAccessor soundSystemAccessor = (SoundSystemAccessor) client.getSoundManager();
				soundSystemAccessor.getSoundSystem().reloadSounds();
				client.execute(() -> client.inGameHud.getChatHud().addMessage(new TranslatableText("soundreloader.reloaded_sounds")));
			}
		});
	}
}
