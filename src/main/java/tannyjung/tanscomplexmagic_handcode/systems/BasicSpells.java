package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import tannyjung.tanscomplexmagic_core.game.EntityManager;
import tannyjung.tanscomplexmagic_core.game.GameUtils;
import tannyjung.tanscomplexmagic_core.game.NBTManager;

import java.util.List;

public class BasicSpells {

    public static void restart (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_book = Book.Fly.get(level_server, player_server);

        if (entity_book != null) {

            Book.Fly.setCustomLookTarget(entity_book, null);

        }

        TerritoryGuardian.restart(level_server, player_server);

    }

    public static void updateTick (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_book = Book.Fly.get(level_server, player_server);

        if (entity_book == null) {

            return;

        }

        if (NBTManager.Mob.getText(player_server, "main", "basic_spell").equals("Territory Guardian") == true)
            TerritoryGuardian.updateTick(level_server, player_server, entity_book);

    }

    public static void updateSecond (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_book = Book.Fly.get(level_server, player_server);

        if (entity_book == null) {

            return;

        }

        if (NBTManager.Mob.getText(player_server, "main", "basic_spell").equals("Territory Guardian") == true)
            TerritoryGuardian.updateSecond(level_server, player_server, entity_book);

    }

    private static class TerritoryGuardian {

        private static final double FIREBALL_SPEED = 0.5;

        private static void restart (ServerLevel level_server, ServerPlayer player_server) {

            Entity entity_fireball = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"basic_spell1_fireball"}));

            if (entity_fireball == null) {

                return;

            }

            explode(level_server, entity_fireball);

        }

        public static void updateTick (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            Entity entity_fireball = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"basic_spell1_fireball"}));

            if (entity_fireball == null) {

                return;

            }

            // Life Time
            {

                int life_time = (int) NBTManager.Mob.getNumber(entity_book, "basic_spell1", "life_time");

                if (life_time > 1) {

                    NBTManager.Mob.setNumber(entity_book, "basic_spell1", "life_time", life_time - 1, false);

                } else {

                    explode(level_server, entity_fireball);
                    return;

                }

            }

            Entity entity_target = EntityManager.getByUUID(level_server, NBTManager.Mob.getText(entity_book, "basic_spell1", "target"));

            if (entity_target == null) {

                GameUtils.spawnParticle(level_server, entity_fireball.position(), 0, 0, 0, 0.01, 1, "minecraft:small_flame");
                return;

            }

            for (double loop = 0; loop < FIREBALL_SPEED; loop = loop + 0.25) {

                GameUtils.spawnParticle(level_server, entity_fireball.position(), 0, 0, 0, 0.01, 1, "minecraft:small_flame");
                entity_fireball.setPos(EntityManager.getPosRayWithDistance(entity_fireball, entity_target.position().add(0, 1, 0), 0.25, false));

                if (entity_fireball.position().distanceTo(entity_target.position().add(0, 1, 0)) < 0.5) {

                    damage(level_server, player_server, entity_fireball, entity_target);
                    return;

                }

            }

        }

        private static void updateSecond (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            Entity entity_target = null;
            Entity entity_fireball = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"basic_spell1_fireball"}));

            if (entity_fireball == null) {

                if (Book.Fly.isUserLookAt(player_server, entity_book) == true) {

                    return;

                }

                if (NBTManager.Mob.getNumber(player_server, "basic_spell1", "ammo") > 0) {

                    entity_target = getNearestTarget(level_server, player_server, entity_book);

                }

                if (entity_target == null) {

                    Book.Fly.setCustomLookTarget(entity_book, null);

                } else {

                    Book.Fly.setCustomLookTarget(entity_book, entity_target);

                    if (Book.Fly.isModelOpen(entity_book) == false) {

                        return;

                    }

                    Book.Fly.setCustomLookTarget(entity_book, entity_target);
                    NBTManager.Mob.setText(entity_book, "basic_spell1", "target", entity_target.getStringUUID(), false);
                    entity_fireball = summon(level_server, player_server, entity_book);

                }

            }

            if (entity_fireball == null) {

                return;

            }

            GameUtils.playSound(level_server, entity_fireball.position(), (Math.random() * 1.5) + 0.5, 20, "minecraft:block.fire.ambient");

            if (entity_target == null) {

                entity_target = EntityManager.getByUUID(level_server, NBTManager.Mob.getText(entity_book, "basic_spell1", "target"));

            }

            if (entity_target == null) {

                entity_target = getNearestTarget(level_server, player_server, entity_book);

                if (entity_target != null) {

                    Book.Fly.setCustomLookTarget(entity_book, entity_target);
                    NBTManager.Mob.setText(entity_book, "basic_spell1", "target", entity_target.getStringUUID(), false);
                    GameUtils.spawnParticle(level_server, entity_fireball.position(), 0, 0, 0, 0.05, 10, "minecraft:small_flame");
                    GameUtils.playSound(level_server, entity_fireball.position(), Math.random() + 1.0, 20, "minecraft:entity.blaze.shoot");

                }

            }

            if (entity_target == null) {

                return;

            }

            // Target Out of Range or Sight
            {

                int radius = (int) NBTManager.Mob.getNumber(player_server, "basic_spell1", "radius");

                if (entity_book.position().distanceTo(entity_fireball.position().add(0, 1, 0)) > radius || EntityManager.testPosRayNoBlocking(entity_book, entity_target.position().add(0, 1, 0), false) == false) {

                    NBTManager.Mob.setText(entity_book, "basic_spell1", "target", "", false);
                    return;

                }

            }

            // Prevent Block Stuck
            {

                String pos_old = NBTManager.Mob.getText(entity_fireball, "main", "pos_old");
                String pos_new = entity_fireball.getBlockX() + "/" + entity_fireball.getBlockY() + "/" + entity_fireball.getBlockZ();

                if (pos_old.equals(pos_new) == true) {

                    explode(level_server, entity_fireball);

                } else {

                    NBTManager.Mob.setText(entity_fireball, "main", "pos_old", pos_new, false);

                }

            }

        }

        private static Entity getNearestTarget (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            int radius = (int) NBTManager.Mob.getNumber(player_server, "basic_spell1", "radius");
            List<Entity> available_targets = EntityManager.Population.getAreaOnlyLiving(level_server, entity_book.position(), radius, "", "", Utils.Tag.convertEnemy(player_server));

            if (available_targets.isEmpty() == true) {

                return null;

            }

            String target = NBTManager.Mob.getText(player_server, "basic_spell1", "target");

            for (Entity entity_scan : EntityManager.Population.sort(available_targets, entity_book.position(), false, 0)) {

                if (target.equals("Enemy Monster") == true) {

                    if (entity_scan instanceof Enemy == false) {

                        continue;

                    }

                } else if (target.equals("Enemy Player") == true) {

                    if (entity_scan instanceof ServerPlayer == false) {

                        continue;

                    }

                } else {

                    if (entity_scan instanceof ServerPlayer == true) {

                        continue;

                    }

                }

                if (EntityManager.testPosRayNoBlocking(entity_book, entity_scan.position().add(0, 1, 0), false) == false) {

                    continue;

                }

                return entity_scan;

            }

            return null;

        }

        private static Entity summon (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {
            
            Entity entity_fireball = EntityManager.summonSystem(level_server, EntityManager.getPosRayForward(entity_book, 0.5, false), false, "minecraft:marker", "Fireball", Utils.Tag.convertSystemSpecific(player_server, new String[]{"basic_spell1_fireball"}), "");

            if (entity_fireball == null) {

                return null;

            }

            NBTManager.Mob.addNumber(player_server, "basic_spell1", "ammo", -1, true);
            NBTManager.Mob.addNumber(player_server, "basic_spell1", "ammo_max", -1, true);

            int radius = (int) NBTManager.Mob.getNumber(player_server, "basic_spell1", "radius");
            NBTManager.Mob.setNumber(entity_book, "basic_spell1", "life_time", radius / FIREBALL_SPEED, false);
            GameUtils.spawnParticle(level_server, entity_fireball.position(), 0, 0, 0, 0.05, 10, "minecraft:small_flame");
            GameUtils.playSound(level_server, entity_fireball.position(), Math.random() + 1.0, 20, "minecraft:entity.blaze.shoot");
            return entity_fireball;

        }

        private static void explode (ServerLevel level_server, Entity entity_fireball) {

            GameUtils.spawnParticle(level_server, entity_fireball.position(), 0, 0, 0, 0.05, 10, "minecraft:small_flame");
            GameUtils.playSound(level_server, entity_fireball.position(), Math.random() + 1.0, 20, "minecraft:entity.player.hurt_on_fire");
            entity_fireball.discard();

        }

        private static void damage (ServerLevel level_server, ServerPlayer player_server, Entity entity_fireball, Entity entity_target) {

            entity_target.setRemainingFireTicks(100);
            EntityManager.damage(entity_target, player_server, 5);
            explode(level_server, entity_fireball);

        }

    }

}
