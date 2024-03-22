package us.mytheria.blobarachnid.entities;

import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.entities.display.DisplayData;
import us.mytheria.bloblib.entities.display.DisplayOperator;
import us.mytheria.bloblib.entities.display.DisplayOperatorReader;
import us.mytheria.bloblib.exception.ConfigurationFieldException;

import java.util.Objects;

public record BlockDisplayOperator(@NotNull DisplayData getDisplayData,
                                   @NotNull Transformation getTransformation,
                                   @NotNull Plugin getPlugin,
                                   @NotNull BlockData getBlockData) implements DisplayOperator {

    @NotNull
    public static BlockDisplayOperator READ(@NotNull ConfigurationSection config,
                                            @NotNull String path,
                                            @NotNull JavaPlugin plugin) {
        Objects.requireNonNull(config, "'config' cannot be null");
        Objects.requireNonNull(path, "'path' cannot be null");
        if (!config.isString("BlockData"))
            throw new ConfigurationFieldException("'BlockData' is not set or valid");
        DisplayOperator operator = DisplayOperatorReader
                .READ(config, path, plugin);
        BlockData blockData = plugin.getServer().createBlockData(config.getString("BlockData"));
        return new BlockDisplayOperator(
                operator.getDisplayData(),
                operator.getTransformation(),
                plugin,
                blockData);
    }

    /**
     * Applies the DisplayData, Transformation, and BlockData to the given BlockDisplay.
     *
     * @param display the BlockDisplay to apply the DisplayData, Transformation, and BlockData to
     */
    public void apply(@NotNull BlockDisplay display) {
        DisplayOperator.super.apply(display);
        display.setBlock(getBlockData);
    }
}
