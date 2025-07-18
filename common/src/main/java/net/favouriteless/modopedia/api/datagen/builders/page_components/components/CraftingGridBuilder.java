package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.WidgetPageComponent;

import net.minecraft.resources.RegistryOps;

public class CraftingGridBuilder extends PageComponentBuilder {

    private CraftingGridBuilder() {
        super(WidgetPageComponent.ID_CRAFTING_GRID);
    }

    public static CraftingGridBuilder of() {
        return new CraftingGridBuilder();
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {}

}
