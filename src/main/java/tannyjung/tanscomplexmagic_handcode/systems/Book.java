package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import tannyjung.tanscomplexmagic.init.TanscomplexmagicModItems;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.game.*;
import tannyjung.tanscomplexmagic_core.game.screen.GUIManager;
import tannyjung.tanscomplexmagic_core.game.screen.ScreenDrawing;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

import java.util.List;

public class Book {

    public static void whenRightClick (ServerLevel level_server, ServerPlayer player_server) {

        boolean is_main_key_active = NBTManager.Mob.getLogic(player_server, "main", "is_main_key_active");
        ItemStack item = player_server.getItemBySlot(EquipmentSlot.MAINHAND);

        if (item.getItem() == TanscomplexmagicModItems.USER_BOOK.asItem()) {

            if (is_main_key_active == true) {

                player_server.setItemSlot(EquipmentSlot.MAINHAND, TanscomplexmagicModItems.USER_BOOK_OPEN.toStack());

            }

        } else if (item.getItem() == TanscomplexmagicModItems.USER_BOOK_OPEN.asItem()) {

            if (is_main_key_active == true) {

                player_server.setItemSlot(EquipmentSlot.MAINHAND, TanscomplexmagicModItems.USER_BOOK.toStack());
                GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:block.wood.break");

            } else {

                open(level_server, player_server);

            }

        }

    }

    public static void whenRightClickAtBlock (ServerLevel level_server, ServerPlayer player_server, Vec3 vec3) {

        if (NBTManager.Mob.getLogic(player_server, "main", "is_main_key_active") == false) {

            return;

        }

        BlockPos pos = BlockPos.containing(vec3).above();

        if (level_server.getBlockState(pos).isAir() == false || level_server.getBlockState(pos.above()).isAir() == false) {

            return;

        }

        place(level_server, player_server, vec3.add(0.5, 1.5, 0.5));

    }

    public static void open (ServerLevel level_server, ServerPlayer player_server) {

        int page = (int) NBTManager.Mob.getNumber(player_server, "book", "page");

        if (page == 0) {

            page = 1;
            NBTManager.Mob.setNumber(player_server, "book", "page", page, false);

        }

        GUIManager.open(player_server, "entity", page);
        GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:item.book.page_turn");

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
        GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:item.book.page_turn");

    }

    private static void place (ServerLevel level_server, ServerPlayer player_server, Vec3 vec3) {

        if (NBTManager.Mob.getLogic(player_server, "main", "is_placed_book") == false) {

            NBTManager.Mob.setLogic(player_server, "main", "is_placed_book", true, false);

        } else {

            return;

        }

        ItemManager.removeFromBody(player_server, TanscomplexmagicModItems.USER_BOOK.asItem());
        ItemManager.removeFromBody(player_server, TanscomplexmagicModItems.USER_BOOK_OPEN.asItem());

        Entity entity = DisplayManager.summonItem(level_server, vec3.add(0, 0.75, 0), (int) (Math.random() * 360), 0, 1, false, "Placed User Book", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book"}), "tanscomplexmagic:user_book");
        NBTManager.Mob.setLogic(entity, "main", "is_open", false, false);
        NBTManager.Mob.setNumber(entity, "main", "delay_open", 20, false);

        EntityManager.summon(level_server, entity.position().add(0, -1.25, 0), false, "minecraft:marker", "Placed User Book Fireball", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book_light"}), "");
        level_server.setBlock(BlockPos.containing(vec3), Blocks.LIGHT.defaultBlockState(), 2);

    }

    public static void pickup (ServerLevel level_server, ServerPlayer player_server) {

        ItemManager.removeFromBody(player_server, TanscomplexmagicModItems.USER_BOOK.asItem());
        ItemManager.removeFromBody(player_server, TanscomplexmagicModItems.USER_BOOK_OPEN.asItem());
        NBTManager.Mob.setLogic(player_server, "main", "is_placed_book", false, false);
        GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 50, "minecraft:block.bell.use");

        Core.DelayedWork.createBasic(20, () -> {

            ItemManager.give(player_server, TanscomplexmagicModItems.USER_BOOK.toStack());

        });

        for (Entity entity : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book"}))) {

            DisplayManager.setItemID(entity, "tanscomplexmagic:user_book");
            GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.5) + 0.5, 50, "minecraft:block.bell.use");

            Core.DelayedWork.createBasic(20, () -> {

                GameUtils.spawnParticle(level_server, entity.position(), 0, 0, 0, 0, 1, "minecraft:flash");
                GameUtils.spawnParticle(level_server, entity.position(), 0.25, 0.25, 0.25, 0.01, 10, "minecraft:campfire_signal_smoke");
                GameUtils.playSound(level_server, player_server.position(), (Math.random() * 0.5) + 0.5, 50, "minecraft:entity.illusioner.cast_spell");
                level_server.removeBlock(entity.blockPosition().below(), false);
                entity.discard();

                GameUtils.spawnParticle(level_server, player_server.position(), 0, 0, 0, 0, 1, "minecraft:flash");
                GameUtils.spawnParticle(level_server, player_server.position().add(0, 1, 0), 1, 1, 1, 0, 10, "minecraft:firework");

            });

        }

        for (Entity entity : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book_light"}))) {

            entity.discard();

        }

    }

    public static void updatePlacedBook (ServerLevel level_server, ServerPlayer player_server, Entity entity) {

        if (player_server.position().distanceTo(entity.position()) < 5) {

            if (NBTManager.Mob.getLogic(entity, "main", "is_open") == false) {

                int delay = (int) NBTManager.Mob.getNumber(entity, "main", "delay_open");

                if (delay > 1) {

                    NBTManager.Mob.setNumber(entity, "main", "delay_open", delay - 1, false);

                } else {

                    NBTManager.Mob.setLogic(entity, "main", "is_open", true, false);
                    DisplayManager.setItemID(entity, "tanscomplexmagic:user_book_open");
                    GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:item.book.page_turn");

                }

            }

            entity.lookAt(EntityAnchorArgument.Anchor.FEET, player_server.getEyePosition());

        } else {

            if (NBTManager.Mob.getLogic(entity, "main", "is_open") == true) {

                NBTManager.Mob.setLogic(entity, "main", "is_open", false, false);
                NBTManager.Mob.setNumber(entity, "main", "delay_open", 20, false);
                DisplayManager.setItemID(entity, "tanscomplexmagic:user_book");
                entity.setXRot(0);
                entity.setYRot((float) (Math.random() * 360));
                GameUtils.playSound(level_server, entity.position(), (Math.random() * 0.5) + 0.5, 20, "minecraft:block.wood.break");

            }

            entity.setYRot(entity.getYRot() + 1);

        }

        // Fly Up Down
        {

            int fly_up_down = (int) NBTManager.Mob.getNumber(entity, "main", "fly_up_down");

            if (fly_up_down > 1) {

                fly_up_down = fly_up_down - 1;
                NBTManager.Mob.setNumber(entity, "main", "fly_up_down", fly_up_down, false);

            } else {

                NBTManager.Mob.setNumber(entity, "main", "fly_up_down", 200, false);

            }

            if (fly_up_down >= 100) {

                entity.setPos(entity.position().add(0, 0.001, 0));

            } else {

                entity.setPos(entity.position().add(0, -0.001, 0));

            }

        }

        for (Entity entity_fireball : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book_light"}))) {

            entity_fireball.setYRot(entity_fireball.getYRot() - 10);
            GameUtils.spawnParticle(level_server, EntityManager.getPosLook(entity_fireball, 0, 0, 0.15), 0, 0, 0, 0, 1, "minecraft:end_rod");
            GameUtils.spawnParticle(level_server, EntityManager.getPosLook(entity_fireball, 0, 0, -0.15), 0, 0, 0, 0, 1, "minecraft:end_rod");

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
            boolean should_make_sound = false;
            Vec3 vec3 = null;
            
            if (NBTManager.Mob.getLogic(player_server, "main", "is_placed_book") == true) {

                Entity entity = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book"}));

                if (entity != null) {

                    vec3 = entity.position();

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
                    List<Entity> list = EntityManager.Population.getArea(level_server, vec3, distance, "", "", new String[]{});

                    if (list.isEmpty() == false) {

                        for (Entity scan : EntityManager.Population.sort(list, vec3, false, 0)) {

                            if (scan instanceof Monster == false) {

                                continue;

                            }

                            text = "Detected \"" + scan.getName().getString() + "\" at " + (int) vec3.distanceTo(scan.position()) + " block(s) away";
                            should_make_sound = true;
                            break;

                        }

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
                            should_make_sound = true;

                        }

                    }

                }

            } else if (mode.equals("Light Level 0") == true) {

                {

                    int level = level_server.getBrightness(LightLayer.BLOCK, BlockPos.containing(vec3));
                    text = "Light level of this position is " + level;

                    if (level == 0) {

                        should_make_sound = true;

                    }

                }

            } else if (mode.equals("Mana Full") == true) {

                {

                    if (Mana.get(player_server)[0] == Mana.CAPACITY_MAX) {

                        text = "Mana is full";
                        should_make_sound = true;

                    } else {

                        text = "Mana will be full in " + TimeUtils.convertSecondToReadable(TimeUtils.getRemain(NBTManager.Mob.getText(player_server, "main", "timestamp_mana")));

                    }

                }

            }

            if (should_make_sound == true) {

                String sound = NBTManager.Mob.getText(player_server, "book", "text_display_trigger_sound");
                GameUtils.playSound(level_server, vec3, 0.75, 20, "minecraft:block.note_block." + sound.toLowerCase());

            }

            return "§c" + text;

        }

    }

}
