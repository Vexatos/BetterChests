package aroma1997.betterchests;

import aroma1997.core.inventories.ISpecialInventory;


public interface IBetterChest extends ISpecialInventory, IUpgradeProvider {
	
    public double getXPos();

    public double getYPos();

    public double getZPos();
	
}
