package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.util.Comparator;

public class Spell4GUIbuttonSwordActivateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String from_item = "";
		String to_item = "";
		double type = 0;
		if (!(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS).equals("") && entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell == 4) {
			to_item = "sword";
			type = 1;
			for (int _i1 = 0; _i1 < 6; _i1++) {
				if (true) {
					if (type == 1) {
						from_item = "minecraft:wooden_sword";
					}
					if (type == 2) {
						from_item = "minecraft:stone_sword";
					}
					if (type == 3) {
						from_item = "minecraft:iron_sword";
					}
					if (type == 4) {
						from_item = "minecraft:golden_sword";
					}
					if (type == 5) {
						from_item = "minecraft:diamond_sword";
					}
					if (type == 6) {
						from_item = "minecraft:netherite_sword";
					}
				}
				{
					final Vec3 _center = new Vec3(x, y, z);
					for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
						if (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell4_weapon_sword_count_max > entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell4_weapon_sword_count) {
							if ((BuiltInRegistries.ITEM.getKey((entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).getItem()).toString()).equals(from_item)) {
								{
									TanscomplexmagicModVariables.PlayerVariables _vars = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES);
									_vars.spell4_weapon_sword_count = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell4_weapon_sword_count + 1;
									_vars.markSyncDirty();
								}
								{
									Entity _ent = entityiterator;
									if (!_ent.level().isClientSide() && _ent.getServer() != null) {
										_ent.getServer().getCommands().performPrefixedCommand(
												new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(),
														_ent.getDisplayName(), _ent.level().getServer(), _ent),
												(("summon armor_stand ~ ~ ~ {Tags:[\"LOTW\",\"<ID>\",\"LOTW-spell4_" + "" + to_item + "],Invisible:1b,Marker:1b,NoGravity:1b,ShowArms:1b,CustomName:'{\"text\":\"LOTW-spell4\"}'}").replace("<ID>",
														entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
									}
								}
								{
									Entity _ent = entityiterator;
									if (!_ent.level().isClientSide() && _ent.getServer() != null) {
										_ent.getServer().getCommands().performPrefixedCommand(
												new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(),
														_ent.getDisplayName(), _ent.level().getServer(), _ent),
												(("data modify entity @e[tag=<ID>,tag=LOTW-spell4_" + to_item + ",limit=1,distance=..0.01] HandItems[0] set from entity @s Item").replace("<ID>",
														entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
									}
								}
								{
									Entity _ent = entityiterator;
									if (!_ent.level().isClientSide() && _ent.getServer() != null) {
										_ent.getServer().getCommands().performPrefixedCommand(
												new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(),
														_ent.getDisplayName(), _ent.level().getServer(), _ent),
												(("data modify entity @e[tag=<ID>,tag=LOTW-spell4_" + to_item + ",tag=<ID>,limit=1,distance=..0.01] ArmorItems[0] set from entity @s Item").replace("<ID>",
														entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
									}
								}
								{
									Entity _ent = entityiterator;
									if (!_ent.level().isClientSide() && _ent.getServer() != null) {
										_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(),
												_ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent),
												"particle minecraft:campfire_signal_smoke ~ ~ ~ 0.5 0.5 0.5 0.005 5 force");
									}
								}
								{
									Entity _ent = entityiterator;
									if (!_ent.level().isClientSide() && _ent.getServer() != null) {
										_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(),
												_ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent),
												"playsound minecraft:entity.evoker.prepare_summon ambient @a[distance=..100] ~ ~ ~ 1 0.75 0.025");
									}
								}
								(entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).setCount((entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.getItem() : ItemStack.EMPTY).getCount() - 1);
							}
						} else {
							break;
						}
					}
				}
				type = type + 1;
			}
		}
	}
}