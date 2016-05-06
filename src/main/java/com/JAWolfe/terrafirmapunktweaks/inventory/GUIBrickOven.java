package com.JAWolfe.terrafirmapunktweaks.inventory;

import org.lwjgl.opengl.GL11;

import com.JAWolfe.terrafirmapunktweaks.inventory.containers.ContainerBrickOven;
import com.JAWolfe.terrafirmapunktweaks.reference.References;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEBrickOven;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.GUI.GuiContainerTFC;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GUIBrickOven extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(References.ModID.toLowerCase(), "textures/gui/BrickOvenGUI.png");
	private TEBrickOven teBrickOven;

	public GUIBrickOven(InventoryPlayer inventoryplayer, TEBrickOven te, World world, int x, int y, int z) 
	{
		super(new ContainerBrickOven(inventoryplayer, te), 176, 85);
		this.teBrickOven = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		if (texture != null)
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			guiLeft = (width - xSize) / 2;
			guiTop = (height - ySize) / 2;
			int height = this.getShiftedYSize();

			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, height);
			
			drawForeground(guiLeft, guiTop);
		}
		
		PlayerInventory.drawInventory(this, width, height, this.getShiftedYSize());
	}
	
	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if(teBrickOven != null)
		{
			int scale = teBrickOven.getTemperatureScaled(49);
			drawTexturedModalRect(guiLeft + 30, guiTop + 65 - scale, 185, 31, 15, 6);
		}
	}
}
