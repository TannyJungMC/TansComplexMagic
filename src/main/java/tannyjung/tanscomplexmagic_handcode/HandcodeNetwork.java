package tannyjung.tanscomplexmagic_handcode;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import tannyjung.tanscomplexmagic.init.TanscomplexmagicModItems;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.game.*;
import tannyjung.tanscomplexmagic_core.game.screen.GUIManager;
import tannyjung.tanscomplexmagic_handcode.systems.Book;
import tannyjung.tanscomplexmagic_handcode.systems.Mana;
import tannyjung.tanscomplexmagic_handcode.systems.Spell1;
import tannyjung.tanscomplexmagic_handcode.systems.Utils;

public class HandcodeNetwork {

    public static void run (ServerLevel level_server, ServerPlayer player_server, String category, String name, CompoundTag extra) {

        if (category.equals("key") == true) {

            {

                if (name.startsWith("main_") == true) {

                    {

                        if (name.equals("main_push") == true) {

                            {

                                NBTManager.Mob.setLogic(player_server, "main", "is_main_key_active", true, false);
                                Item item = player_server.getItemBySlot(EquipmentSlot.MAINHAND).getItem();

                                if (item == TanscomplexmagicModItems.USER_BOOK.asItem() || item == TanscomplexmagicModItems.USER_BOOK_OPEN.asItem()) {

                                    return;

                                }

                                // Open Placed User Book
                                {

                                    if (NBTManager.Mob.getLogic(player_server, "main", "is_placed_user_book") == true) {

                                        Vec3 vec3 = EntityManager.getPosRayForward(player_server, 1, false);

                                        for (Entity entity : EntityManager.Population.getArea(level_server, vec3, 0.75, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"placed_book"}))) {

                                            if (DisplayManager.getItemID(entity).equals("tanscomplexmagic:user_book_open") == false) {

                                                continue;

                                            }

                                            Book.open(level_server, player_server);
                                            return;

                                        }

                                    }

                                }

                                int spam = (int) NBTManager.Mob.getNumber(player_server, "main", "main_key_spam") + 1;
                                NBTManager.Mob.setNumber(player_server, "main", "main_key_spam", spam, false);

                                Core.DelayedWork.createBasic(10, () -> {

                                    if (NBTManager.Mob.getNumber(player_server, "main", "main_key_spam") == 2) {

                                        Book.pickup(level_server, player_server);

                                    }

                                    NBTManager.Mob.setNumber(player_server, "main", "main_key_spam", 0, false);

                                });

                            }

                        } else if (name.equals("main_release") == true) {

                            {

                                NBTManager.Mob.setLogic(player_server, "main", "is_main_key_active", false, false);

                            }

                        }

                    }

                } else if (name.startsWith("spell1_") == true) {

                    {

                        if (name.equals("spell1_activate") == true) {

                            {

                                if (NBTManager.Mob.getLogic(player_server, "spell1", "is_active") == false) {

                                    NBTManager.Mob.setLogic(player_server, "spell1", "is_active", true, true);
                                    Spell1.activate(level_server, player_server);
                                    GameUtils.playSound(level_server, player_server.position(), 1.5, 10, "minecraft:block.note_block.chime");

                                    Core.DelayedWork.createBasic(3, () -> {

                                        GameUtils.playSound(level_server, player_server.position(), 2, 10, "minecraft:block.note_block.chime");

                                    });

                                } else {

                                    NBTManager.Mob.setLogic(player_server, "spell1", "is_active", false, true);
                                    Spell1.deactivate(level_server, player_server);

                                    for (Entity entity : EntityManager.Population.getEverywhereStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"spell1"}))) {

                                        entity.discard();

                                    }

                                    GameUtils.playSound(level_server, player_server.position(), 1, 20, "minecraft:block.note_block.chime");

                                    Core.DelayedWork.createBasic(3, () -> {

                                        GameUtils.playSound(level_server, player_server.position(), 0.5, 20, "minecraft:block.note_block.chime");

                                    });

                                }

                            }

                        } else if (name.equals("spell1_pause_all") == true) {

                            {

                                boolean value = NBTManager.Mob.getLogic(player_server, "spell1", "is_pause_all") == false;
                                NBTManager.Mob.setLogic(player_server, "spell1", "is_pause_all", value, true);
                                double pitch = 0;

                                if (value == true) {

                                    pitch = 1.25;

                                } else {

                                    pitch = 1.75;

                                }

                                GameUtils.playSound(level_server, player_server.position(), pitch, 10, "minecraft:block.note_block.bell");

                            }

                        } else if (name.startsWith("spell1_radius_") == true) {

                            {

                                if (name.equals("spell1_radius_stop") == true) {

                                    NBTManager.Mob.setNumber(player_server, "spell1", "radius_key_control", 0, false);
                                    NBTManager.Mob.setNumber(player_server, "spell1", "radius_key_control_tick", 0, false);

                                } else if (name.equals("spell1_radius_increase") == true) {

                                    NBTManager.Mob.setNumber(player_server, "spell1", "radius_key_control", 2, false);

                                } else if (name.equals("spell1_radius_decrease") == true) {

                                    NBTManager.Mob.setNumber(player_server, "spell1", "radius_key_control", 1, false);

                                }

                            }

                        }

                    }

                }

            }

        } else if (category.equals("gui") == true) {

            {

                if (name.startsWith("book_") == true) {

                    {

                        if (name.equals("book_close") == true) {

                            {

                                GameUtils.playSound((ServerLevel) player_server.level(), player_server.position(), 1.0, 10, "minecraft:item.book.page_turn");
                                GUIManager.close(player_server);

                            }

                        } else if (name.equals("book_page_previous") == true) {

                            Book.pageTurn(level_server, player_server, false);

                        } else if (name.equals("book_page_next") == true) {

                            Book.pageTurn(level_server, player_server, true);

                        } else if (name.equals("book_compass_custom_set") == true) {

                            {

                                NBTManager.Mob.setNumber(player_server, "book", "compassX", player_server.getX(), true);
                                NBTManager.Mob.setNumber(player_server, "book", "compassZ", player_server.getZ(), true);

                            }

                        } else if (name.equals("book_log") == true) {

                            Book.Log.setType(player_server);

                        } else if (name.startsWith("book_bookmark_") == true) {

                            {

                                if (name.startsWith("book_bookmark_custom") == true) {

                                    {

                                        if (name.startsWith("book_bookmark_custom_set") == true) {

                                            int number = Integer.parseInt(name.substring("book_bookmark_custom_set".length()));
                                            NBTManager.Mob.setText(player_server, "main", "bookmark" + number, NBTManager.Mob.getText(player_server, "gui", "id"), false);

                                        } else {

                                            int number = Integer.parseInt(name.substring("book_bookmark_custom".length()));
                                            String page = NBTManager.Mob.getText(player_server, "main", "bookmark" + number);
                                            NBTManager.Mob.setText(player_server, "gui", "id", page, false);
                                            Book.open(level_server, player_server);

                                        }

                                    }

                                } else {

                                    String page = name.substring("book_bookmark_".length());
                                    NBTManager.Mob.setText(player_server, "gui", "id", page, false);
                                    Book.open(level_server, player_server);

                                }

                            }

                        }

                    }

                } else if (name.startsWith("spell1_") == true) {

                    {

                        if (name.startsWith("spell1_activate_") == true) {

                            {

                                String type = name.substring("spell1_activate_".length());

                                if (type.equals("cards") == true) {

                                    type = "";

                                } else {

                                    type = "_effect";

                                }

                                for (int number = 1; number <= 13; number++) {

                                    NBTManager.Mob.setLogic(player_server, "spell1", "is_card_activate" + type + number, true, true);

                                }

                                if (type.isEmpty() == true) {

                                    Spell1.Card.refresh(level_server, player_server);

                                }

                            }

                        } else if (name.startsWith("spell1_deactivate_") == true) {

                            {

                                String type = name.substring("spell1_deactivate_".length());

                                if (type.equals("cards") == true) {

                                    type = "";

                                } else {

                                    type = "_effect";

                                }

                                for (int number = 1; number <= 13; number++) {

                                    NBTManager.Mob.setLogic(player_server, "spell1", "is_card_activate" + type + number, false, true);

                                }

                            }

                        } else if (name.equals("spell1_buy_duration") == true) {

                            {

                                int number = extra.getInt("number");
                                String positive_negative = extra.getString("positive_negative");
                                int duration_per_mana = extra.getInt("duration_per_mana");

                                if (Mana.spend(player_server, 1) == true) {

                                    NBTManager.Mob.addNumber(player_server, "spell1", "card_duration_max_" + positive_negative + number, duration_per_mana, true);

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}
