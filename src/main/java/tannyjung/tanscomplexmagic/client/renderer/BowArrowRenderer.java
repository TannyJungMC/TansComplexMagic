package tannyjung.tanscomplexmagic.client.renderer;

import tannyjung.tanscomplexmagic.entity.BowArrowEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

public class BowArrowRenderer extends HumanoidMobRenderer<BowArrowEntity, HumanoidModel<BowArrowEntity>> {
	private final ResourceLocation entityTexture = ResourceLocation.parse("tanscomplexmagic:textures/entities/invisible.png");

	public BowArrowRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<BowArrowEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(BowArrowEntity entity) {
		return entityTexture;
	}
}