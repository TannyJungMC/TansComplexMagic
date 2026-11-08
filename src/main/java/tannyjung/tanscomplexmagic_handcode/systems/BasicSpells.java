package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import tannyjung.tanscomplexmagic_core.game.EntityManager;
import tannyjung.tanscomplexmagic_core.game.GameUtils;
import tannyjung.tanscomplexmagic_core.game.NBTManager;

public class BasicSpells {

    public static void loopTick (ServerLevel level_server, ServerPlayer player_server) {







        if (true) return;










        Entity entity_target = null;

        for (Entity entity : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"protector_fireball"}))) {

            // Life Time
            {

                int life_time = (int) NBTManager.Mob.getNumber(entity, "main", "life_time");

                if (life_time > 1) {

                    NBTManager.Mob.setNumber(entity, "main", "life_time", life_time - 1, false);

                } else {

                    entity.discard();

                }

            }

            entity_target = EntityManager.getByUUID(level_server, NBTManager.Mob.getText(entity, "main", "target"));

            if (entity_target == null) {

                entity.discard();
                continue;

            }

            for (Entity entity_scan : EntityManager.Population.getArea(level_server, entity.position(), 1.5, "", "", Utils.Tag.convertEnemy(player_server))) {

                entity_scan.setRemainingFireTicks(20);
                entity.discard();
                return;

            }

            entity.setPos(EntityManager.getPosRayWithDistance(entity, entity_target.position().add(0, 1, 0), 0.5, true));
            GameUtils.spawnParticle(level_server, entity.position(), 0, 0, 0, 0, 1, "minecraft:flame");

        }

    }

    public static void loopSecond (ServerLevel level_server, ServerPlayer player_server) {

        // Protector.loopSecond(level_server, player_server);

    }

    private static class Protector {

        private static void loopSecond (ServerLevel level_server, ServerPlayer player_server) {

            for (Entity entity_book : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book"}))) {

                for (Entity entity_scan : EntityManager.Population.sort(EntityManager.Population.getArea(level_server, entity_book.position(), 100, "!minecraft:player", "", Utils.Tag.convertEnemy(player_server)), entity_book.position(), false, 0)) {

                    if (EntityManager.testPosRayNoBlocking(entity_book, entity_scan.getEyePosition(), true) == false) {

                        continue;

                    }

                    if (EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"protector_fireball", "protector_fireball_" + entity_scan.getStringUUID()})).isEmpty() == false) {

                        continue;

                    }

                    Entity entity_fireball = EntityManager.summon(level_server, entity_book.position(), false, "minecraft:marker", "Fireball", Utils.Tag.convertSystemSpecific(player_server, new String[]{"protector_fireball", "protector_fireball_" + entity_scan.getStringUUID()}), "");

                    if (entity_fireball == null) {

                        continue;

                    }

                    NBTManager.Mob.setText(entity_fireball, "main", "target", entity_scan.getStringUUID(), false);
                    NBTManager.Mob.setNumber(entity_fireball, "main", "life_time", 200, false);

                }

            }

        }

    }

}
