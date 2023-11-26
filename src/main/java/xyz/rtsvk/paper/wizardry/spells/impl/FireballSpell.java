package xyz.rtsvk.paper.wizardry.spells.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;

public class FireballSpell implements ProjectileSpell {
	@Override
	public Material getMenuIcon() {
		return Material.FIRE_CHARGE;
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Fireball spell").color(TextColor.color(0xFF0000));
	}

	@Override
	public String getId() {
		return "fireball";
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Launches a fireball");
	}

	@Override
	public int getHungerCost() {
		return 2;
	}

	@Override
	public int getCooldownTicks() {
		return 40;
	}

	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		target.setFireTicks(100);
		target.getLocation().createExplosion(2, true, true);
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		target.getLocation().createExplosion(2, true, true);
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {
		spell.setShooter(shooter);
		spell.setItem(new ItemStack(Material.FIRE_CHARGE));
	}
}
