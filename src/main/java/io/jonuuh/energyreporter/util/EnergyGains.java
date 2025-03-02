package io.jonuuh.energyreporter.util;

public enum EnergyGains
{
    COW(25,20),
    HUN(4,8, 0.75F),
    SRK(18,18),
    ARC(34,34),
    DRE(10,10),
    GOL(10,10),
    HBR(25,25),
    PIG(10,10),
    ZOM(12,12, 1, 2),
    BLA(8,4, 4),
    END(20,20),
    SHA(10,10),
    SQU(10,10),
    CRE(30,20),
    PIR(12,12),
    SHP(10,5, 2),
    SKE(0,20, 1),
    SPI(8,8, 4),
    WER(10,10, 2, 2),
    ANG(12,12),
    ASN(10,10, 2),
    ATN(4,4, 6, 6),
    MOL(10,10,3), // auto is 5 while in prep phase but we gonna ignore that
    PHX(8,14, 1),
    DRG(12,8, 1),
    REN(13,17),
    SNO(8,8, 2);

    /** Energy per hit (melee) */
    public final int melee;
    /** Energy per hit (bow) */
    public final int bow;
    /** Energy when hit (melee) */
    public final int hitmelee;
    /** Energy when hit (bow) */
    public final int hitbow;
    /** Energy per second */
    public final float auto;

    EnergyGains(int melee, int bow, int hitmelee, int hitbow, float auto)
    {
        this.melee = melee;
        this.bow = bow;
        this.hitmelee = hitmelee;
        this.hitbow = hitbow;
        this.auto = auto;
    }

    EnergyGains(int melee, int bow)
    {
        this(melee, bow, -1, -1, -1);
    }

    EnergyGains(int melee, int bow, float auto)
    {
        this(melee, bow, -1, -1, auto);
    }

    EnergyGains(int melee, int bow, int hitmelee, int hitbow)
    {
        this(melee, bow, hitmelee, hitbow, -1);
    }
}
