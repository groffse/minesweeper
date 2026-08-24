package groffse;
import Graphics.GameController;
import Graphics.MenuScreen;
import Graphics.SettingsScreen;
import Graphics.InGameScreen;
import de.gurkenlabs.litiengine.*;

import de.gurkenlabs.litiengine.gui.GuiProperties;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

public class App
{

    public static final Font GUI_FONT = Resources.fonts().get("res/marquee moon.ttf").deriveFont(48f).deriveFont(30f);
    public static final Font GUI_FONT_SMALL = GUI_FONT.deriveFont(30f);
    public static float HUD_SCALE = 2.0f;

    public static final Image CURSOR_STANDARD = Resources.images().get("res/cursor-standard.png");

    public static void main( String[] args )
    {
        Game.init();
        Game.info().setName("Minesweeper");
        Game.window().setTitle("Minesweeper 2.0");
        Game.window().setResolution(Resolution.Ratio16x9.RES_1280x720);
        Game.window().getRenderComponent().setCursorOffset(0,0);
        Game.window().getRenderComponent().setCursor(CURSOR_STANDARD);
        initApp();
        Game.start();
    }

    private static void initApp() {
        GuiProperties.setDefaultFont(GUI_FONT);
        GuiProperties.getDefaultAppearance().setTextAntialiasing(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        GuiProperties.getDefaultAppearanceDisabled().setTextAntialiasing(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        GuiProperties.getDefaultAppearanceHovered().setTextAntialiasing(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Game.screens().add(new MenuScreen());
        Game.screens().add(new SettingsScreen());
        Game.screens().add(new InGameScreen());
        //Game.screens().display("MENU");
        Game.graphics().setBaseRenderScale(4.6f);

        GameController.init();
    }
}
