package io.jonuuh.energyreporter.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class Settings
{
    private static Settings instance;
    private final Configuration configuration;
    private boolean doAutoReports;
    private boolean showHitsNeeded;
    private boolean showAbilityName;

    public static void createInstance(File configFile)
    {
        if (instance != null)
        {
            throw new IllegalStateException("Settings instance has already been created");
        }

        instance = new Settings(configFile);
    }

    public static Settings getInstance()
    {
        if (instance == null)
        {
            throw new NullPointerException("Settings instance has not been created");
        }

        return instance;
    }

    public boolean doAutoReports()
    {
        return doAutoReports;
    }

    public void setDoAutoReports(boolean doAutoReports)
    {
        this.doAutoReports = doAutoReports;
    }

    public boolean showHitsNeeded()
    {
        return showHitsNeeded;
    }

    public void setShowHitsNeeded(boolean showHitsNeeded)
    {
        this.showHitsNeeded = showHitsNeeded;
    }

    public boolean showAbilityName()
    {
        return showAbilityName;
    }

    public void setShowAbilityName(boolean showAbilityName)
    {
        this.showAbilityName = showAbilityName;
    }

    // Load settings (read data from Configuration, write it into each setting)
    private Settings(File configFile)
    {
        this.configuration = new Configuration(configFile);

        doAutoReports = getBoolProperty("doAutoReports", false).getBoolean();
        showHitsNeeded = getBoolProperty("showHitsNeeded", true).getBoolean();
        showAbilityName = getBoolProperty("showAbilityName", true).getBoolean();
    }

    // Save settings (read data from each setting, write it into Configuration)
    public void save()
    {
        getBoolProperty("doAutoReports", false).setValue(doAutoReports);
        getBoolProperty("showHitsNeeded", true).setValue(showHitsNeeded);
        getBoolProperty("showAbilityName", true).setValue(showAbilityName);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    private Property getBoolProperty(String settingKey, boolean defaultVal)
    {
        return configuration.get("all", settingKey, defaultVal);
    }
}
