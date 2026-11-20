package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import tannyjung.tanscomplexmagic_core.game.BlockManager;
import tannyjung.tanscomplexmagic_core.game.EntityManager;
import tannyjung.tanscomplexmagic_core.game.GameUtils;
import tannyjung.tanscomplexmagic_core.game.NBTManager;

import java.util.List;

public class BasicSpells {

    public static void cancel (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_book = Book.Fly.get(level_server, player_server);

        if (entity_book != null) {

            Book.Fly.setCustomLookTarget(entity_book, null);

        }

        Number1.cancel(level_server, player_server);
        Number2.cancel(level_server, player_server);

    }

    public static void loopTick (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_book = Book.Fly.get(level_server, player_server);

        if (entity_book == null) {

            return;

        }

        String basic_spell = NBTManager.Mob.getText(player_server, "main", "basic_spell");

        if (basic_spell.equals("Territory Guardian") == true)
            Number1.loopTick(level_server, player_server, entity_book);
        if (basic_spell.equals("Shooting Star") == true)
            Number2.loopTick(level_server, player_server, entity_book);

    }

    public static void loopSecond (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_book = Book.Fly.get(level_server, player_server);

        if (entity_book == null) {

            return;

        }

        String basic_spell = NBTManager.Mob.getText(player_server, "main", "basic_spell");

        if (basic_spell.equals("Territory Guardian") == true)
            Number1.loopSecond(level_server, player_server, entity_book);
        if (basic_spell.equals("Shooting Star") == true)
            Number2.loopSecond(level_server, player_server, entity_book);

    }

    private static class Number1 {

        private static final double FIREBALL_SPEED = 0.5;

        private static void cancel (ServerLevel level_server, ServerPlayer player_server) {

            Entity entity_fireball = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell1"}));

            if (entity_fireball == null) {

                return;

            }

            explode(level_server, entity_fireball);

        }

        public static void loopTick (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            Entity entity_fireball = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell1"}));

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

        private static void loopSecond (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            Entity entity_target = null;
            Entity entity_fireball = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell1"}));

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
            List<Entity> available_targets = EntityManager.Population.getAreaOnlyLiving(level_server, entity_book.position(), radius, "", "", new String[]{Utils.Tag.getEnemy(player_server)});

            if (available_targets.isEmpty() == true) {

                return null;

            }

            String target = NBTManager.Mob.getText(player_server, "basic_spell1", "target");

            for (Entity entity_scan : EntityManager.Population.sort(available_targets, entity_book.position(), false, 0)) {

                if (target.equals("Enemy Non-Player") == true) {

                    if (entity_scan instanceof Enemy == false) {

                        continue;

                    }

                } else if (target.equals("Enemy Player") == true) {

                    if (entity_scan instanceof ServerPlayer == false) {

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
            
            Entity entity_fireball = EntityManager.summonSystem(level_server, EntityManager.getPosRayForward(entity_book, 0.5, false), false, "minecraft:marker", "Fireball", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell1"}), "");

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

    private static class Number2 {

        private static final double CENTER_MOVE_SPEED = 0.15;
        private static final double DISTANCE_BETWEEN_LIGHT = 1.5;

        private static void cancel (ServerLevel level_server, ServerPlayer player_server) {

            for (Entity entity_light : getAllLights(level_server, player_server)) {

                remove(level_server, entity_light);

            }

            Entity entity_center = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2_center"}));

            if (entity_center == null) {

                return;

            }

            int level = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "level");
            GameUtils.spawnParticle(level_server, entity_center.position(), 0, 0, 0, level * 0.05, level * 10, "minecraft:end_rod");
            GameUtils.playSound(level_server, entity_center.position(), (Math.random() * 0.5) + 0.5 + (1.0 - (level / 10.0)), level * 10, "minecraft:entity.firework_rocket.blast_far");
            GameUtils.playSound(level_server, entity_center.position(), 0, level * 10, "minecraft:entity.illusioner.cast_spell");
            entity_center.discard();

        }

        private static void loopTick (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            Entity entity_center = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2_center"}));

            if (entity_center == null) {

                return;

            }

            int level = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "level");
            GameUtils.spawnParticle(level_server, entity_center.position(), 0, 0, 0, level / 200.0, level, "minecraft:end_rod");

            // Move
            {

                double distance_to_book = (level / 5.0) + 0.5;
                Vec3 vec3 = null;
                boolean should_stay_above = false;

                if (level_server.getBlockState(entity_book.blockPosition().below(2)).canBeReplaced() == false) {

                    vec3 = entity_book.position().add(0, distance_to_book, 0);
                    should_stay_above = true;

                } else {

                    vec3 = EntityManager.getPosRayWithDistance(entity_book, entity_center.position(), distance_to_book, false);

                }

                if (entity_center.position().distanceTo(vec3) > CENTER_MOVE_SPEED + 0.1) {

                    entity_center.setPos(EntityManager.getPosRayWithDistance(entity_center, vec3, CENTER_MOVE_SPEED, false));

                } else {

                    if (should_stay_above == true) {

                        if (entity_book.getX() != entity_center.getX() || entity_book.getX() != entity_center.getZ()) {

                            entity_center.setPos(vec3);

                        }

                    }

                }

            }

            place(level_server, player_server, entity_center);
            updateAllLights(level_server, player_server, entity_center);

        }

        private static void loopSecond (ServerLevel level_server, ServerPlayer player_server, Entity entity_book) {

            int level = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "level");
            int capacitor = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "capacitor");
            Entity entity_center = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2_center"}));

            if (entity_center == null) {

                if (capacitor < level) {

                    return;

                }

                if (Book.Fly.isUserLookAt(player_server, entity_book) == true) {

                    return;

                }

                Book.Fly.setCustomLookTarget(entity_book, entity_book);

                if (Book.Fly.isModelOpen(entity_book) == false) {

                    return;

                }

                entity_center = EntityManager.summonSystem(level_server, entity_book.position().add(0, (level / 5.0) + 0.5, 0), false, "minecraft:marker", "Light Center", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2_center"}), "");

                if (entity_center == null) {

                    return;

                }

                Book.Fly.setCustomLookTarget(entity_book, entity_center);
                GameUtils.spawnParticle(level_server, entity_book.position().add(0, 0.1, 0), 0, 0, 0, 0.1, 10, "minecraft:instant_effect");
                GameUtils.spawnParticle(level_server, entity_center.position(), 0, 0, 0, level * 0.05, level * 10, "minecraft:end_rod");
                GameUtils.playSound(level_server, entity_center.position(), (Math.random() * 0.5) + 0.5 + (1.0 - (level / 10.0)), level * 10, "minecraft:entity.firework_rocket.blast_far");
                GameUtils.playSound(level_server, entity_center.position(), (Math.random() * 0.5) + 0.5 + (1.0 - (level / 10.0)), level * 10, "minecraft:block.enchantment_table.use");

            } else {

                capacitor = capacitor - level;
                NBTManager.Mob.setNumber(player_server, "basic_spell2", "capacitor", capacitor, true);
                NBTManager.Mob.addNumber(player_server, "basic_spell2", "capacitor_max", -level, true);

                if (capacitor < level) {

                    cancel(level_server, player_server);
                    Book.Fly.setCustomLookTarget(entity_book, null);

                }

                if (Math.random() > 0.5) {

                    return;

                }

                GameUtils.playSound(level_server, entity_center.position(), (Math.random() * 0.5) + 0.5, level * 10, "minecraft:block.beacon.ambient");

            }

        }

        private static List<Entity> getAllLights (ServerLevel level_server, ServerPlayer player_server) {

            return EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2"}));

        }

        private static void place (ServerLevel level_server, ServerPlayer player_server, Entity entity_center) {

            int level = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "level");
            int radius = level * 10;

            for (int loop = radius; loop > 0; loop--) {

                Vec3 vec3 = EntityManager.getPosLook(entity_center, Mth.nextDouble(RandomSource.create(), -1, 1), Mth.nextDouble(RandomSource.create(), -1, 1), Mth.nextDouble(RandomSource.create(), -1, 1));
                vec3 = EntityManager.getPosRayWithDistance(entity_center, vec3, radius, false);
                BlockPos pos = BlockPos.containing(vec3);
                vec3 = pos.getCenter();
                double distance = entity_center.position().distanceTo(vec3);

                if (distance > radius - 1) {

                    continue;

                }

                if (level_server.getBlockState(pos).isAir() == false) {

                    continue;

                }

                if (EntityManager.Population.getAreaAny(level_server, vec3, DISTANCE_BETWEEN_LIGHT, "", "", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2"})).isEmpty() == false) {

                    continue;

                }

                Entity entity_light = EntityManager.summonSystem(level_server, vec3, false, "minecraft:marker", "Light", Utils.Tag.convertSystem(player_server, new String[]{"basic_spell2"}), "");

                if (entity_light == null) {

                    continue;

                }

                updateLightLevel(level_server, player_server, entity_light, distance);

            }

        }

        private static void remove (ServerLevel level_server, Entity entity_light) {

            if (level_server.getBlockState(entity_light.blockPosition()).getBlock() == Blocks.LIGHT) {

                BlockManager.removeNormal(level_server, entity_light.blockPosition());

            }

            entity_light.discard();

        }

        private static void updateAllLights (ServerLevel level_server, ServerPlayer player_server, Entity entity_center) {

            List<Entity> list = getAllLights(level_server, player_server);
            int process_start = (int) NBTManager.Mob.getNumber(entity_center, "main", "process");

            if (process_start == list.size()) {

                process_start = 0;

            }

            int level = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "level");
            int radius = level * 10;
            int process_end = process_start + radius;
            int process = 0;
            double distance = 0.0;

            for (Entity entity_light : list) {

                if (process >= process_end) {

                    break;

                }

                process = process + 1;

                if (process < process_start) {

                    continue;

                }

                distance = entity_center.position().distanceTo(entity_light.position());

                if (distance > radius) {

                    remove(level_server, entity_light);
                    continue;

                }

                if (EntityManager.testPosRayNoBlocking(entity_light, entity_center.position(), false) == false) {

                    remove(level_server, entity_light);
                    continue;

                }

                updateLightLevel(level_server, player_server, entity_light, distance);

            }

            NBTManager.Mob.setNumber(entity_center, "main", "process", process, false);

        }

        private static void updateLightLevel (ServerLevel level_server, ServerPlayer player_server, Entity entity_light, double distance) {

            double level = (int) NBTManager.Mob.getNumber(player_server, "basic_spell2", "level");
            level = 1.0 - (distance / (level * 10));
            level = Math.round(level * 15);
            BlockState block = BlockManager.setPropertyNumber(Blocks.LIGHT.defaultBlockState(), "level", (int) level);

            if (level_server.getBlockState(entity_light.blockPosition()) == block) {

                return;

            }

            BlockManager.placeNormal(level_server, entity_light.blockPosition(), block);

        }

    }

}
