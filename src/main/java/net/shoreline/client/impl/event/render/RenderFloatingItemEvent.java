package net.shoreline.client.impl.event.render;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

/**
 * @author linus
 * @since 1.0
 */
@Cancelable
public class RenderFloatingItemEvent extends Event {
    private final ItemStack floatingItem;

    public RenderFloatingItemEvent(ItemStack floatingItem) {
        this.floatingItem = floatingItem;
    }

    public Item getFloatingItem() {
        return floatingItem.getItem();
    }

    public ItemStack getFloatingItemStack() {
        return floatingItem;
    }
}
