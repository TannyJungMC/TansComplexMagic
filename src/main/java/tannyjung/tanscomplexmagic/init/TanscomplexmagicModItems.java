/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package tannyjung.tanscomplexmagic.init;

import tannyjung.tanscomplexmagic.item.*;
import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

public class TanscomplexmagicModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(TanscomplexmagicMod.MODID);
	public static final DeferredItem<Item> TARGET_ARROW;
	public static final DeferredItem<Item> USER_BOOK;
	public static final DeferredItem<Item> USER_BOOK_OPEN;
	public static final DeferredItem<Item> SPELL_HEALING_CIRCLE;
	public static final DeferredItem<Item> REJUVENATING_MUSHROOM;
	public static final DeferredItem<Item> SPELL_TELEPORT_CIRCLE;
	public static final DeferredItem<Item> BOW;
	public static final DeferredItem<Item> BOW_1;
	public static final DeferredItem<Item> BOW_2;
	public static final DeferredItem<Item> BOW_3;
	public static final DeferredItem<Item> BOW_ARROW_SPAWN_EGG;
	public static final DeferredItem<Item> ANCHOR;
	public static final DeferredItem<Item> KNIGHT_SWORD;
	public static final DeferredItem<Item> FORGOTTEN_SWORD;
	public static final DeferredItem<Item> SWORD_KNIGHT;
	public static final DeferredItem<Item> ZODIAC_CARD_1;
	public static final DeferredItem<Item> ZODIAC_CARD_2;
	public static final DeferredItem<Item> ZODIAC_CARD_3;
	public static final DeferredItem<Item> ZODIAC_CARD_4;
	public static final DeferredItem<Item> ZODIAC_CARD_5;
	public static final DeferredItem<Item> ZODIAC_CARD_6;
	public static final DeferredItem<Item> ZODIAC_CARD_7;
	public static final DeferredItem<Item> ZODIAC_CARD_8;
	public static final DeferredItem<Item> ZODIAC_CARD_9;
	public static final DeferredItem<Item> ZODIAC_CARD_10;
	public static final DeferredItem<Item> ZODIAC_CARD_11;
	public static final DeferredItem<Item> ZODIAC_CARD_12;
	public static final DeferredItem<Item> ZODIAC_CARD_13;
	public static final DeferredItem<Item> CREATIVE_MANA;
	static {
		TARGET_ARROW = block(TanscomplexmagicModBlocks.TARGET_ARROW);
		USER_BOOK = REGISTRY.register("user_book", UserBookItem::new);
		USER_BOOK_OPEN = REGISTRY.register("user_book_open", UserBookOpenItem::new);
		SPELL_HEALING_CIRCLE = block(TanscomplexmagicModBlocks.SPELL_HEALING_CIRCLE);
		REJUVENATING_MUSHROOM = block(TanscomplexmagicModBlocks.REJUVENATING_MUSHROOM);
		SPELL_TELEPORT_CIRCLE = block(TanscomplexmagicModBlocks.SPELL_TELEPORT_CIRCLE);
		BOW = REGISTRY.register("bow", BowItem::new);
		BOW_1 = REGISTRY.register("bow_1", Bow1Item::new);
		BOW_2 = REGISTRY.register("bow_2", Bow2Item::new);
		BOW_3 = REGISTRY.register("bow_3", Bow3Item::new);
		BOW_ARROW_SPAWN_EGG = REGISTRY.register("bow_arrow_spawn_egg", () -> new DeferredSpawnEggItem(TanscomplexmagicModEntities.BOW_ARROW, -1, -1, new Item.Properties()));
		ANCHOR = REGISTRY.register("anchor", AnchorItem::new);
		KNIGHT_SWORD = REGISTRY.register("knight_sword", KnightSwordItem::new);
		FORGOTTEN_SWORD = REGISTRY.register("forgotten_sword", ForgottenSwordItem::new);
		SWORD_KNIGHT = block(TanscomplexmagicModBlocks.SWORD_KNIGHT);
		ZODIAC_CARD_1 = REGISTRY.register("zodiac_card_1", ZodiacCard1Item::new);
		ZODIAC_CARD_2 = REGISTRY.register("zodiac_card_2", ZodiacCard2Item::new);
		ZODIAC_CARD_3 = REGISTRY.register("zodiac_card_3", ZodiacCard3Item::new);
		ZODIAC_CARD_4 = REGISTRY.register("zodiac_card_4", ZodiacCard4Item::new);
		ZODIAC_CARD_5 = REGISTRY.register("zodiac_card_5", ZodiacCard5Item::new);
		ZODIAC_CARD_6 = REGISTRY.register("zodiac_card_6", ZodiacCard6Item::new);
		ZODIAC_CARD_7 = REGISTRY.register("zodiac_card_7", ZodiacCard7Item::new);
		ZODIAC_CARD_8 = REGISTRY.register("zodiac_card_8", ZodiacCard8Item::new);
		ZODIAC_CARD_9 = REGISTRY.register("zodiac_card_9", ZodiacCard9Item::new);
		ZODIAC_CARD_10 = REGISTRY.register("zodiac_card_10", ZodiacCard10Item::new);
		ZODIAC_CARD_11 = REGISTRY.register("zodiac_card_11", ZodiacCard11Item::new);
		ZODIAC_CARD_12 = REGISTRY.register("zodiac_card_12", ZodiacCard12Item::new);
		ZODIAC_CARD_13 = REGISTRY.register("zodiac_card_13", ZodiacCard13Item::new);
		CREATIVE_MANA = REGISTRY.register("creative_mana", CreativeManaItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}
}