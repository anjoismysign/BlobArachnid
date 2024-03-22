package us.mytheria.blobarachnid.entities;

import com.heledron.spideranimation.Gait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.jetbrains.annotations.NotNull;
import us.mytheria.bloblib.entities.BlobObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record ArachnidData(@NotNull Gait getGait,
                           @NotNull List<BlockDisplay> blocks,
                           @NotNull List<ItemDisplay> items,
                           @NotNull String getKey)
        implements BlobObject {

    public static ArachnidData READ(@NotNull File file) {
        Objects.requireNonNull(file, "'file' cannot be null");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection gaitSection = configuration.getConfigurationSection("Gait");
        Gait gait;
        if (gaitSection != null)
            gait = ArachnidReader.getInstance().gait(gaitSection);
        else
            gait = new Gait();
        World world = Bukkit.getWorlds().get(0);
        if (world == null)
            throw new NullPointerException("World is null");
        Location location = new Location(world, 0, 0, 0);
        List<BlockDisplay> blocks = new ArrayList<>();
        ConfigurationSection blocksSection = configuration.getConfigurationSection("Block-Bones");
        if (blocksSection != null) {
            List<BlockDisplayOperator> operators = ArachnidReader.getInstance()
                    .blockDisplayOperators(blocksSection, file.getPath());
            operators.forEach(operator -> {
                BlockDisplay blockDisplay = world.createEntity(location, BlockDisplay.class);
                operator.apply(blockDisplay);
                blocks.add(blockDisplay);
            });
        }
        List<ItemDisplay> items = new ArrayList<>();
        ConfigurationSection itemsSection = configuration.getConfigurationSection("Item-Bones");
        if (itemsSection != null) {
            List<ItemDisplayOperator> operators = ArachnidReader.getInstance()
                    .itemDisplayOperators(itemsSection, file.getPath());
            operators.forEach(operator -> {
                ItemDisplay itemDisplay = world.createEntity(location, ItemDisplay.class);
                operator.apply(itemDisplay);
                items.add(itemDisplay);
            });
        }
        return new ArachnidData(gait, blocks, items, file.getName().replace(".yml", ""));
    }

    @Override
    public File saveToFile(File file) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
