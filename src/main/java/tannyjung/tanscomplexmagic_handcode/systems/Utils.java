package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.game.NBTManager;

public class Utils {

    public static class Tag {

        public static String getUser (Player player) {

            String mod = Core.mod_id_big;
            String uuid = player.getUUID().toString();

            return mod + "-user-" + uuid;

        }

        public static String getAlly (Player player) {

            String mod = Core.mod_id_big;
            String uuid = player.getUUID().toString();

            return mod + "-ally-" + uuid;

        }

        public static String[] convertSystemAll (Player player) {

            return new String[]{Core.mod_id_big, getUser(player)};

        }

        public static String[] convertSystemSpecific (Player player, String[] tags) {

            String[] convert = new String[tags.length + 1];
            convert[0] = getUser(player);

            for (int number = 0; number < tags.length; number++) {

                convert[number + 1] = Core.mod_id_big + "-" + tags[number];

            }

            return convert;

        }

        public static String[] convertAlly (Player player) {

            return new String[]{getAlly(player)};

        }

        public static String[] convertEnemy (Player player) {

            return new String[]{"!" + Core.mod_id_big, "!" + getUser(player), "!" + getAlly(player)};

        }

    }

}
