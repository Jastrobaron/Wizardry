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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import xyz.rtsvk.paper.wizardry.spells.ProjectileSpell;
import xyz.rtsvk.paper.wizardry.spells.Spell;
import xyz.rtsvk.paper.wizardry.spells.SpellManager;

public class YeetSpell implements ProjectileSpell {
	private static final String YEET_METADATA = "yeet";

	private final Plugin plugin;

	public YeetSpell(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onEntityHit(Projectile spell, Entity target) {
		target.setVelocity(spell.getVelocity().multiply(2));
	}

	@Override
	public void onBlockHit(Projectile spell, Block target, BlockFace face) {
		Player shooter = (Player) spell.getShooter();
		Vector spellVelocity  = spell.getVelocity();
		if (spellVelocity.length() < 0.1) {
			return; // Don't yeet if the spell is not moving
		}

		if (face == BlockFace.UP || face == BlockFace.DOWN) {
			spellVelocity.setY(-spellVelocity.getY());
		}
		else if (face == BlockFace.NORTH || face == BlockFace.SOUTH) {
			spellVelocity.setZ(-spellVelocity.getZ());
		}
		else if (face == BlockFace.EAST || face == BlockFace.WEST) {
			spellVelocity.setX(-spellVelocity.getX());
		}

		int ttl = spell.getMetadata(YEET_METADATA).get(0).asInt();
		if (ttl > 0) {
			Snowball sb = spell.getWorld().spawn(spell.getLocation(), Snowball.class);
			sb.setVelocity(spellVelocity);
			sb.setShooter(shooter);

			if (!spell.getPassengers().isEmpty()) {
				sb.addPassenger(shooter);
			}
			Spell spellData = (Spell) spell.getMetadata(SpellManager.SPELL_META).get(0).value();
			sb.setMetadata(SpellManager.SPELL_META, new FixedMetadataValue(this.plugin, spellData));
			sb.setMetadata(YEET_METADATA, new FixedMetadataValue(this.plugin, ttl - 1));
		}
	}

	@Override
	public void onFire(Snowball spell, Player shooter) {
		spell.addPassenger(shooter);
		spell.setMetadata(YEET_METADATA, new FixedMetadataValue(this.plugin, 5)); // TTL
	}

	@Override
	public Material getMenuIcon() {
		return Material.ELYTRA;
	}

	@Override
	public String getId() {
		return "yeet";
	}

	@Override
	public TextComponent getDisplayName() {
		return Component.text("Yeetus").color(TextColor.color(0x00FF00));
	}

	@Override
	public TextComponent getDescription() {
		return Component.text("Yeets you in the direction of the spell");
	}

	@Override
	public int getHungerCost() {
		return 3;
	}

	@Override
	public int getCooldownTicks() {
		return 20;
	}
}
