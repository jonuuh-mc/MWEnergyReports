package io.jonuuh.energyreporter;

import io.jonuuh.energyreporter.config.Command;
import io.jonuuh.energyreporter.config.Settings;
import io.jonuuh.energyreporter.event.ChatReporter;
import io.jonuuh.energyreporter.event.EnergyChangePoster;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

@Mod(
        modid = "energyreporter",
        version = "1.0.1",
        acceptedMinecraftVersions = "[1.8.9]"
)
public class EnergyReporter
{
    private KeyBinding sendEnergyReportKey;

    @Mod.EventHandler
    public void FMLPreInit(FMLPreInitializationEvent event)
    {
        Settings.createInstance(event.getSuggestedConfigurationFile());
        sendEnergyReportKey = new KeyBinding("Send Energy Report", Keyboard.KEY_G, "~EnergyReporter");
    }

    @Mod.EventHandler
    public void FMLInit(FMLInitializationEvent event)
    {
        ClientRegistry.registerKeyBinding(sendEnergyReportKey);
        ClientCommandHandler.instance.registerCommand(new Command());

        MinecraftForge.EVENT_BUS.register(new EnergyChangePoster());
        MinecraftForge.EVENT_BUS.register(new ChatReporter(sendEnergyReportKey));
    }
}
