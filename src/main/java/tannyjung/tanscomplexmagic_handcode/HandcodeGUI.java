package tannyjung.tanscomplexmagic_handcode;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.game.screen.GUIManager;
import tannyjung.tanscomplexmagic_core.game.screen.ScreenDrawing;
import tannyjung.tanscomplexmagic_core.outside.NetworkManager;
import tannyjung.tanscomplexmagic_core.outside.OutsideUtils;
import tannyjung.tanscomplexmagic_core.outside.TimeUtils;
import tannyjung.tanscomplexmagic_handcode.systems.Mana;
import tannyjung.tanscomplexmagic_handcode.systems.Spell1;
import tannyjung.tanscomplexmagic_handcode.systems.Utils;

public class HandcodeGUI {

    public static void add () {

        GUIManager.add(() -> {

            generateBackground();

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -12 + 0);
            ScreenDrawing.ComponentWidget.Switch.drawText("main", "is_user", "User?");

        });

        GUIManager.add(() -> {

            generateBackground();

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -12 + 0);
            ScreenDrawing.ComponentWidget.Board.drawEntity("Ally List", Utils.Tag.getAlly(ScreenDrawing.player_local), "   Manage list of your ally. Some spell systems can interact with this list, some can skip negative actions on them.");

        });

        // Spell 1
        {

            GUIManager.add(() -> generateWikiCard(1, "Courage and Fear", "drawing_spell1_eyes", new String[]{"   The 1st sign of zodiac. Aries is the ram full of courage. The legend is quite complicated, some is the golden ram sent by gods to save two children. After all, the ram becomes a symbol of courage and sacrifice.","   There is low information to tell what is the true power of this sign. Astral magicians said it can give some of courage to people to do something they are scared to do. Some of them discovered it can reduce their fear of height, or even make them not afraid to do illegal acts.", "After my tests, I can bring one of its power into the card. The power to see in the dark, as I am scared of darkness."}, new String[]{"   The card has ability to give people part of his courage to gains fear of darkness. On the other hand, flipping the card will breaks their wall of mind. Give them the test of Aries to facing the fear of darkness.", "   At the end, I would say that this card should be used in a good way. Not to hurt, oppress, or bully others. Hurting people with magic is also Illegal to the laws of magic."}));
            GUIManager.add(() -> generateWikiCard(2, "Hardiness and Worthlessness", "", new String[]{"   The 2nd sign of zodiac. Taurus is the big strong bull. The legend is that Zeus turned himself into the strong white bull to win the heart of the princess Europa, then carrying her across the sea.", "   Even the history is about love and relationship, but the power of Taurus sign is one of strongest power in the zodiacs. The sign gives power of body protection that can protect the user from almost all physical damages, as though this is the bless of Zeus for the princess Europa.", "   Note that the sign does not giving people body strength, only body protection. Some magicians called it \"Iron Skin\"."}, new String[]{"   The card has ability to share part of Taurus protection to people and other creatures. Can absorbs almost all of incoming damages. While flipping the card not giving them vulnerability, but instead they will receive the feeling of drunken, as if heartbroken.", "   However, this sign seems to use a lot of mana than others, just like how powerful it is."}));
            GUIManager.add(() -> generateWikiCard(3, "Hopeful and Hopeless", "", new String[]{"   The 3rd sign of zodiac. Gemini is the twins. Some history tell that they is Pollux and Castor. One has the bloodline of god, so he is an immortal. But other one is a mortal, since he is human. After Castor died, Pollux begged his father to give Castor immortality.", "   Gemini sign can make people feel of motivation, increasing their strength by no reason. Some astral magicians said that this sign is one of dangerous signs if use it incorrectly. Even it has the power to give people motivation, but can also give the feeling of discouraged and hopeless to some people."}, new String[]{"   The card has ability to give people feeling of bond, motivation, and energy. If flipping, it will make them feel tired and sometime hopeless."}));
            GUIManager.add(() -> generateWikiCard(4, "Luck and Unluck", "drawing_spell1_creature_attraction", new String[]{"   The 4th sign of zodiac. Cancer is the crab. Some history tells that he crushed under the foot of Heracles. Moved by his loyalty, Hera brings him placed in the sky and becomes the Cancer constellation.", "   Many astral magicians said that Cancer is one of weakest signs in the zodiac, with the power of animal attraction. However after my some experiments, I discovered that it might be one of the most powerful sign. While making the card, I found out it is not animal attraction, but luck. Sometimes I am very lucky, sometimes nearly die in accidents. Animals come toward you, because luck changes their destination."}, new String[]{"   The card has ability to control luck. Head up card gives good luck, head down card gives bad luck. While bad luck is opposite of good luck, it does not attract animals, but something from the darkness.", "   I would not recommend to use this power of luck, unless it is necessary. Be careful, the result of luck is unstable. Luck may change to unluck, or just no affect to targets."}));
            GUIManager.add(() -> generateWikiCard(5, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(6, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(7, "Fairness and Unfairness", "drawing_spell1_scales_of_justice", new String[]{"   The 7th sign of zodiac. The scales of justice. The only sign in the zodiac that represented as an object.", "   Libra does not have much information. No one know the true power of it. Some astral magicians discovered that it can detect liars perfectly, some discovered it can change some reality. So they mark this sign as still in dangerous to use, because of not enough information.", "   I bring one of its popular powers into the card. Astral magicians named it \"Fair Fight\", they use it in arenas. It cleanses buffs and debuffs from all fighters in the area."}, new String[]{"   However, big note for the use of Fair Fight. It seems that Libra somehow control over it. While it is in active, other zodiac cards will not work. This seems to be another mysterious power of Libra.", "   Another power I bring into flipped card \"Effect Sharing\". The card will share part of buffs and debuffs from the user to targets. But seems to only work with effects in the type of status, instant effects can not be shared."}));
            GUIManager.add(() -> generateWikiCard(8, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(9, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(10, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(11, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(12, "", "", new String[]{"",""}, new String[]{""}));
            GUIManager.add(() -> generateWikiCard(13, "", "", new String[]{"",""}, new String[]{""}));

            GUIManager.add(() -> {

                generateBackground();

                ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -12 + 0);
                ScreenDrawing.ComponentOverlay.drawTextBasic(1, "Advantage of Nightfall");
                ScreenDrawing.Position.addZ(8);
                ScreenDrawing.ComponentOverlay.drawTextParagraph(160, "   The stars can see clearer in the night. The zodiac is the constellations, they also affected by night time as well. When the sun goes down, the zodiac cards will use lesser mana. Unlike day time, they use more mana as some of their powers was blocked by the sunlight.");

                ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 2 + 0, 8 * -12 + 0);
                ScreenDrawing.ComponentOverlay.drawTextBasic(1, "Empowered Card");
                ScreenDrawing.Position.addZ(8);
                ScreenDrawing.ComponentOverlay.drawTextParagraph(160, "   Every time, the Earth rotates around itself. This changes some of the effects of zodiac signs. Every hour, one of zodiac cards will be empowered. Makes it generate free duration of positive or negative effect.");
                ScreenDrawing.Position.addZ(8);
                ScreenDrawing.ComponentOverlay.drawTextParagraph(160, "   From my tests. Empowered card have about 5 levels. Higher level is the more of duration gain from the generation. Empower levels mostly randomly between 1 to 3, but sometimes rarely, it can get higher to 4 and 5.");

                ScreenDrawing.Position.addZ(8);
                ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "Current Empower");
                ScreenDrawing.Position.addZ(4);
                ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "- Sign : " + Spell1.getNameFromNumber((int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card")));
                ScreenDrawing.Position.addZ(4);
                ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "- Level : " + (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card_level"));
                ScreenDrawing.Position.addZ(4);
                String positive_negative = "";

                if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_empowered_card_negative") == true) {

                    positive_negative = "Negative";

                } else {

                    positive_negative = "Positive";

                }

                ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "- Effect Type : " + positive_negative);
                ScreenDrawing.Position.addZ(4);
                ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "- Remaining Time : " + TimeUtils.convertSecondToReadable(TimeUtils.getRemain(NBTManager.Mob.getText(ScreenDrawing.player_local, "spell1", "timestamp_next_bonus"))));


            });

            GUIManager.add(() -> {

                generateBackground();

                ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -12 + 0);
                ScreenDrawing.ComponentWidget.Board.drawEntity("Target Mark", Utils.Tag.getUser(ScreenDrawing.player_local) + "_spell1_mark", "   You can mark specific targets you want to give effects using this list. Once adding them, you can enable target mode \"Mark\" in the card settings page.");

            });

            GUIManager.add(() -> {

                generateBackground();

                ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -12 + 0);
                ScreenDrawing.ComponentOverlay.drawTextBasic(1, "General Settings");
                ScreenDrawing.Position.addZ(8);
                ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_pause_all", "Pause All Cards");
                ScreenDrawing.Position.addZ(2);
                ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_overlay_enable", "Show Overlay While Active");
                ScreenDrawing.Position.addZ(8);
                ScreenDrawing.ComponentWidget.Slider.drawText(160, 1, 200, 1, "spell1", "radius", "Radius");
                ScreenDrawing.Position.addZ(4);
                ScreenDrawing.ComponentWidget.Slider.drawText(160, 5, 200, 5, "spell1", "radius_touch_ground", "Radius Touch Ground");

                ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * 6 + 5);

                ScreenDrawing.ComponentWidget.ButtonNormal.drawText(160, "Activate Effects", () -> {

                    NetworkManager.runServer("gui", "spell1_activate_effects", new CompoundTag());

                });

                ScreenDrawing.Position.addZ(2);

                ScreenDrawing.ComponentWidget.ButtonNormal.drawText(160, "Deactivate Effects", () -> {

                    NetworkManager.runServer("gui", "spell1_deactivate_effect", new CompoundTag());

                });

                ScreenDrawing.Position.addZ(2);
                ScreenDrawing.Position.setMarkZ();

                ScreenDrawing.ComponentWidget.ButtonNormal.drawText(80, "Activate Cards", () -> {

                    NetworkManager.runServer("gui", "spell1_activate_cards", new CompoundTag());

                });

                ScreenDrawing.Position.returnMarkZ();
                ScreenDrawing.Position.addX(80);

                ScreenDrawing.ComponentWidget.ButtonNormal.drawText(80, "Deactivate Cards", () -> {

                    NetworkManager.runServer("gui", "spell1_deactivate_cards", new CompoundTag());

                });

                generateSpell1SettingsCard(false, "Aries", 1, 60 * 3, 60 * 5);

            });

            GUIManager.add(() -> {

                generateBackground();
                generateSpell1SettingsCard(true, "Taurus", 2, 60 * 1, 60 * 5);
                generateSpell1SettingsCard(false, "Gemini", 3, 60 * 2, 60 * 5);

            });

            GUIManager.add(() -> {

                generateBackground();
                generateSpell1SettingsCard(true, "Cancer", 4, 60 * 3, 60 * 3);
                generateSpell1SettingsCard(false, "Leo", 5, 60 * 1, 60 * 4);

            });

            GUIManager.add(() -> {

                generateBackground();
                generateSpell1SettingsCard(true, "Virgo", 6, 60 * 1, 60 * 2);
                generateSpell1SettingsCard(false, "Libra", 7, 60 * 1, 60 * 1);

            });

            GUIManager.add(() -> {

                generateBackground();
                generateSpell1SettingsCard(true, "Scorpio", 8, 60 * 3, 60 * 2);
                generateSpell1SettingsCard(false, "Sagittarius", 9, 60 * 2, 60 * 4);

            });

            GUIManager.add(() -> {

                generateBackground();
                generateSpell1SettingsCard(true, "Capricorn", 10, 60 * 1, 60 * 3);
                generateSpell1SettingsCard(false, "Aquarius", 11, 60 * 2, 60 * 3);

            });

            GUIManager.add(() -> {

                generateBackground();
                generateSpell1SettingsCard(true, "Pisces", 12, 60 * 2, 60 * 3);
                generateSpell1SettingsCard(false, "Ophiuchus", 13, 60 * 1, 60 * 1);

            });

            GUIManager.add(() -> {

                generateBackground();

            });

        }

    }
    
    private static void generateBackground () {

        int id = (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "gui", "id");

        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -25 + 0, 8 * -15 + 0);
        ScreenDrawing.ComponentOverlay.drawImageBasic(400, 240, "tanscomplexmagic:textures/screens/book/background.png");
        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 1 + 0, 8 * 13 + 6);
        ScreenDrawing.ComponentOverlay.drawImageSplit(168, 7, true, 24, (int) ((ScreenDrawing.player_local.clientLevel.dayTime() % 24000) / 1000), "tanscomplexmagic:textures/screens/book/clock.png");
        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 25 + 0, 8 * 15 + 0);

        ScreenDrawing.ComponentWidget.ButtonImage.draw(16, 8, true, true, false, "tanscomplexmagic:textures/screens/book/close.png", () -> {

            NetworkManager.runServer("gui", "book_close", new CompoundTag());

        });

        // Page Number
        {

            StringBuilder page_left = new StringBuilder().append(id);
            StringBuilder page_right = new StringBuilder().append(id + 1);

            while (page_left.length() < 3) {

                page_left.insert(0, "0");

            }

            while (page_right.length() < 3) {

                page_right.insert(0, "0");

            }

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -21 + 1, 8 * 12 + 0);
            ScreenDrawing.ComponentOverlay.drawTextBasic(0.5, page_left.toString());
            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 19 + 6, 8 * 12 + 0);
            ScreenDrawing.ComponentOverlay.drawTextBasic(0.5, page_right.toString());

        }

        // Page Turn
        {

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -23 - 4, 8 * 11 + 4);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(32, 16, true, true, false, "tanscomplexmagic:textures/screens/book/page_previous.png", () -> {

                NetworkManager.runServer("gui", "book_page_previous", new CompoundTag());

            });

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 22 - 4, 8 * 11 + 4);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(32, 16, true, true, false, "tanscomplexmagic:textures/screens/book/page_next.png", () -> {

                NetworkManager.runServer("gui", "book_page_next", new CompoundTag());

            });

        }

        // Compass
        {

            Vec3 destination_pos = new Vec3(0, 0, 0);
            int degree = 0;

            destination_pos = ScreenDrawing.player_local.clientLevel.getSharedSpawnPos().getCenter();
            degree = (int) OutsideUtils.Calculation.getDegreeDestinationX(ScreenDrawing.player_local.getYRot(), ScreenDrawing.player_local.getX(), ScreenDrawing.player_local.getZ(), destination_pos.x, destination_pos.z);
            degree = (int) Math.round(degree / 45.0);

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 2 - 0, 8 * 13 + 6);
            ScreenDrawing.ComponentOverlay.drawImageSplit(56, 7, true, 8, degree, "tanscomplexmagic:textures/screens/book/compass.png");

            destination_pos = new Vec3(NBTManager.Mob.getNumber(ScreenDrawing.player_local, "book", "compassX"), 0, NBTManager.Mob.getNumber(ScreenDrawing.player_local, "book", "compassZ"));
            degree = (int) OutsideUtils.Calculation.getDegreeDestinationX(ScreenDrawing.player_local.getYRot(), ScreenDrawing.player_local.getX(), ScreenDrawing.player_local.getZ(), destination_pos.x, destination_pos.z);
            degree = (int) Math.round(degree / 45.0);

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 3 - 0, 8 * 13 + 6);
            ScreenDrawing.ComponentOverlay.drawImageSplit(56, 7, true, 8, degree, "tanscomplexmagic:textures/screens/book/compass_custom.png");
            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 4 + 0, 8 * 13 + 8);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(6, 3, true, true, false, "tanscomplexmagic:textures/screens/book/compass_custom_set.png", () -> {

                NetworkManager.runServer("gui", "book_compass_custom_set", new CompoundTag());

            });

        }

        // Log
        {

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * 13 + 8);
            ScreenDrawing.ComponentOverlay.drawTextBasic(0.48, NBTManager.Mob.getText(ScreenDrawing.player_local, "book", "log_text"));

            int log = 0;

            if (NBTManager.Mob.getText(ScreenDrawing.player_local, "book", "log").equals("mana") == true) {

                log = 1;

            } else if (NBTManager.Mob.getText(ScreenDrawing.player_local, "book", "log").equals("action") == true) {

                log = 2;

            } else if (NBTManager.Mob.getText(ScreenDrawing.player_local, "book", "log").equals("warning") == true) {

                log = 3;

            }

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -23 - 4, 8 * 13 + 6);
            ScreenDrawing.ComponentOverlay.drawImageSplit(28, 7, true, 4, log, "tanscomplexmagic:textures/screens/book/log_type.png");
            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -23 - 4, 8 * 13 + 6);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(14, 7, true, true, false, "tanscomplexmagic:textures/screens/book/log_type_set.png", () -> {

                NetworkManager.runServer("gui", "book_log", new CompoundTag());

            });

        }

        // Bookmark
        {

            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -27 + 0, 8 * -12 + 0);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_home.png", () -> {

                NetworkManager.runServer("gui", "book_bookmark_home", new CompoundTag());

            });
            ScreenDrawing.Position.addZ(2);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_ally_list.png", () -> {

                NetworkManager.runServer("gui", "book_bookmark_ally_list_manager", new CompoundTag());

            });

            ScreenDrawing.Position.addZ(2);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_basic_spells.png", () -> {

                NetworkManager.runServer("gui", "book_bookmark_basic_spell", new CompoundTag());

            });

            ScreenDrawing.Position.addZ(2);

            ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_spell_control.png", () -> {

                NetworkManager.runServer("gui", "book_bookmark_spell1_settings", new CompoundTag());

            });

            // Custom
            {

                ScreenDrawing.Position.addZ(16);

                ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_custom.png", () -> {

                    NetworkManager.runServer("gui", "book_bookmark_custom1", new CompoundTag());

                });

                ScreenDrawing.Position.setMarkX();
                ScreenDrawing.Position.add(19, 2);

                ScreenDrawing.ComponentWidget.ButtonImage.draw(8, 4, true, true, false, "tanscomplexmagic:textures/screens/book/bookmark_custom_set.png", () -> {

                    NetworkManager.runServer("gui", "book_bookmark_custom_set1", new CompoundTag());

                });

                ScreenDrawing.Position.returnMarkX();
                ScreenDrawing.Position.addZ(2);

                ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_custom.png", () -> {

                    NetworkManager.runServer("gui", "book_bookmark_custom2", new CompoundTag());

                });

                ScreenDrawing.Position.setMarkX();
                ScreenDrawing.Position.add(19, 2);

                ScreenDrawing.ComponentWidget.ButtonImage.draw(8, 4, true, true, false, "tanscomplexmagic:textures/screens/book/bookmark_custom_set.png", () -> {

                    NetworkManager.runServer("gui", "book_bookmark_custom_set2", new CompoundTag());

                });

                ScreenDrawing.Position.returnMarkX();
                ScreenDrawing.Position.addZ(2);

                ScreenDrawing.ComponentWidget.ButtonImage.draw(24, 16, false, true, false, "tanscomplexmagic:textures/screens/book/bookmark_custom.png", () -> {

                    NetworkManager.runServer("gui", "book_bookmark_custom3", new CompoundTag());

                });

                ScreenDrawing.Position.add(19, 2);

                ScreenDrawing.ComponentWidget.ButtonImage.draw(8, 4, true, true, false, "tanscomplexmagic:textures/screens/book/bookmark_custom_set.png", () -> {

                    NetworkManager.runServer("gui", "book_bookmark_custom_set3", new CompoundTag());

                });

            }

        }

        // Mana Bar
        {

            int[] mana = Mana.get(ScreenDrawing.player_local);
            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 24 + 3, 8 * -14 + 4);
            ScreenDrawing.ComponentOverlay.drawImageSplit(151, 150, true, 151, mana[0], "tanscomplexmagic:textures/screens/book/mana_bar.png");
            ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 24 + 3, 8 * 5 + 6);
            ScreenDrawing.ComponentOverlay.drawImageSplit(61, 60, true, 61, mana[1], "tanscomplexmagic:textures/screens/book/mana_bar_charge.png");

        }

    }
    
    private static void generateWikiCard (int number, String caption, String drawing, String[] description_left, String[] description_right) {

        generateBackground();

        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -12 + 0);
        ScreenDrawing.ComponentOverlay.drawImageBasic(20, 36, "tanscomplexmagic:textures/screens/background.png");
        ScreenDrawing.Position.add(2, 2);
        ScreenDrawing.ComponentOverlay.drawImageBasic(32, 32, "tanscomplexmagic:textures/block/zodiac_card" + number + ".png");
        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -18 + 0, 8 * -10 - 3);
        ScreenDrawing.ComponentOverlay.drawTextCenteredBasic(true, 1, Spell1.getNameFromNumber(number));
        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -18 + 0, 8 * -9 - 1);
        ScreenDrawing.ComponentOverlay.drawTextCenteredBasic(true, ScreenDrawing.DEFAULT_FONT_SCALE, caption);

        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * -22 + 0, 8 * -6 + 0);

        for (String scan : description_left) {

            ScreenDrawing.ComponentOverlay.drawTextParagraph(160, scan);
            ScreenDrawing.Position.addZ(8);

        }

        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, 8 * 2 + 0, 8 * -12 + 0);

        for (String scan : description_right) {

            ScreenDrawing.ComponentOverlay.drawTextParagraph(160, scan);
            ScreenDrawing.Position.addZ(8);

        }

        if (drawing.isEmpty() == false) {

            ScreenDrawing.Position.addZ(8);
            ScreenDrawing.ComponentOverlay.drawImageBasic(160, 80, "tanscomplexmagic:textures/screens/book/" + drawing + ".png");

        }

    }

    private static void generateSpell1SettingsCard (boolean is_page_left, String name, int number, int duration_per_mana_positive, int duration_per_mana_negative) {

        int mana = Mana.get(ScreenDrawing.player_local)[0];
        int posX = 0;

        if (is_page_left == true) {

            posX = 8 * -22 + 0;

        } else {

            posX = 8 * 2 + 0;

        }

        ScreenDrawing.Position.set(ScreenDrawing.Anchor.Type.CENTER, posX, 8 * -12 + 0);
        ScreenDrawing.ComponentOverlay.drawTextBasic(1, "Card Settings : " + name);
        ScreenDrawing.Position.addZ(8);
        ScreenDrawing.ComponentOverlay.drawTextBasic( ScreenDrawing.DEFAULT_FONT_SCALE, "Detected Targets : " + (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "number_of_targets_detected" + number));
        ScreenDrawing.Position.addZ(4);
        ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "Remaining Duration : " + TimeUtils.convertSecondToReadable((long) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "remaining_duration" + number)));
        ScreenDrawing.Position.addZ(8);
        ScreenDrawing.Position.setMarkZ();
        ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_activate" + number, "Activate Card");
        ScreenDrawing.Position.addZ(2);
        ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_activate_effect" + number, "Activate Effect");
        ScreenDrawing.Position.returnMarkZ();
        ScreenDrawing.Position.setMarkX();
        ScreenDrawing.Position.addX(80);
        ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_negative" + number, "Flip Card");
        ScreenDrawing.Position.addZ(2);
        ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_high_power_mode" + number, "High Power Mode");
        ScreenDrawing.Position.returnMarkX();

        // Targets
        {

            ScreenDrawing.Position.addZ(8);
            ScreenDrawing.ComponentOverlay.drawTextBasic(ScreenDrawing.DEFAULT_FONT_SCALE, "Targets");
            ScreenDrawing.Position.addZ(4);
            ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_target_user" + number, "User");
            ScreenDrawing.Position.addZ(2);
            ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_target_ally_player" + number, "Ally Player");
            ScreenDrawing.Position.addZ(2);
            ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_target_ally_non_player" + number, "Ally Non-Player");
            ScreenDrawing.Position.addZ(2);
            ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_target_enemy_player" + number, "Enemy Player");
            ScreenDrawing.Position.addZ(2);
            ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_target_enemy_non_player" + number, "Enemy Non-Player");
            ScreenDrawing.Position.addZ(2);
            ScreenDrawing.ComponentWidget.Switch.drawText("spell1", "is_card_target_mark" + number, "Mark");

        }

        // Duration
        {

            String empowered_positive = "";
            String empowered_negative = "";

            if (number == NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card")) {

                int level = (int) NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "empowered_card_level");
                String text = "§7(+" + level + " Empower)";

                if (NBTManager.Mob.getLogic(ScreenDrawing.player_local, "spell1", "is_empowered_card_negative") == true) {

                    empowered_negative = text;

                } else {

                    empowered_positive = text;

                }

            }

            ScreenDrawing.Position.setZ(26);
            ScreenDrawing.ComponentWidget.Slider.drawTextWithSuffix(160, 0, NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "card_duration_max_positive" + number), 1, "spell1", "card_duration_positive" + number, "Positive Duration", empowered_positive);
            ScreenDrawing.Position.addZ(2);

            ScreenDrawing.ComponentWidget.ButtonNormal.drawTextLockable(160, true, mana < 1, "+" + duration_per_mana_positive + " / 1 Mana", "+" + duration_per_mana_positive + " / 1 Mana", () -> {

                CompoundTag tag = new CompoundTag();
                tag.putInt("number", number);
                tag.putString("positive_negative", "positive");
                tag.putInt("duration_per_mana", duration_per_mana_positive);
                NetworkManager.runServer("gui", "spell1_buy_duration", tag);

            });

            ScreenDrawing.Position.addZ(8);
            ScreenDrawing.ComponentWidget.Slider.drawTextWithSuffix(160, 0, NBTManager.Mob.getNumber(ScreenDrawing.player_local, "spell1", "card_duration_max_negative" + number), 1, "spell1", "card_duration_negative" + number, "Negative Duration", empowered_negative);
            ScreenDrawing.Position.addZ(2);

            ScreenDrawing.ComponentWidget.ButtonNormal.drawTextLockable(160, true, mana < 1, "+" + duration_per_mana_negative + " / 1 Mana)", "+" + duration_per_mana_negative + " / 1 Mana", () -> {

                CompoundTag tag = new CompoundTag();
                tag.putInt("number", number);
                tag.putString("positive_negative", "negative");
                tag.putInt("duration_per_mana", duration_per_mana_positive);
                NetworkManager.runServer("gui", "spell1_buy_duration", tag);

            });

        }

    }

}
