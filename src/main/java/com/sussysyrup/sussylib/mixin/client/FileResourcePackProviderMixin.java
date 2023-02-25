package com.sussysyrup.sussylib.mixin.client;


import com.sussysyrup.sussylib.resource.SussyResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

//POTENTIALLY USE
@Environment(EnvType.CLIENT)
@Mixin(FileResourcePackProvider.class)
public abstract class FileResourcePackProviderMixin {

    @Shadow @Final
    private ResourceType type;

    @Inject(method = "register", at = @At("HEAD"))
    private void reg(Consumer<ResourcePackProfile> profileAdder, CallbackInfo ci) {

        ResourcePackProfile profile = ResourcePackProfile.create("Sussy Resource Pack", Text.literal("Sussy Resource Pack"),
                true, (name) -> new SussyResourcePack("Sussy Resource Pack"), this.type, ResourcePackProfile.InsertionPosition.BOTTOM,
                ResourcePackSource.create((param) -> Text.literal("sussylib"), false));

        if (profile != null) {
            profileAdder.accept(profile);
        }
    }
}
