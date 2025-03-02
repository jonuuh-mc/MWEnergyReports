package io.jonuuh.energyreporter.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EnergyChangeEvent extends Event
{
    public final Type type;
    public final int amount;
    public final int total;

    public EnergyChangeEvent(Type type, int amount, int total)
    {
        this.type = type;
        this.amount = amount;
        this.total = total;
    }

    public enum Type
    {
        INCREASE, DECREASE
    }

    @Override
    public String toString()
    {
        return "EnergyChangeEvent{" + "type=" + type + ", amount=" + amount + ", total=" + total + "}";
    }
}
