package io.jonuuh.energyreporter.event;

import io.jonuuh.energyreporter.config.Settings;
import io.jonuuh.energyreporter.util.EnergyGains;
import io.jonuuh.energyreporter.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ChatReporter
{
    private final Minecraft mc;
    private final KeyBinding sendEnergyReportKey;
    private static Settings settingsInstance;

    public ChatReporter(KeyBinding sendEnergyReportKey)
    {
        this.mc = Minecraft.getMinecraft();
        this.sendEnergyReportKey = sendEnergyReportKey;
        settingsInstance = Settings.getInstance();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (!Util.isMWEnvironment())
        {
            return;
        }

        // rudimentary client spectator check
        if (mc.thePlayer.capabilities.allowFlying && mc.thePlayer.isInvisible())
        {
            return;
        }

        if (sendEnergyReportKey.isPressed())
        {
            String className = Util.getClientClassName();
            String abilityName = settingsInstance.showAbilityName() ? Util.getClientAbilityName(className) : "Ability";
            EnergyGains gains;

            try
            {
                gains = EnergyGains.valueOf(className);
            }
            catch (IllegalArgumentException e)
            {
                String errMsg = "Failed to parse class name from client team color suffix - please DM 'jonuuh' on discord";
                mc.thePlayer.addChatMessage(new ChatComponentText(errMsg).setChatStyle(new ChatStyle().setItalic(true)));
                return;
            }

            int currentEnergy = mc.thePlayer.experienceLevel;

            if (currentEnergy == 100)
            {
                Util.sendChatMessage("★ " + abilityName + " is fully charged! (100%)");
                return;
            }

            int highestEPH = Math.max(gains.melee, gains.bow);
            String highestEPHWeapon = (gains.melee >= gains.bow) ? " melee" : " bow";

            float hitsNeeded = (float) (100 - currentEnergy) / highestEPH;

            String keyword = (currentEnergy >= 80) ? "high" : (currentEnergy >= 50) ? "half" : "low";

            String mainMsg = abilityName + " is " + keyword + " charged! (" + currentEnergy + "%)";
            String hitsNeededSuffix = settingsInstance.showHitsNeeded() ? " -> (Need " + hitsNeeded + highestEPHWeapon + " hits)" : "";

            Util.sendChatMessage(mainMsg + hitsNeededSuffix);
        }
    }

    @SubscribeEvent
    public void onEnergyChange(EnergyChangeEvent event)
    {
        if (event.total == 100)
        {
            String className = Util.getClientClassName();
            String abilityName = settingsInstance.showAbilityName() ? Util.getClientAbilityName(className) : "Ability";

            Util.sendChatMessage("★ " + abilityName + " is fully charged! (100%)");
        }
    }
}
