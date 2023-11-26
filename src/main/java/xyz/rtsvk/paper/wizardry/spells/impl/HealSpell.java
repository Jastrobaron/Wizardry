package xyz.rtsvk.paper.wizardry.spells.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;

public class HealSpell implements ProjectileSpell {

	@Override
	public Material getMenuIcon() {
		return Material.ENCHANTED_GOLDEN_APPLE;
	}

	@Override
	public String getId() {
		return "health_self";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Healing spell");
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Heals the target for 5 hearts or up to their max health");
	}

	@Override
	public int getHungerCost() {
		return 10;
	}

	@Override
	public int getCooldownTicks() {
		return 200;
	}

	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		if (target instanceof LivingEntity mob) {
			mob.setHealth(Math.min(mob.getHealth() + 10, mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
		}
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		for (Entity entity : target.getLocation().getNearbyEntities(2, 2, 2)) {
			if (entity instanceof LivingEntity mob) {
				mob.setHealth(Math.min(mob.getHealth() + 10, mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
			}
		}
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {

	}
}
