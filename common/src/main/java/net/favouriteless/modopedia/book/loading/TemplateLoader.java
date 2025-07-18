package net.favouriteless.modopedia.book.loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.registries.client.TemplateRegistry;
import net.favouriteless.modopedia.book.registries.client.TemplateRegistryImpl;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class TemplateLoader extends JsonResourceLoader {

    public TemplateLoader(Gson gson, String dir) {
        super(gson, dir, "templates");
    }

    @Override
    protected void load(Map<ResourceLocation, JsonElement> jsonMap) {
        TemplateRegistryImpl.INSTANCE.clear();
        jsonMap.forEach((location, element) -> {
            try {
                TemplateRegistry.get().registerTemplate(location, element.getAsJsonObject());
            }
            catch (JsonParseException e) {
                Modopedia.LOG.error("Error loading template {}: {}", location, e);
            }
        });
    }

}
