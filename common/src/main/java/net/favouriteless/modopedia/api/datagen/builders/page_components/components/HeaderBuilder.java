package net.favouriteless.modopedia.api.datagen.builders.page_components.components;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.api.datagen.builders.PageComponentBuilder;
import net.favouriteless.modopedia.client.page_components.HeaderPageComponent;

import net.minecraft.resources.RegistryOps;

public class HeaderBuilder extends PageComponentBuilder {

    private final String text;

    private Either<Boolean, String> centered;
    private Either<Boolean, String> bold;
    private Either<Boolean, String> underline;
    private Either<Integer, String> colour;

    private HeaderBuilder(String text) {
        super(HeaderPageComponent.ID);
        this.text = text;
    }

    public static HeaderBuilder of(String text) {
        return new HeaderBuilder(text);
    }

    @Override
    public HeaderBuilder x(int x) {
        return (HeaderBuilder)super.x(x);
    }

    @Override
    public HeaderBuilder x(String x) {
        return (HeaderBuilder)super.x(x);
    }

    @Override
    public HeaderBuilder y(int y) {
        return (HeaderBuilder)super.y(y);
    }

    @Override
    public HeaderBuilder y(String y) {
        return (HeaderBuilder)super.y(y);
    }

    public HeaderBuilder centered(boolean centered) {
        this.centered = Either.left(centered);
        return this;
    }

    public HeaderBuilder centered(String centered) {
        this.centered = Either.right(centered);
        return this;
    }

    public HeaderBuilder colour(int colour) {
        this.colour = Either.left(colour);
        return this;
    }

    public HeaderBuilder colour(String colour) {
        this.colour = Either.right(colour);
        return this;
    }

    public HeaderBuilder bold(boolean centered) {
        this.bold = Either.left(centered);
        return this;
    }

    public HeaderBuilder bold(String reference) {
        this.bold = Either.right(reference);
        return this;
    }

    public HeaderBuilder underline(boolean underline) {
        this.underline = Either.left(underline);
        return this;
    }

    public HeaderBuilder underline(String underline) {
        this.underline = Either.right(underline);
        return this;
    }

    @Override
    protected void build(JsonObject json, RegistryOps<JsonElement> ops) {
        json.add("text", resolveString(text));
        if(centered != null)
            json.add("centered", resolveBool(centered));
        if(bold != null)
            json.add("bold", resolveBool(bold));
        if(underline != null)
            json.add("underline", resolveBool(underline));
        if(colour != null)
            json.add("colour", resolveNum(colour));
    }

}
