package xyz.rtsvk.paper.wizardry.spells.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;
import xyz.rtsvk.paper.wizardry.spells.SpellManager;

public class TunnelSpell implements ProjectileSpell {

	private final Plugin plugin;
	public TunnelSpell(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		if (target instanceof LivingEntity le) {
			le.setVelocity(spell.getVelocity().multiply(2));
			le.damage(4.0, spell);
		}
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		breakArea(target, 1);
		target.getLocation().getWorld().createExplosion(
				target.getLocation(),
				4.0f,
				false,
				false,
				spell);

		if (spell.getVelocity().length() < 0.1) return;

		// spawn a new projectile to continue the tunnel
		Snowball newSpell = target.getWorld().spawn(target.getLocation().add(spell.getVelocity()), Snowball.class);
		ProjectileSpell spellData = (ProjectileSpell) spell.getMetadata(SpellManager.SPELL_META).get(0).value();
		newSpell.setVelocity(spell.getVelocity());
		newSpell.setShooter(spell.getShooter());
		newSpell.setMetadata(SpellManager.SPELL_META, new FixedMetadataValue(this.plugin, spellData));
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {
		// do nothing
	}

	@Override
	public Material getMenuIcon() {
		return Material.IRON_PICKAXE;
	}

	@Override
	public String getId() {
		return "tunnel";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Tunnel spell").color(TextColor.color(0x00FF00));
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Breaks a 3x3 area around the target block");
	}

	@Override
	public int getHungerCost() {
		return 5;
	}

	@Override
	public int getCooldownTicks() {
		return 20;
	}

	private void breakArea(Block block, int radius) {
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				block.getRelative(x, y, 0).breakNaturally();
			}
		}
	}
}
