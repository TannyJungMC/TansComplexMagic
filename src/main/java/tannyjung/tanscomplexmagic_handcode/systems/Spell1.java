package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.Vec3;
import tannyjung.tanscomplexmagic_core.game.*;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

import java.util.*;

public class Spell1 {

    public static void activate (ServerLevel level_server, ServerPlayer player_server) {

        BlockPos pos = BlockPos.containing(EntityManager.getPosRayForward(player_server, 200, false));

        if (BlockManager.isPassable(level_server, pos) == false || BlockManager.isPassable(level_server, pos.below()) == true) {

            pos = player_server.blockPosition();

        }

        GameUtils.spawnParticle(level_server, pos.getCenter().add(0, -0.5, 0), 0, 0, 0, 1, 10, "minecraft:instant_effect");

        Entity entity_center = EntityManager.summon(level_server, pos.getCenter().add(0, -0.5, 0), false, "minecraft:marker", "Zodiac Center", Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1", "spell1_center"}), "");

        if (entity_center == null) {

            return;

        }

        Card.summonAll(level_server, player_server, entity_center);

    }

    public static void deactivate (ServerLevel level_server, ServerPlayer player_server) {

        Card.removeAll(level_server, player_server);

    }

    public static void loopTick (ServerLevel level_server, ServerPlayer player_server) {

        Entity entity_center = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1", "spell1_center"}));

        if (entity_center == null) {

            return;

        }

        Map<Integer, Entity> cards = Card.getActive(level_server, player_server);
        updateCenter(player_server, entity_center);
        Card.updateTick(level_server, player_server, entity_center, cards);

        // Radius Key Control
        {

            int control = (int) NBTManager.Mob.getNumber(player_server, "spell1", "radius_key_control");

            if (control != 0) {

                int radius = (int) NBTManager.Mob.getNumber(player_server, "spell1", "radius");
                int tick = (int) NBTManager.Mob.getNumber(player_server, "spell1", "radius_key_control_tick");

                if (tick > 1) {

                    NBTManager.Mob.setNumber(player_server, "spell1", "radius_key_control_tick", tick - 1, false);

                } else {

                    if (radius < 20) {

                        tick = 5;

                    } else if (radius < 50) {

                        tick = 4;

                    } else if (radius < 100) {

                        tick = 3;

                    } else {

                        tick = 2;

                    }

                    NBTManager.Mob.setNumber(player_server, "spell1", "radius_key_control_tick", tick, false);
                    int add = 0;
                    double pitch = 0;

                    if (control == 1) {

                        if (radius > 1) {

                            add = -1;
                            pitch = 1.5;

                        }

                    } else {

                        if (radius < 200) {

                            add = 1;
                            pitch = 2;

                        }

                    }

                    if (add != 0) {

                        radius = radius + add;
                        NBTManager.Mob.setNumber(player_server, "spell1", "radius", radius, true);
                        GameUtils.playSound(level_server, player_server.position(), pitch, 20, "minecraft:block.note_block.hat");

                    }

                }

            }

        }

    }

    public static void loopSecond (ServerLevel level_server, ServerPlayer player_server) {

        Empower.updateSecond(level_server, player_server);

        Entity entity_center = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1", "spell1_center"}));

        if (entity_center == null) {

            return;

        }

        Map<Integer, Entity> cards = Card.getActive(level_server, player_server);
        Card.updateSecond(level_server, player_server, entity_center, cards);

    }

    private static void updateCenter (ServerPlayer player_server, Entity entity_center) {

        double rotation = entity_center.getYRot();
        double add = ((1.0 - (NBTManager.Mob.getNumber(player_server, "spell1", "radius") / 200.0)) * 0.1) + 0.025;

        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_reverse_rotation") == true) {

            rotation = rotation - add;

            if (rotation < 0) {

                rotation = rotation + 360;

            }

        } else {

            rotation = rotation + add;

            if (rotation >= 360) {

                rotation = rotation - 360;

            }

        }

        entity_center.setYRot((float) rotation);

    }

    public static String getNameFromNumber (int number) {

        if (number == 1) return "Aries";
        else if (number == 2)
            return "Taurus";
        else if (number == 3)
            return "Gemini";
        else if (number == 4)
            return "Cancer";
        else if (number == 5)
            return "Leo";
        else if (number == 6)
            return "Virgo";
        else if (number == 7)
            return "Libra";
        else if (number == 8)
            return "Scorpio";
        else if (number == 9)
            return "Sagittarius";
        else if (number == 10)
            return "Capricorn";
        else if (number == 11)
            return "Aquarius";
        else if (number == 12)
            return "Pisces";
        else if (number == 13)
            return "Ophiuchus";

        return "";

    }

    public static class Card {

        public static void refresh (ServerLevel level_server, ServerPlayer player_server) {

            removeAll(level_server, player_server);

            Entity entity_center = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1", "spell1_center"}));

            if (entity_center == null) {

                return;

            }

            summonAll(level_server, player_server, entity_center);

        }

        private static void summonAll (ServerLevel level_server, ServerPlayer player_server, Entity entity_center) {

            entity_center.setYRot((float) (Math.random() * 360));
            List<Integer> list = new ArrayList<>();

            for (int number = 1; number <= 13; number++) {

                if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_activate" + number) == true) {

                    list.add(number);

                }

            }

            int[] numbers = new int[list.size()];

            for (int loop = 0; loop < list.size(); loop++) {

                numbers[loop] = list.get(loop);

            }

            summonSpecific(level_server, player_server, entity_center, numbers);

        }

        private static void summonSpecific (ServerLevel level_server, ServerPlayer player_server, Entity entity_center, int[] numbers) {

            double radius = NBTManager.Mob.getNumber(player_server, "spell1", "radius");
            double height = 0;

            if (radius <= NBTManager.Mob.getNumber(player_server, "spell1", "radius_touch_ground")) {

                height = 0.075;

            } else {

                height = radius;

            }

            Vec3 vec3 = entity_center.position().add(0, height, 0);

            for (int number : numbers) {

                entity_center.setYRot((float) (entity_center.getYRot() + (360.0 / numbers.length)));
                DisplayManager.summonItem(level_server, vec3, (int) (entity_center.getYRot() + 180), 90, 0, true, "Zodiac Card " + number, Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1", "spell1_card", "spell1_card" + number}), "tanscomplexmagic:zodiac_card_" + number);

                if (entity_center.getYRot() >= 360) {

                    entity_center.setYRot(entity_center.getYRot() - 360);

                }

            }

            GameUtils.spawnParticle(level_server, vec3, 0, 0, 0, 0, 1, "minecraft:flash");
            GameUtils.playSound(level_server, vec3, 0, radius * 5, "minecraft:entity.evoker.prepare_summon");
            GameUtils.playSound(level_server, vec3, 0, radius * 20, "minecraft:block.enchantment_table.use");

        }

        private static void removeAll (ServerLevel level_server, ServerPlayer player_server) {

            double radius = NBTManager.Mob.getNumber(player_server, "spell1", "radius");

            Map<Integer, Entity> cards = getActive(level_server, player_server);
            Entity entity_card = null;

            for (int number = 1; number <= 13; number++) {

                entity_card = cards.get(number);

                if (entity_card == null) {

                    continue;

                }

                removeSpecific(level_server, player_server, entity_card, number, radius);

            }

        }

        private static void removeSpecific (ServerLevel level_server, ServerPlayer player_server, Entity entity_card, int card_number, double radius) {

            NBTManager.Mob.setText(player_server, "spell1", "card_status" + card_number, "", true);
            NBTManager.Mob.setNumber(player_server, "spell1", "number_of_detected_targets" + card_number, 0, true);
            NBTManager.Mob.setNumber(player_server, "spell1", "remaining_duration" + card_number, 0, true);

            GameUtils.spawnParticle(level_server, entity_card.position(), 0, 0, 0, 0, 1, "minecraft:flash");
            GameUtils.playSound(level_server, entity_card.position(), 0, radius * 4, "minecraft:entity.evoker.cast_spell");
            entity_card.discard();

        }

        private static void updateTick (ServerLevel level_server, ServerPlayer player_server, Entity entity_center, Map<Integer, Entity> cards) {

            double radius = NBTManager.Mob.getNumber(player_server, "spell1", "radius");

            for (Map.Entry<Integer, Entity> entry : cards.entrySet()) {

                entity_center.setYRot(entity_center.getYRot() + (float) (360.0 / cards.size()));

                if (entity_center.getYRot() >= 360) {

                    entity_center.setYRot(entity_center.getYRot() - 360);

                }

                // When Update Radius
                {

                    if (NBTManager.Mob.getNumber(entry.getValue(), "main", "save_radius") != radius) {

                        NBTManager.Mob.setNumber(entry.getValue(), "main", "save_radius", radius, false);
                        setSize(player_server, entry.getValue());

                    }

                }

                setPosition(player_server, entity_center, entry.getValue(), entry.getKey(), cards.size());
                setPose(level_server, player_server, entity_center, entry.getValue(), entry.getKey(), cards.size());

            }

        }

        private static void updateSecond (ServerLevel level_server, ServerPlayer player_server, Entity entity_center, Map<Integer, Entity> cards) {

            double radius = NBTManager.Mob.getNumber(player_server, "spell1", "radius");
            Map<String, List<Entity>> targets = new HashMap<>();
            Entity entity_card = null;

            // Get Targets
            {

                List<Entity> list = EntityManager.Population.filterLivingEntity(EntityManager.Population.getArea(level_server, entity_center.position(), radius, "", "", new String[]{}));
                targets.put("user", new ArrayList<>());

                if (list.contains(player_server) == true) {

                    targets.get("user").add(player_server);

                }

                targets.put("ally_player", EntityManager.Population.filter(list, "minecraft:player", "", Utils.Tag.convertAlly(player_server)));
                targets.put("ally_non_player", EntityManager.Population.filter(list, "!minecraft:player", "", Utils.Tag.convertAlly(player_server)));
                targets.put("enemy_player", EntityManager.Population.filter(list, "minecraft:player", "", Utils.Tag.convertEnemy(player_server)));
                targets.put("enemy_non_player", EntityManager.Population.filter(list, "!minecraft:player", "", Utils.Tag.convertEnemy(player_server)));
                targets.put("mark", EntityManager.Population.filter(list, "", "", new String[]{Utils.Tag.getUser(player_server) + "_spell1_mark"}));

            }

            Set<Entity> target_all = new HashSet<>();
            Entity[] target_sort = new Entity[]{};
            boolean is_activate = false;
            boolean is_negative = false;
            String positive_negative = "";

            int duration_spend_per_second = 0;
            int remaining_duration = 0;
            boolean is_high_power_mode = false;

            for (int number = 1; number <= 13; number++) {

                entity_card = cards.get(number);
                is_activate = NBTManager.Mob.getLogic(player_server, "spell1", "is_card_activate" + number);

                // When Update Card Activate
                {

                    if (is_activate != NBTManager.Mob.getLogic(player_server, "spell1", "save_is_card_enable" + number)) {

                        NBTManager.Mob.setLogic(player_server, "spell1", "save_is_card_enable" + number, is_activate, false);

                        if (entity_card == null) {

                            if (is_activate == true) {

                                summonSpecific(level_server, player_server, entity_center, new int[]{number});

                            }

                        } else {

                            if (is_activate == false) {

                                removeSpecific(level_server, player_server, entity_card, number, radius);
                                entity_card = null;

                            }

                        }

                    }

                }

                if (entity_card == null) {

                    continue;

                }

                // Sort Targets
                {

                    target_all.clear();

                    if (number != 7 && NBTManager.Mob.getText(player_server, "spell1", "card_status7").isEmpty() == false && NBTManager.Mob.getLogic(player_server, "spell1", "is_card_negative7") == false) {

                        target_sort = new Entity[]{};

                    } else {

                        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_target_user" + number) == true)
                            target_all.addAll(targets.get("user"));
                        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_target_ally_player" + number) == true)
                            target_all.addAll(targets.get("ally_player"));
                        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_target_ally_non_player" + number) == true)
                            target_all.addAll(targets.get("ally_non_player"));
                        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_target_enemy_player" + number) == true)
                            target_all.addAll(targets.get("enemy_player"));
                        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_target_enemy_non_player" + number) == true)
                            target_all.addAll(targets.get("enemy_non_player"));
                        if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_target_mark" + number) == true)
                            target_all.addAll(targets.get("mark"));

                        target_sort = EntityManager.Population.sort(target_all.stream().toList(), entity_center.position(), false, 0);

                    }

                }

                is_negative = NBTManager.Mob.getLogic(player_server, "spell1", "is_card_negative" + number) == true;

                if (is_negative == true) {

                    positive_negative = "negative";

                } else {

                    positive_negative = "positive";

                }

                is_high_power_mode = NBTManager.Mob.getLogic(player_server, "spell1", "is_card_high_power_mode" + number);

                if (NBTManager.Mob.getText(player_server, "spell1", "card_status" + number).equals("effect") == true) {

                    // Apply Effects
                    {

                        if (is_high_power_mode == true) {

                            duration_spend_per_second = 2;

                        } else {

                            duration_spend_per_second = 1;

                        }

                        if (level_server.isDay() == true) {

                            duration_spend_per_second = duration_spend_per_second * 2;

                        }

                        int duration = (int) NBTManager.Mob.getNumber(player_server, "spell1", "card_duration_" + positive_negative + number);
                        int duration_max = (int) NBTManager.Mob.getNumber(player_server, "spell1", "card_duration_max_" + positive_negative + number);

                        for (Entity entity : target_sort) {

                            Effect.giveSort(level_server, player_server, number, entity, is_negative, is_high_power_mode);
                            duration = duration - duration_spend_per_second;
                            duration_max = duration_max - duration_spend_per_second;

                            if (duration_max <= 0) {

                                duration_max = 0;

                            }

                            if (duration <= 0) {

                                duration = 0;
                                break;

                            }

                        }

                        NBTManager.Mob.setNumber(player_server, "spell1", "card_duration_" + positive_negative + number, duration, true);
                        NBTManager.Mob.setNumber(player_server, "spell1", "card_duration_max_" + positive_negative + number, duration_max, true);

                    }

                }

                remaining_duration = 0;

                if (target_sort.length != 0) {

                    remaining_duration = (int) (NBTManager.Mob.getNumber(player_server, "spell1", "card_duration_" + positive_negative + number) / target_sort.length);

                    if (is_high_power_mode == true) {

                        remaining_duration = remaining_duration / 2;

                    }

                    if (level_server.isDay() == true) {

                        remaining_duration = remaining_duration / 2;

                    }

                }

                NBTManager.Mob.setNumber(player_server, "spell1", "number_of_detected_targets" + number, target_sort.length, true);
                NBTManager.Mob.setNumber(player_server, "spell1", "remaining_duration" + number, remaining_duration, true);

            }

        }

        private static void setPosition (ServerPlayer player_server, Entity entity_center, Entity entity_card, int card_number, int card_count) {

            String status = NBTManager.Mob.getText(player_server, "spell1", "card_status" + card_number);
            boolean is_rest = status.isEmpty() == true;
            double radius = NBTManager.Mob.getNumber(player_server, "spell1", "radius");
            double height = 0;
            double far = 0;

            if (radius <= NBTManager.Mob.getNumber(player_server, "spell1", "radius_touch_ground")) {

                if (is_rest == true) {

                    height = 0.075;

                } else {

                    height = radius / 3.0;

                }

            } else {

                if (is_rest == true) {

                    height = radius;

                } else {

                    height = radius - (radius / 3.0);

                }

            }

            if (card_count > 1) {

                if (status.equals("effect") == true) {

                    far = radius;

                } else {

                    far = radius * 1.25;

                }

            }

            entity_card.setPos(EntityManager.getPosLook(entity_center, 0, height, far));

        }

        private static void setPose (ServerLevel level_server, ServerPlayer player_server, Entity entity_center, Entity entity_card, int card_number, int card_count) {

            if (card_count == 1) {

                entity_card.setYRot((float) (entity_card.getYRot() - 0.25));

            } else {

                float rotX_save = entity_card.getXRot();
                entity_card.lookAt(EntityAnchorArgument.Anchor.FEET, entity_center.position());
                entity_card.setXRot(rotX_save);

            }

            int degree_set = testAvailableToActive(player_server, card_number);

            if (entity_card.getXRot() != degree_set) {

                // Flipping
                {

                    double radius = NBTManager.Mob.getNumber(player_server, "spell1", "radius");
                    boolean is_high_power_mode = NBTManager.Mob.getLogic(player_server, "spell1", "is_card_high_power_mode" + card_number);

                    if (NBTManager.Mob.getText(player_server, "spell1", "card_status" + card_number).equals("flip") == false) {

                        // At Start of Flipping
                        {

                            NBTManager.Mob.setText(player_server, "spell1", "card_status" + card_number, "flip", true);
                            GameUtils.playSound(level_server, entity_card.position(), (Math.random() * 0.5) + 0.5, radius * 4.0, "minecraft:block.ender_chest.open");

                        }

                    }

                    // Add Degree
                    {

                        double degree = 1.0 - (radius / 200.0);
                        degree = (degree * 1.5) + 0.5;

                        if (is_high_power_mode == true) {

                            degree = degree * 0.75;

                        }

                        if (entity_card.getXRot() > degree_set && entity_card.getXRot() - (float) degree > degree_set) {

                            entity_card.setXRot(entity_card.getXRot() - (float) degree);

                        } else if (entity_card.getXRot() < degree_set && entity_card.getXRot() + (float) degree < degree_set) {

                            entity_card.setXRot(entity_card.getXRot() + (float) degree);

                        } else {

                            entity_card.setXRot((float) degree_set);

                        }

                    }

                    // Gear Sound
                    {

                        int tick = (int) NBTManager.Mob.getNumber(entity_card, "", "gear_sound_tick");

                        if (tick > 1) {

                            NBTManager.Mob.setNumber(entity_card, "", "gear_sound_tick", tick - 1, false);

                        } else {

                            double value = Math.floor(radius / 20) + 7;

                            if (is_high_power_mode == true) {

                                value = value * 1.25;

                            }

                            NBTManager.Mob.setNumber(entity_card, "", "gear_sound_tick", value, false);
                            GameUtils.playSound(level_server, entity_card.position(), 0, radius * 4.0, "minecraft:block.wooden_door.open");

                        }

                    }

                    if (entity_card.getXRot() == degree_set || entity_card.getXRot() == -180) {

                        // Finish Flipping
                        {

                            GameUtils.playSound(level_server, entity_card.position(), 0, radius * 4.0, "minecraft:block.iron_door.open");

                            if (degree_set == 90) {

                                NBTManager.Mob.setText(player_server, "spell1", "card_status" + card_number, "", true);

                            } else {

                                NBTManager.Mob.setText(player_server, "spell1", "card_status" + card_number, "effect", true);

                            }

                        }

                    }

                }

            }

        }

        private static void setSize (ServerPlayer player_server, Entity entity_card) {

            double scale = NBTManager.Mob.getNumber(player_server, "spell1", "radius") / 5.0;
            DisplayManager.setItemScale(entity_card, scale);

        }

        private static int testAvailableToActive (ServerPlayer player_server, int card_number) {

            if (NBTManager.Mob.getLogic(player_server, "spell1", "is_pause_all") == true) {

                return 90;

            }

            if (NBTManager.Mob.getLogic(player_server, "spell1", "is_card_activate_effect" + card_number) == false) {

                return 90;

            }

            boolean is_negative = NBTManager.Mob.getLogic(player_server, "spell1", "is_card_negative" + card_number);
            String positive_negative = "";

            if (is_negative == true) {

                positive_negative = "negative";

            } else {

                positive_negative = "positive";

            }

            if (NBTManager.Mob.getNumber(player_server, "spell1", "card_duration_" + positive_negative + card_number) == 0) {

                return 90;

            }

            if (is_negative == true) {

                return -180;

            } else {

                return 0;

            }

        }

        private static Map<Integer, Entity> getActive (ServerLevel level_server, ServerPlayer player_server) {

            Map<Integer, Entity> map = new HashMap<>();
            Entity entity = null;

            for (int number = 1; number <= 13; number++) {

                entity = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1_card" + number}));

                if (entity == null) {

                    continue;

                }

                map.put(number, entity);

            }

            return map;

        }

    }

    private static class Effect {

        private static void giveSort (ServerLevel level_server, ServerPlayer player_server, int card_number, Entity entity, boolean is_negative, boolean is_high_power_mod) {

            if (card_number == 1) give1(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 2) give2(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 3) give3(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 4) give4(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 5) give5(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 6) give6(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 7) give7(level_server, player_server, entity, is_negative, is_high_power_mod);
            if (card_number == 8) give8(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 9) give9(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 10) give10(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 11) give11(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 12) give12(level_server, entity, is_negative, is_high_power_mod);
            if (card_number == 13) give13(level_server, entity, is_negative, is_high_power_mod);

        }

        private static void give1 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:night_vision", 240) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:night_vision") == false) {

                        EffectManager.giveBasic(level_server, entity, "minecraft:blindness", 0, 20);
                        GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:entity.goat.ambient");
                        GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:block.beacon.power_select");
                        GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0, 5, "minecraft:end_rod");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:night_vision", 1, 240);

                    if (is_high_power_mode == false) {

                        EffectManager.giveBasic(level_server, entity, "minecraft:darkness", 1, 20);

                    }

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:darkness", 60) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:darkness") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.allay.death");
                        GameUtils.playSound(level_server, entity.position(), 1.25, 20, "minecraft:entity.illusioner.cast_spell");

                    }

                    if (Math.random() < 0.25) GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.goat.ambient");
                    if (Math.random() < 0.1) GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:ambient.cave");

                    GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0, 5, "minecraft:campfire_cosy_smoke");
                    EffectManager.giveBasic(level_server, entity, "minecraft:darkness", 1, 60);

                    double chance = 0;

                    if (is_high_power_mode == true) {

                        chance = 1.0;

                    } else {

                        chance = 0.25;

                    }

                    if (Math.random() < chance) {

                        EntityManager.clearTarget(entity);
                        EntityManager.go(entity, entity.position().add(20.0 - (Math.random() * 40.0), 20.0 - (Math.random() * 40.0), 20.0 - (Math.random() * 40.0)), 1.5);

                    }

                }

            }

        }

        private static void give2 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:resistance", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:resistance") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.zombie_horse.ambient");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.amethyst_block.resonate");
                        GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0, 3, "minecraft:heart");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 5;

                    } else {

                        level = 2;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:resistance", level, 40);

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:nausea", 100) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:nausea") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.ravager.death");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:nausea", 1, 100);
                    GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0, 3, "minecraft:falling_water");

                    if (Math.random() < 0.5) {

                        GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.25) + 0.5, 20, "minecraft:entity.ghast.ambient");

                    }

                    if (Math.random() < 0.25) {

                        if (is_high_power_mode == true) {

                            GameUtils.playSound(level_server, entity.position(), (Math.random() * 1.5) + 0.5, 20, "minecraft:ambient.cave");
                            EntityManager.go(entity, entity.position().add(20.0 - (Math.random() * 40.0), 20.0 - (Math.random() * 40.0), 20.0 - (Math.random() * 40.0)), 0.25);
                            EntityManager.clearTarget(entity);

                        }

                    }

                }

            }

        }

        private static void give3 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:haste", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:haste") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.allay.ambient_with_item");
                        GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0, 5, "minecraft:glow");
                        GameUtils.spawnParticle(level_server, entity.getEyePosition(), 0, 0, 0, 2, 5, "minecraft:portal");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 30;

                    } else {

                        level = 10;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:haste", level, 40);

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:mining_fatigue", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:mining_fatigue") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.allay.ambient_without_item");
                        GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0, 5, "minecraft:glow");
                        GameUtils.spawnParticle(level_server, entity.getEyePosition(), 1, 1, 1, 0.1, 5, "minecraft:reverse_portal");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 5;

                    } else {

                        level = 2;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:mining_fatigue", level, 40);

                }

            }

        }

        private static void give4 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:luck", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:luck") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.stray.step");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.beacon.power_select");
                        GameUtils.playSound(level_server, entity.position(), 1, 20, "minecraft:block.beacon.power_select");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:happy_villager");

                    }

                    int level = 0;
                    double event_chance = 0;

                    if (is_high_power_mode == true) {

                        level = 100;
                        event_chance = 0.25;

                    } else {

                        level = 50;
                        event_chance = 1.0;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:luck", level, 40);

                    // Animal Attraction
                    {

                        if (Math.random() < event_chance) {

                            if (entity instanceof ServerPlayer == true) {

                                for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 100, "", "", new String[]{})) {

                                    if (Math.random() >= 0.5) {

                                        continue;

                                    }

                                    if (scan instanceof Animal == true) {

                                        if (EntityManager.getTarget(scan) != null) {

                                            continue;

                                        }

                                        EntityManager.go(scan, entity.position().add(10.0 - (Math.random() * 20.0), 10.0 - (Math.random() * 20.0), 10.0 - (Math.random() * 20.0)), 0.75);

                                    }

                                }

                            }

                        }

                    }

                    // Monster Repulsion
                    {

                        if (Math.random() < event_chance) {

                            if (entity instanceof ServerPlayer == true) {

                                for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 100, "", "", new String[]{})) {

                                    if (Math.random() >= 0.5) {

                                        continue;

                                    }

                                    if (scan instanceof Monster == true) {

                                        if (EntityManager.getTarget(scan) != null) {

                                            continue;

                                        }

                                        EntityManager.go(scan, EntityManager.getPosLookReverse(scan, entity.position(), 0, 0, -10), 0.75);

                                    }

                                }

                            }

                        }

                    }

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:unluck", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:unluck") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.stray.hurt");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.beacon.power_select");
                        GameUtils.playSound(level_server, entity.position(), 1, 20, "minecraft:block.beacon.power_select");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:happy_villager");

                    }

                    int level = 0;
                    double event_chance = 0;

                    if (is_high_power_mode == true) {

                        level = 100;
                        event_chance = 0.25;

                    } else {

                        level = 50;
                        event_chance = 0.1;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:unluck", level, 40);

                    // Animal Repulsion
                    {

                        if (Math.random() < event_chance) {

                            if (entity instanceof ServerPlayer == true) {

                                for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 100, "", "", new String[]{})) {

                                    if (Math.random() >= 0.5) {

                                        continue;

                                    }

                                    if (scan instanceof Animal == true) {

                                        if (EntityManager.getTarget(scan) != null) {

                                            continue;

                                        }

                                        EntityManager.go(scan, EntityManager.getPosLookReverse(scan, entity.position(), 0, 0, -10), 0.75);

                                    }

                                }

                            }

                        }

                    }

                    // Monster Attraction
                    {

                        if (Math.random() < event_chance) {

                            if (entity instanceof ServerPlayer == true) {

                                for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 100, "", "", new String[]{})) {

                                    if (Math.random() >= 0.5) {

                                        continue;

                                    }

                                    if (scan instanceof Monster == true) {

                                        if (EntityManager.getTarget(scan) != null) {

                                            continue;

                                        }

                                        EntityManager.go(scan, entity.position().add(10.0 - (Math.random() * 20.0), 10.0 - (Math.random() * 20.0), 10.0 - (Math.random() * 20.0)), 0.75);

                                    }

                                }

                            }

                        }

                    }

                    // Lightning
                    {

                        if (Math.random() < event_chance) {

                            if (level_server.isRainingAt(entity.blockPosition()) == true) {

                                EntityManager.summon(level_server, entity.position(), false, "minecraft:lightning_bolt", "Lightning Bolt", new String[]{}, "");

                            }

                        }

                    }

                }

            }

        }

        private static void give5 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:hero_of_the_village", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:hero_of_the_village") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.warden.roar");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.beacon.power_select");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.player.levelup");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:dripping_honey");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:happy_villager");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 5;

                    } else {

                        level = 2;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:hero_of_the_village", level, 40);

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:hunger", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:hunger") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.warden.roar");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.beacon.power_select");
                        GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:entity.evoker.prepare_attack");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:dripping_honey");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:squid_ink");

                    }

                    int level = 0;
                    double event_chance = 0;

                    if (is_high_power_mode == true) {

                        level = 5;
                        event_chance = 1.0;

                    } else {

                        level = 2;
                        event_chance = 0.5;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:hunger", level, 40);

                    // Mob Repulsion
                    {

                        if (entity instanceof ServerPlayer == true) {

                            for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 20, "", "", new String[]{})) {

                                if (Math.random() >= event_chance) {

                                    continue;

                                }

                                if (scan instanceof LivingEntity entity_living) {

                                    EntityManager.clearTarget(entity_living);
                                    EntityManager.go(entity_living, EntityManager.getPosLookReverse(entity_living, entity.position(), 0, 0, -20), 1.5);

                                }

                            }

                        }

                    }

                }

            }

        }

        private static void give6 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:invisibility", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:invisibility") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:entity.illusioner.mirror_move");
                        GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:block.enchantment_table.use");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:campfire_signal_smoke");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:invisibility", 1, 40);

                    if (is_high_power_mode == true) {

                        // Mob Blind
                        {

                            for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 20, "", "", new String[]{})) {

                                if (EntityManager.getTarget(scan) == entity) {

                                    EntityManager.clearTarget(scan);

                                }

                            }

                        }

                    }

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:glowing", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:glowing") == false) {

                        GameUtils.playSound(level_server, entity.position(), 2, 20, "minecraft:block.amethyst_block.fall");

                    }

                    double chance = 0;

                    if (is_high_power_mode == true) {

                        chance = 1.0;

                    } else {

                        chance = 0.25;

                    }

                    if (Math.random() < chance) {

                        EffectManager.giveBasic(level_server, entity, "minecraft:glowing", 1, 40);
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 1, "minecraft:firework");

                    }

                }

            }

        }

        private static void give7 (ServerLevel level_server, ServerPlayer player_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (is_high_power_mode == false) {

                    int delay = (int) NBTManager.Mob.getNumber(entity, "spell1", "libra_cleanse_delay");

                    if (delay > 1) {

                        NBTManager.Mob.setNumber(entity, "spell1", "libra_cleanse_delay", delay - 1, false);
                        return;

                    }

                }

                if (EffectManager.clearAll(entity) == true) {

                    if (is_high_power_mode == false) {

                        NBTManager.Mob.setNumber(entity, "spell1", "libra_cleanse_delay", 10, false);

                    }

                    GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.enchantment_table.use");
                    GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:block.beacon.power_select");
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.1, 20, "minecraft:end_rod");

                }

            } else {

                if (player_server != entity) {

                    int level = 0;

                    if (is_high_power_mode == false) {

                        level = 1;

                    }

                    for (Object object_instance : EffectManager.getActive(player_server)) {

                        object_instance = EffectManager.modify(object_instance, level, 30);
                        EffectManager.giveAdvance(entity, object_instance);

                    }

                }

                GameUtils.playSound(level_server, entity.position(), Math.random() + 1.0, 20, "minecraft:block.beacon.ambient");

            }

        }

        private static void give8 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:fire_resistance", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:fire_resistance") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0.75, 20, "minecraft:entity.silverfish.ambient");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.illusioner.prepare_blindness");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:flame");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:fire_resistance", 1, 40);

                    if (is_high_power_mode == true) {

                        // Fire Skin
                        {

                            for (Entity scan : EntityManager.Population.getArea(level_server, entity.position(), 10, "", "", new String[]{})) {

                                if (EntityManager.getTarget(scan) == entity) {

                                    scan.setRemainingFireTicks(20);
                                    GameUtils.playSound(level_server, scan.position(), 0, 20, "minecraft:entity.skeleton_horse.ambient");
                                    GameUtils.playSound(level_server, scan.position(), 1, 20, "minecraft:entity.blaze.burn");
                                    GameUtils.spawnParticle(level_server, scan.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:large_smoke");

                                }

                            }

                        }

                    }

                }

            } else {

                GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.skeleton_horse.ambient");
                GameUtils.playSound(level_server, entity.position(), 1, 20, "minecraft:entity.blaze.burn");
                GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:large_smoke");

                double chance = 0;

                if (is_high_power_mode == true) {

                    chance = 1.0;

                } else {

                    chance = 0.5;

                }

                if (Math.random() < chance) {

                    entity.setRemainingFireTicks(20);

                }

            }

        }

        private static void give9 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (entity.isSprinting() == false) {

                    return;

                }

                if (EffectManager.hasDuration(level_server, entity, "minecraft:speed", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:speed") == false) {

                        GameUtils.playSound(level_server, entity.position(), 1.5, 20, "minecraft:item.trident.thunder");
                        GameUtils.playSound(level_server, entity.position(), 2, 20, "minecraft:item.trident.thunder");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.1, 20, "minecraft:reverse_portal");

                    }

                    int level_speed = 0;
                    int level_jump = 0;

                    if (is_high_power_mode == true) {

                        level_speed = 10;
                        level_jump = 3;

                    } else {

                        level_speed = 5;
                        level_jump = 1;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:speed", level_speed, 40);
                    EffectManager.giveBasic(level_server, entity, "minecraft:jump_boost", level_jump, 40);

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:slowness", 40) == false) {

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 5;

                    } else {

                        level = 3;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:slowness", level, 40);
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.1, 1, "minecraft:reverse_portal");

                    if (Math.random() < 0.25) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:ambient.crimson_forest.mood");

                    }

                }

            }

        }

        private static void give10 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:strength", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:strength") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.player.breath");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.enchantment_table.use");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 1, 20, "minecraft:enchant");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 3;

                    } else {

                        level = 1;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:strength", level, 40);

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:weakness", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:weakness") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.wither.ambient");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:block.beacon.power_select");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 1, 20, "minecraft:enchant");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 3;

                    } else {

                        level = 1;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:weakness", level, 40);

                }

            }

        }

        private static void give11 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:slow_falling", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:slow_falling") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.allay.ambient_with_item");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:ambient.cave");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.1, 20, "minecraft:cloud");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:slow_falling", 1, 40);
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 5, "minecraft:instant_effect");

                    if (is_high_power_mode == true) {

                        if (entity.isShiftKeyDown() == true) {

                            EffectManager.giveBasic(level_server, entity, "minecraft:levitation", 20, 10);
                            GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:entity.ender_dragon.flap");
                            GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.1, 20, "minecraft:cloud");

                        }

                    }

                }

            } else {

                if (EffectManager.hasDuration(level_server, entity, "minecraft:levitation", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:levitation") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.allay.ambient_without_item");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:ambient.cave");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.1, 20, "minecraft:cloud");

                    }

                    int level = 0;

                    if (is_high_power_mode == true) {

                        level = 30;

                    } else {

                        level = 10;

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:levitation", level, 25);
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 5, "minecraft:instant_effect");

                    if (Math.random() < 0.25) {

                        GameUtils.playSound(level_server, entity.position(), 2, 20, "minecraft:ambient.warped_forest.mood");

                    }

                }

            }

        }

        private static void give12 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                if (entity.isUnderWater() == false) {

                    return;

                }

                if (EffectManager.hasDuration(level_server, entity, "minecraft:water_breathing", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:water_breathing") == false) {

                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.dolphin.play");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.allay.ambient_with_item");
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 20, "minecraft:glow");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:water_breathing", 1, 40);
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 5, "minecraft:bubble_column_up");

                    if (is_high_power_mode == true) {

                        EffectManager.giveBasic(level_server, entity, "minecraft:dolphins_grace", 1, 40);

                    }

                }

            } else {

                if (entity.isUnderWater() == false) {

                    return;

                }

                if (EffectManager.hasDuration(level_server, entity, "minecraft:blindness", 40) == false) {

                    if (EffectManager.has(level_server, entity, "minecraft:blindness") == false) {

                        GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:entity.player.breath");
                        GameUtils.playSound(level_server, entity.position(), 0, 20, "minecraft:entity.guardian.death");

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:blindness", 1, 40);
                    GameUtils.runCommandEntity(entity, "damage @s 1 minecraft:drown");
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 5, "minecraft:squid_ink");
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 5, "minecraft:bubble_column_up");

                    if (Math.random() < 0.5) {

                        GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:entity.elder_guardian.ambient");

                    }

                    if (Math.random() < 0.25) {

                        GameUtils.playSound(level_server, entity.position(), 1.5, 20, "minecraft:ambient.underwater.loop.additions.ultra_rare");

                    }

                }

            }

        }

        private static void give13 (ServerLevel level_server, Entity entity, boolean is_negative, boolean is_high_power_mode) {

            if (is_negative == false) {

                GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 1, "minecraft:soul_fire_flame");

                int delay = (int) NBTManager.Mob.getNumber(entity, "spell1", "ophiuchus_heal_delay");

                if (delay > 1) {

                    NBTManager.Mob.setNumber(entity, "spell1", "ophiuchus_heal_delay", delay - 1, false);
                    return;

                } else {

                    NBTManager.Mob.setNumber(entity, "spell1", "ophiuchus_heal_delay", 5, false);

                }

                if (is_high_power_mode == false) {

                    EffectManager.giveBasic(level_server, entity, "minecraft:regeneration", 3, 20);

                    if (Math.random() < 0.5) {

                        GameUtils.playSound(level_server, entity.position(), (Math.random() * 1.5) + 0.5, 20, "minecraft:block.amethyst_block.chime");

                    }

                } else {

                    EffectManager.giveBasic(level_server, entity, "minecraft:instant_health", 1, 1);
                    GameUtils.playSound(level_server, entity.position(), (Math.random() * 1.5) + 0.5, 20, "minecraft:block.amethyst_block.chime");
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0.01, 20, "minecraft:fishing");

                }

            } else {

                if (is_high_power_mode == false) {

                    if (EffectManager.hasDuration(level_server, entity, "minecraft:poison", 40) == false) {

                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 1, "minecraft:sculk_soul");

                        if (Math.random() < 0.25) {

                            GameUtils.playSound(level_server, entity.position(), (Math.random() * 1.5) + 0.5, 20, "minecraft:ambient.soul_sand_valley.mood");

                        }

                        int delay = (int) NBTManager.Mob.getNumber(entity, "spell1", "ophiuchus_poison_delay");

                        if (delay > 1) {

                            NBTManager.Mob.setNumber(entity, "spell1", "ophiuchus_poison_delay", delay - 1, false);
                            return;

                        } else {

                            NBTManager.Mob.setNumber(entity, "spell1", "ophiuchus_poison_delay", 2, false);

                        }

                        EffectManager.giveBasic(level_server, entity, "minecraft:poison", 5, 10);
                        GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 1, 20, "minecraft:witch");

                    }

                } else {

                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 0, 1, "minecraft:sculk_soul");

                    if (Math.random() < 0.25) {

                        GameUtils.playSound(level_server, entity.position(), (Math.random() * 1.5) + 0.5, 20, "minecraft:ambient.soul_sand_valley.mood");

                    }

                    int delay = (int) NBTManager.Mob.getNumber(entity, "spell1", "ophiuchus_damage_delay");

                    if (delay > 1) {

                        NBTManager.Mob.setNumber(entity, "spell1", "ophiuchus_damage_delay", delay - 1, false);
                        return;

                    } else {

                        NBTManager.Mob.setNumber(entity, "spell1", "ophiuchus_damage_delay", 5, false);

                    }

                    EffectManager.giveBasic(level_server, entity, "minecraft:instant_damage", 1, 1);
                    GameUtils.spawnParticle(level_server, entity.position().add(0, 1, 0), 1, 1, 1, 1, 20, "minecraft:witch");

                }

            }

        }

    }

    private static class Empower {

        private static void updateSecond (ServerLevel level_server, ServerPlayer player_server) {

            int number = 0;
            int empowered_card_level = 0;
            boolean is_empowered_card_negative = false;

            if (TimeUtils.isPassed(NBTManager.Mob.getText(player_server, "spell1", "timestamp_next_bonus")) == true) {

                NBTManager.Mob.setText(player_server, "spell1", "timestamp_next_bonus", TimeUtils.getTimestampEvery(1, 0, 0), true);

                RandomSource random = RandomSource.create(Long.parseLong(TimeUtils.getTimestampCurrent()));
                number = random.nextInt(13) + 1;
                is_empowered_card_negative = random.nextBoolean();

                if (random.nextDouble() < 0.1) {

                    empowered_card_level = 4;

                } else if (random.nextDouble() < 0.05) {

                    empowered_card_level = 5;

                } else {

                    empowered_card_level = random.nextInt(3) + 1;

                }

                NBTManager.Mob.setNumber(player_server, "spell1", "empowered_card", number, true);
                NBTManager.Mob.setNumber(player_server, "spell1", "empowered_card_level", empowered_card_level, true);
                NBTManager.Mob.setLogic(player_server, "spell1", "is_empowered_card_negative", is_empowered_card_negative, true);

            } else {

                number = (int) NBTManager.Mob.getNumber(player_server, "spell1", "empowered_card");
                empowered_card_level = (int) NBTManager.Mob.getNumber(player_server, "spell1", "empowered_card_level");
                is_empowered_card_negative = NBTManager.Mob.getLogic(player_server, "spell1", "is_empowered_card_negative");

            }

            String positive_negative = "";

            if (is_empowered_card_negative == true) {

                positive_negative = "negative";

            } else {

                positive_negative = "positive";

            }

            int duration = (int) NBTManager.Mob.getNumber(player_server, "spell1", "card_duration_" + positive_negative + number);
            int duration_max = (int) NBTManager.Mob.getNumber(player_server, "spell1", "card_duration_max_" + positive_negative + number);

            if (duration == duration_max) {

                NBTManager.Mob.setNumber(player_server, "spell1", "card_duration_" + positive_negative + number, duration_max + empowered_card_level, true);

            }

            NBTManager.Mob.setNumber(player_server, "spell1", "card_duration_max_" + positive_negative + number, duration_max + empowered_card_level, true);

        }

    }

}
