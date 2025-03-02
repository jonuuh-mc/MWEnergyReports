package io.jonuuh.energyreporter.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class Command extends CommandBase
{
    private static Settings settingsInstance;

    public Command()
    {
        settingsInstance = Settings.getInstance();
    }

    @Override
    public String getCommandName()
    {
        return "energyreporter";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (!(sender.getCommandSenderEntity() instanceof EntityPlayerSP) || args.length != 1)
        {
            return;
        }

        String t = EnumChatFormatting.GREEN + "true";
        String f = EnumChatFormatting.RED + "false";
        String msg = "";

        switch (args[0])
        {
            case "doAutoReports":
                settingsInstance.setDoAutoReports(!settingsInstance.doAutoReports());
                msg = "Automatic energy reports: " + (settingsInstance.doAutoReports() ? t : f);
                break;
            case "showHitsNeeded":
                settingsInstance.setShowHitsNeeded(!settingsInstance.showHitsNeeded());
                msg = "Show needed max eph hits for ability in energy reports: " + (settingsInstance.showHitsNeeded() ? t : f);
                break;
            case "showAbilityName":
                settingsInstance.setShowAbilityName(!settingsInstance.showAbilityName());
                msg = "Show ability name in energy reports: " + (settingsInstance.showAbilityName() ? t : f);
                break;
        }

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
        settingsInstance.save();
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "doAutoReports", "showHitsNeeded", "showAbilityName");
        }
        return null;
    }
}