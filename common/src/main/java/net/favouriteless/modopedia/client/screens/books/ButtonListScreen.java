package net.favouriteless.modopedia.client.screens.books;

import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.BookContent.LocalisedBookContent;
import net.favouriteless.modopedia.api.book.BookTexture.Rectangle;
import net.favouriteless.modopedia.api.book.Category;
import net.favouriteless.modopedia.api.book.Entry;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.BlankScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.ScreenPage;
import net.favouriteless.modopedia.client.screens.books.book_screen_pages.TitledScreenPage;
import net.favouriteless.modopedia.client.screens.widgets.BookItemTextButton;
import net.favouriteless.modopedia.common.book_types.LockedViewType;
import net.favouriteless.modopedia.util.client.AdvancementHelper;
import net.favouriteless.modopedia.util.common.ListUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ButtonListScreen extends MultiPageBookScreen {

    private final Component listTitle;
    private final List<Supplier<List<String>>> idSuppliers;
    private final List<Factory> buttonFactories;
    private final LockedViewType lockedType;

    public ButtonListScreen(Book book, String lang, LocalisedBookContent content, Component title, BookScreen lastScreen, Component listTitle,
                            LockedViewType type, List<Supplier<List<String>>> idSuppliers, List<Factory> buttonFactories) {
        super(book, lang, content, lastScreen, title);
        this.listTitle = listTitle;
        this.idSuppliers = idSuppliers;
        this.buttonFactories = buttonFactories;
        this.lockedType = type;

        if(idSuppliers.size() != buttonFactories.size())
            throw new IllegalArgumentException("LandingScreen must have the same number of ID suppliers and button factories");
    }

    protected abstract ScreenPage initFirstPage();

    @Override
    protected void initPages(final Consumer<ScreenPage> pageConsumer) {
        pageConsumer.accept(initFirstPage());
        if(content == null)
            return;

        List<List<String>> idLists = new ArrayList<>();
        idSuppliers.forEach(s -> idLists.add(s.get()));

        if(ListUtils.size(idLists) == 0)
            return;

        final int spacing = BookItemTextButton.SIZE+1;

        Rectangle rectangle = texture.pages().get(getPageCount() % texture.pages().size());
        ScreenPage page = new TitledScreenPage(this, listTitle, rectangle);
        int y = rectangle.v() + minecraft.font.lineHeight + 3;

        final Rectangle sep = texture.widgets().get("separator");
        if(sep != null)
            y += sep.height();

        for(int i = 0; i < idLists.size(); i++) {
            final List<String> ids = idLists.get(i);
            final Factory factory = buttonFactories.get(i);

            int onPage = (rectangle.height() - (y - rectangle.v())) / spacing;

            for(String id : ids) {
                int count = page.getWidgets().size();

                if(count >= onPage) {
                    pageConsumer.accept(page);

                    page = new BlankScreenPage(this);
                    rectangle = texture.pages().get(getPageCount() % texture.pages().size());
                    y = rectangle.v();
                    count = 0;
                }

                page.addWidget(factory.create(this, id, leftPos + rectangle.u(), topPos + y + spacing*count, rectangle.width()));
            }
        }
        pageConsumer.accept(page);
    }

    protected static BookItemTextButton createCategoryButton(ButtonListScreen screen, String id, int x, int y, int width) {
        Category cat = screen.content.getCategory(id);
        ResourceLocation advancement = cat.getAdvancement();

        return new BookItemTextButton(x, y, width, cat.getIcon(),
                Component.literal(cat.getTitle()).withStyle(screen.getStyle()),
                b -> Minecraft.getInstance().setScreen(new CategoryScreen(screen.book, screen.lang, screen.content, cat, screen.lockedType, screen)),
                screen.book.getFlipSound(), advancement != null && !AdvancementHelper.hasAdvancement(cat.getAdvancement())
        );
    }

    protected static BookItemTextButton createEntryButton(ButtonListScreen screen, String id, int x, int y, int width) {
        Entry entry = screen.content.getEntry(id);
        ResourceLocation advancement = entry.getAdvancement();

        return new BookItemTextButton(x, y, width, entry.getIcon(),
                Component.literal(entry.getTitle()).withStyle(screen.getStyle()),
                b -> Minecraft.getInstance().setScreen(new EntryScreen(screen.book, screen.lang, screen.content, entry, screen)),
                screen.book.getFlipSound(), advancement != null && !AdvancementHelper.hasAdvancement(entry.getAdvancement())
        );
    }

    @FunctionalInterface
    public interface Factory {

        BookItemTextButton create(ButtonListScreen screen, String id, int x, int y, int width);

    }

}
