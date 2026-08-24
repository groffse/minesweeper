package Graphics;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import groffse.App;

import java.awt.*;

public class MenuScreen extends Screen {

    public static final String SCREEN_NAME = "MENU";
    private MenuButtons menuButtons;

    public MenuScreen() {
        super(SCREEN_NAME);
    }

    @Override
    public void render(final Graphics2D g) {

        if(Game.world().environment() != null) {
            Game.world().environment().render(g);
        }

        String title_name = "Minesweeper 2.0";
        String info = "A game made with litiengine";

        g.setFont(App.GUI_FONT);
        g.setColor(Color.WHITE);

        double infoX = Game.window().getCenter().getX() - g.getFontMetrics().stringWidth(info) / 2.0;
        double infoY = Game.window().getResolution().getHeight() - g.getFontMetrics().getHeight() - 10;
        TextRenderer.render(g, info, infoX, infoY);

        double title_nameX = Game.window().getCenter().getX() - g.getFontMetrics().stringWidth(title_name) / 2.0;
        double title_nameY = g.getFontMetrics().getHeight() + 30;
        TextRenderer.render(g,title_name,title_nameX,title_nameY);

        super.render(g);
    }

    @Override
    public void prepare() {
        super.prepare();
        GameController.setGameState(GameState.MAINMENU);

    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();

        menuButtons = new MenuButtons(
                Resolution.Ratio16x9.RES_1280x720,
                "PLAY GAME",
                         "SETTINGS",
                         "EXIT GAME"
                                    );

        for(ImageComponent button : menuButtons.getMenuButtons()) {
            this.getComponents().add(button);
        }

        initMenuEvents();
    }

    private void initMenuEvents() {

        // Play button
        this.getComponents().get(0).onClicked(e -> {
            turnOffMenu("INGAME");
            GameController.setGameState(GameState.INGAME);
        });
        // Settings button
        this.getComponents().get(1).onClicked(e -> {
            turnOffMenu("SETTINGS");
            GameController.setGameState(GameState.SETTINGS);
        });
        // Exit button
        this.getComponents().get(2).onClicked(e -> {
            System.exit(0);
        });
    }

    // Fade out menu and disable the button
    private void turnOffMenu(String next_screen) {
        Game.window().getRenderComponent().fadeOut(1000);
        this.setVisible(false);
        Game.screens().get(next_screen).setVisible(true);
        Game.screens().display(next_screen);
        Game.window().getRenderComponent().fadeIn(1000);
    }
}
