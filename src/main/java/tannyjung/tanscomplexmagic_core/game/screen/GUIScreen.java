package tannyjung.tanscomplexmagic_core.game.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;
import tannyjung.tanscomplexmagic_core.outside.NetworkManager;

public class GUIScreen extends AbstractContainerScreen<GUIContainer> {

    public static String current_category = "";
    public static String current_name = "";
    public static String current_nbt_type = "";

    public GUIScreen (GUIContainer container, Inventory inventory, Component text) {

        super(container, inventory, text);
        ScreenDrawing.gui_screen = this;
        ScreenDrawing.player_local = (LocalPlayer) container.player;

    }

    public static void close () {

        ScreenDrawing.gui_screen = null;
        ScreenDrawing.graphic = null;
        ScreenDrawing.player_local = null;

        ScreenDrawing.setGUIRefreshQueue();
        NetworkManager.runServerCore("gui", "close", new CompoundTag());

    }

    @Override
    public boolean keyPressed (int key, int scan, int modifier) {

        if (key == GLFW.GLFW_KEY_E) {

            {

                for (Renderable renderable : this.renderables) {

                    if (renderable instanceof EditBox box) {

                        if (box.isFocused() == true) {

                            return false;

                        }

                    }

                }

            }

        } else if (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) {

            {

                for (Renderable renderable : this.renderables) {

                    if (renderable instanceof EditBox box) {

                        box.setFocused(false);

                    }

                }

                return true;

            }

        }

        return super.keyPressed(key, scan, modifier);

    }

    @Override
    public boolean mouseDragged (double mouseX, double mouseY, int button, double dragX, double dragY) {

        if (this.isDragging() == true && button == 0) {

            return this.getFocused().mouseDragged(mouseX, mouseY, button, dragX, dragY);

        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);

    }

    @Override
    public void render (GuiGraphics graphic, int mouseX, int mouseY, float tick) {

        super.render(graphic, mouseX, mouseY, tick);

        if (ScreenDrawing.graphic == null) {

            ScreenDrawing.graphic = graphic;

        }

        ScreenDrawing.refreshGUI();

    }

    @Override
    protected void renderBg (GuiGraphics graphic, float partialTicks, int mouseX, int mouseY) {

        ScreenDrawing.ComponentOverlay.Storage.renderImage();

    }

    @Override
    protected void renderLabels (GuiGraphics graphic, int mouseX, int mouseY) {

        ScreenDrawing.ComponentOverlay.Storage.renderText();

    }

    @Override
    public void init () {

        super.init();
        current_category = "";
        current_name = "";
        current_nbt_type = "";

        ScreenDrawing.setGUIRefreshQueue();
        ScreenDrawing.refreshGUI();

    }

    public static class Widget {

        public static void add (AbstractWidget widget) {

            ScreenDrawing.gui_screen.addRenderableWidget(widget);

        }

        public static void removeAll () {

            ScreenDrawing.gui_screen.clearWidgets();

        }

        public static void removeSpecific (AbstractWidget widget) {

            ScreenDrawing.gui_screen.removeWidget(widget);

        }

    }

}