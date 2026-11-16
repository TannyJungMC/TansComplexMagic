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
import tannyjung.tanscomplexmagic_handcode.systems.*;

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

                                Entity entity_book = EntityManager.Population.getEverywhereOneStatic(level_server, "", "", Utils.Tag.convertSystemSpecific(player_server, new String[]{"flying_user_book"}));

                                if (entity_book != null) {

                                    // Open Flying User Book
                                    {

                                        if (Book.Fly.isUserLookAt(player_server, entity_book) == true && Book.Fly.isModelOpen(entity_book) == true) {

                                            Book.open(level_server, player_server);
                                            return;

                                        }

                                    }

                                }

                                NBTManager.Mob.addNumber(player_server, "main", "main_key_spam", 1, false);

                                Core.DelayedWork.createBasic(5, () -> {

                                    int spam = (int) NBTManager.Mob.getNumber(player_server, "main", "main_key_spam");
                                    NBTManager.Mob.setNumber(player_server, "main", "main_key_spam", 0, false);

                                    if (spam == 1) {

                                        // Flying Book Stay Follow
                                        {

                                            if (entity_book == null) {

                                                return;

                                            }

                                            if (NBTManager.Mob.getLogic(player_server, "main", "is_main_key_active") == false) {

                                                return;

                                            }

                                            double stayX = NBTManager.Mob.getNumber(entity_book, "main", "stayX");
                                            double stayY = NBTManager.Mob.getNumber(entity_book, "main", "stayY");
                                            double stayZ = NBTManager.Mob.getNumber(entity_book, "main", "stayZ");

                                            if (stayX == 0 && stayY == 0 && stayZ == 0 == true) {

                                                Vec3 vec3 = EntityManager.getPosRayForward(player_server, Book.Fly.MAX_DISTANCE_SET_STAY, true);
                                                Book.Fly.trySetStay(level_server, entity_book, vec3);

                                            } else {

                                                if (entity_book.getX() == stayX && entity_book.getZ() == stayZ) {

                                                    Book.Fly.setFollow(level_server, entity_book);

                                                } else {

                                                    Book.Fly.trySetStay(level_server, entity_book, entity_book.position());

                                                }

                                            }

                                        }

                                    } else if (spam == 2) {

                                        Book.Fly.pickup(level_server, player_server);

                                    }

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
                                    GameUtils.playSound(level_server, player_server.position(), 1.5, 20, "minecraft:block.note_block.chime");

                                    Core.DelayedWork.createBasic(3, () -> {

                                        GameUtils.playSound(level_server, player_server.position(), 2, 20, "minecraft:block.note_block.chime");

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

                                GameUtils.playSound(level_server, player_server.position(), pitch, 20, "minecraft:block.note_block.bell");

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

                if (name.equals("get_user") == true) {

                    player_server.addTag(Utils.Tag.getUser(player_server));

                } else if (name.startsWith("book_") == true) {

                    {

                        if (name.equals("book_close") == true) {

                            {

                                GameUtils.playSound((ServerLevel) player_server.level(), player_server.position(), (Math.random() * 0.5) + 0.25, 20, "minecraft:item.book.page_turn");
                                GUIManager.close(player_server);

                            }

                        } else if (name.equals("book_page_previous") == true) {

                            Book.pageTurn(level_server, player_server, false);

                        } else if (name.equals("book_page_next") == true) {

                            Book.pageTurn(level_server, player_server, true);

                        } else if (name.equals("book_compass_custom_set") == true) {

                            {

                                NBTManager.Mob.setNumber(player_server, "book", "compassX", Math.floor(player_server.getX()) + 0.5, true);
                                NBTManager.Mob.setNumber(player_server, "book", "compassZ", Math.floor(player_server.getZ()) + 0.5, true);

                            }

                        } else if (name.equals("book_text_display") == true) {

                            Book.TextDisplay.setType(level_server, player_server);

                        } else if (name.startsWith("book_bookmark_") == true) {

                            {

                                if (name.startsWith("book_bookmark_custom") == true) {

                                    {

                                        if (name.startsWith("book_bookmark_custom_set") == true) {

                                            {

                                                int number = Integer.parseInt(name.substring("book_bookmark_custom_set".length()));
                                                NBTManager.Mob.setNumber(player_server, "book", "bookmark" + number, NBTManager.Mob.getNumber(player_server, "gui", "id"), true);

                                            }

                                        } else {

                                            {

                                                int number = Integer.parseInt(name.substring("book_bookmark_custom".length()));
                                                NBTManager.Mob.setNumber(player_server, "book", "page", NBTManager.Mob.getNumber(player_server, "book", "bookmark" + number), false);
                                                Book.open(level_server, player_server);

                                            }

                                        }

                                    }

                                } else {

                                    {

                                        String mark_name = name.substring("book_bookmark_".length());
                                        int page = 0;

                                        if (mark_name.equals("home") == true) {

                                            page = 1;

                                        } else if (mark_name.equals("ally_list") == true) {

                                            page = 2;

                                        } else if (mark_name.equals("basic_spell") == true) {

                                            page = 1;

                                        } else if (mark_name.equals("spell1_settings") == true) {

                                            page = 18;

                                        }

                                        NBTManager.Mob.setNumber(player_server, "book", "page", page, false);
                                        Book.open(level_server, player_server);

                                    }

                                }

                            }

                        }

                    }

                } else if (name.startsWith("basic_spell") == true) {

                    {

                        if (name.equals("basic_spell_restart") == true) {

                            BasicSpells.cancel(level_server, player_server);

                        } else if (name.equals("basic_spell1_buy") == true) {

                            {

                                int mana = extra.getInt("mana");

                                if (Mana.spend(player_server, mana) == true) {

                                    NBTManager.Mob.addNumber(player_server, "basic_spell1", "ammo_max", mana * 20, true);

                                }

                            }

                        } else if (name.equals("basic_spell2_buy") == true) {

                            {

                                int mana = extra.getInt("mana");

                                if (Mana.spend(player_server, mana) == true) {

                                    NBTManager.Mob.addNumber(player_server, "basic_spell2", "capacitor_max", mana * 300, true);

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

                        } else if (name.equals("spell1_buy") == true) {

                            {

                                int number = extra.getInt("number");
                                String positive_negative = extra.getString("positive_negative");
                                int capacitor_per_mana = extra.getInt("capacitor_per_mana");
                                int mana = extra.getInt("mana");

                                if (Mana.spend(player_server, mana) == true) {

                                    NBTManager.Mob.addNumber(player_server, "spell1", "card_capacitor_max_" + positive_negative + number, capacitor_per_mana * mana, true);

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}
