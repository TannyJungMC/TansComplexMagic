package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;
import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class PersonalSkillClickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill).equals("The Missing Person")) {
			if (true) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem(),
							(int) (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_cooldown * 20));
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:flash ~ ~1 ~ 0 0 0 0 1 force");
					}
				}
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:campfire_signal_smoke ~ ~1 ~ 1 1 1 0.01 10 force");
					}
				}
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:entity.illusioner.mirror_move ambient @a[distance=..100] ~ ~ ~ 2 0 0.025");
					}
				}
				if (true) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
											_ent.level().getServer(), _ent),
									(("effect give @p minecraft:invisibility "
											+ (new java.text.DecimalFormat("##.##")
													.format(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_duration * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level)).replace(".0", "")
											+ " 1 true").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
						}
					}
				}
				TanscomplexmagicMod.queueServerWork((int) (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_duration * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level * 20), () -> {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:flash ~ ~1 ~ 0 0 0 0 1 force");
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:campfire_signal_smoke ~ ~1 ~ 1 1 1 0.01 10 force");
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:entity.illusioner.cast_spell ambient @a[distance=..100] ~ ~ ~ 2 0.75 0.025");
						}
					}
				});
			}
		}
		if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill).equals("Circle of Healing")) {
			if (true) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem(),
							(int) (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_cooldown * 20));
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(
								new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
										_ent.level().getServer(), _ent),
								(("summon armor_stand ~ ~-0.7 ~ {Tags:[" + "\"LOTW\",\"<ID>\",\"<ID>-ally\",\"LOTW-circle_hl_sX\"" + "],NoGravity:1b,Invisible:1b,Marker:1b,CustomName:'{\"text\":\"" + "<ID>-personal_skill_circle"
										+ "\"}',ArmorItems:[{},{},{},{id:\"lotw:spell_" + "healing_circle" + "\",Count:1b}],Small:1b}").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					}
				}
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~0.7 ~ run particle flash ~ ~ ~ 0 0 0 0 1 force".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run particle minecraft:instant_effect ~ ~0.1 ~ 1 0 1 0 20 force".replace("<ID>",
									entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run particle minecraft:firework ~ ~0.1 ~ 1 0 1 0.1 20 force".replace("<ID>",
									entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~0.7 ~ run playsound minecraft:block.ender_chest.open ambient @a[distance=..100] ~ ~ ~ 2 0.75 0.025".replace("<ID>",
									entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~0.7 ~ run playsound minecraft:block.beacon.power_select ambient @a[distance=..100] ~ ~ ~ 2 0 0.025".replace("<ID>",
									entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run playsound minecraft:entity.evoker.prepare_summon ambient @a[distance=..100] ~ ~ ~ 2 0.75 0.025".replace("<ID>",
									entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							(("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run effect give @e[name=!<ID>-personal_skill_circle,distance=.." + entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_radius
									+ "] instant_health 1 " + (new java.text.DecimalFormat("##.##").format((new Object() {
										double convert(String s) {
											try {
												return Double.parseDouble(s.trim());
											} catch (Exception e) {
											}
											return 0;
										}
									}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) / 4) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level - 1)).replace(".0", "")
									+ " true").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				TanscomplexmagicMod.queueServerWork(100, () -> {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run particle flash ~ ~ ~ 0 0 0 0 1 force".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run playsound minecraft:entity.illusioner.cast_spell ambient @a[distance=..100] ~ ~ ~ 2 0 0.025".replace("<ID>",
										entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("kill @e[name=<ID>-personal_skill_circle]".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				});
			}
		}
		if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill).equals("Rejuvenating Mushroom")) {
			if (true) {
				if (world
						.getBlockState(new BlockPos(
								entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(200)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(),
								entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(200)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY(),
								entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(200)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ()))
						.canOcclude()) {
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem(),
								(int) (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_cooldown * 20));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(
								(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(200)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX()),
								(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(200)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY()),
								(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(200)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())),
								Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								(("execute unless block ~ ~ ~ #lotw:passable_blocks run summon armor_stand ~0.5 ~1 ~0.5 {Tags:[" + "\"LOTW\",\"<ID>\",\"<ID>-ally\"" + "],NoGravity:1b,Invisible:1b,Marker:1b,CustomName:'{\"text\":\""
										+ "<ID>-personal_skill_raytrace" + "\"}'}").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("execute at @e[name=<ID>-personal_skill_raytrace] run setblock ~ ~ ~ lotw:rejuvenating_mushroom keep".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("execute at @e[name=<ID>-personal_skill_raytrace] run particle minecraft:instant_effect ~ ~ ~ 0 0 0 1 10 force".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("execute at @e[name=<ID>-personal_skill_raytrace] run playsound minecraft:block.grass.place ambient @a[distance=..100] ~ ~ ~ 2 2 0.025".replace("<ID>",
										entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
									_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:item.trident.riptide_1 ambient @a[distance=..100] ~ ~ ~ 0.25 1.25 0.025");
						}
					}
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								(("execute at @e[name=<ID>-personal_skill_raytrace] run data modify block ~ ~ ~ " + "ForgeData.duration" + " set value "
										+ entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_duration * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level)
										.replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								(("execute at @e[name=<ID>-personal_skill_raytrace] run data modify block ~ ~ ~ " + "ForgeData.radius" + " set value " + entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_radius)
										.replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								(("execute at @e[name=<ID>-personal_skill_raytrace] run data modify block ~ ~ ~ " + "ForgeData.custom_value" + " set value " + new Object() {
									double convert(String s) {
										try {
											return Double.parseDouble(s.trim());
										} catch (Exception e) {
										}
										return 0;
									}
								}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value)).replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("kill @e[name=<ID>-personal_skill_raytrace]".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				}
			}
		}
		if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill).equals("Blink")) {
			if (true) {
				if (world.getBlockState(new BlockPos(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale((new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level))), ClipContext.Block.OUTLINE,
						ClipContext.Fluid.NONE, entity)).getBlockPos().getX(), entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale((new Object() {
							double convert(String s) {
								try {
									return Double.parseDouble(s.trim());
								} catch (Exception e) {
								}
								return 0;
							}
						}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level))), ClipContext.Block.OUTLINE,
								ClipContext.Fluid.NONE, entity)).getBlockPos().getY(),
						entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale((new Object() {
							double convert(String s) {
								try {
									return Double.parseDouble(s.trim());
								} catch (Exception e) {
								}
								return 0;
							}
						}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level))), ClipContext.Block.OUTLINE,
								ClipContext.Fluid.NONE, entity)).getBlockPos().getZ()))
						.canOcclude()) {
					if (entity instanceof Player _player)
						_player.getCooldowns().addCooldown((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem(),
								(int) (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_cooldown * 20));
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands()
								.performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3((entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale((new Object() {
									double convert(String s) {
										try {
											return Double.parseDouble(s.trim());
										} catch (Exception e) {
										}
										return 0;
									}
								}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level))), ClipContext.Block.OUTLINE,
										ClipContext.Fluid.NONE, entity)).getBlockPos().getX()), (entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale((new Object() {
											double convert(String s) {
												try {
													return Double.parseDouble(s.trim());
												} catch (Exception e) {
												}
												return 0;
											}
										}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level))),
												ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY()),
										(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale((new Object() {
											double convert(String s) {
												try {
													return Double.parseDouble(s.trim());
												} catch (Exception e) {
												}
												return 0;
											}
										}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level))),
												ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ())),
										Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
										(("execute unless block ~ ~ ~ #lotw:passable_blocks run summon armor_stand ~0.5 ~1 ~0.5 {Tags:[" + "\"LOTW\",\"<ID>\",\"<ID>-ally\"" + "],NoGravity:1b,Invisible:1b,Marker:1b,CustomName:'{\"text\":\""
												+ "<ID>-personal_skill_raytrace" + "\"}'}").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					if (true) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(
										new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(),
												_ent.getDisplayName(), _ent.level().getServer(), _ent),
										(("summon armor_stand ~ ~-0.7 ~ {Tags:[" + "\"LOTW\",\"<ID>\",\"<ID>-ally\",\"LOTW-circle_hl_sX\"" + "],NoGravity:1b,Invisible:1b,Marker:1b,CustomName:'{\"text\":\"" + "<ID>-personal_skill_circle"
												+ "\"}',ArmorItems:[{},{},{},{id:\"lotw:spell_" + "teleport" + "_circle\",Count:1b}],Small:1b}").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:flash ~ ~1 ~ 0 0 0 0 1 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:campfire_signal_smoke ~ ~1 ~ 1 1 1 0.01 10 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:portal ~ ~0.25 ~ 1 0 1 0.1 50 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:dragon_breath ~ ~0.25 ~ 1 0 1 0 20 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:block.ender_chest.open ambient @a[distance=..100] ~ ~ ~ 2 0 0.025");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:entity.illusioner.mirror_move ambient @a[distance=..100] ~ ~ ~ 2 0.75 0.025");
							}
						}
					}
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands()
									.performPrefixedCommand(
											new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(),
													_ent.getDisplayName(), _ent.level().getServer(), _ent),
											("execute at @e[name=<ID>-personal_skill_raytrace] run tp @p ~ ~ ~".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
						}
					}
					if (true) {
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(
										new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(),
												_ent.getDisplayName(), _ent.level().getServer(), _ent),
										(("summon armor_stand ~ ~-0.7 ~ {Tags:[" + "\"LOTW\",\"<ID>\",\"<ID>-ally\",\"LOTW-circle_hl_sX\"" + "],NoGravity:1b,Invisible:1b,Marker:1b,CustomName:'{\"text\":\"" + "<ID>-personal_skill_circle"
												+ "\"}',ArmorItems:[{},{},{},{id:\"lotw:spell_" + "teleport" + "_circle\",Count:1b}],Small:1b}").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:flash ~ ~1 ~ 0 0 0 0 1 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:campfire_signal_smoke ~ ~1 ~ 1 1 1 0.01 10 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:reverse_portal ~ ~0.25 ~ 1 0 1 0.1 50 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "particle minecraft:dragon_breath ~ ~0.25 ~ 1 0 1 0.1 20 force");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:block.ender_chest.open ambient @a[distance=..100] ~ ~ ~ 2 0 0.025");
							}
						}
						{
							Entity _ent = entity;
							if (!_ent.level().isClientSide() && _ent.getServer() != null) {
								_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
										_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:entity.illusioner.mirror_move ambient @a[distance=..100] ~ ~ ~ 2 0.75 0.025");
							}
						}
					}
					TanscomplexmagicMod.queueServerWork(100, () -> {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run particle flash ~ ~ ~ 0 0 0 0 1 force".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									("execute at @e[name=<ID>-personal_skill_circle] positioned ~ ~1.45 ~ run playsound minecraft:entity.illusioner.cast_spell ambient @a[distance=..100] ~ ~ ~ 2 0 0.025".replace("<ID>",
											entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									("kill @e[name=<ID>-personal_skill_circle]".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									("kill @e[name=<ID>-personal_skill_raytrace]".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
					});
				}
			}
		}
	}
}