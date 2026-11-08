package tannyjung.tanscomplexmagic_handcode;

import net.minecraft.server.level.ServerLevel;
import tannyjung.tanscomplexmagic_core.Core;

import java.util.Map;

public class Handcode {

    public static void sync () {

        Core.sync(

                "Tan's Complex Magic",
                "tanscomplexmagic",
                "M",
                "",
                "",
                false,
                false,

                1,
                "",
                "",
                ""

        );

    }

    public static void organizeCustomPack () {



    }

    public static void repairData (ServerLevel level_server) {



    }

    public static class Config {

        public static void repair (String start, String end) {



        }

        public static void apply (Map<String, String> data) {



        }

    }

    public static class DataMigration {

        public static void runConfig (String version) {



        }

        public static void runWorld (String version) {



        }

    }

}
