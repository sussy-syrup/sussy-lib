package com.sussysyrup.sussylib;

import com.sussysyrup.sussylib.resource.SussyResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("sussylib");

	public static final Item TEST1 = new Item(new FabricItemSettings());

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, new Identifier("sussylib", "test1"), TEST1);
		SussyResourcePack.addNamespace("sussylib");
	}
}
