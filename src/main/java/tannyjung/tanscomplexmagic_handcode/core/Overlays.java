package tannyjung.tanscomplexmagic_handcode.core;

import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.game.screen.ScreenDrawing;
import tannyjung.tanscomplexmagic_core.outside.OutsideUtils;

public class Overlays {
    
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
                    String positive_negative = "";
                    String card_status = "";

                    for (int number = 1; number <= 13; number++) {

                        if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_enable" + number) == false) {

                            continue;

                        }

                        ScreenDrawing.ComponentOverlay.drawImageBasic(12, 12, "tanscomplexmagic:textures/block/zodiac_card" + number + ".png");

                        // Status
                        {

                            status.setLength(0);

                            if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_negative" + number) == true) {

                                positive_negative = "§c[-] ";

                            } else {

                                positive_negative = "§a[+] ";

                            }

                            status.append(positive_negative);
                            status.append(" ");

                            if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_active" + number) == true) {

                                card_status = NBTManager.Mob.getText(ScreenDrawing.player_local, "spell1", "card_status" + number);

                                if (card_status.equals("effect") == true) {

                                    status.append("§b[O] ");

                                } else if (card_status.equals("flip") == true) {

                                    status.append("§6[>] ");

                                } else {

                                    status.append("§7[=] ");

                                }

                            }

                            if (number == empowered_card && is_empowered_card_negative == NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_card_negative" + number)) {

                                status.append("§d");
                                status.repeat("<> ", (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card_level"));

                            }

                            ScreenDrawing.Position.setMark();
                            ScreenDrawing.Position.add(10, 2);
                            ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, status.toString());

                        }

                        ScreenDrawing.Position.addZ(2);
                        ScreenDrawing.ComponentOverlay.drawTextBasic(0.4, "§f" + (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "number_of_targets_detected" + number) + " > " + OutsideUtils.Calculation.convertSecondToTime((int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "remaining_duration" + number)));
                        ScreenDrawing.Position.returnMark();
                        ScreenDrawing.Position.addZ(15);

                    }

                }

            }

        }

    }

}
