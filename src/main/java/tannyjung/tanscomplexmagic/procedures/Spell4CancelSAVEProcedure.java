package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;
import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class Spell4CancelSAVEProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		TanscomplexmagicMod.queueServerWork(20, () -> {
			{
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:block.note_block.bell ambient @a[distance=..100] ~ ~ ~ 1 1 0.025");
				}
			}
			TanscomplexmagicMod.queueServerWork(5, () -> {
				{
					Entity _ent = entity;
					if (!_ent.level().isClientSide() && _ent.getServer() != null) {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "playsound minecraft:block.note_block.bell ambient @a[distance=..100] ~ ~ ~ 1 0.75 0.025");
					}
				}
			});
		});
		TanscomplexmagicMod.queueServerWork(20, () -> {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						("kill @e[tag=<ID>,name=LOTW-spell4]".replace("<ID>", entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user_DELETE_THIS)));
			if (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell == 4) {
				{
					TanscomplexmagicModVariables.PlayerVariables _vars = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES);
					_vars.spell = 0;
					_vars.markSyncDirty();
				}
			}
		});
		if (true) {
			Spell4GUIbuttonSwordClearProcedure.execute(world, x, y, z, entity);
			Spell4GUIbuttonShieldClearProcedure.execute(world, x, y, z, entity);
			Spell4GUIbuttonBowClearProcedure.execute(world, x, y, z, entity);
			Spell4GUIbuttonAnchorClearProcedure.execute(world, x, y, z, entity);
			Spell4GUIbuttonKnightSwordClearProcedure.execute(world, x, y, z, entity);
		}
	}
}