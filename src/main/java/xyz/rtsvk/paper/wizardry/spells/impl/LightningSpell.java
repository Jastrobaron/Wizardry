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
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;

public class LightningSpell implements ProjectileSpell {
	@Override
	public Material getMenuIcon() {
		return Material.NETHER_STAR;
	}

	@Override
	public String getId() {
		return "lightning";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Lightning spell").color(TextColor.color(0x00FFFF));
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Strikes a lightning");
	}

	@Override
	public int getHungerCost() {
		return 4;
	}

	@Override
	public int getCooldownTicks() {
		return 40;
	}

	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		target.getWorld().strikeLightning(target.getLocation());
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		target.getWorld().strikeLightning(target.getLocation());
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {

	}
}
