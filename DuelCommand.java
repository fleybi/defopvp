package dev.duel.plugin.kit;

public enum KitType {
    NODEBUFF("nodebuff", "&aNoDebuff"),
    DIAMOND("diamond", "&bDiamond"),
    UHC("uhc", "&6UHC"),
    SUMO("sumo", "&eSumo"),
    BUILDUHC("builduhc", "&dBuildUHC"),
    BOXING("boxing", "&cBoxing"),
    OP("op", "&4&lOP"),
    NETHERITE("netherite", "&8&lNetherite"),
    ARCHER("archer", "&2Archer"),
    SOUP("soup", "&eSoup"),
    GAPPLE("gapple", "&6Gapple"),
    BEDFIGHT("bedfight", "&dBedFight"),
    AXE("axe", "&7Axe"),
    CLASSIC("classic", "&fClassic");

    private final String id;
    private final String displayName;

    KitType(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }

    public static KitType fromString(String s) {
        for (KitType k : values()) {
            if (k.id.equalsIgnoreCase(s)) return k;
        }
        return null;
    }
}
