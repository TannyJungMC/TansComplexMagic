package tannyjung.tanscomplexmagic_core.game.world_gen;

import net.minecraft.world.level.ChunkPos;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.outside.FileManager;
import tannyjung.tanscomplexmagic_handcode.HandcodeWorldGen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldGenStepEnd {

    public static void start (String dimension, ChunkPos chunk_pos) {

        if (Core.have_world_data == true) {

            String path_suffix = dimension + "/" + (chunk_pos.x >> 5) + "," + (chunk_pos.z >> 5) + ".bin";

            File file = new File(Core.path_folder_world_mod + "/world_gen/regions/" + path_suffix);
            List<String> test = new ArrayList<>();
            test.add("b0");
            FileManager.writeBIN(file.getPath(), test, true);

            if (FileManager.readBIN(file.getPath()).capacity() >= 1024) {

                test.clear();
                test.add("b0");
                FileManager.writeBIN(file.getPath(), test, false);
                HandcodeWorldGen.finishRegion(dimension, chunk_pos);

            }

        }

    }

}