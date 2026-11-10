package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

public class Mana {

    public static int[] get (Player player) {

        String timestamp = NBTManager.Mob.getText(player, "main", "timestamp_mana");

        if (timestamp.equals("0") == true) {

            return new int[]{150, 0};

        }

        long test = TimeUtils.combineTime(0, 150, 0) - TimeUtils.getRemain(timestamp);
        return new int[]{Math.toIntExact(test / 60), Math.toIntExact(test % 60)};

    }

    public static boolean spend (ServerPlayer player_server, int amount) {

        int[] mana = get(player_server);

        if (mana[0] < amount) {

            return false;

        }

        String timestamp_mana = "";

        if (mana[0] == 150) {

            timestamp_mana = TimeUtils.getTimestampCurrent();

        } else {

            timestamp_mana = NBTManager.Mob.getText(player_server, "main", "timestamp_mana");

        }

        NBTManager.Mob.setText(player_server, "main", "timestamp_mana", TimeUtils.addTimeToTimestamp(timestamp_mana, 0, amount, 0), true);
        return true;

    }

    public static void setFull (ServerPlayer player_server) {

        NBTManager.Mob.setText(player_server, "main", "timestamp_mana", TimeUtils.getTimestampCurrent(), true);

    }

}
