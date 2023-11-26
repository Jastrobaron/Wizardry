package xyz.rtsvk.paper.wizardry.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.rtsvk.paper.wizardry.items.Wand;

public class WandGiveCommand implements CommandExecutor {

	public static final String COMMAND_NAME = "wand";

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		if (commandSender instanceof Player plr) {
			plr.getInventory().addItem(new Wand());
		}
		return true;
	}
}
