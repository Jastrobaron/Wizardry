package xyz.rtsvk.paper.wizardry.spells;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;

public interface Spell {
	Material getMenuIcon();
	String getId();
	TextComponent getDisplayName();
	TextComponent getDescription();
	int getHungerCost();
	int getCooldownTicks();

}
