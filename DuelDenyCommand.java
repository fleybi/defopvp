package dev.duel.plugin.kit;

import dev.duel.plugin.DuelPlugin;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class KitManager {

    private final DuelPlugin plugin;

    public KitManager(DuelPlugin plugin) {
        this.plugin = plugin;
    }

    public void applyKit(Player player, KitType kit) {
        player.getInventory().clear();
        player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(20f);

        switch (kit) {
            case NODEBUFF -> applyNoDebuff(player);
            case DIAMOND -> applyDiamond(player);
            case UHC -> applyUHC(player);
            case SUMO -> applySumo(player);
            case BUILDUHC -> applyBuildUHC(player);
            case BOXING -> applyBoxing(player);
            case OP -> applyOP(player);
            case NETHERITE -> applyNetherite(player);
            case ARCHER -> applyArcher(player);
            case SOUP -> applySoup(player);
            case GAPPLE -> applyGapple(player);
            case BEDFIGHT -> applyBedFight(player);
            case AXE -> applyAxe(player);
            case CLASSIC -> applyClassic(player);
        }
    }

    private void applyNoDebuff(Player player) {
        // Armor
        player.getInventory().setHelmet(enchant(new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION, 2));
        player.getInventory().setChestplate(enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment.PROTECTION, 2));
        player.getInventory().setLeggings(enchant(new ItemStack(Material.DIAMOND_LEGGINGS), Enchantment.PROTECTION, 2));
        player.getInventory().setBoots(enchant(new ItemStack(Material.DIAMOND_BOOTS), Enchantment.PROTECTION, 2));

        // Sword
        ItemStack sword = enchant(new ItemStack(Material.DIAMOND_SWORD), Enchantment.SHARPNESS, 5);
        player.getInventory().setItem(0, sword);

        // Pots (splash instant health II)
        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
        org.bukkit.inventory.meta.PotionMeta meta = (org.bukkit.inventory.meta.PotionMeta) pot.getItemMeta();
        meta.setBasePotionType(org.bukkit.potion.PotionType.STRONG_HEALING);
        pot.setItemMeta(meta);
        for (int i = 1; i <= 16; i++) {
            player.getInventory().setItem(i, pot.clone());
        }

        // Speed II
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
    }

    private void applyDiamond(Player player) {
        player.getInventory().setHelmet(enchant(new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION, 4));
        player.getInventory().setChestplate(enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment.PROTECTION, 4));
        player.getInventory().setLeggings(enchant(new ItemStack(Material.DIAMOND_LEGGINGS), Enchantment.PROTECTION, 4));
        player.getInventory().setBoots(enchant(new ItemStack(Material.DIAMOND_BOOTS), Enchantment.PROTECTION, 4));

        ItemStack sword = enchant(new ItemStack(Material.DIAMOND_SWORD), Enchantment.SHARPNESS, 5);
        enchant(sword, Enchantment.FIRE_ASPECT, 2);
        player.getInventory().setItem(0, sword);

        player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
    }

    private void applyUHC(Player player) {
        player.getInventory().setHelmet(enchant(new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION, 2));
        player.getInventory().setChestplate(enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment.PROTECTION, 2));
        player.getInventory().setLeggings(enchant(new ItemStack(Material.DIAMOND_LEGGINGS), Enchantment.PROTECTION, 2));
        player.getInventory().setBoots(enchant(new ItemStack(Material.DIAMOND_BOOTS), Enchantment.PROTECTION, 2));

        ItemStack sword = enchant(new ItemStack(Material.DIAMOND_SWORD), Enchantment.SHARPNESS, 3);
        player.getInventory().setItem(0, sword);

        // Bow
        ItemStack bow = enchant(new ItemStack(Material.BOW), Enchantment.POWER, 4);
        enchant(bow, Enchantment.INFINITY, 1);
        player.getInventory().setItem(1, bow);
        player.getInventory().setItem(8, new ItemStack(Material.ARROW, 1));

        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
    }

    private void applySumo(Player player) {
        player.getInventory().setItem(0, new ItemStack(Material.FISHING_ROD));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
        // No armor for sumo
    }

    private void applyBuildUHC(Player player) {
        applyUHC(player);
        player.getInventory().setItem(3, new ItemStack(Material.SANDSTONE, 64));
        player.getInventory().setItem(4, new ItemStack(Material.SANDSTONE, 64));
        player.getInventory().setItem(5, new ItemStack(Material.OAK_WOOD, 64));
        player.getInventory().setItem(6, new ItemStack(Material.OAK_PLANKS, 64));
    }

    private void applyBoxing(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        player.getInventory().setItem(0, new ItemStack(Material.WOODEN_SWORD));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
    }

    // ========================== OP ==========================
    // Непробиваемая незерка + мечи + инвиз + зелья
    private void applyOP(Player player) {
        // Незерит броня с максимальными зачарованиями
        ItemStack helm = enchant(new ItemStack(Material.NETHERITE_HELMET), Enchantment.PROTECTION, 4);
        enchant(helm, Enchantment.UNBREAKING, 3);
        enchant(helm, Enchantment.MENDING, 1);

        ItemStack chest = enchant(new ItemStack(Material.NETHERITE_CHESTPLATE), Enchantment.PROTECTION, 4);
        enchant(chest, Enchantment.UNBREAKING, 3);
        enchant(chest, Enchantment.MENDING, 1);

        ItemStack legs = enchant(new ItemStack(Material.NETHERITE_LEGGINGS), Enchantment.PROTECTION, 4);
        enchant(legs, Enchantment.UNBREAKING, 3);
        enchant(legs, Enchantment.MENDING, 1);

        ItemStack boots = enchant(new ItemStack(Material.NETHERITE_BOOTS), Enchantment.PROTECTION, 4);
        enchant(boots, Enchantment.UNBREAKING, 3);
        enchant(boots, Enchantment.FEATHER_FALLING, 4);
        enchant(boots, Enchantment.MENDING, 1);

        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);

        // Меч Sharp10 + Looting3 + Fire Aspect2
        ItemStack sword = enchant(new ItemStack(Material.NETHERITE_SWORD), Enchantment.SHARPNESS, 10);
        enchant(sword, Enchantment.FIRE_ASPECT, 2);
        enchant(sword, Enchantment.LOOTING, 3);
        enchant(sword, Enchantment.UNBREAKING, 3);
        player.getInventory().setItem(0, sword);

        // Топор Sharp10 + Effi5
        ItemStack axe = enchant(new ItemStack(Material.NETHERITE_AXE), Enchantment.SHARPNESS, 10);
        enchant(axe, Enchantment.EFFICIENCY, 5);
        enchant(axe, Enchantment.UNBREAKING, 3);
        player.getInventory().setItem(1, axe);

        // Лук Power5 + Flame + Infinity
        ItemStack bow = enchant(new ItemStack(Material.BOW), Enchantment.POWER, 5);
        enchant(bow, Enchantment.FLAME, 1);
        enchant(bow, Enchantment.INFINITY, 1);
        player.getInventory().setItem(2, bow);
        player.getInventory().setItem(35, new ItemStack(Material.ARROW, 1));

        // Золотые яблоки
        player.getInventory().setItem(3, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 16));

        // Splash зелья: сила II + регенерация
        ItemStack strengthPot = makeSplashPotion(org.bukkit.potion.PotionType.STRONG_STRENGTH);
        ItemStack regenPot = makeSplashPotion(org.bukkit.potion.PotionType.STRONG_REGENERATION);
        for (int i = 4; i <= 9; i++) player.getInventory().setItem(i, strengthPot.clone());
        for (int i = 10; i <= 15; i++) player.getInventory().setItem(i, regenPot.clone());

        // Эффекты
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 0, false, false));
    }

    // ========================== NETHERITE ==========================
    // Чистая незерка без OP фич — стандарт WellMore/PvPCult
    private void applyNetherite(Player player) {
        ItemStack helm = enchant(new ItemStack(Material.NETHERITE_HELMET), Enchantment.PROTECTION, 4);
        enchant(helm, Enchantment.UNBREAKING, 3);
        ItemStack chest = enchant(new ItemStack(Material.NETHERITE_CHESTPLATE), Enchantment.PROTECTION, 4);
        enchant(chest, Enchantment.UNBREAKING, 3);
        ItemStack legs = enchant(new ItemStack(Material.NETHERITE_LEGGINGS), Enchantment.PROTECTION, 4);
        enchant(legs, Enchantment.UNBREAKING, 3);
        ItemStack boots = enchant(new ItemStack(Material.NETHERITE_BOOTS), Enchantment.PROTECTION, 4);
        enchant(boots, Enchantment.FEATHER_FALLING, 4);
        enchant(boots, Enchantment.UNBREAKING, 3);

        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);

        // Меч Sharp5
        ItemStack sword = enchant(new ItemStack(Material.NETHERITE_SWORD), Enchantment.SHARPNESS, 5);
        enchant(sword, Enchantment.UNBREAKING, 3);
        player.getInventory().setItem(0, sword);

        // Лечащие зелья
        ItemStack pot = makeSplashPotion(org.bukkit.potion.PotionType.STRONG_HEALING);
        for (int i = 1; i <= 16; i++) player.getInventory().setItem(i, pot.clone());

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
    }

    // ========================== ARCHER ==========================
    private void applyArcher(Player player) {
        player.getInventory().setHelmet(enchant(new ItemStack(Material.LEATHER_HELMET), Enchantment.PROTECTION, 1));
        player.getInventory().setChestplate(enchant(new ItemStack(Material.CHAINMAIL_CHESTPLATE), Enchantment.PROTECTION, 1));
        player.getInventory().setLeggings(enchant(new ItemStack(Material.CHAINMAIL_LEGGINGS), Enchantment.PROTECTION, 1));
        player.getInventory().setBoots(enchant(new ItemStack(Material.LEATHER_BOOTS), Enchantment.FEATHER_FALLING, 4));

        // Меч Short для добивания
        player.getInventory().setItem(0, enchant(new ItemStack(Material.STONE_SWORD), Enchantment.SHARPNESS, 2));

        // Лук Power5 + Punch2
        ItemStack bow = enchant(new ItemStack(Material.BOW), Enchantment.POWER, 5);
        enchant(bow, Enchantment.PUNCH, 2);
        enchant(bow, Enchantment.INFINITY, 1);
        player.getInventory().setItem(1, bow);
        player.getInventory().setItem(8, new ItemStack(Material.ARROW, 1));

        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
    }

    // ========================== SOUP ==========================
    // Классический суп-файт — грибной суп вместо зелий
    private void applySoup(Player player) {
        player.getInventory().setHelmet(enchant(new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION, 2));
        player.getInventory().setChestplate(enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment.PROTECTION, 2));
        player.getInventory().setLeggings(enchant(new ItemStack(Material.DIAMOND_LEGGINGS), Enchantment.PROTECTION, 2));
        player.getInventory().setBoots(enchant(new ItemStack(Material.DIAMOND_BOOTS), Enchantment.PROTECTION, 2));

        player.getInventory().setItem(0, enchant(new ItemStack(Material.DIAMOND_SWORD), Enchantment.SHARPNESS, 3));

        // 27 грибных супов
        for (int i = 1; i <= 27; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_STEW));
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
    }

    // ========================== GAPPLE ==========================
    // Diamond + много enchanted golden apples
    private void applyGapple(Player player) {
        player.getInventory().setHelmet(enchant(new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION, 3));
        player.getInventory().setChestplate(enchant(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment.PROTECTION, 3));
        player.getInventory().setLeggings(enchant(new ItemStack(Material.DIAMOND_LEGGINGS), Enchantment.PROTECTION, 3));
        player.getInventory().setBoots(enchant(new ItemStack(Material.DIAMOND_BOOTS), Enchantment.PROTECTION, 3));

        ItemStack sword = enchant(new ItemStack(Material.DIAMOND_SWORD), Enchantment.SHARPNESS, 5);
        enchant(sword, Enchantment.FIRE_ASPECT, 2);
        player.getInventory().setItem(0, sword);

        // Enchanted golden apples (notch apples)
        player.getInventory().setItem(1, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 6));
        // Обычные gold apples
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 8));

        // Несколько зелий хила
        ItemStack pot = makeSplashPotion(org.bukkit.potion.PotionType.STRONG_HEALING);
        for (int i = 3; i <= 8; i++) player.getInventory().setItem(i, pot.clone());

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
    }

    // ========================== BEDFIGHT ==========================
    // Без брони, только меч и блоки — по типу Bed Wars
    private void applyBedFight(Player player) {
        player.getInventory().setItem(0, enchant(new ItemStack(Material.IRON_SWORD), Enchantment.SHARPNESS, 1));
        player.getInventory().setItem(1, new ItemStack(Material.SANDSTONE, 64));
        player.getInventory().setItem(2, new ItemStack(Material.SANDSTONE, 64));
        player.getInventory().setItem(3, new ItemStack(Material.TNT, 4));
        player.getInventory().setItem(4, new ItemStack(Material.FLINT_AND_STEEL));
        player.getInventory().setItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
    }

    // ========================== AXE ==========================
    // Незеритовый топор — популярный стиль в 1.9+
    private void applyAxe(Player player) {
        ItemStack helm = enchant(new ItemStack(Material.NETHERITE_HELMET), Enchantment.PROTECTION, 4);
        enchant(helm, Enchantment.UNBREAKING, 3);
        ItemStack chest = enchant(new ItemStack(Material.NETHERITE_CHESTPLATE), Enchantment.PROTECTION, 4);
        enchant(chest, Enchantment.UNBREAKING, 3);
        ItemStack legs = enchant(new ItemStack(Material.NETHERITE_LEGGINGS), Enchantment.PROTECTION, 4);
        enchant(legs, Enchantment.UNBREAKING, 3);
        ItemStack boots = enchant(new ItemStack(Material.NETHERITE_BOOTS), Enchantment.PROTECTION, 4);
        enchant(boots, Enchantment.FEATHER_FALLING, 4);

        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);

        // Основной топор — Sharp5 + Effi5
        ItemStack axe = enchant(new ItemStack(Material.NETHERITE_AXE), Enchantment.SHARPNESS, 5);
        enchant(axe, Enchantment.EFFICIENCY, 5);
        enchant(axe, Enchantment.UNBREAKING, 3);
        player.getInventory().setItem(0, axe);

        // Меч как запасной
        player.getInventory().setItem(1, enchant(new ItemStack(Material.NETHERITE_SWORD), Enchantment.SHARPNESS, 5));

        // Лечилки
        player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 2));
        ItemStack pot = makeSplashPotion(org.bukkit.potion.PotionType.STRONG_HEALING);
        for (int i = 3; i <= 8; i++) player.getInventory().setItem(i, pot.clone());

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
    }

    // ========================== CLASSIC ==========================
    // Классика 1.8 — айрон броня + алмазный меч
    private void applyClassic(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

        player.getInventory().setItem(0, enchant(new ItemStack(Material.DIAMOND_SWORD), Enchantment.SHARPNESS, 3));

        // Пол инвентаря хила
        ItemStack pot = makeSplashPotion(org.bukkit.potion.PotionType.STRONG_HEALING);
        for (int i = 1; i <= 12; i++) player.getInventory().setItem(i, pot.clone());
        player.getInventory().setItem(13, new ItemStack(Material.GOLDEN_APPLE, 2));

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
    }

    // ========================== HELPERS ==========================
    private ItemStack makeSplashPotion(org.bukkit.potion.PotionType type) {
        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
        org.bukkit.inventory.meta.PotionMeta meta = (org.bukkit.inventory.meta.PotionMeta) pot.getItemMeta();
        meta.setBasePotionType(type);
        pot.setItemMeta(meta);
        return pot;
    }

    private ItemStack enchant(ItemStack item, Enchantment ench, int level) {
        item.addUnsafeEnchantment(ench, level);
        return item;
    }

    public List<KitType> getEnabledKits() {
        List<KitType> list = new ArrayList<>();
        for (KitType kit : KitType.values()) {
            if (plugin.getConfig().getBoolean("kits." + kit.getId() + ".enabled", true)) {
                list.add(kit);
            }
        }
        return list;
    }
}
