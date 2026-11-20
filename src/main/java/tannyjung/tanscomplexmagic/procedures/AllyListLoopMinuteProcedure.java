package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class AllyListLoopMinuteProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String id = "";
		String chosen = "";
		for (int _i1 = 0; _i1 < 3; _i1++) {
			if ((id).equals("")) {
				id = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_auto1;
			} else if ((id).equals(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_auto1)) {
				id = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_auto2;
			} else {
				id = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_auto3;
			}
			chosen = ("@e[" + id + ",tag=!<ID>-ally]").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS);
			if (true) {
				if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User") || (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User and Chosen")
						|| (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User and Ally")
						|| (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User, Chosen and Ally")) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
											_ent.level().getServer(), _ent),
									("execute if entity " + chosen + " run tellraw @p [\"\",{\"text\":\"Added \",\"color\":\"dark_green\"},{\"text\":\"\",\"color\":\"gray\",\"extra\":[{\"selector\":\"" + chosen
											+ "\"}]},{\"text\":\" to ally list\",\"color\":\"dark_green\"}]"));
						}
					}
				}
				if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("Ally") || (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User and Ally")
						|| (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("Chosen and Ally")
						|| (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User, Chosen and Ally")) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
											_ent.level().getServer(), _ent),
									(("execute if entity " + chosen
											+ " run tellraw @a[tag=<ID>-ally_custom] [\"\",{\"text\":\"\",\"color\":\"dark_green\",\"extra\":[{\"selector\":\"@p\"}]},{\"text\":\" added \",\"color\":\"dark_green\"},{\"text\":\"\",\"color\":\"gray\",\"extra\":[{\"selector\":\""
											+ chosen + "\"}]},{\"text\":\" to ally list\",\"color\":\"dark_green\"}]").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
						}
					}
				}
				if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("Chosen") || (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User and Chosen")
						|| (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("Chosen and Ally")
						|| (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).ally_list_chat_setting).equals("User, Chosen and Ally")) {
					{
						Entity _ent = entity;
						if (!_ent.level().isClientSide() && _ent.getServer() != null) {
							_ent.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4, _ent.getName().getString(), _ent.getDisplayName(),
											_ent.level().getServer(), _ent),
									("execute if entity " + chosen + " run tellraw " + chosen + " [\"\",{\"text\":\"\",\"color\":\"dark_green\",\"extra\":[{\"selector\":\"@p\"}]},{\"text\":\" added you to ally list\",\"color\":\"dark_green\"}]"));
						}
					}
				}
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							("execute if entity " + chosen + " run effect give " + chosen + " glowing 5 1 true"));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							(("execute if entity " + chosen + " run tag " + chosen + " add <ID>-ally_custom").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							(("execute if entity " + chosen + " run tag " + chosen + " add <ID>-ally").replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
			}
		}
	}
}