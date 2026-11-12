package tannyjung.tanscomplexmagic_core.outside;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.game.screen.GUIManager;
import tannyjung.tanscomplexmagic_handcode.HandcodeNetwork;

public class NetworkBuiltInServer {

    public static void run (Level level, Player player, String category, String name, CompoundTag extra, boolean is_core) {

        ServerLevel level_server = (ServerLevel) level;
        ServerPlayer player_server = (ServerPlayer) player;

        if (is_core == false) {

            HandcodeNetwork.run(level_server, player_server, category, name, extra);
            return;

        }

        if (category.equals("gui") == true) {

            {

                if (name.equals("when_close") == true) {

                    {

                        GUIManager.server_player_watching.remove(player_server);
                        NBTManager.Mob.setNumber(player_server, "gui", "id", 0, true);

                    }

                } else if (name.equals("switch") == true) {

                    {

                        String nbt_type = extra.getString("nbt_category");
                        String nbt_name = extra.getString("nbt_name");
                        GUIManager.NBT.setLogic(player_server, nbt_type, nbt_name, GUIManager.NBT.getLogic(player_server, nbt_type, nbt_name) == false);

                    }

                } else if (name.equals("slider") == true) {

                    {

                        String nbt_type = extra.getString("nbt_category");
                        String nbt_name = extra.getString("nbt_name");
                        double nbt_value = extra.getDouble("nbt_value");
                        GUIManager.NBT.setNumber(player_server, nbt_type, nbt_name, nbt_value);

                    }

                } else if (name.equals("radio") == true) {

                    {

                        String nbt_type = extra.getString("nbt_category");
                        String nbt_name = extra.getString("nbt_name");
                        String nbt_value = extra.getString("nbt_value");
                        GUIManager.NBT.setText(player_server, nbt_type, nbt_name, nbt_value);

                    }

                } else if (name.equals("text_box") == true) {

                    {

                        String nbt_type = extra.getString("nbt_category");
                        String nbt_name = extra.getString("nbt_name");
                        String nbt_value = extra.getString("nbt_value");
                        GUIManager.NBT.setText(player_server, nbt_type, nbt_name, nbt_value);

                    }

                } else if (name.startsWith("board_entity_") == true) {

                    {

                        String nbt_name = extra.getString("nbt_name");

                        if (name.equals("board_entity_add") == true) {

                            GUIManager.Board.addEntity(player_server, nbt_name);

                        } else if (name.equals("board_entity_clear") == true) {

                            GUIManager.Board.clearEntity(player_server, nbt_name);

                        } else if (name.equals("board_entity_remove") == true) {

                            GUIManager.Board.removeEntity(level_server, player_server, nbt_name);

                        }

                        GUIManager.Board.refreshEntity(level_server, player_server, nbt_name);

                    }

                }

            }

        }

    }

}
