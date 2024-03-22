package us.mytheria.blobarachnid.entities;

import com.heledron.spideranimation.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Arachnid {
    private final Location location;
    private final Spider spider;
    private final DisplayRenderer renderer;
    private boolean isValid;

    private Arachnid(JavaPlugin plugin,
                     Location location,
                     ArachnidData arachnidData) {
        this.isValid = true;
        this.location = location;
        this.spider = new Spider(location, arachnidData.getGait());
        this.renderer = new DisplayRenderer(plugin);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isValid) {
                    cancel();
                    return;
                }
                spider.update();
                renderer.renderSpider(spider, RenderDebugOptions.Companion.none());
            }
        }.runTaskTimer(plugin, 0, 1);
        arachnidData.blocks().forEach(display ->
                spider.getBlockBones().add(display));
        arachnidData.items().forEach(display ->
                spider.getItemBones().add(display));
    }

    /**
     * Creates an arachnid that follows an entity
     *
     * @param plugin       The plugin
     * @param arachnidData The data carrier
     * @param entity       The entity to follow
     * @return The arachnid
     */
    @NotNull
    public static Arachnid follower(
            @NotNull JavaPlugin plugin,
            @NotNull Entity entity,
            @NotNull ArachnidData arachnidData) {
        Objects.requireNonNull(entity, "'player' cannot be null");
        Location location = entity.getLocation();
        Arachnid arachnid = noAI(plugin, location, arachnidData);
        entity.setSilent(true);
        entity.setVisibleByDefault(false);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!arachnid.isValid()) {
                    cancel();
                    return;
                }
                if (!entity.isValid()) {
                    arachnid.remove();
                    cancel();
                    return;
                }
                arachnid.target(entity.getLocation(), 0.5f);
            }
        }.runTaskTimer(plugin, 0, 1);
        return arachnid;
    }

    /**
     * Creates an arachnid with no AI
     *
     * @param plugin       The plugin
     * @param location     The location
     * @param arachnidData The data carrier
     * @return The arachnid
     */
    @NotNull
    public static Arachnid noAI(@NotNull JavaPlugin plugin,
                                @NotNull Location location,
                                @NotNull ArachnidData arachnidData) {
        Objects.requireNonNull(plugin, "'plugin' cannot be null");
        Objects.requireNonNull(location, "'location' cannot be null");
        Objects.requireNonNull(arachnidData, "'arachnidData' cannot be null");
        return new Arachnid(plugin, location, arachnidData);
    }

    /**
     * @return The location of the arachnid
     */
    @NotNull
    public Location getLocation() {
        return location;
    }

    /**
     * Removes the arachnid, making it invalid
     */
    public void remove() {
        this.isValid = false;
        renderer.clearSpider(spider);
    }

    /**
     * Whether the arachnid is still valid/active
     *
     * @return Whether the arachnid is still valid/active
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Walks to a location
     *
     * @param location The location to walk to
     * @param distance The distance to stop at
     */
    public void target(@NotNull Location location, double distance) {
        Objects.requireNonNull(location, "'location' cannot be null");
        spider.setBehaviour(new TargetBehaviour(location, distance, false));
    }

    /**
     * Walks to a location.
     * Defaults distance to 0.0
     *
     * @param location The location to walk to
     */
    public void target(@NotNull Location location) {
        target(location, 0.0);
    }

    /**
     * Makes the arachnid idle
     */
    public void idle() {
        spider.setBehaviour(StayStillBehaviour.INSTANCE);
    }

    /**
     * @return The spider entity
     */
    @NotNull
    public Spider getSpider() {
        return spider;
    }
}
