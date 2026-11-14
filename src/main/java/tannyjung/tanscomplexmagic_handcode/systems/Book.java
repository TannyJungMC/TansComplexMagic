package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import tannyjung.tanscomplexmagic.init.TanscomplexmagicModItems;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.game.*;
import tannyjung.tanscomplexmagic_core.game.screen.GUIManager;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

public class Book {

    public static void whenRightClick (ServerLevel level_server, ServerPlayer player_server) {

        boolean is_main_key_active = NBTManager.Mob.getLogic(player_server, "main", "is_main_key_active");
        ItemStack item_left = player_server.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack item_right = player_server.getItemBySlot(EquipmentSlot.MAINHAND);

        if (item_left.isEmpty() == true) {

            if (item_right.getItem() == TanscomplexmagicModItems.USER_BOOK.asItem()) {

                if (is_main_key_active == true) {

                    player_server.setItemSlot(EquipmentSlot.MAINHAND, TanscomplexmagicModItems.USER_BOOK_OPEN.toStack());
                    GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:item.book.page_turn");

                }

            } else if (item_right.getItem() == TanscomplexmagicModItems.USER_BOOK_OPEN.asItem()) {

                if (is_main_key_active == true) {

                    player_server.setItemSlot(EquipmentSlot.MAINHAND, TanscomplexmagicModItems.USER_BOOK.toStack());

                    Core.DelayedWork.createBasic(4, () -> {

                        GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:block.wood.break");

                    });

                } else {

                    open(level_server, player_server);

                }

            }

        } else {

            if (item_left.getItem() == TanscomplexmagicModItems.USER_BOOK_OPEN.asItem()) {

                ItemManager.setCooldown(player_server, item_left, 20);

            }

        }

    }

    public static void whenRightClickAtBlock (ServerLevel level_server, ServerPlayer player_server, Vec3 vec3) {

        if (NBTManager.Mob.getLogic(player_server, "main", "is_main_key_active") == false || player_server.getMainHandItem().getItem() != TanscomplexmagicModItems.USER_BOOK.asItem()) {

            whenRightClick(level_server, player_server);
            return;

        }

        BlockPos pos = BlockPos.containing(vec3).above();

        if (level_server.getBlockState(pos).isAir() == false || level_server.getBlockState(pos.above()).isAir() == false) {

            return;

        }

        Fly.place(level_server, player_server, vec3.add(0.5, 1.5, 0.5));

    }

    public static void open (ServerLevel level_server, ServerPlayer player_server) {

        int page = (int) NBTManager.Mob.getNumber(player_server, "book", "page");

        if (page == 0) {

            page = 1;
            NBTManager.Mob.setNumber(player_server, "book", "page", page, false);

        }

        GUIManager.open(player_server, "entity", page);
        GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:item.book.page_turn");

    }

    public static void pageTurn (ServerLevel level_server, ServerPlayer player_server, boolean is_next) {

        int page = (int) NBTManager.Mob.getNumber(player_server, "book", "page");

        if (is_next == true) {

            page = page + 1;

        } else {

            if (page - 1 <= 0) {

                return;

            }

            page = page - 1;

        }

        NBTManager.Mob.setNumber(player_server, "book", "page", page, false);
        GUIManager.open(player_server, "entity", page);
        GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:item.book.page_turn");

    }

    public static class Fly {

        public static void updateTick (ServerLevel level_server, ServerPlayer player_server) {

            Entity entity_book = Fly.get(level_server, player_server);

            if (entity_book == null) {

                return;

            }

            // Fly Follow
            {

                if (NBTManager.Mob.getLogic(entity_book, "main", "is_fly_follow") == true) {

                    Vec3 vec3 = EntityManager.getPosLook(player_server, 1.25, 0, 1.25);
                    vec3 = new Vec3(vec3.x, player_server.getEyeY() + 0.5, vec3.z);

                    if (entity_book.position().distanceTo(vec3) > 2) {

                        entity_book.setPos(vec3);

                    }

                }

            }

            // Fly Up Down
            {

                int fly_up_down = (int) NBTManager.Mob.getNumber(entity_book, "main", "fly_up_down");

                if (fly_up_down > 1) {

                    fly_up_down = fly_up_down - 1;
                    NBTManager.Mob.setNumber(entity_book, "main", "fly_up_down", fly_up_down, false);

                } else {

                    NBTManager.Mob.setNumber(entity_book, "main", "fly_up_down", 200, false);

                }

                if (fly_up_down >= 100) {

                    entity_book.setPos(entity_book.position().add(0, 0.001, 0));

                } else {

                    entity_book.setPos(entity_book.position().add(0, -0.001, 0));

                }

            }

            for (Entity entity_light : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"flying_user_book_light"}))) {

                entity_light.setYRot(entity_light.getYRot() - 10);
                GameUtils.spawnParticle(level_server, EntityManager.getPosLook(entity_light, 0, 0, 0.15), 0, 0, 0, 0, 1, "minecraft:end_rod");
                GameUtils.spawnParticle(level_server, EntityManager.getPosLook(entity_light, 0, 0, -0.15), 0, 0, 0, 0, 1, "minecraft:end_rod");

            }

            String custom_look_target = NBTManager.Mob.getText(entity_book, "main", "custom_look_target");

            if (isUserLookAt(player_server, entity_book) == true || custom_look_target.isEmpty() == false) {

                if (NBTManager.Mob.getLogic(entity_book, "main", "is_focus") == false) {

                    int delay = (int) NBTManager.Mob.getNumber(entity_book, "main", "delay_open");

                    if (delay > 1) {

                        NBTManager.Mob.setNumber(entity_book, "main", "delay_open", delay - 1, false);

                    } else {

                        NBTManager.Mob.setLogic(entity_book, "main", "is_focus", true, false);
                        DisplayManager.setItemID(entity_book, "tanscomplexmagic:user_book_open");
                        GameUtils.playSound(level_server, entity_book.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:item.book.page_turn");

                    }

                }

                Entity entity_look_target = null;

                if (isUserLookAt(player_server, entity_book)) {

                    entity_look_target = player_server;

                } else {

                    entity_look_target = EntityManager.getByUUID(level_server, custom_look_target);

                }

                if (entity_look_target != null) {

                    entity_book.lookAt(EntityAnchorArgument.Anchor.FEET, entity_look_target.getEyePosition());

                }

            } else {

                if (NBTManager.Mob.getLogic(entity_book, "main", "is_focus") == true) {

                    NBTManager.Mob.setLogic(entity_book, "main", "is_focus", false, false);
                    NBTManager.Mob.setNumber(entity_book, "main", "delay_open", 20, false);

                    entity_book.setXRot(0);
                    entity_book.setYRot((float) (Math.random() * 360));
                    DisplayManager.setItemID(entity_book, "tanscomplexmagic:user_book");
                    GameUtils.playSound(level_server, entity_book.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:block.wood.break");

                }

                entity_book.setYRot(entity_book.getYRot() + 1);

            }

        }

        public static Entity get (ServerLevel level_server, ServerPlayer player_server) {

            return EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"flying_user_book"}));

        }

        private static void place (ServerLevel level_server, ServerPlayer player_server, Vec3 vec3) {

            if (NBTManager.Mob.getLogic(player_server, "main", "is_book_flying") == false) {

                NBTManager.Mob.setLogic(player_server, "main", "is_book_flying", true, false);

            } else {

                return;

            }

            Entity entity_book = DisplayManager.summonItem(level_server, vec3.add(0, 0.75, 0), (int) (Math.random() * 360), 0, 1, false, "Flying User Book", Utils.Tag.convertSystemSpecific(player_server, new String[]{"flying_user_book"}), "tanscomplexmagic:user_book");
            NBTManager.Mob.setNumber(entity_book, "main", "delay_open", 20, false);

            if (player_server.isShiftKeyDown() == true) {

                EntityManager.summonSystem(level_server, entity_book.position().add(0, -1.25, 0), false, "minecraft:marker", "Flying User Book Light", Utils.Tag.convertSystemSpecific(player_server, new String[]{"flying_user_book_light"}), "");
                level_server.setBlock(BlockPos.containing(vec3), Blocks.LIGHT.defaultBlockState(), 2);

            } else {

                NBTManager.Mob.setLogic(entity_book, "main", "is_fly_follow", true, true);

            }

            player_server.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);

        }

        public static void pickup (ServerLevel level_server, ServerPlayer player_server) {

            NBTManager.Mob.setLogic(player_server, "main", "is_book_flying", false, false);
            ItemManager.removeFromBody(player_server, TanscomplexmagicModItems.USER_BOOK.asItem());
            ItemManager.removeFromBody(player_server, TanscomplexmagicModItems.USER_BOOK_OPEN.asItem());
            GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 50, "minecraft:block.bell.use");

            Core.DelayedWork.createBasic(20, () -> {

                GameUtils.spawnParticle(level_server, player_server.position(), 0, 0, 0, 0, 1, "minecraft:flash");
                GameUtils.spawnParticle(level_server, player_server.position().add(0, 1, 0), 1, 1, 1, 0, 10, "minecraft:firework");
                ItemManager.give(player_server, TanscomplexmagicModItems.USER_BOOK.toStack());

                BasicSpells.restart(level_server, player_server);

                for (Entity entity_light : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"flying_user_book_light"}))) {

                    if (level_server.getBlockState(entity_light.blockPosition()) != Blocks.LIGHT.defaultBlockState()) {

                        continue;

                    }

                    level_server.removeBlock(entity_light.blockPosition(), false);
                    entity_light.discard();

                }

            });

            Entity entity_book = Fly.get(level_server, player_server);

            if (entity_book == null) {

                return;

            }

            DisplayManager.setItemID(entity_book, "tanscomplexmagic:user_book");
            GameUtils.playSound(level_server, entity_book.position(), (Math.random() * 0.5) + 0.5, 50, "minecraft:block.bell.use");

            Core.DelayedWork.createBasic(20, () -> {

                GameUtils.spawnParticle(level_server, entity_book.position(), 0, 0, 0, 0, 1, "minecraft:flash");
                GameUtils.spawnParticle(level_server, entity_book.position(), 0.25, 0.25, 0.25, 0.01, 10, "minecraft:campfire_signal_smoke");
                GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 50, "minecraft:entity.illusioner.cast_spell");
                entity_book.discard();

            });

        }

        public static void setCustomLookTarget (Entity entity_book, Entity entity_target) {

            String uuid = "";

            if (entity_target != null) {

                uuid = entity_target.getStringUUID();

            }

            NBTManager.Mob.setText(entity_book, "main", "custom_look_target", uuid, false);
            
        }

        public static boolean isUserLookAt (ServerPlayer player_server, Entity entity_book) {

            return EntityManager.getPosRayForward(player_server, 1, true).distanceTo(entity_book.position()) < 0.5 || EntityManager.getPosRayForward(player_server, 0.5, true).distanceTo(entity_book.position()) < 0.5;

        }

        public static boolean isModelOpen (Entity entity_book) {

            return DisplayManager.getItemID(entity_book).equals("tanscomplexmagic:user_book_open") == true;

        }
        
    }

    public static class TextDisplay {

        public static void setType (ServerLevel level_server, ServerPlayer player_server) {

            String type = NBTManager.Mob.getText(player_server, "book", "text_display_type");

            if (type.isEmpty() == true) {

                type = "mana";

            } else if (type.equals("mana") == true) {

                type = "information";

            } else if (type.equals("information") == true) {

                type = "trigger";

            } else {

                type = "";

            }

            NBTManager.Mob.setText(player_server, "book", "text_display_type", type, true);
            update(level_server, player_server);

        }

        public static void update (ServerLevel level_server, ServerPlayer player_server) {

            String type = NBTManager.Mob.getText(player_server, "book", "text_display_type");
            String text = "";

            if (type.equals("trigger") == true) {

                text = updateTrigger(level_server, player_server);

                if (GUIManager.server_player_watching.isEmpty() == true) {

                    return;

                }

            } else {

                if (GUIManager.server_player_watching.isEmpty() == true) {

                    return;

                }

                if (type.equals("mana") == true) {

                    {

                        int[] mana = Mana.get(player_server);
                        String time = "";

                        if (mana[0] == Mana.CAPACITY_MAX) {

                            time = "Full";

                        } else {

                            time = "Will be full in " + TimeUtils.convertSecondToReadable(TimeUtils.getRemain(NBTManager.Mob.getText(player_server, "main", "timestamp_mana")));

                        }

                        text = "§eMana " + mana[0] + "/" + Mana.CAPACITY_MAX + " Charge " + mana[1] + "/60 - " + time;

                    }

                } else if (type.equals("information") == true) {

                    text = updateInformation(level_server, player_server);

                }

            }

            NBTManager.Mob.setText(player_server, "book", "text_display_text", text, true);

        }

        public static String updateInformation (ServerLevel level_server, ServerPlayer player_server) {

            String mode = NBTManager.Mob.getText(player_server, "book", "text_display_information_mode");
            String text = "";

            if (mode.equals("Current Game Time") == true) {

                {

                    long hour = (((level_server.getDayTime() % 24000) / 1000) + 6) % 24;
                    long minute = (long) Math.floor(((level_server.getDayTime() % 1000) / 1000.0) * 60.0);
                    String time = String.valueOf(minute);

                    if (time.length() == 1) {

                        time = "0" + time;

                    }

                    time = hour + ":" + time;

                    if (time.length() == 4) {

                        time = "0" + time;

                    }

                    text = "Current game time is " + time + " of day " + level_server.getGameTime() / 24000;

                }

            } else if (mode.equals("Distance to World Spawn") == true) {

                text = "You are " + (int) player_server.position().distanceTo(GameUtils.Space.getWorldSpawnPos(level_server).getCenter()) + " block(s) away from world spawn";

            } else if (mode.equals("Distance to Custom Compass Position") == true) {

                {

                    int compassX = (int) NBTManager.Mob.getNumber(player_server, "book", "compassX");
                    int compassZ = (int) NBTManager.Mob.getNumber(player_server, "book", "compassZ");
                    text = "You are " + (int) player_server.position().distanceTo(new Vec3(compassX, player_server.getY(), compassZ)) + " block(s) away from custom compass position";

                }

            } else if (mode.equals("Distance to Selected Ally") == true) {

                {

                    Entity entity = GUIManager.Board.getEntitySelect(level_server, player_server, Utils.Tag.getAlly(player_server));

                    if (entity == null) {

                        text = "Can not get distance to selected ally, because not detected.";

                    } else {

                        text = "You are " + (int) player_server.position().distanceTo(entity.position()) + " block(s) away from selected ally";

                    }

                }

            }

            return "§a" + text;

        }

        public static String updateTrigger (ServerLevel level_server, ServerPlayer player_server) {

            String mode = NBTManager.Mob.getText(player_server, "book", "text_display_trigger_mode");
            String text = "";
            boolean cam_make_sound = false;
            Vec3 vec3 = null;
            
            if (NBTManager.Mob.getLogic(player_server, "main", "is_book_flying") == true) {

                Entity entity = Fly.get(level_server, player_server);

                if (entity != null) {

                    vec3 = entity.position().add(0, -1, 0);

                }

            } else {

                for (ItemStack item : player_server.getHandSlots()) {

                    if (item.getItem() == TanscomplexmagicModItems.USER_BOOK.asItem() || item.getItem() == TanscomplexmagicModItems.USER_BOOK_OPEN.asItem()) {

                        vec3 = player_server.position();
                        break;

                    }

                }

            }

            if (vec3 == null) {

                return "";

            }
            
            if (mode.startsWith("Nearby Monster (") == true) {

                {

                    int distance = Integer.parseInt(mode.substring("Nearby Monster (".length(), mode.length() - " Blocks)".length()));

                    for (Entity scan : EntityManager.Population.sort(EntityManager.Population.getAreaAny(level_server, vec3, distance, "", "", new String[]{}), vec3, false, 0)) {

                        if (scan instanceof Enemy == false) {

                            continue;

                        }

                        text = "Detected \"" + scan.getName().getString() + "\" at " + (int) vec3.distanceTo(scan.position()) + " block(s) away";
                        cam_make_sound = true;
                        break;

                    }

                    if (text.isEmpty() == true) {

                        text = "No monsters detected within " + distance + " blocks";

                    }

                }

            } else if (mode.equals("Insomnia Danger Zone") == true) {

                {

                    int day = player_server.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) / 24000;

                    if (day == 0) {

                        text = "You have enough sleep for today";

                    } else {

                        text = "You have not slept for " + day + " day(s). ";

                        if (day < 6) {

                            text = text + "Try to sleep when have the chance.";

                        } else {

                            text = text + "This is dangerous!";
                            cam_make_sound = true;

                        }

                    }

                }

            } else if (mode.equals("Light Level 0") == true) {

                {

                    int level = level_server.getBrightness(LightLayer.BLOCK, BlockPos.containing(vec3));
                    text = "Light level of this position is " + level;

                    if (level == 0) {

                        cam_make_sound = true;

                    }

                }

            } else if (mode.equals("Mana Full") == true) {

                {

                    if (Mana.get(player_server)[0] == Mana.CAPACITY_MAX) {

                        text = "Mana is full";
                        cam_make_sound = true;

                    } else {

                        text = "Mana will be full in " + TimeUtils.convertSecondToReadable(TimeUtils.getRemain(NBTManager.Mob.getText(player_server, "main", "timestamp_mana")));

                    }

                }

            }

            if (cam_make_sound == true) {

                String sound = NBTManager.Mob.getText(player_server, "book", "text_display_trigger_sound");
                GameUtils.playSound(level_server, vec3, 0.75, 20, "minecraft:block.note_block." + sound.toLowerCase());

            }

            return "§c" + text;

        }

    }

}
