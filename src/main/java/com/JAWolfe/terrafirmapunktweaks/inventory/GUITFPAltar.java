package com.JAWolfe.terrafirmapunktweaks.inventory;

import org.lwjgl.opengl.GL11;

import com.JAWolfe.terrafirmapunktweaks.inventory.containers.ContainerTFPAltar;
import com.JAWolfe.terrafirmapunktweaks.tiles.TEAltar;
import com.sirolf2009.necromancy.lib.ReferenceNecromancy;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GUITFPAltar extends GuiContainer
{
	public GUITFPAltar(InventoryPlayer par1InventoryPlayer, TEAltar par2TileEntityAltar)
	{
		super(new ContainerTFPAltar(par1InventoryPlayer, par2TileEntityAltar));
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRendererObj.drawString("Blood", 21, 60, 0xFFFFFF);
        fontRendererObj.drawString("Soul", 132, 60, 0xFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ReferenceNecromancy.TEXTURES_GUI_ALTAR);
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
        drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
    }
}
