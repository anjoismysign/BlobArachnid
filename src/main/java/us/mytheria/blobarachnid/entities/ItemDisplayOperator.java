package us.mytheria.blobarachnid.entities;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayOperator;
import us.mytheria.bloblib.entities.display.DisplayOperatorReader;
import us.mytheria.bloblib.entities.translatable.TranslatableItem;
import us.mytheria.bloblib.exception.ConfigurationFieldException;

import java.util.Objects;

public record ItemDisplayOperator(@NotNull DisplayData getDisplayData,
                                  @NotNull Transformation getTransformation,
                                  @NotNull Plugin getPlugin,
                                  @NotNull ItemStack getItemStack,
                                  @NotNull ItemDisplay.ItemDisplayTransform getItemDisplayTransform) implements DisplayOperator {

    @NotNull
    public static ItemDisplayOperator READ(@NotNull ConfigurationSection config,
                                           @NotNull String path,
                                           @NotNull JavaPlugin plugin) {
        Objects.requireNonNull(config, "'config' cannot be null");
        Objects.requireNonNull(path, "'path' cannot be null");
        if (!config.isString("TranslatableItem"))
            throw new ConfigurationFieldException("'TranslatableItem' is not set or valid");
        DisplayOperator operator = DisplayOperatorReader
                .READ(config, path, plugin);
        TranslatableItem item = TranslatableItem.by(config.getString("TranslatableItem"));
        if (item == null)
            throw new ConfigurationFieldException("'TranslatableItem' doesn''t point to a valid TranslatableItem");
        ItemDisplay.ItemDisplayTransform itemDisplayTransform = ItemDisplay.ItemDisplayTransform.FIXED;
        if (config.isString("ItemDisplayTransform")) {
            try {
                itemDisplayTransform = ItemDisplay.ItemDisplayTransform.valueOf(config.getString("ItemDisplayTransform"));
            } catch (IllegalArgumentException e) {
                throw new ConfigurationFieldException("'ItemDisplayTransform' doesn't point to a valid ItemDisplayTransform");
            }
        }
        return new ItemDisplayOperator(
                operator.getDisplayData(),
                operator.getTransformation(),
                plugin,
                item.get().clone(),
                itemDisplayTransform);
    }

    /**
     * Applies the DisplayData, Transformation, and BlockData to the given BlockDisplay.
     *
     * @param display the BlockDisplay to apply the DisplayData, Transformation, and BlockData to
     */
    public void apply(@NotNull ItemDisplay display) {
        DisplayOperator.super.apply(display);
        display.setItemStack(getItemStack);
        display.setItemDisplayTransform(getItemDisplayTransform);
    }
}
