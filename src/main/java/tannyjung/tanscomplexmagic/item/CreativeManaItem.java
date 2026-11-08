package tannyjung.tanscomplexmagic.item;

import tannyjung.tanscomplexmagic.procedures.CreativeManaClickProcedure;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import java.util.List;

public class CreativeManaItem extends Item {
	public CreativeManaItem() {
		super(new Item.Properties().stacksTo(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, list, flag);
		list.add(Component.translatable("item.tanscomplexmagic.creative_mana.description_0"));
		list.add(Component.translatable("item.tanscomplexmagic.creative_mana.description_1"));
		list.add(Component.translatable("item.tanscomplexmagic.creative_mana.description_2"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		CreativeManaClickProcedure.execute(entity);
		return ar;
	}
}