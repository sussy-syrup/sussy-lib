package com.sussysyrup.sussylib;

import com.sussysyrup.sussylib.resource.ResourcePackHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ResourcePackHelper.registerTexture(new Identifier("sussylib", "textures/item/test1.png"), new Identifier("textures/item/iron_ingot.png"));
        ResourcePackHelper.registerItemModel(new Identifier("sussylib", "models/item/test1.json"), new Identifier("sussylib","item/test1"));
    }
}
