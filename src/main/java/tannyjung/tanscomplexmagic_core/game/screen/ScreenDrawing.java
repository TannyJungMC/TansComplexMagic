package tannyjung.tanscomplexmagic_core.game.screen;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.game.NBTManager;
import tannyjung.tanscomplexmagic_core.outside.NetworkManager;
import tannyjung.tanscomplexmagic_core.outside.OutsideUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.*;

public class ScreenDrawing {

    public static final double DEFAULT_FONT_SCALE = 0.67;

    public static GUIScreen screen = null;
    public static GuiGraphics graphic = null;
    public static LocalPlayer player_local = null;
    public static boolean refresh = false;

    public static void refresh () {

        if (refresh == false) {

            return;

        } else {

            refresh = false;

        }

        int id = (int) NBTManager.Mob.getNumber(player_local, "gui", "id");

        if (GUIManager.client_current_id != id) {

            GUIManager.client_current_id = id;
            GUIScreen.Widget.removeAll();

            ComponentWidget.Shape.map.clear();
            ComponentWidget.ButtonNormal.map.clear();
            ComponentWidget.ButtonImage.map.clear();
            ComponentWidget.TextBox.map.clear();
            ComponentWidget.Slider.map.clear();
            ComponentWidget.Radio.set.clear();

        }

        ComponentOverlay.Storage.clear();
        GUIManager.draw(id);

    }

    public static class Position {

        private static int posX = 0;
        private static int posZ = 0;
        private static int markX = 0;
        private static int markZ = 0;

        public static void set (Anchor.Type type, int posX, int posZ) {

            Anchor.set(type);
            setX(posX);
            setZ(posZ);

        }

        public static void setX (int posX) {

            Position.posX = posX;

        }

        public static void setZ (int posZ) {

            Position.posZ = posZ;

        }

        public static void add (int sizeX, int sizeZ) {

            addX(sizeX);
            addZ(sizeZ);

        }

        public static void addX (int size) {

            posX = posX + size;

        }

        public static void addZ (int size) {

            posZ = posZ + size;

        }

        public static void setMark () {

            setMarkX();
            setMarkZ();

        }

        public static void setMarkX () {

            markX = posX;

        }

        public static void setMarkZ () {

            markZ = posZ;

        }

        public static void returnMark () {

            returnMarkX();
            returnMarkZ();

        }

        public static void returnMarkX () {

            posX = markX;

        }

        public static void returnMarkZ () {

            posZ = markZ;

        }

    }

    public static class Anchor {

        private static Type type = Type.CENTER;
        private static int at_screenX = 0;
        private static int at_screenZ = 0;
        private static int at_graphicX = 0;
        private static int at_graphicZ = 0;

        public enum Type {

            CENTER,
            TOP_LEFT,
            CENTER_LEFT

        }

        private static void set (Type type) {

            Anchor.type = type;

            if (screen != null) {

                at_screenX = screen.width;
                at_screenZ = screen.height;

            }

            at_graphicX = graphic.guiWidth();
            at_graphicZ = graphic.guiHeight();

            if (type == Type.CENTER) {

                at_screenX = at_screenX / 2;
                at_screenZ = at_screenZ / 2;
                at_graphicX = at_graphicX / 2;
                at_graphicZ = at_graphicZ / 2;

            } else if (type == Type.TOP_LEFT) {

                at_screenX = 0;
                at_screenZ = 0;
                at_graphicX = 0;
                at_graphicZ = 0;

            } else if (type == Type.CENTER_LEFT) {

                at_screenX = 0;
                at_screenZ = at_screenZ / 2;
                at_graphicX = 0;
                at_graphicZ = at_graphicZ / 2;

            }

        }

        public static int[] getWindowPoint (int windowX, int windowZ, boolean is_top, boolean is_bottom, boolean is_left, boolean is_right) {

            windowX = windowX / 2;
            windowZ = windowZ / 2;

            if (is_top == true && is_bottom == false) {

                if (is_left == true && is_right == false) {

                    return new int[]{-windowX, -windowZ};

                } else if (is_left == false && is_right == true) {

                    return new int[]{windowX, -windowZ};

                } else {

                    return new int[]{(int) (windowX * 1.5), (int) (windowZ * 1.5)};

                }

            }

            return new int[]{0, 0};

        }

        public static int[] convertPosCenter (int centerX, int centerZ, int sizeX, int sizeZ) {

            return new int[]{centerX - sizeX, centerZ - sizeZ};

        }

        private static int[] convertPosCenterText (int centerX, int centerZ, double scale, String text) {

            int sizeX = Minecraft.getInstance().font.width(text) / 2;
            int sizeZ = (Minecraft.getInstance().font.lineHeight - 1) / 2;
            sizeX = (int) Math.round(sizeX * scale);
            sizeZ = (int) Math.round(sizeZ * scale);
            return Anchor.convertPosCenter(centerX, centerZ, sizeX, sizeZ);

        }

    }

    public static class ComponentOverlay {

        private static final Map<String, String> online_image_id = new HashMap<>();
        private static final Map<String, String> online_image_status = new HashMap<>();

        private static void drawText (double scale, boolean shadow, String text) {

            int posX = Anchor.at_graphicX + Position.posX;
            int posZ = Anchor.at_graphicZ + Position.posZ;
            posX = (int) Math.round(posX / scale);
            posZ = (int) Math.round(posZ / scale);

            /*
            (1.20.1) (1.21.1)
            graphic.pose().pushPose();
            graphic.pose().scale((float) scale, (float) scale, 1.0f);
            graphic.drawString(Minecraft.getInstance().font, text, posX, posZ, 0, shadow);
            graphic.pose().popPose();
            (1.21.8)
            graphic.pose().pushMatrix();
            graphic.pose().scale((float) scale, (float) scale);
            graphic.drawString(Minecraft.getInstance().font, text, posX, posZ, 0, shadow);
            graphic.pose().popMatrix();
            */
            graphic.pose().pushPose();
            graphic.pose().scale((float) scale, (float) scale, 1.0f);
            graphic.drawString(Minecraft.getInstance().font, text, posX, posZ, 0, shadow);
            graphic.pose().popPose();

        }
        
        public static void drawTextBasic (double scale, String text) {

            Storage.addText(scale, false, text);
            Position.addZ((int) Math.round(8 * scale));
            
        }

        public static void drawTextParagraph (int length_per_line, String text) {

            Map<Integer, StringBuilder> paragraph = new HashMap<>();
            int line = 0;
            int width = 0;
            int width_all = 0;

            for (String word : text.split(" ")) {

                word = word + " ";
                width = (int) (Minecraft.getInstance().font.width(word) * DEFAULT_FONT_SCALE);

                if (width_all + width < length_per_line) {

                    width_all = width_all + width;

                } else {

                    line = line + 1;
                    width_all = width;

                }

                paragraph.computeIfAbsent(line, create -> new StringBuilder()).append(word);

            }

            for (Map.Entry<Integer, StringBuilder> entry : paragraph.entrySet()) {

                Storage.addText(DEFAULT_FONT_SCALE, false, entry.getValue().toString());
                Position.addZ(8);

            }

        }

        private static void drawTextCentered (boolean is_only_vertical, double scale, boolean shadow, String text) {

            int posX = Position.posX;
            int posZ = Position.posZ;
            int[] pos_convert = Anchor.convertPosCenterText(posX, posZ, scale, text);

            if (is_only_vertical == true) {

                pos_convert[0] = posX;

            }

            Position.set(Anchor.type, pos_convert[0], pos_convert[1]);
            Storage.addText(scale, shadow, text);
            Position.addZ((int) Math.round(8 * scale));

        }

        public static void drawTextCenteredBasic (boolean is_only_vertical, double scale, String text) {

            drawTextCentered(is_only_vertical, scale, false, text);

        }

        public static void drawTextCenteredShadow (boolean is_only_vertical, double scale, String text) {

            drawTextCentered(is_only_vertical, scale, true, text);

        }

        private static void drawImage (int overall_sizeX, int overall_sizeZ, int slideX, int slideZ, int split_sizeX, int split_sizeZ, String path) {

            int posX = Anchor.at_graphicX + Position.posX;
            int posZ = Anchor.at_graphicZ + Position.posZ;
            graphic.blit(ResourceLocation.parse(path), posX, posZ, slideX ,slideZ, split_sizeX, split_sizeZ, overall_sizeX, overall_sizeZ);

        }

        public static void drawImageBasic (int sizeX, int sizeZ, String path) {

            Storage.addImage(sizeX, sizeZ, 0, 0, sizeX, sizeZ, path);

        }

        public static void drawImageSplit (int overall_sizeX, int overall_sizeZ, boolean is_horizontal, int piece_count, int choose, String path) {

            int split_sizeX = 0;
            int split_sizeZ = 0;
            int slideX = 0;
            int slideZ = 0;

            if (is_horizontal == true) {

                split_sizeX = overall_sizeX / piece_count;
                split_sizeZ = overall_sizeZ;
                slideX = split_sizeX * choose;
                slideZ = overall_sizeZ;

            } else {

                split_sizeX = overall_sizeX;
                split_sizeZ = overall_sizeZ / piece_count;
                slideX = overall_sizeX;
                slideZ = split_sizeZ * choose;

            }

            Storage.addImage(overall_sizeX, overall_sizeZ, slideX, slideZ, split_sizeX, split_sizeZ, path);

        }

        public static void drawImageOnline (int sizeX, int sizeZ, String url, String path_loading, String path_fail) {

            String id = online_image_id.get(url);

            if (id == null) {

                id = "tannyjung:online_image_" + (online_image_id.size() + 1) + ".png";
                online_image_id.put(url, id);
                online_image_status.put(id, "loading");
                String id_final = id;

                Core.thread.submit(() -> {

                    {

                        if (OutsideUtils.isURLAvailable(url) == true) {

                            try {

                                BufferedImage buffer = ImageIO.read(URI.create(url).toURL());
                                NativeImage native_image = new NativeImage(buffer.getWidth(), buffer.getHeight(), false);

                                // Color Convert
                                {

                                    int argb = 0;
                                    int a = 0;
                                    int r = 0;
                                    int g = 0;
                                    int b = 0;
                                    int abgr = 0;

                                    for (int scanY = 0; scanY < buffer.getHeight(); scanY++) {

                                        for (int scanX = 0; scanX < buffer.getWidth(); scanX++) {

                                            argb = buffer.getRGB(scanX, scanY);
                                            a = (argb >>> 24) & 0xFF;
                                            r = (argb >>> 16) & 0xFF;
                                            g = (argb >>> 8) & 0xFF;
                                            b = (argb) & 0xFF;
                                            abgr = (a << 24) | (b << 16) | (g << 8) | r;

                                            /*
                                            (1.20.1) (1.21.1)
                                            native_image.setPixelRGBA(scanX, scanY, abgr);
                                            (1.21.8)
                                            native_image.setPixelABGR(scanX, scanY, abgr);
                                            */
                                            native_image.setPixelRGBA(scanX, scanY, abgr);

                                        }

                                    }

                                }

                                /*
                                (1.20.1) (1.21.1)
                                Minecraft.getInstance().getTextureManager().register(ResourceLocation.parse(id_final), new DynamicTexture(native_image));
                                (1.21.8)
                                Minecraft.getInstance().getTextureManager().register(location, new DynamicTexture(() -> "test", native_image));
                                */
                                Minecraft.getInstance().getTextureManager().register(ResourceLocation.parse(id_final), new DynamicTexture(native_image));

                                online_image_status.put(id_final, "available");

                            } catch (Exception exception) {

                                OutsideUtils.exception(new Exception(), exception, "");
                                online_image_status.put(id_final, "fail");

                            }

                        } else {

                            online_image_status.put(id_final, "fail");

                        }

                    }

                });

            }

            String status = online_image_status.get(id);

            if (status.equals("loading") == true) {

                id = path_loading;

            } else if (status.equals("fail") == true) {

                id = path_fail;

            }

            if (id.isEmpty() == true) {

                return;

            }

            Storage.addImage(sizeX, sizeZ, 0, 0, sizeX, sizeZ, id);

        }

        public static class Storage {

            private static final List<Object[]> text = new ArrayList<>();
            private static final List<Object[]> image = new ArrayList<>();

            public static void clear () {

                text.clear();
                image.clear();

            }

            public static void renderText () {

                for (Object[] objects : new ArrayList<>(text)) {

                    Position.set(Anchor.type, (int) objects[0], (int) objects[1]);
                    drawText((double) objects[2], (boolean) objects[3], (String) objects[4]);

                }

            }

            public static void renderImage () {

                for (Object[] objects : new ArrayList<>(image)) {

                    Position.set(Anchor.type, (int) objects[0], (int) objects[1]);
                    drawImage((int) objects[2], (int) objects[3], (int) objects[4], (int) objects[5], (int) objects[6], (int) objects[7], (String) objects[8]);

                }

            }

            private static void addText (double scale, boolean shadow, String text) {

                int posX = Position.posX;
                int posZ = Position.posZ;

                if (screen == null) {

                    drawText(scale, shadow, text);

                } else {

                    Storage.text.add(new Object[]{posX, posZ, scale, shadow, text});

                }

            }

            private static void addImage (int overall_sizeX, int overall_sizeZ, int slideX, int slideZ, int split_sizeX, int split_sizeZ, String path) {

                int posX = Position.posX;
                int posZ = Position.posZ;

                if (screen == null) {

                    drawImage(overall_sizeX, overall_sizeZ, slideX, slideZ, split_sizeX, split_sizeZ, path);

                } else {

                    image.add(new Object[]{posX, posZ, overall_sizeX, overall_sizeZ, slideX, slideZ, split_sizeX, split_sizeZ, path});

                }

            }

        }

    }

    public static class ComponentWidget {

        public static class ButtonNormal {

            private static final Map<String, Button> map = new HashMap<>();

            private static void draw (int sizeX, int sizeZ, boolean is_active, boolean is_lock, Runnable runnable) {

                int posX = Position.posX;
                int posZ = Position.posZ;
                String key = posX + "/" + posZ;
                Button button = map.get(key);

                if (button == null) {

                    button = Button.builder(Component.empty(), create -> {

                        runnable.run();

                    }).build();

                    button.setPosition(Anchor.at_screenX + posX, Anchor.at_screenZ + posZ);
                    button.setSize(sizeX, sizeZ);

                    GUIScreen.Widget.add(button);
                    map.put(key, button);

                }

                button.active = is_active == true && is_lock == false;
                Position.addZ(sizeZ);

            }

            public static void drawBasic (int sizeX, int sizeZ, Runnable runnable) {

                drawBasicLockable(sizeX, sizeZ, true, false, runnable);

            }

            public static void drawBasicLockable (int sizeX, int sizeZ, boolean is_active, boolean is_lock, Runnable runnable) {

                draw(sizeX, sizeZ, is_active, is_lock, runnable);

            }

            public static void drawText (int length, String text, Runnable runnable) {

                drawTextLockable(length, true, false, "", text, runnable);

            }

            public static void drawTextLockable (int length, boolean is_active, boolean is_lock, String text_lock, String text_unlock, Runnable runnable) {

                drawBasicLockable(length, 11, is_active, is_lock, runnable);

                // Ingredient
                {

                    String text = "";

                    if (is_active == true && is_lock == false) {

                        text = "§f" + text_unlock;

                    } else {

                        text = "§c" + text_lock;

                    }

                    int posX = Position.posX;
                    int posZ = Position.posZ;
                    Position.add(length / 2, -5);
                    ComponentOverlay.drawTextCenteredShadow(false, DEFAULT_FONT_SCALE, text);
                    Position.set(Anchor.type, posX, posZ);

                }

            }

        }

        public static class ButtonImage {

            private static final Map<String, ImageButton> map = new HashMap<>();

            public static void draw (int sizeX, int sizeZ, boolean is_horizontal, boolean is_active, boolean is_lock, String path, Runnable runnable) {
                
                if (is_horizontal == true) {

                    sizeX = sizeX / 2;

                } else {

                    sizeZ = sizeZ / 2;

                }

                int posX = Position.posX;
                int posZ = Position.posZ;
                String key = posX + "/" + posZ;
                ImageButton button = map.get(key);

                if (button == null) {

                    ResourceLocation location = ResourceLocation.parse(path);

                    button = new ImageButton(Anchor.at_screenX + posX, Anchor.at_screenZ + posZ, sizeX, sizeZ, new WidgetSprites(location, location), create -> {

                        runnable.run();

                    }) {

                        @Override
                        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

                            int offsetX = 0;
                            int offsetZ = 0;
                            int multiply_width = 1;
                            int multiply_height = 1;

                            if (isHoveredOrFocused() == true) {

                                if (is_horizontal == true) {

                                    offsetX = width;

                                } else {

                                    offsetZ = height;

                                }

                            }

                            if (is_horizontal == true) {

                                multiply_width = 2;

                            } else {

                                multiply_height = 2;

                            }

                            guiGraphics.blit(sprites.get(true, isHoveredOrFocused()), getX(), getY(), offsetX, offsetZ, width, height, width * multiply_width, height * multiply_height);

                        }

                    };

                    button.active = is_active == true && is_lock == false;
                    GUIScreen.Widget.add(button);
                    map.put(key, button);

                }

                Position.addZ(sizeZ);

            }

        }

        public static class TextBox {

            private static final Map<String, EditBox> map = new HashMap<>();

            private static void draw (int length, boolean is_active, boolean is_lock, String nbt_category, String nbt_name, String text_lock) {

                int posX = Position.posX;
                int posZ = Position.posZ;
                String key = posX + "/" + posZ;
                EditBox box = map.get(key);

                if (box == null) {

                    EditBox box_new = new EditBox(Minecraft.getInstance().font, Anchor.at_screenX + posX, Anchor.at_screenZ + posZ, length, 14, Component.empty());
                    box_new.setMaxLength(10000);
                    box_new.setTextShadow(false);

                    box_new.setResponder(value -> {

                        if (box_new.isFocused() == false) {

                            return;

                        }

                        CompoundTag extra = new CompoundTag();
                        extra.putString("nbt_category", nbt_category);
                        extra.putString("nbt_name", nbt_name);
                        extra.putString("nbt_value", value);
                        NetworkManager.runServerCore("gui", "text_box", extra);

                    });

                    box = box_new;
                    GUIScreen.Widget.add(box_new);
                    map.put(key, box_new);

                }

                if (is_active == false || is_lock == true) {

                    box.active = false;
                    box.setEditable(false);

                } else {

                    if (box.isFocused() == false) {

                        box.setValue(GUIManager.NBT.getText(player_local, nbt_category, nbt_name));

                    }

                }

                // Ingredient
                {

                    if (is_lock == true) {

                        Position.add(9 + (box.getWidth() / 2), 15);
                        ComponentOverlay.drawTextCenteredBasic(false, DEFAULT_FONT_SCALE, text_lock);

                    }

                }

                Position.set(Anchor.type, posX, posZ + 14);

            }

            public static void drawBasic (int length, String nbt_category, String nbt_name) {

                drawBasicLockable(length, true, false, nbt_category, nbt_name, "");

            }

            public static void drawBasicLockable (int length, boolean is_active, boolean is_lock, String nbt_category, String nbt_name, String text_lock) {

                draw(length, is_active, is_lock, nbt_category, nbt_name, text_lock);

            }

            public static void drawText (int length, String nbt_category, String nbt_name, String text) {

                drawTextLockable(length, true, false, nbt_category, nbt_name, text, "");

            }

            public static void drawTextLockable (int length, boolean is_active, boolean is_lock, String nbt_category, String nbt_name, String text, String text_lock) {

                // Ingredient
                {

                    ComponentOverlay.drawTextBasic(DEFAULT_FONT_SCALE, text);

                }

                Position.addZ(4);
                drawBasicLockable(length, is_active, is_lock, nbt_category, nbt_name, text_lock);

            }

        }

        public static class Slider {

            private static final Map<String, AbstractSliderButton> map = new HashMap<>();

            private static void draw (int length, boolean is_active, boolean is_lock, double value_min, double value_max, double value_move, String nbt_category, String nbt_name) {

                int posX = Position.posX;
                int posZ = Position.posZ;
                String key = posX + "/" + posZ;
                AbstractSliderButton slider = map.get(key);

                if (slider != null) {

                    map.remove(key);
                    GUIScreen.Widget.removeSpecific(slider);

                }

                double range = value_max - value_min;
                double value_default = 0;

                // Get Default Value
                {

                    double value = GUIManager.NBT.getNumber(player_local, nbt_category, nbt_name);
                    value_default = (value - value_min) / range;

                    if (value_default < 0.0 || value_default > 1.0) {

                        double set = 0;

                        if (range == 0 || value_default < 0.0) {

                            value_default = 0;
                            set = value_min;

                        } else {

                            value_default = 1.0;
                            set = value_max;

                        }

                        CompoundTag extra = new CompoundTag();
                        extra.putString("nbt_category", nbt_category);
                        extra.putString("nbt_name", nbt_name);
                        extra.putDouble("nbt_value", set);
                        NetworkManager.runServerCore("gui", "slider", extra);

                    }

                }

                slider = new AbstractSliderButton(Anchor.at_screenX + posX, Anchor.at_screenZ + posZ, length, 6, Component.empty(), value_default) {

                    private double value_current = value_min - 1;

                    @Override
                    protected void updateMessage () {
                    }

                    @Override
                    protected void applyValue () {

                        double value = this.value * range;

                        if (value_min >= value_move || value > Math.abs(value_min - value_move)) {

                            value = value + value_min;

                        }

                        value = Math.round(value / value_move) * value_move;

                        if (value < value_min) {

                            value = value_min;

                        }

                        this.value = (value - value_min) / range;

                        if (value_current != value) {

                            value_current = value;
                            GUIManager.NBT.setNumber(player_local, nbt_category, nbt_name, value);
                            refresh = true;

                        }

                    }

                    @Override
                    public void onRelease (double p_93609_, double p_93610_) {

                        super.onRelease(p_93609_, p_93610_);
                        CompoundTag extra = new CompoundTag();
                        extra.putString("nbt_category", nbt_category);
                        extra.putString("nbt_name", nbt_name);
                        extra.putDouble("nbt_value", value_current);
                        NetworkManager.runServerCore("gui", "slider", extra);

                    }

                };

                GUIScreen.Widget.add(slider);
                map.put(key, slider);

                if (range == 0) {

                    slider.active = false;

                } else {

                    slider.active = is_active == true && is_lock == false;

                }

                Position.addZ(6);

            }

            public static void drawBasic (int length, double value_min, double value_max, double value_move, String nbt_category, String nbt_name) {

                drawBasicLockable(length, true, false, value_min, value_max, value_move, nbt_category, nbt_name);

            }

            public static void drawBasicLockable (int length, boolean is_active, boolean is_lock, double value_min, double value_max, double value_move, String nbt_category, String nbt_name) {

                draw(length, is_active, is_lock, value_min, value_max, value_move, nbt_category, nbt_name);

            }

            public static void drawText (int length, double value_min, double value_max, double value_move, String nbt_category, String nbt_name, String text) {

                drawTextWithSuffix(length, value_min, value_max, value_move, nbt_category, nbt_name, text, "");

            }

            public static void drawTextWithSuffix (int length, double value_min, double value_max, double value_move, String nbt_category, String nbt_name, String text, String suffix) {

                // Ingredient
                {

                    double value_default = GUIManager.NBT.getNumber(player_local, nbt_category, nbt_name);
                    StringBuilder builder = new StringBuilder();
                    builder.append(text).append(" ");

                    // Number Show
                    {

                        builder.append("(");

                        if (value_default % 1 == 0) {

                            builder.append((int) value_default);

                        } else {

                            builder.append(value_default);

                        }

                        if (value_min == 0 || value_min == 1) {

                            builder.append("/");

                            if (value_default % 1 == 0) {

                                builder.append((int) value_max);

                            } else {

                                builder.append(value_max);

                            }

                        }

                        builder.append(")");

                    }

                    builder.append(" ").append(suffix);
                    ComponentOverlay.drawTextBasic(DEFAULT_FONT_SCALE, builder.toString());

                }

                Position.addZ(4);
                drawBasicLockable(length, true, false, value_min, value_max, value_move, nbt_category, nbt_name);

            }

        }

        public static class Shape {

            private static final Map<String, Button> map = new HashMap<>();

            private static void drawLockedButton (int sizeX, int sizeZ) {

                int posX = Position.posX;
                int posZ = Position.posZ;
                String key = posX + "/" + posZ;
                Button button = map.get(key);

                if (button == null) {

                    button = Button.builder(Component.empty(), create -> {}).build();
                    button.setPosition(Anchor.at_screenX + posX, Anchor.at_screenZ + posZ);
                    button.setSize(sizeX, sizeZ);
                    button.active = false;
                    GUIScreen.Widget.add(button);
                    map.put(key, button);

                }

            }

        }

        public static class Switch {

            private static void draw (boolean is_active, boolean is_lock, String nbt_category, String nbt_name) {

                boolean value = GUIManager.NBT.getLogic(player_local, nbt_category, nbt_name);

                Runnable runnable = () -> {

                    CompoundTag tag = new CompoundTag();
                    tag.putString("nbt_category", nbt_category);
                    tag.putString("nbt_name", nbt_name);
                    NetworkManager.runServerCore("gui", "switch", tag);

                };

                ButtonNormal.drawBasicLockable(6, 6, is_active && value == false, is_lock, runnable);
                Position.addZ(-6);
                Position.addX(6);
                ButtonNormal.drawBasicLockable(6, 6, is_active && value == true, is_lock, runnable);
                Position.addX(-6);

            }

            public static void drawBasic (String nbt_category, String nbt_name) {

                drawBasicLockable(true, false, nbt_category, nbt_name);

            }

            public static void drawBasicLockable (boolean is_active, boolean is_lock, String nbt_category, String nbt_name) {

                drawTextLockable(is_active, is_lock, nbt_category, nbt_name, "");

            }

            public static void drawText (String nbt_category, String nbt_name, String text) {

                drawTextLockable(true, false, nbt_category, nbt_name, text);

            }

            public static void drawTextLockable (boolean is_active, boolean is_lock, String nbt_category, String nbt_name, String text) {

                draw(is_active, is_lock, nbt_category, nbt_name);

                // Ingredient
                {

                    if (text.isEmpty() == false) {

                        int posX = Position.posX;
                        int posZ = Position.posZ;
                        Position.add(16, -2);
                        ComponentOverlay.drawTextCenteredBasic(true, DEFAULT_FONT_SCALE, text);
                        Position.set(Anchor.type, posX, posZ);

                    }

                }

            }

        }

        public static class Radio {

            private static final Set<String> set = new HashSet<>();

            private static void draw (boolean is_active, boolean is_lock, String text, String nbt_category, String nbt_name, String[] nbt_values) {

                int posX = Position.posX;
                int posZ = Position.posZ;
                String key = posX + "/" + posZ;

                if (set.contains(key) == false) {

                    set.add(key);

                    // Start Auto Sync
                    {

                        if (GUIManager.NBT.getText(player_local, nbt_category, nbt_name).isEmpty() == true) {

                            CompoundTag extra = new CompoundTag();
                            extra.putString("nbt_category", nbt_category);
                            extra.putString("nbt_name", nbt_name);
                            extra.putString("nbt_value", nbt_values[0]);
                            NetworkManager.runServerCore("gui", "radio", extra);

                        }

                    }

                }

                // Ingredient
                {

                    ComponentOverlay.drawTextBasic(DEFAULT_FONT_SCALE, text);

                }

                Position.addZ(2);
                int scanX = 0;
                int scanZ = 0;
                boolean is_select = false;

                for (String scan : nbt_values) {

                    Position.addZ(2);
                    is_select = GUIManager.NBT.getText(player_local, nbt_category, nbt_name).equals(scan) == true;
                    String scan_final = scan;

                    ButtonNormal.drawBasicLockable(6, 6, is_active, is_lock == true || is_select == true, () -> {

                        CompoundTag extra = new CompoundTag();
                        extra.putString("nbt_category", nbt_category);
                        extra.putString("nbt_name", nbt_name);
                        extra.putString("nbt_value", scan_final);
                        NetworkManager.runServerCore("gui", "radio", extra);

                    });

                    // Ingredient
                    {

                        if (is_select == true) {

                            scan = "§8" + scan;

                        } else {

                            scan = "§7" + scan;

                        }

                        scanX = Position.posX;
                        scanZ = Position.posZ;
                        Position.add(10, -2);
                        ComponentOverlay.drawTextCenteredBasic(true, DEFAULT_FONT_SCALE, scan);

                    }

                    Position.set(Anchor.type, scanX, scanZ);

                }

            }

            public static void drawBasic (String text, String nbt_category, String nbt_name, String[] nbt_values) {

                drawBasicLockable(true, false, text, nbt_category, nbt_name, nbt_values);

            }

            public static void drawBasicLockable (boolean is_active, boolean is_lock, String text, String nbt_category, String nbt_name, String[] nbt_values) {

                draw(is_active, is_lock, text, nbt_category, nbt_name, nbt_values);

            }

        }

        public static class Board {

            private static void draw (int sizeX, int sizeZ, String name) {

                int posX = Position.posX;
                int posZ = Position.posZ;

                List<String> list = GUIManager.NBT.getList(player_local, "gui", name);
                int line_limit = (int) Math.floor(sizeZ / 10.5);

                if (list.isEmpty() == true) {

                    Position.add(sizeX / 2, sizeZ / 2);
                    ComponentOverlay.drawTextCenteredBasic(false, DEFAULT_FONT_SCALE, "§fEmpty List");

                } else {

                    Position.add(8, 5);

                    // List
                    {

                        int sub_start = (int) GUIManager.NBT.getNumber(player_local, "gui", name + "_page");
                        int sub_end = (sub_start + line_limit);
                        int select = (int) GUIManager.NBT.getNumber(player_local, "gui", name + "_select");
                        int number = -1;

                        for (String scan : list) {

                            number = number + 1;

                            if (number < sub_start) {

                                continue;

                            }

                            if (number > sub_end) {

                                break;

                            }

                            if (number == select) {

                                scan = scan + "          §e<-";

                            }

                            Position.addZ(4);
                            ComponentOverlay.drawTextBasic(DEFAULT_FONT_SCALE, "§8" + number + " " + scan);

                        }

                    }

                }

                // Ingredient
                {

                    int slide_size = list.size() - line_limit;

                    if (slide_size < 0) {

                        slide_size = 0;

                    }

                    Position.set(Anchor.type, posX, posZ);
                    Shape.drawLockedButton(sizeX, sizeZ);
                    Position.addZ(sizeZ + 2);
                    Slider.drawBasic(sizeX, 0, slide_size - 1, 1, "gui", name + "_page");
                    Position.addZ(2);
                    Slider.drawBasic(sizeX, 0, list.size() - 1, 1, "gui", name + "_select");

                }

            }

            public static void drawBasic (int sizeX, int sizeZ, String name) {

                Board.draw(sizeX, sizeZ, "board_" + name);

            }

            public static void drawEntity (String title, String name, String description) {

                int posX = ScreenDrawing.Position.posX;
                int posZ = ScreenDrawing.Position.posZ;
                ScreenDrawing.Position.addX(192);
                drawBasic(160, 150, "entity_" + name);

                // Ingredient
                {

                    CompoundTag extra = new CompoundTag();
                    extra.putString("nbt_name", name);

                    int temporary_number = 0;

                    ScreenDrawing.Position.addZ(6);
                    temporary_number = ScreenDrawing.Position.posZ;
                    ScreenDrawing.ComponentWidget.ButtonNormal.drawText(40, "Refresh", () -> NetworkManager.runServerCore("gui", "board_entity_refresh", extra));
                    ScreenDrawing.Position.addX(120);
                    ScreenDrawing.Position.setZ(temporary_number);
                    ScreenDrawing.ComponentWidget.ButtonNormal.drawText(40, "Clear", () -> NetworkManager.runServerCore("gui", "board_entity_clear", extra));

                    ScreenDrawing.Position.set(ScreenDrawing.Anchor.type, posX, posZ);

                    ScreenDrawing.ComponentOverlay.drawTextBasic(1, title);
                    ScreenDrawing.Position.addZ(8);
                    ScreenDrawing.ComponentOverlay.drawTextParagraph(160, description);

                    ScreenDrawing.Position.setZ(posZ + 80);
                    ScreenDrawing.ComponentWidget.TextBox.drawText(160, "gui", "board_entity_" + name + "_id", "Entity ID and Name");
                    ScreenDrawing.Position.addZ(2);
                    ScreenDrawing.ComponentWidget.TextBox.drawBasic(160, "gui", "board_entity_" + name + "_name");
                    ScreenDrawing.Position.addZ(8);
                    ScreenDrawing.ComponentWidget.Slider.drawText(160, 5, 200, 10, "gui", "board_entity_" + name + "_radius", "Area Radius");

                    ScreenDrawing.Position.addZ(8);
                    posZ = ScreenDrawing.Position.posZ;
                    ScreenDrawing.ComponentWidget.Radio.drawBasic("Removal Mode", "gui", "board_entity_" + name + "_remove_mode", new String[]{"Area", "Overall", "Selection"});
                    ScreenDrawing.Position.setZ(posZ);
                    ScreenDrawing.Position.add(80, 22);
                    posZ = ScreenDrawing.Position.posZ;
                    ScreenDrawing.ComponentWidget.ButtonNormal.drawText(39, "Remove", () -> NetworkManager.runServerCore("gui", "board_entity_remove", extra));
                    ScreenDrawing.Position.setZ(posZ);
                    ScreenDrawing.Position.addX(41);
                    ScreenDrawing.ComponentWidget.ButtonNormal.drawText(39, "Add", () -> NetworkManager.runServerCore("gui", "board_entity_add", extra));

                }

            }
            
        }

    }

}
