package xyz.rtsvk.paper.wizardry;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.rtsvk.paper.wizardry.commands.WandGiveCommand;
import xyz.rtsvk.paper.wizardry.items.Wand;
import xyz.rtsvk.paper.wizardry.spells.SpellMenu;
import xyz.rtsvk.paper.wizardry.spells.SpellManager;
import xyz.rtsvk.paper.wizardry.spells.impl.*;

public final class Wizardry extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().addRecipe(Wand.getRecipe());
        this.getServer().getPluginCommand(WandGiveCommand.COMMAND_NAME).setExecutor(new WandGiveCommand());

        SpellManager spellManager = new SpellManager(this);
        spellManager.registerSpell(new FireballSpell());
        spellManager.registerSpell(new LightningSpell());
        spellManager.registerSpell(new TeleportSpell());
        spellManager.registerSpell(new YeetSpell(this));
        spellManager.registerSpell(new HealSpell());
        spellManager.registerSpell(new InvisibilitySpell());
        spellManager.registerSpell(new InstaKillSpell());
        spellManager.registerSpell(new TunnelSpell(this));

        SpellMenu spellMenu = new SpellMenu(spellManager);
        spellMenu.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
