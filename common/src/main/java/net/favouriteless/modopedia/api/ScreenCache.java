package net.favouriteless.modopedia.api;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.client.ScreenCacheImpl;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

/**
 * Stores the most recently open {@link Screen} for each {@link Book}, per language. Will get cleared when the book
 * gets reloaded.
 */
public interface ScreenCache {

    static ScreenCache get() {
        return ScreenCacheImpl.INSTANCE;
    }

    void setLastScreen(ResourceLocation book, String language, BookScreen screen);

    BookScreen getLastScreen(ResourceLocation book, String language);

}
