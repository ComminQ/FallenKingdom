package fr.devsylone.fkpi.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public final class XPotionData
{
	private final PotionType type;

	private final boolean extended;

	private final boolean upgraded;

	private static final boolean VERSION1_8 = Bukkit.getBukkitVersion().contains("1.8");

	public XPotionData(PotionType type, boolean extended, boolean upgraded)
	{
		Validate.notNull(type, "Potion Type must not be null");
		Validate.isTrue(!(upgraded && type.getMaxLevel() == 1), "Potion Type is not upgradable");
		Validate.isTrue(!(extended && type.isInstant()), "Potion Type is not extendable");
		Validate.isTrue(!(upgraded && extended), "Potion cannot be both extended and upgraded");
		this.type = type;
		this.extended = extended;
		this.upgraded = upgraded;
	}

	public XPotionData(PotionType type)
	{
		this(type, false, false);
	}

	public PotionType getType()
	{
		return this.type;
	}

	public boolean isUpgraded()
	{
		return this.upgraded;
	}

	public boolean isExtended()
	{
		return this.extended;
	}

	public int hashCode()
	{
		int hash = 7;
		hash = 23 * hash + ((this.type != null) ? this.type.hashCode() : 0);
		hash = 23 * hash + (this.extended ? 1 : 0);
		hash = 23 * hash + (this.upgraded ? 1 : 0);
		return hash;
	}

	@Override
	public String toString()
	{
		return "XPotionData [type=" + type + ", extended=" + extended + ", upgraded=" + upgraded + "]";
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		XPotionData other = (XPotionData) obj;
		return(this.upgraded == other.upgraded && this.extended == other.extended && this.type == other.type);
	}

	public static XPotionData fromItemStack(ItemStack potionItem)
	{
		if(potionItem == null || !(potionItem.getItemMeta() instanceof PotionMeta))
			return null;

		if(VERSION1_8)
		{
			Potion potion = Potion.fromItemStack(potionItem);
			return potion.getType() == null ? null : new XPotionData(potion.getType(), potion.hasExtendedDuration(), potion.getLevel() > 1);
		}
		else
		{//TODO type == null ??
			PotionData data = ((PotionMeta) potionItem.getItemMeta()).getBasePotionData();
			return new XPotionData(data.getType(), data.isExtended(), data.isUpgraded());
		}
	}

	public void applyTo(ItemStack potionItem)
	{
		if(potionItem == null || !(potionItem.getItemMeta() instanceof PotionMeta))
			return;

		if(VERSION1_8)
		{
			Potion potion = Potion.fromItemStack(potionItem);
			potion.setType(type);
			if(!type.isInstant())
				potion.setHasExtendedDuration(extended);
			potion.setLevel(upgraded ? 2 : 1);
			potion.apply(potionItem);
		}
		else
		{
			PotionMeta meta = (PotionMeta) potionItem.getItemMeta();
			meta.setBasePotionData(new PotionData(type, extended, upgraded));
			potionItem.setItemMeta(meta);
		}
	}
	
	public static boolean isExtendable(PotionType type)
	{
		if(VERSION1_8)
			return !type.isInstant();
		else
			return type.isExtendable();
	}
	
	public static boolean isUpgradable(PotionType type)
	{
		if(VERSION1_8)
			return type.getMaxLevel() > 1;
		else
			return type.isUpgradeable();
	}
}
