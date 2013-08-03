package aroma1997.betterchests;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;


public class TileEntityBChest extends TileEntityChest {
	
	private short stackLimit;
	private short slotLimit;
	private boolean redstoneUpgrade;
	private short light;
	private boolean comparator;

	public TileEntityBChest() {
		stackLimit = 1;
		slotLimit = 0;
		light = 0;
	}

	@Override
	public String getInvName() {
		return "Adjustable Chest";
	}
	
	@Override
	public int getSizeInventory() {
		return slotLimit;
	}

	@Override
	public int getInventoryStackLimit() {
		return stackLimit;
	}
	
	public boolean upgrade(EntityPlayer player, World world) {
		if (!(player.getHeldItem().getItem() instanceof ItemUpgrade)) return false;
		ItemStack item = player.getHeldItem();
		Upgrade upgrade = Upgrade.values()[item.getItemDamage()];
		switch (upgrade) {
			case SLOT:  {
				if (slotLimit + 9 > Reference.Conf.SLOT_LIMIT) return false;
				slotLimit += 9;
				onUpgradeInserted(player);
				return true;
			}
			case STACK: {
				if (stackLimit + 1 > 64) return false;
				stackLimit += 1;
				onUpgradeInserted(player);
				return true;
			}
			case REDSTONE: {
				if(redstoneUpgrade) return false;
				redstoneUpgrade = true;
				onUpgradeInserted(player);
				return true;
			}
			case LIGHT: {
				if(light + 1 > Reference.Conf.LIGHT_LIMIT) return false;
				light++;
				onUpgradeInserted(player);
				return true;
			}
			case COMPARATOR: {
				if (this.comparator) return false;
				comparator = true;
				onUpgradeInserted(player);
				return true;
			}
		}
		return false;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
		this.slotLimit = par1NBTTagCompound.getShort("slotLimit");
		this.stackLimit = par1NBTTagCompound.getShort("stackLimit");
		this.redstoneUpgrade = par1NBTTagCompound.getBoolean("redstoneUpgrade");
		this.light = par1NBTTagCompound.getShort("lightValue");
		this.comparator = par1NBTTagCompound.getBoolean("comparator");
        super.readFromNBT(par1NBTTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("slotLimit", this.slotLimit);
        par1NBTTagCompound.setShort("stackLimit", this.stackLimit);
        par1NBTTagCompound.setBoolean("redstoneUpgrade", this.redstoneUpgrade);
        par1NBTTagCompound.setShort("lightValue", this.light);
        par1NBTTagCompound.setBoolean("comparator", this.comparator);
    }
    
    private void onUpgradeInserted(EntityPlayer player) {
    	player.inventory.mainInventory[player.inventory.currentItem].stackSize -= 1;
    	NBTTagCompound nbttagcompound = new NBTTagCompound();
    	this.writeToNBT(nbttagcompound);
    	super.readFromNBT(nbttagcompound);
    }
    
    public boolean hasRedstoneUpgrade() {
    	return this.redstoneUpgrade;
    }
    
    public int getLightValue() {
    	return light;
    }
    
    public boolean isComparator() {
    	return this.comparator;
    }
	
}
