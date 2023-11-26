package xyz.rtsvk.paper.wizardry.spells.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;

public class TeleportSpell implements ProjectileSpell {
	@Override
	public Material getMenuIcon() {
		return Material.ENDER_PEARL;
	}

	@Override
	public String getId() {
		return "teleport";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Teleport spell");
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Teleports you where the spell lands");
	}

	@Override
	public int getHungerCost() {
		return 5;
	}

	@Override
	public int getCooldownTicks() {
		return 0;
	}

	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		if (spell.getShooter() instanceof Player plr) {
			plr.teleport(target.getLocation());
		}
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		if (spell.getShooter() instanceof Player plr) {
			plr.teleport(target.getLocation().add(0.0, 1.5, 0.0));  // add 1.5 to y to avoid getting stuck in the block
		}
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {
		spell.setShooter(shooter);
	}
}
