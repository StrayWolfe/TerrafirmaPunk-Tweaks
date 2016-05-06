package com.JAWolfe.terrafirmapunktweaks.blocks;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class TFPBrickOvenRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) 
	{
		int ovenType = 0;
		
		if(block instanceof TFPBrickOven)
			ovenType = ((TFPBrickOven)block).getOvenType();
		
		IIcon cobbleIcon = TFCBlocks.stoneIgExCobble.getIcon(0, ovenType);
		
		if(ovenType > 3)
			cobbleIcon = TFCBlocks.stoneIgInCobble.getIcon(0, ovenType - 4);
				
		renderer.renderAllFaces = true;
		
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);

		renderer.overrideBlockTexture = cobbleIcon;
		renderer.renderStandardBlock(block, x, y, z);
		renderer.clearOverrideBlockTexture();
		
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		
		renderer.renderAllFaces = false;
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) 
	{
		int ovenType = 0;
		
		if(block instanceof TFPBrickOven)
			ovenType = ((TFPBrickOven)block).getOvenType();
		
		IIcon cobbleIcon = TFCBlocks.stoneIgExCobble.getIcon(0, ovenType);
		
		if(ovenType > 3)
			cobbleIcon = TFCBlocks.stoneIgInCobble.getIcon(0, ovenType - 4);
		
		renderInvBlock(block, renderer, cobbleIcon);
	}
	
	private void renderInvBlock(Block block, RenderBlocks renderer, IIcon icon)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(5));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return 0;
	}

}
