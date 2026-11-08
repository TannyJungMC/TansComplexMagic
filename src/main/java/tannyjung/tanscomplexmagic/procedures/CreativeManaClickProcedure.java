package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic_handcode.systems.Mana;

import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

public class CreativeManaClickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (false) {
			TanscomplexmagicMod.LOGGER.info(entity);
		}
		if (entity instanceof ServerPlayer == false)
			return;
		ServerPlayer player_server = (ServerPlayer) entity;
		Mana.setFull(player_server);
	}
}