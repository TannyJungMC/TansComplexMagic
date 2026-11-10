package tannyjung.tanscomplexmagic_handcode;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import tannyjung.tanscomplexmagic_core.game.EntityManager;
import tannyjung.tanscomplexmagic_handcode.systems.*;

public class HandcodeLoop {

    public static void tick (ServerLevel level_server) {

        for (ServerPlayer player_server : EntityManager.Population.filterPlayerServer(EntityManager.Population.getEverywhereStatic(level_server, "minecraft:player", "", new String[]{}))) {

            // Placed User Book
            {

                for (Entity entity : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book"}))) {

                    Book.updatePlacedBook(level_server, player_server, entity);

                }

            }

            BasicSpells.loopTick(level_server, player_server);
            Spell1.loopTick(level_server, player_server);

        }

    }

    public static void second (ServerLevel level_server) {

        for (ServerPlayer player_server : EntityManager.Population.filterPlayerServer(EntityManager.Population.getEverywhereStatic(level_server, "minecraft:player", "", new String[]{}))) {

            Book.Log.update(player_server);
            BasicSpells.loopSecond(level_server, player_server);
            Spell1.loopSecond(level_server, player_server);

        }

    }

}
