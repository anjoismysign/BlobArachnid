package us.mytheria.blobarachnid.director.manager;

import org.bukkit.configuration.ConfigurationSection;
import us.mytheria.blobarachnid.director.ArachnidManager;
import us.mytheria.blobarachnid.director.ArachnidManagerDirector;
import us.mytheria.bloblib.entities.ConfigDecorator;

public class ConfigManager extends ArachnidManager {
    private boolean tinyDebug;

    public ConfigManager(ArachnidManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        ConfigDecorator configDecorator = getPlugin().getConfigDecorator();
        ConfigurationSection settingsSection = configDecorator.reloadAndGetSection("Settings");
        tinyDebug = settingsSection.getBoolean("Tiny-Debug");
    }

    public boolean tinyDebug() {
        return tinyDebug;
    }
}