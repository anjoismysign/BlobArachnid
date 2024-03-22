package us.mytheria.blobarachnid.director;

import me.anjoismysign.skeramidcommands.command.Command;
import me.anjoismysign.skeramidcommands.command.CommandBuilder;
import me.anjoismysign.skeramidcommands.server.bukkit.BukkitAdapter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import us.mytheria.blobarachnid.BlobArachnid;
import us.mytheria.blobarachnid.director.manager.ConfigManager;
import us.mytheria.blobarachnid.entities.Arachnid;
import us.mytheria.blobarachnid.entities.ArachnidData;
import us.mytheria.bloblib.api.BlobLibMessageAPI;
import us.mytheria.bloblib.entities.GenericManagerDirector;
import us.mytheria.bloblib.entities.ObjectDirector;
import us.mytheria.bloblib.entities.ObjectManager;

public class ArachnidManagerDirector extends GenericManagerDirector<BlobArachnid> {

    public ArachnidManagerDirector(BlobArachnid plugin) {
        super(plugin);
        Command command = CommandBuilder.of("blobarachnid").build();
        addManager("ConfigManager",
                new ConfigManager(this));
        addDirector("ArachnidData", ArachnidData::READ, false);
        ObjectManager<ArachnidData> objectManager = getArachnidDataDirector().getObjectManager();
        Command disguise = command.child("spawn");
        disguise.onExecute((messenger, args) -> {
            if (args.length < 1)
                return;
            CommandSender sender = BukkitAdapter.getInstance().of(messenger);
            if (!(sender instanceof Player player)) {
                BlobLibMessageAPI.getInstance()
                        .getMessage("System.Console-Not-Allowed-Command")
                        .toCommandSender(sender);
                return;
            }
            String key = args[0];
            ArachnidData data = objectManager.parse(key);
            if (data == null)
                return;
            Zombie zombie = player.getWorld().spawn(player.getLocation(), Zombie.class);
            zombie.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
            Arachnid.follower(plugin, zombie, data);
        });
        disguise.setParameters(objectManager);
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getConfigManager().reload();
        getArachnidDataDirector().reload();
    }

    @Override
    public void unload() {
    }

    @NotNull
    public final ConfigManager getConfigManager() {
        return getManager("ConfigManager", ConfigManager.class);
    }

    @NotNull
    public final ObjectDirector<ArachnidData> getArachnidDataDirector() {
        return getDirector("ArachnidData", ArachnidData.class);
    }
}