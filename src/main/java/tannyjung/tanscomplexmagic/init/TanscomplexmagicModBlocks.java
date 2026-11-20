/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package tannyjung.tanscomplexmagic.init;

import tannyjung.tanscomplexmagic.block.TargetArrowBlock;
import tannyjung.tanscomplexmagic.block.SwordKnightBlock;
import tannyjung.tanscomplexmagic.block.SpellTeleportCircleBlock;
import tannyjung.tanscomplexmagic.block.SpellHealingCircleBlock;
import tannyjung.tanscomplexmagic.block.RejuvenatingMushroomBlock;
import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

public class TanscomplexmagicModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(TanscomplexmagicMod.MODID);
	public static final DeferredBlock<Block> TARGET_ARROW;
	public static final DeferredBlock<Block> SPELL_HEALING_CIRCLE;
	public static final DeferredBlock<Block> REJUVENATING_MUSHROOM;
	public static final DeferredBlock<Block> SPELL_TELEPORT_CIRCLE;
	public static final DeferredBlock<Block> SWORD_KNIGHT;
	static {
		TARGET_ARROW = REGISTRY.register("target_arrow", TargetArrowBlock::new);
		SPELL_HEALING_CIRCLE = REGISTRY.register("spell_healing_circle", SpellHealingCircleBlock::new);
		REJUVENATING_MUSHROOM = REGISTRY.register("rejuvenating_mushroom", RejuvenatingMushroomBlock::new);
		SPELL_TELEPORT_CIRCLE = REGISTRY.register("spell_teleport_circle", SpellTeleportCircleBlock::new);
		SWORD_KNIGHT = REGISTRY.register("sword_knight", SwordKnightBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}