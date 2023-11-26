package xyz.rtsvk.paper.wizardry.spells.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;

public class InstaKillSpell implements ProjectileSpell {
	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		if (target instanceof LivingEntity le) {
			if (target instanceof Boss && spell.getShooter() instanceof Player plr) {
				plr.sendActionBar(Component.text("You can't insta-kill bosses! Fuck you!"));
				return;
			}

			le.setHealth(0);
			le.getLocation().getWorld().playSound(le, Sound.ENTITY_WITHER_DEATH, 1.0f, 1.0f);
		}
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		target.getLocation().getWorld().createExplosion(
				target.getLocation(),
				4.0f,
				false,
				false,
				spell
		);
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {
		shooter.setFoodLevel(0);
		shooter.addPotionEffect(new PotionEffect(
				PotionEffectType.DARKNESS,
				20 * 10,
				0,
				false,
				false
		));
		shooter.addPotionEffect(new PotionEffect(
				PotionEffectType.BLINDNESS,
				20 * 10,
				0,
				false,
				false
		));
		shooter.addPotionEffect(new PotionEffect(
				PotionEffectType.CONFUSION,
				20 * 10,
				0,
				false,
				false
		));
	}

	@Override
	public Material getMenuIcon() {
		return Material.DIAMOND_SWORD;
	}

	@Override
	public String getId() {
		return "insta_kill";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Insta-kill spell").color(TextColor.color(0xFF0000));
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Instantly kills the target, but drains 20 hunger points");
	}

	@Override
	public int getHungerCost() {
		return 20;
	}

	@Override
	public int getCooldownTicks() {
		return 1200;
	}
}
