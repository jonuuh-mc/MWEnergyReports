package io.jonuuh.energyreporter.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Util
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Pattern ingamePattern = Pattern.compile("^\\s*[0-9]+\\sClass\\sPoints?\\s*$");
    private static final Pattern classNameSuffixPattern = Pattern.compile(".*\\[[A-Z]{3}].*");

    public static void sendChatMessage(String msg)
    {
        if (mc.thePlayer != null)
        {
            mc.thePlayer.sendChatMessage(msg);
        }
    }

    public static String getScoreboardHeader(Scoreboard sb)
    {
        if (sb == null || sb.getObjectiveInDisplaySlot(1) == null)
        {
            return "";
        }

        String header = sb.getObjectiveInDisplaySlot(1).getDisplayName();
        return header != null ? EnumChatFormatting.getTextWithoutFormattingCodes(header) : "";
    }

    public static String getScoreboardScoreAtIndex(Scoreboard sb, boolean removeFormatting, int index)
    {
        List<String> cleanScores = getScoreboardScores(sb, removeFormatting);
        return (index < cleanScores.size()) ? cleanScores.get(index) : null;
    }

    public static List<String> getScoreboardScores(Scoreboard sb, boolean removeFormatting)
    {
        if (sb == null || sb.getObjectiveInDisplaySlot(1) == null)
        {
            return Collections.emptyList();
        }

        return new ArrayList<>(sb.getScores()).stream()
                .sorted(Comparator.comparingInt(score -> -score.getScorePoints())) // fix sidebar scores stored in reverse order
                .filter(score -> score.getObjective().getName().equals(sb.getObjectiveInDisplaySlot(1).getName())) // sidebar score name = sidebar header name?
                .map(score -> getScoreText(sb, score, removeFormatting)) // get text from each score
                .collect(Collectors.toList());
    }

    private static String getScoreText(Scoreboard sb, Score score, boolean removeFormatting)
    {
        ScorePlayerTeam spt = sb.getPlayersTeam(score.getPlayerName()); // getPlayerName: hexadecimal string? (hypixel specific)

        if (spt == null)
        {
            return "";
        }

        // If score text is <16 char, text stored in getColorPrefix; possible overflow (>16) stored in getColorSuffix (hypixel specific)
        String scoreText = spt.formatString("");
        return removeFormatting ? EnumChatFormatting.getTextWithoutFormattingCodes(scoreText) : scoreText;
    }

    public static boolean isMWEnvironment()
    {
        if (mc.theWorld == null)
        {
            return false;
        }

        Scoreboard sb = mc.theWorld.getScoreboard();

        if (sb == null || !getScoreboardHeader(sb).equals("MEGA WALLS"))
        {
            return false;
        }

        String possibleIngameScore = getScoreboardScoreAtIndex(sb, true, 11);

        if (possibleIngameScore == null)
        {
            return false;
        }

        return ingamePattern.matcher(possibleIngameScore).matches();
    }

    public static String getClientClassName()
    {
        if (mc.thePlayer == null)
        {
            return "";
        }

        NetworkPlayerInfo playerInfo = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
        ScorePlayerTeam playerTeam = playerInfo.getPlayerTeam();

        if (playerTeam == null)
        {
            return "";
        }

        String raw = EnumChatFormatting.getTextWithoutFormattingCodes(playerTeam.getColorSuffix());
        System.out.println("`" + raw + "`");

        if (classNameSuffixPattern.matcher(raw).matches())
        {
            return raw.substring(raw.indexOf('[') + 1, raw.indexOf(']'));
        }
        return "";
    }

    public static String getClientAbilityName(String className)
    {
        switch (className)
        {
            case "COW":
                return "Soothing Moo";
            case "HUN":
                return "Eagle's Eye";
            case "SRK":
                return "From the Depths";
            case "ARC":
                return "Arcane Beam";
            case "DRE":
                return "Shadow Burst";
            case "GOL":
                return "Iron Punch";
            case "HBR":
                return "Wrath";
            case "PIG":
                return "Burning Soul";
            case "ZOM":
                return "Circle of Healing";
            case "BLA":
                return "Immolating Burst";
            case "END":
                return "Teleport";
            case "SHA":
                return "Tornado";
            case "SQU":
                return "Squid Splash";
            case "CRE":
                return "Detonate";
            case "PIR":
                return "Cannon Fire";
            case "SHP":
                return "Wool War";
            case "SKE":
                return "Explosive Arrow";
            case "SPI":
                return "Leap";
            case "WER":
                return "Lycanthropy";
            case "ANG":
                return "Divine Intervention";
            case "ASN":
                return "Shadow Cloak";
            case "ATN":
                return "EMP";
            case "MOL":
                return "Dig";
            case "PHX":
                return "Spirit Bond";
            case "DRG":
                return "Scorching Breath";
            case "REN":
                return "Rend";
            case "SNO":
                return "Ice Bolt";
        }
        return "";
    }
}
