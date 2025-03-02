package io.jonuuh.energyreporter.event;

import io.jonuuh.energyreporter.config.Settings;
import io.jonuuh.energyreporter.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EnergyChangePoster
{
    private final Minecraft mc;
    private int lastTickEnergy = 0;

    public EnergyChangePoster()
    {
        this.mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.ClientTickEvent event)
    {
        if (mc.thePlayer == null || event.phase != TickEvent.Phase.END || !Util.isMWEnvironment() || !Settings.getInstance().doAutoReports())
        {
            return;
        }

        int thisTickEnergy = mc.thePlayer.experienceLevel;

        if (thisTickEnergy != lastTickEnergy)
        {
            EnergyChangeEvent.Type type = (thisTickEnergy > lastTickEnergy) ? EnergyChangeEvent.Type.INCREASE : EnergyChangeEvent.Type.DECREASE;
            int energyChange = Math.abs(thisTickEnergy - lastTickEnergy);

            MinecraftForge.EVENT_BUS.post(new EnergyChangeEvent(type, energyChange, thisTickEnergy));

            lastTickEnergy = thisTickEnergy;
        }
    }
}
