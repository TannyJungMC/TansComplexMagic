package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.world.entity.player.Player;
import tannyjung.tanscomplexmagic_core.Core;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static boolean isUser (Player player) {

        return player.getTags().contains(Utils.Tag.getUser());

    }

    public static class Tag {

        private static int getPlayerID (Player player) {

            return player.getUUID().hashCode();

        }

        public static String getUser () {

            return Core.mod_id_big + "-user";

        }

        public static String getAlly (Player player) {

            return Core.mod_id_big + "-ally-" + getPlayerID(player);

        }

        public static String getEnemy (Player player) {

            return "!" + getAlly(player);

        }

        public static String getSystemGeneral (String tag) {

            return Core.mod_id_big + "-" + tag;

        }

        public static String getSystemUser (Player player, String tag) {

            return getSystemGeneral(tag) + "-" + getPlayerID(player);

        }

        public static String[] convertSystem (Player player, String[] tags) {

            List<String> list = new ArrayList<>();
            list.add(Core.mod_id_big + "-system-" + getPlayerID(player));

            for (String scan : tags) {

                list.add(getSystemGeneral(scan));
                list.add(getSystemUser(player, scan));

            }

            return list.toArray(new String[]{});

        }

    }

}
