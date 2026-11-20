package tannyjung.tanscomplexmagic_core.game.screen;

import io.netty.buffer.Unpooled;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.game.EntityManager;
import tannyjung.tanscomplexmagic_core.game.NBTManager;

import java.util.*;

public class GUIManager {

    public static final DeferredRegister<MenuType<?>> register = DeferredRegister.create(Registries.MENU, Core.mod_id);
    public static final DeferredHolder<MenuType<?>, MenuType<GUIContainer>> gui = register.register("gui", () -> IMenuTypeExtension.create(GUIContainer::new));
    public static final List<ServerPlayer> server_player_watching = new ArrayList<>();
    public static boolean server_refresh = false;

    private static final Map<String, Map<String, Runnable>> guis = new HashMap<>();
    private static final Map<String, Map<String, Integer>> index_name_number = new HashMap<>();
    private static final Map<String, Map<Integer, String>> index_name_text = new HashMap<>();

    public record Index (String category, String name) {

        public Index {

            int number = index_name_number.getOrDefault(category, new HashMap<>()).size() + 1;
            index_name_number.computeIfAbsent(category, create -> new HashMap<>()).put(name, number);
            index_name_text.computeIfAbsent(category, create -> new HashMap<>()).put(number, name);

        }

    }

    public static void register (Index index, Runnable runnable) {

        guis.computeIfAbsent(index.category, create -> new HashMap<>()).put(index.name, runnable);

    }

    public static void draw (String category, String name) {

        Map<String, Runnable> map = guis.get(category);

        if (map == null) {

            return;

        }

        Runnable runnable = map.get(name);

        if (runnable == null) {

            return;

        }

        runnable.run();

    }

    public static void open (ServerPlayer player_server, String nbt_type, String category, String name) {

        MenuProvider provider = new MenuProvider() {

            @Override
            public @NotNull Component getDisplayName () {

                return Component.literal(Core.mod_name);

            }

            @Override
            public boolean shouldTriggerClientSideContainerClosingOnOpen () {

                return false;

            }

            @Override
            public AbstractContainerMenu createMenu (int id, @NotNull Inventory inventory, Player player) {

                return new GUIContainer(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(player.blockPosition()));

            }

        };

        if (GUIManager.getCurrentName(player_server).isEmpty() == true) {

            server_player_watching.add(player_server);

        }

        NBTManager.Mob.setText(player_server, "gui", "category", category, true);
        NBTManager.Mob.setText(player_server, "gui", "name", name, true);
        NBTManager.Mob.setText(player_server, "gui", "nbt_type", nbt_type, true);
        player_server.openMenu(provider, player_server.blockPosition());

    }

    public static void close (ServerPlayer player_server) {

        player_server.closeContainer();
        GUIManager.server_player_watching.remove(player_server);
        NBTManager.Mob.setText(player_server, "gui", "category", "", true);
        NBTManager.Mob.setText(player_server, "gui", "name", "", true);
        NBTManager.Mob.setText(player_server, "gui", "nbt_type", "", true);

    }

    public static String getCurrentCategory (Player player) {

        return NBTManager.Mob.getText(player, "gui", "category");

    }

    public static String getCurrentName (Player player) {

        return NBTManager.Mob.getText(player, "gui", "name");

    }

    public static String getCurrentNBTType (Player player) {

        return NBTManager.Mob.getText(player, "gui", "nbt_type");

    }

    public static int getNumberFromName (String category, String name) {

        Map<String, Integer> map = index_name_number.get(category);

        if (map == null) {

            return 0;

        }

        return map.get(name);

    }

    public static String getNameFromNumber (String category, int number) {

        Map<Integer, String> map = index_name_text.get(category);

        if (map == null) {

            return "";

        }

        return Objects.requireNonNullElse(map.get(number), "");

    }

    public static class NBT {

        public static boolean getLogic (Player player, String type, String name) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                return NBTManager.Item.getLogic(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name);

            } else {

                return NBTManager.Mob.getLogic(player, type, name);

            }

        }

        public static void setLogic (Player player, String type, String name, boolean value) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                NBTManager.Item.setLogic(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name, value);

            } else {

                NBTManager.Mob.setLogic(player, type, name, value, true);

            }

        }

        public static String getText (Player player, String type, String name) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                return NBTManager.Item.getText(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name);

            } else {

                return NBTManager.Mob.getText(player, type, name);

            }

        }

        public static void setText (Player player, String type, String name, String value) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                NBTManager.Item.setText(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name, value);

            } else {

                NBTManager.Mob.setText(player, type, name, value, true);

            }

        }

        public static double getNumber (Player player, String type, String name) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                return NBTManager.Item.getNumber(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name);

            } else {

                return NBTManager.Mob.getNumber(player, type, name);

            }

        }

        public static void setNumber (Player player, String type, String name, double value) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                NBTManager.Item.setNumber(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name, value);

            } else {

                NBTManager.Mob.setNumber(player, type, name, value, true);

            }

        }

        public static List<String> getList (Player player, String type, String name) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                return NBTManager.Item.getListAsList(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name);

            } else {

                return NBTManager.Mob.getListAsList(player, type, name);

            }

        }

        public static void setList (Player player, String type, String name, List<String> value) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                NBTManager.Item.setListAlternate(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name, value);

            } else {

                NBTManager.Mob.setListAlternate(player, type, name, value, true);

            }

        }

        public static void addListMultiple (Player player, String type, String name, List<String> value) {

            if (NBTManager.Mob.getText(player, "gui", "nbt_type").equals("item") == true) {

                NBTManager.Item.addListMultiple(player.getItemBySlot(EquipmentSlot.MAINHAND), type, name, value);

            } else {

                NBTManager.Mob.addListMultiple(player, type, name, value, true);

            }

        }

    }

    public static class Board {

        private static String getSelect (List<String> list, int select) {

            if (list.isEmpty() == true) {

                return "";

            }

            if (list.size() <= select) {

                return "";

            }

            return list.get(select);

        }

        public static String getBasicSelect (ServerPlayer player_server, String name) {

            return getSelect(NBT.getList(player_server, "gui", "board_" + name), (int) NBT.getNumber(player_server, "gui", "board_" + name + "_select"));

        }

        public static Entity getEntitySelect (ServerLevel level_server, ServerPlayer player_server, String tag) {

            String select = getSelect(NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_uuid"), (int) NBT.getNumber(player_server, "gui", "board_entity_" + tag + "_select"));
            return EntityManager.getByUUID(level_server, select);

        }

        public static void refreshEntity (ServerLevel level_server, ServerPlayer player_server, String tag) {

            for (Entity scan : EntityManager.Population.getEverywhereUpdatable(level_server, "", "", new String[]{tag})) {

                scan.removeTag(tag);

            }

            List<String> list_id = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_id");
            List<String> list_name = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_name");
            List<String> list = new ArrayList<>();
            Entity entity = null;
            String entity_name = "";
            String entity_name_color = "";
            String entity_status = "";
            int number = 0;

            for (String scan : NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_uuid")) {

                entity = EntityManager.getByUUID(level_server, scan);
                entity_name = list_name.get(number);

                if (list_id.get(number).equals("minecraft:player") == true) {

                    if (entity == null) {

                        entity_name_color = "§6";
                        entity_status = " (Offline)";

                    } else {

                        entity_name_color = "§a";
                        entity_status = " (Online)";
                        entity.addTag(tag);

                    }

                } else {

                    if (entity == null) {

                        entity_name_color = "§c";
                        entity_status = " (Not Detected)";

                    } else {

                        if (entity.hasCustomName() == true) {

                            entity_name_color = "§d";
                            entity_status = "";

                        } else {

                            entity_name_color = "§f";
                            entity_status = "";

                        }

                        entity.addTag(tag);

                    }

                }

                list.add(entity_name_color + entity_name + entity_status);
                number = number + 1;

            }

            NBT.setList(player_server, "gui", "board_entity_" + tag, list);

        }

        public static void addEntity (ServerPlayer player_server, String tag) {

            String target_id = NBT.getText(player_server, "gui", "board_entity_" + tag + "_id");
            String target_name = NBT.getText(player_server, "gui", "board_entity_" + tag + "_name");
            int radius = (int) NBT.getNumber(player_server, "gui", "board_entity_" + tag + "_radius");

            List<String> list_uuid_original = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_uuid");
            List<String> list_uuid = new ArrayList<>();
            List<String> list_id = new ArrayList<>();
            List<String> list_name = new ArrayList<>();
            String uuid = "";

            for (Entity entity : EntityManager.Population.sort(EntityManager.Population.getAreaAny(player_server.serverLevel(), player_server.position(), radius, target_id, target_name, new String[]{"!" + Core.mod_id_big}), player_server.position(), false, 0)) {

                uuid = entity.getStringUUID();

                if (list_uuid_original.contains(uuid) == true) {

                    continue;

                }

                list_uuid.add(uuid);
                list_id.add(EntityManager.getID(entity));
                list_name.add(entity.getDisplayName().getString());

            }

            NBT.addListMultiple(player_server, "gui", "board_entity_" + tag + "_list_uuid", list_uuid);
            NBT.addListMultiple(player_server, "gui", "board_entity_" + tag + "_list_id", list_id);
            NBT.addListMultiple(player_server, "gui", "board_entity_" + tag + "_list_name", list_name);

        }

        public static void clearEntity (ServerPlayer player_server, String tag) {

            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_uuid", new ArrayList<>());
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_id", new ArrayList<>());
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_name", new ArrayList<>());
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_name_custom", new ArrayList<>());

        }

        public static void removeEntity (ServerLevel level_server, ServerPlayer player_server, String tag) {

            String mode = NBT.getText(player_server, "gui", "board_entity_" + tag + "_remove_mode");

            if (mode.equals("Area") == true) {

                removeEntityScan(level_server, player_server, tag, false);

            } else if (mode.equals("Overall") == true) {

                removeEntityScan(level_server, player_server, tag, true);

            } else if (mode.equals("Selection") == true) {

                removeEntitySelect(player_server, tag);

            }

        }

        private static void removeEntityScan (ServerLevel level_server, ServerPlayer player_server, String tag, boolean is_mode_overall) {

            String target_id = NBT.getText(player_server, "gui", "board_entity_" + tag + "_id");
            String target_name = NBT.getText(player_server, "gui", "board_entity_" + tag + "_name");
            int radius = (int) NBT.getNumber(player_server, "gui", "board_entity_" + tag + "_radius");

            List<String> list_id = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_id");
            List<String> list_name = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_name");
            List<String> list_uuid_new = new ArrayList<>();
            List<String> list_id_new = new ArrayList<>();
            List<String> list_name_new = new ArrayList<>();

            int number = -1;
            String scan_id = "";
            String scan_name = "";
            Entity entity = null;

            for (String scan : NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_uuid")) {

                number = number + 1;
                scan_id = list_id.get(number);
                scan_name = list_name.get(number);

                if (is_mode_overall == true) {

                    {

                        if (target_id.isEmpty() == true || scan_id.equals(target_id) == true) {

                            if (target_name.isEmpty() == true || scan_name.equals(target_name) == true) {

                                continue;

                            }

                        }

                    }

                } else {

                    {

                        entity = EntityManager.getByUUID(level_server, scan);

                        if (entity != null) {

                            if (player_server.position().distanceTo(entity.position()) <= radius) {

                                if (target_id.isEmpty() == true || EntityManager.getID(entity).equals(target_id) == true) {

                                    if (target_name.isEmpty() == true || entity.getDisplayName().getString().equals(target_name) == true) {

                                        continue;

                                    }

                                }

                            }

                        }

                    }

                }

                list_uuid_new.add(scan);
                list_id_new.add(scan_id);
                list_name_new.add(scan_name);

            }

            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_uuid", list_uuid_new);
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_id", list_id_new);
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_name", list_name_new);

        }

        private static void removeEntitySelect (ServerPlayer player_server, String tag) {

            List<String> list_uuid = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_uuid");

            if (list_uuid.isEmpty() == true) {

                return;

            }

            List<String> list_id = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_id");
            List<String> list_name = NBT.getList(player_server, "gui", "board_entity_" + tag + "_list_name");

            int select = (int) NBT.getNumber(player_server, "gui", "board_entity_" + tag + "_select");
            list_uuid.remove(select);
            list_id.remove(select);
            list_name.remove(select);

            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_uuid", list_uuid);
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_id", list_id);
            NBT.setList(player_server, "gui", "board_entity_" + tag + "_list_name", list_name);

        }

    }

}
