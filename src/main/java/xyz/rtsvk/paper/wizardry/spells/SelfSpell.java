package xyz.rtsvk.paper.wizardry.spells;

import org.bukkit.entity.Player;

public interface SelfSpell extends Spell {
	void onCast(Player player);
}
