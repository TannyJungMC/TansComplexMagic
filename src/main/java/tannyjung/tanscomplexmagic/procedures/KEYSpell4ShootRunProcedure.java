package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class KEYSpell4ShootRunProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).user == true && Math.floor(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell) == 4) {
			if (!(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell4_type).equals("Off")) {
				{
					TanscomplexmagicModVariables.PlayerVariables _vars = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES);
					_vars.spell4_shoot = true;
					_vars.markSyncDirty();
				}
				if (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).main_key == false) {
					if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell4_mode).equals("Single Shot")) {
						Spell4ShootNearestProcedure.execute(world, x, y, z, entity);
					} else if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).spell4_mode).equals("Multiple Shot")) {
						for (int _i1 = 0; _i1 < 100; _i1++) {
							Spell4ShootNearestProcedure.execute(world, x, y, z, entity);
						}
					}
				}
			}
		}
	}
}