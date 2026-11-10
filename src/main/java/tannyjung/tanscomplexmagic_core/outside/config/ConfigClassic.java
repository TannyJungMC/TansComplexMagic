package tannyjung.tanscomplexmagic_core.outside.config;

import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.outside.FileManager;

import java.util.*;

public class ConfigClassic {

	public static void repair (String original) {

        if (Core.have_custom_pack == true) {

            original = """
                    ----------------------------------------------------------------------------------------------------
                    Main Pack
                    ----------------------------------------------------------------------------------------------------
                    
                    main_pack_auto_check_update = true
                    | Check for new update from GitHub every time the world starts
                    
                    main_pack_wip = false
                    | Use development version of the pack instead of release version. Not recommended for game play, as it's still in development, it might unstable. Sometimes it needed development version of the mod.
                    
                    """ + original;

        }

        original = original + """
                ----------------------------------------------------------------------------------------------------
                Miscellaneous
                ----------------------------------------------------------------------------------------------------
            
                developer_mode = false
                | Enable some features for debugging
            
                ----------------------------------------------------------------------------------------------------
                """;

        Map<String, String> old_values = new HashMap<>();
        Map<String, Boolean> keep = new HashMap<>();

        // Get Old Values
        {

            List<String> options = new ArrayList<>();
            List<String> values = new ArrayList<>();
            List<String> defaults = new ArrayList<>();

            // Get
            {

                String[] split = null;

                for (String scan : FileManager.readTXT(Core.path_folder_config + "/config.txt")) {

                    if (scan.contains(" = ") == true) {

                        split = scan.split(" = ");
                        options.add(split[0]);
                        values.add(split[1]);
                        old_values.put(split[0], split[1]);

                    } else if (scan.startsWith("| Default is ") == true) {

                        scan = scan.substring("| Default is ".length());
                        scan = scan.substring(2, scan.length() - 2);
                        defaults.addAll(Arrays.asList(scan.split(" ] \\[ ")));

                        while (defaults.size() > values.size()) {

                            options.add("");
                            values.add("");

                        }

                    }

                }

            }

            // Test Keep
            {

                boolean test = false;
                String get = "";

                for (int loop = 0; loop < options.size(); loop++) {

                    if (defaults.isEmpty() == false) {

                        get = defaults.get(loop);

                    }

                    if (get.isEmpty() == true) {

                        test = false;

                    } else {

                        test = values.get(loop).equals(get) == false;

                    }

                    keep.put(options.get(loop), test);

                }

            }

        }

        StringBuilder write = new StringBuilder();
        write.append("Important Notes");
        write.append("\n");
        write.append("\n");
        write.append("- To apply this config and repair missing values, run this command [ /").append(Core.mod_id_big).append(" restart ] or restart the world.");
        write.append("\n");
        write.append("- This config can be automatic repair itself to keep your unchanged values up to date, so do not panic when you see option values change by themselves!");
        write.append("\n");
        write.append("\n");

        if (original.isEmpty() == false) {

            // Generate
            {

                String option = "";
                String value = "";
                String[] split = null;
                List<String> written_values = new ArrayList<>();

                for (String scan : original.split("\\n")) {

                    if (scan.startsWith("|") == false && scan.contains(" = ") == true) {

                        // Write Normal
                        {

                            split = scan.split(" = ");
                            option = split[0];
                            value = split[1];

                            if (old_values.containsKey(option) == true) {

                                if (keep.get(option) == true) {

                                    write.append(option).append(" = ").append(old_values.get(option));

                                } else {

                                    write.append(scan);

                                }

                            } else {

                                write.append(scan);

                            }

                            written_values.add(value);

                        }

                    } else if (scan.isEmpty() == true && written_values.size() > 0) {

                        // Write Default
                        {

                            write.append(scan).append("| Default is");

                            for (String get : written_values) {

                                write.append(" [ ").append(get).append(" ]");

                            }

                            write.append("\n");
                            written_values.clear();

                        }

                    } else {

                        write.append(scan);

                    }

                    write.append("\n");

                }

            }

            write.append("\n");
            write.append("\n");

        }

        FileManager.writeTXT(Core.path_folder_config + "/config.txt", write.toString(), false);

	}

    public static Map<String, String> getValues (String path) {

        return FileManager.readTXTAsMap(path);

    }

}