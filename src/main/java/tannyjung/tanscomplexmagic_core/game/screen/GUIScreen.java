package tannyjung.tanscomplexmagic_core.game.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lwjgl.glfw.GLFW;
import tannyjung.tanscomplexmagic_core.Core;
import tannyjung.tanscomplexmagic_core.outside.NetworkManager;

public class GUIScreen extends AbstractContainerScreen<GUIContainer> {

    public GUIScreen (GUIContainer container, Inventory inventory, Component text) {

        super(container, inventory, text);
        ScreenDrawing.screen = this;
        ScreenDrawing.player_local = (LocalPlayer) container.player;

    }

    public static void close () {

        ScreenDrawing.screen = null;
        ScreenDrawing.graphic = null;
        ScreenDrawing.player_local = null;

        ScreenDrawing.refresh = true;
        NetworkManager.runServerCore("gui", "when_close", new CompoundTag());

    }

    @Override
    public boolean keyPressed (int key, int scan, int modifier) {

        if (key == GLFW.GLFW_KEY_ESCAPE) {

            {

                GUIManager.close(ScreenDrawing.player_local);
                return true;

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

        } if (key == GLFW.GLFW_KEY_E) {

            {

                for (Renderable renderable : this.renderables) {

                    if (renderable instanceof EditBox box) {

                        if (box.isFocused() == true) {

                            return false;

                        }

                    }

                }

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

        ScreenDrawing.refresh();

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
        GUIManager.client_current_id = 0;

        ScreenDrawing.refresh = true;
        ScreenDrawing.refresh();

    }

    public static class Widget {

        public static void add (AbstractWidget widget) {

            ScreenDrawing.screen.addRenderableWidget(widget);

        }

        public static void removeAll () {

            ScreenDrawing.screen.clearWidgets();

        }

        public static void removeSpecific (AbstractWidget widget) {

            ScreenDrawing.screen.removeWidget(widget);

        }

    }

}