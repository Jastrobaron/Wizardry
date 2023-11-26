package xyz.rtsvk.paper.wizardry.spells.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import xyz.rtsvk.paper.wizardry.spells.SelfSpell;

public class InvisibilitySpell implements SelfSpell {
	@Override
	public void onCast(Player player) {
		player.addPotionEffect(new PotionEffect(
				org.bukkit.potion.PotionEffectType.INVISIBILITY,
				20 * 30,
				0,
				false,
				false
		));
	}

	@Override
	public Material getMenuIcon() {
		return Material.ENDER_EYE;
	}

	@Override
	public String getId() {
		return "invisibility_self";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Invibility spell");
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Turns you invisible for 30 seconds");
	}

	@Override
	public int getHungerCost() {
		return 5;
	}

	@Override
	public int getCooldownTicks() {
		return 10;
	}
}
