package xyz.rtsvk.paper.wizardry.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public class Wand extends ItemStack {
	public static final Material MATERIAL = Material.STICK;

	public Wand() {
		super(MATERIAL);
		this.editMeta(meta -> {
			meta.displayName(Component.text("Wand").color(TextColor.color(0x00FF00)));
			meta.lore(Collections.singletonList(Component.text("A wand for casting spells")));
			meta.getPersistentDataContainer().set(NamespacedKey.minecraft("wizardry.is_wand"), PersistentDataType.BYTE, (byte) 1);
		});
	}

	public static boolean isWand(ItemStack item) {
		if (item == null) return false;
		ItemMeta meta = item.getItemMeta();
		if (meta == null) return false;
		return meta.getPersistentDataContainer().has(NamespacedKey.minecraft("wizardry.is_wand"), PersistentDataType.BYTE);
	}

	public static Recipe getRecipe() {
		ShapedRecipe rec = new ShapedRecipe(NamespacedKey.minecraft("wizardry.wand"), new Wand());
		rec.shape(" DS", "DBD", "SD ");
		rec.setIngredient('D', Material.DIAMOND);
		rec.setIngredient('S', Material.NETHER_STAR);
		rec.setIngredient('B', Material.BLAZE_ROD);
		return rec;
	}
}
