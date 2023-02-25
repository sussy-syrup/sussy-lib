package com.sussysyrup.sussylib.resource.client.util;

import net.minecraft.util.Identifier;

public record TemplateRecolourItemRecord(Identifier textureID, Identifier outputID, Identifier outputModelID, ColourScheme colourScheme) {
}
