package tannyjung.tanscomplexmagic_core.outside;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.game.screen.ScreenDrawing;

public class NetworkBuiltInClient {

    public static void run (Player player, String category, String name, CompoundTag extra) {

        LocalPlayer player_local = (LocalPlayer) player;

        if (category.equals("gui") == true) {

            {

                if (name.equals("refresh") == true) {

                    ScreenDrawing.refresh = true;

                }

            }

        } else if (category.equals("nbt") == true) {

            {

                if (name.equals("sync") == true) {

                    NBTManager.Mob.merge(player_local, extra, false);

                }

            }

        }

    }

}
