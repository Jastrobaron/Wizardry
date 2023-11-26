package xyz.rtsvk.paper.wizardry.spells;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import xyz.rtsvk.paper.wizardry.items.Wand;

import java.util.ArrayList;
import java.util.List;

public class SpellManager implements Listener {
	public static final String SPELL_META = "wizardry-spell";

	private final Plugin plugin;
	private final List<Spell> spells;

	public SpellManager(Plugin plugin) {
		this.plugin = plugin;
		this.spells = new ArrayList<>();
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler
	public void cast(PlayerInteractEvent event) {   // cast the spell when the player right clicks
		if (!event.getAction().isRightClick()) return;
		if (!Wand.isWand(event.getItem())) return;
		if (event.getPlayer().isSneaking()) return;

		// FIXME: this puts all the sticks in the player's inventory on cooldown, not really a problem unless you want to fight with sticks
		if (event.getPlayer().getCooldown(Wand.MATERIAL) > 0) {
			event.getPlayer().sendActionBar(Component.text("You can't cast spells yet!"));
			return;
		}

		Player plr = event.getPlayer();
		if (!plr.hasMetadata(SPELL_META)) {
			plr.sendActionBar(Component.text("You don't have a spell selected!"));
			return;
		}

		Spell spell = getSpell(plr.getMetadata(SPELL_META).get(0).asString());
		if (spell == null) {
			plr.sendActionBar(Component.text("Invalid spell!"));
			return;
		}

		if (!plr.getGameMode().equals(GameMode.CREATIVE)) {
			if (plr.getFoodLevel() < spell.getHungerCost()) {
				plr.sendActionBar(Component.text("Food level too low!"));
				return;
			}
			plr.setFoodLevel(plr.getFoodLevel() - spell.getHungerCost());
			plr.setCooldown(Wand.MATERIAL, spell.getCooldownTicks());
		}

		if (spell instanceof ProjectileSpell pSpell) {
			Vector projectileVector = plr.getLocation().getDirection().multiply(3);   // get the direction the player is looking at
			Snowball prj = plr.launchProjectile(Snowball.class, projectileVector);   // launch a snowball, which will be our spell
			prj.setMetadata(SPELL_META, new FixedMetadataValue(this.plugin, pSpell));
			prj.setShooter(plr);
			pSpell.onFire(prj, plr);
		}
		else if (spell instanceof SelfSpell selfSpell) {
			selfSpell.onCast(plr);
		}
		else throw new IllegalStateException("Spell " + spell.getId() + " is neither a projectile spell nor a self spell!");

	}

	@EventHandler
	public void onHit(ProjectileHitEvent event) {   // when the spell hits something
		if (!event.getEntity().hasMetadata(SPELL_META)) return;

		Object value = event.getEntity().getMetadata(SPELL_META).get(0).value();
		if (value instanceof ProjectileSpell spell) {
			if (event.getHitEntity() != null) {
				spell.onEntityHit(event.getEntity(), event.getHitEntity());
			} else if (event.getHitBlock() != null) {
				spell.onBlockHit(event.getEntity(), event.getHitBlock(), event.getHitBlockFace());
			}
		}
	}

	public void registerSpell(Spell spell) {
		if (this.getSpell(spell.getId()) != null) {
			throw new IllegalArgumentException("Spell with id " + spell.getId() + " already exists!");
		}
		spells.add(spell);
	}

	public Spell getSpell(String id) {
		if (id == null) return null;
		for (Spell spell : this.spells) {
			if (spell.getId().equals(id)) {
				return spell;
			}
		}
		return null;
	}


	public List<Spell> getSpells() {
		return spells;
	}

	public Plugin getPlugin() {
		return plugin;
	}
}
