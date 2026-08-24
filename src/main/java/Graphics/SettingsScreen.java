package Graphics;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.TextFieldComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;

import de.gurkenlabs.litiengine.resources.Resources;
import groffse.App;
import groffse.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingsScreen extends Screen {

    public static final String SCREEN_NAME = "SETTINGS";
    private TextFieldComponent numberOfBombs;
    private TextFieldComponent grid_height, grid_width;
    private ImageComponent setting_group;

    public SettingsScreen() {
        super(SCREEN_NAME);
    }

    @Override
    public void render(final Graphics2D g) {
        if(Game.world().environment() != null) {
            Game.world().environment().render(g);
        }

        g.setFont(App.GUI_FONT);
        g.setColor(Color.WHITE);

        // Tile screen
        String title_name = "Settings";
        double title_nameX = Game.window().getCenter().getX() - g.getFontMetrics().stringWidth(title_name) / 2.0;
        double title_nameY = g.getFontMetrics().getHeight() + 30;
        TextRenderer.render(g,title_name,title_nameX+10,title_nameY);

        String textNumberOfBombs = "Bombs:";
        double settingsTextX = Game.window().getCenter().getX() - g.getFontMetrics().stringWidth(title_name) / 2.0;
        double settingsTextY = Game.window().getCenter().getY() / 2;
        TextRenderer.render(g, textNumberOfBombs, settingsTextX, settingsTextY);
        String textHeight = "Height:";
        TextRenderer.render(g, textHeight, settingsTextX, settingsTextY + 100);

        String textWidth = "Width:";
        TextRenderer.render(g, textWidth, settingsTextX, settingsTextY + 200);

        super.render(g);
    }

    // Only used to listen to escape key
    @Override
    public void prepare() {
        super.prepare();
        Input.keyboard().onKeyPressed(KeyEvent.VK_ESCAPE, e -> {
            if (this.isVisible()) {

                try{
                    Settings.numberOfBombs = Integer.parseInt(numberOfBombs.getText());
                    Settings.height = Integer.parseInt(grid_height.getText());
                    Settings.width = Integer.parseInt(grid_width.getText());
                }
                catch(NumberFormatException exception) {
                    // Give user warning
                    System.out.println(exception.getMessage());
                    Settings.numberOfBombs = 16;
                    Settings.height = 5;
                    Settings.width = 5;
                }
                finally {
                    Game.window().getRenderComponent().fadeOut(1000);
                    this.setVisible(false);
                    Game.screens().get("MENU").setVisible(true);
                    Game.screens().display("MENU");
                    /*Must be a one-liner for this..*/
                    if(Game.screens().get("INGAME") instanceof InGameScreen) {
                        InGameScreen s = (InGameScreen) Game.screens().get("INGAME");
                        s.update();
                    }
                    Game.window().getRenderComponent().fadeIn(1000);
                }
            }
        });
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

       double x = Game.window().getCenter().getX();
       double y = Game.window().getCenter().getY()/2;

        numberOfBombs   = new TextFieldComponent(x,y-43.0,x,60,null, Integer.toString(Settings.numberOfBombs));
        grid_height     = new TextFieldComponent(x,y+57.0,x,60,null, Integer.toString(Settings.height));
        grid_width      = new TextFieldComponent(x,y+157.0,x,60,null, Integer.toString(Settings.width));

        setting_group   = new ImageComponent(x-68,y-45,Resources.images().get("res/Settings_overlay.png"));
        this.getComponents().add(numberOfBombs);
        this.getComponents().add(grid_height);
        this.getComponents().add(grid_width);
        this.getComponents().add(setting_group);
    }
}
