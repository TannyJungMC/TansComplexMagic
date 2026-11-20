package tannyjung.tanscomplexmagic_handcode;

import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.game.screen.ScreenDrawing;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

public class HandcodeOverlay {
    
    public static void eventMenu () {



    }

    public static void eventInGame () {

        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.TOP_LEFT, 10, 10);
        boolean have_basic_spell = false;

        // Basic Spells
        {

            String basic_spell = NBTManager.Mob.getText(ScreenDrawing.player_local, "main", "basic_spell");

            if (basic_spell.isEmpty() == false && NBTManager.Mob.getLogic(ScreenDrawing.player_local, "basic_spell", "is_overlay_enable") == true) {

                have_basic_spell = true;

                if (basic_spell.equals("Territory Guardian") == true) {

                    ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§fTertiary Guardian Ammo (" + (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "basic_spell1", "ammo") + ")");

                } else if (basic_spell.equals("Shooting Star") == true) {

                    double capacitor = NBTManager.Mob.getNumber(ScreenDrawing.player_local, "basic_spell2", "capacitor");
                    double level = NBTManager.Mob.getNumber(ScreenDrawing.player_local, "basic_spell2", "level");
                    double remaining_time = 0;

                    if (capacitor >= level) {

                        remaining_time = capacitor / level;

                    }

                    ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§fShooting Star Remaining Time (" + TimeUtils.convertSecondToReadable((long) remaining_time) + ")");

                }

            }

        }

        // Advanced Spells
        {

            String advanced_spell = NBTManager.Mob.getText(ScreenDrawing.player_local, "main", "advanced_spell");

            advanced_spell = "test";

            if (advanced_spell.isEmpty() == false) {

                // Spell 1
                {

                    if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_active") == true && NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_overlay_enable") == true) {

                        if (have_basic_spell == true) {

                            ScreenDrawing.Position.addZ(8);
                            ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§f------------------------------");
                            ScreenDrawing.Position.addZ(8);

                        }

                        ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§fPause All Cards (" + NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_pause_all") + ")");
                        ScreenDrawing.Position.addZ(2);
                        ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§fRadius (" + (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "radius") + ")");
                        ScreenDrawing.Position.addZ(8);

                        // Cards
                        {

                            int empowered_card = (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card");
                            boolean is_empowered_card_negative = NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_empowered_card_negative");
                            StringBuilder status = new StringBuilder();
                            boolean is_high_power = false;
                            String positive_negative = "";
                            String card_status = "";

                            for (int number = 1; number <= 13; number++) {

                                if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_activate" + number) == false) {

                                    continue;

                                }

                                ScreenDrawing.ComponentOverlay.drawImageBasic(12, 12, "tanscomplexmagic:textures/block/zodiac_card" + number + ".png");

                                // Status
                                {

                                    status.setLength(0);
                                    is_high_power = NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_high_power_mode" + number) == true;

                                    if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_negative" + number) == true) {

                                        if (is_high_power == true) {

                                            positive_negative = "§c[--] ";

                                        } else {

                                            positive_negative = "§c[-] ";

                                        }

                                    } else {

                                        if (is_high_power == true) {

                                            positive_negative = "§a[++] ";

                                        } else {

                                            positive_negative = "§a[+] ";

                                        }

                                    }

                                    status.append(positive_negative);
                                    status.append(" ");

                                    if (number == empowered_card) {

                                        status.append("§d");
                                        String symbol = "";

                                        if (is_empowered_card_negative == NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_negative" + number)) {

                                            symbol = "<";

                                        } else {

                                            symbol = ">";

                                        }

                                        status.repeat(symbol, (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card_level"));
                                        status.append(" ").append(TimeUtils.convertSecondToReadable(TimeUtils.getRemain(NBTManager.Mob.getText(ScreenDrawing.player_local, "spell1", "timestamp_next_bonus"))));

                                    }

                                    ScreenDrawing.Position.setMark();
                                    ScreenDrawing.Position.add(10, 2);
                                    ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, status.toString());

                                }

                                ScreenDrawing.Position.addZ(2);
                                String target_duration = "";

                                if (NBTManager.Mob.getText(ScreenDrawing.player_local, "spell1", "card_status" + number).equals("Distributing") == false || NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "number_of_target_detected" + number) == 0) {

                                    target_duration = "0";

                                } else {

                                    target_duration = (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "number_of_target_detected" + number) + " - " + TimeUtils.convertSecondToReadable((long) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "remaining_duration" + number));

                                }

                                ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§f" + target_duration);
                                ScreenDrawing.Position.returnMark();
                                ScreenDrawing.Position.addZ(15);

                            }

                        }

                    }

                }

            }

        }

    }

}
