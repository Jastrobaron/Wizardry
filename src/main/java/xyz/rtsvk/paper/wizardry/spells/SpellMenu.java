package xyz.rtsvk.paper.wizardry.spells;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import xyz.rtsvk.paper.wizardry.items.Wand;

import java.util.List;

public class SpellMenu implements Listener {

	private static final String MENU_TITLE = "Spell Menu";
	private final SpellManager spellManager;

	public SpellMenu(SpellManager spellManager) {
		this.spellManager = spellManager;
	}

	public void register() {
		Bukkit.getPluginManager().registerEvents(this, this.spellManager.getPlugin());
	}

	@EventHandler
	public void menuOpen(PlayerInteractEvent e) {
		if (!e.getPlayer().isSneaking()) return;
		if (!Wand.isWand(e.getItem())) return;
		if (!e.getAction().isRightClick()) return;

		List<Spell> spells = this.spellManager.getSpells();
		Player player = e.getPlayer();
		final String spellId;
		if (player.hasMetadata(SpellManager.SPELL_META)) {
			spellId = player.getMetadata(SpellManager.SPELL_META).get(0).asString();
		} else {
			spellId = null;
		}

		Inventory menu = Bukkit.createInventory(player, roundUp(spells.size()), Component.text(MENU_TITLE));
		spells.forEach(spell -> {
			ItemStack item = new ItemStack(spell.getMenuIcon());
			item.editMeta(meta -> {
				meta.displayName(spell.getDisplayName());
				meta.lore(List.of(
						Component.text("Description: ").append(spell.getDescription()),
						Component.text("Hunger cost: ").append(Component.text(spell.getHungerCost())),
						Component.text("Cooldown [s]: ").append(Component.text(spell.getCooldownTicks() / 20.0))
				));
				if (spell.getId().equals(spellId)) {
					meta.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1, true);
				}
				meta.getPersistentDataContainer().set(
						NamespacedKey.minecraft("spell_id"),
						PersistentDataType.STRING, spell.getId()
				);
			});
			menu.addItem(item);
		});

		e.getPlayer().openInventory(menu);
	}

	@EventHandler
	public void menuClick(InventoryClickEvent e) {
		if (e.getCurrentItem() == null) return;
		if (!(e.getView().title() instanceof TextComponent)) {
			return;
		}
		if (!((TextComponent)e.getView().title()).content().equals(MENU_TITLE)) {
			return;
		}

		String id = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(
				NamespacedKey.minecraft("spell_id"),
				PersistentDataType.STRING
		);
		Spell spell = this.spellManager.getSpell(id);
		if (spell == null) {
			e.setCancelled(true);
			e.getWhoClicked().sendActionBar(Component.text("Invalid spell!"));
			return;
		}

		e.getWhoClicked().setMetadata(SpellManager.SPELL_META, new FixedMetadataValue(this.spellManager.getPlugin(), spell.getId()));
		e.getInventory().close();
		e.getWhoClicked().sendActionBar(Component.text("Selected spell: ").append(spell.getDisplayName()));
		e.setCancelled(true);
	}

	private int roundUp(int size) {
		return (int) Math.ceil(size / 9.0) * 9;
	}
}
