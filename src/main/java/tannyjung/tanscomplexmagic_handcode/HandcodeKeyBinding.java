package tannyjung.tanscomplexmagic_handcode;

import net.minecraft.nbt.CompoundTag;
import org.lwjgl.glfw.GLFW;
import tannyjung.tanscomplexmagic_core.game.*;
import tannyjung.tanscomplexmagic_core.outside.NetworkManager;

public class HandcodeKeyBinding {

    public static void add () {

        // Main
        {

            KeyBindingMaker.Create.keyPushRelease("Main Key", GLFW.GLFW_KEY_LEFT_ALT, () -> {

                NetworkManager.runServer("key", "main_push", new CompoundTag());

            }, () -> {

                NetworkManager.runServer("key", "main_release", new CompoundTag());

            });

        }

        // Spell 1
        {

            KeyBindingMaker.Create.keyPush("Spell 1 : Activate", GLFW.GLFW_KEY_1, () -> {

                NetworkManager.runServer("key", "spell1_activate", new CompoundTag());

            });

            KeyBindingMaker.Create.keyPush("Spell 1 : Pause All Cards", GLFW.GLFW_KEY_2, () -> {

                NetworkManager.runServer("key", "spell1_pause_all", new CompoundTag());

            });

            KeyBindingMaker.Create.keyPushRelease("Spell 1 : Radius Decrease", GLFW.GLFW_KEY_3, () -> {

                NetworkManager.runServer("key", "spell1_radius_decrease", new CompoundTag());

            }, () -> {

                NetworkManager.runServer("key", "spell1_radius_stop", new CompoundTag());

            });

            KeyBindingMaker.Create.keyPushRelease("Spell 1 : Radius Increase", GLFW.GLFW_KEY_4, () -> {

                NetworkManager.runServer("key", "spell1_radius_increase", new CompoundTag());

            }, () -> {

                NetworkManager.runServer("key", "spell1_radius_stop", new CompoundTag());

            });

        }

    }

}
