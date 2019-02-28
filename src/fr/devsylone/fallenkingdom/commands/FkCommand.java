package fr.devsylone.fallenkingdom.commands;

import org.bukkit.entity.Player;

import fr.devsylone.fallenkingdom.Fk;
import fr.devsylone.fallenkingdom.players.FkPlayer;
import fr.devsylone.fallenkingdom.utils.FkSound;

public abstract class FkCommand
{
	protected int nbrArgs;
	protected String usage;
	protected String path;
	protected String description;

	public FkCommand(String path, String args, int nbrArgs, String description)
	{
		this.path = path;
		usage = ("/fk " + this.path + " " + args);

		this.nbrArgs = nbrArgs;
		this.description = description;
	}

	public abstract void execute(Player sender, FkPlayer fkp, String[] args) throws Exception;

	public int getNbrArgs()
	{
		return nbrArgs;
	}

	public String getUsage()
	{
		return usage;
	}

	protected void broadcast(String message, String prefix, FkSound sound)
	{
		Fk.broadcast(message, prefix, sound);
	}

	public String getDescription()
	{
		return description;
	}

	public String getPath()
	{
		return path;
	}
}
