package xyz.rtsvk.paper.wizardry.spells;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public interface ProjectileSpell extends Spell {
	void onEntityHit(Projectile spell, Entity target);
	void onBlockHit(Projectile spell, Block target, BlockFace face);
	void onFire(Snowball spell, Player shooter);
}
