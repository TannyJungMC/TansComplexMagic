package tannyjung.tanscomplexmagic_handcode.systems;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

public class Mana {

    public static void update (ServerPlayer player_server) {

        int mana = (int) NBTManager.Mob.getNumber(player_server, "main", "mana");

        if (mana == 150) {

            return;

        }

        long remain = TimeUtils.getRemain(NBTManager.Mob.getText(player_server, "main", "timestamp_mana"));
        int mana_charge = 0;

        if (remain == 0) {

            setFull(player_server);

        } else {

            long test = TimeUtils.combineTime(0, 150, 0) - TimeUtils.getRemain(NBTManager.Mob.getText(player_server, "main", "timestamp_mana"));
            mana = Math.toIntExact(test / 60);
            mana_charge = Math.toIntExact(test % 60);
            NBTManager.Mob.setNumber(player_server, "main", "mana", mana, true);
            NBTManager.Mob.setNumber(player_server, "main", "mana_charge", mana_charge, true);

        }

    }

    public static boolean spend (ServerPlayer player_server, int amount) {

        int mana = (int) NBTManager.Mob.getNumber(player_server, "main", "mana");

        if (mana < amount) {

            return false;

        }

        NBTManager.Mob.addNumber(player_server, "main", "mana", -amount, true);
        String timestamp_mana = "";

        if (mana == 150) {

            timestamp_mana = TimeUtils.getTimestampCurrent();

        } else {

            timestamp_mana = NBTManager.Mob.getText(player_server, "main", "timestamp_mana");

        }

        NBTManager.Mob.setText(player_server, "main", "timestamp_mana", TimeUtils.addTimeToTimestamp(timestamp_mana, 0, amount, 0), true);
        return true;

    }

    public static void setFull (ServerPlayer player_server) {

        NBTManager.Mob.setNumber(player_server, "main", "mana", 150, true);
        NBTManager.Mob.setNumber(player_server, "main", "mana_charge", 0, true);
        NBTManager.Mob.setText(player_server, "main", "timestamp_mana", TimeUtils.getTimestampCurrent(), true);

    }

}
