package tannyjung.tanscomplexmagic_handcode;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import tannyjung.tanscomplexmagic_core.game.EntityManager;
import tannyjung.tanscomplexmagic_handcode.systems.*;

public class HandcodeLoop {

    public static void tick (ServerLevel level_server) {

        for (ServerPlayer player_server : EntityManager.Population.filterPlayerServer(EntityManager.Population.getEverywhereStatic(level_server, "minecraft:player", "", new String[]{}))) {

            Book.Fly.updateTick(level_server, player_server);
            BasicSpells.updateTick(level_server, player_server);
            Spell1.loopTick(level_server, player_server);

        }

    }

    public static void second (ServerLevel level_server) {

        for (ServerPlayer player_server : EntityManager.Population.filterPlayerServer(EntityManager.Population.getEverywhereStatic(level_server, "minecraft:player", "", new String[]{}))) {

            Book.TextDisplay.update(level_server, player_server);
            BasicSpells.updateSecond(level_server, player_server);
            Spell1.loopSecond(level_server, player_server);

        }

    }

}
