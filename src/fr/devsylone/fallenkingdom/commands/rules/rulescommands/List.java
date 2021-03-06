package fr.devsylone.fallenkingdom.commands.rules.rulescommands;

import org.bukkit.entity.Player;

import fr.devsylone.fallenkingdom.commands.rules.FkRuleCommand;
import fr.devsylone.fallenkingdom.players.FkPlayer;
import fr.devsylone.fallenkingdom.utils.RulesFormatter;

public class List extends FkRuleCommand
{
	public List()
	{
		super("list", "", 0, "Donne la liste des règles activées.");
	}

	public void execute(Player sender, FkPlayer fkp, String[] args)
	{
		fkp.sendMessage("§7§m--------§b Liste des règles §7§m--------\n");

		for(String s : RulesFormatter.formatRules())
		{
			fkp.sendMessage(s);
		}
		fkp.sendMessage("§7§m------------------------------\n");
	}
}
