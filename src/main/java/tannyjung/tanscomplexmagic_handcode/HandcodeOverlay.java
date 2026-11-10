package tannyjung.tanscomplexmagic_handcode;

import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.game.screen.ScreenDrawing;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;

public class HandcodeOverlay {
    
    public static void eventMenu () {



    }

    public static void eventInGame () {

        {

            if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_active") == true && NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_overlay_enable") == true) {

                ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.TOP_LEFT, 10, 50);
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

                        if (NBTManager.Mob.getText(ScreenDrawing.player_local, "spell1", "card_status" + number).equals("effect") == false || NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "number_of_targets_detected" + number) == 0) {

                            target_duration = "0";

                        } else {

                            target_duration = (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "number_of_targets_detected" + number) + " - " + TimeUtils.convertSecondToReadable((long) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "remaining_duration" + number));

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
