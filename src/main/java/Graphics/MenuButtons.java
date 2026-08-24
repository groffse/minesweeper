package Graphics;

import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Resolution;

import java.awt.*;
import java.util.ArrayList;

public class MenuButtons {

    ArrayList<ImageComponent> menuButtons;
    private double menuButtonVerticalGap;
    private int numberOfButtons;
    private Resolution resolution;

    MenuButtons ( Resolution res, String ... buttons) {
        menuButtons             = new ArrayList<ImageComponent>();
        numberOfButtons         = buttons.length;
        menuButtonVerticalGap   = 70;
        resolution              = res;
        init(buttons);
    }

    private void init(String ... buttonNames) {
        int x = resolution.getWidth() / 2;
        int y = (resolution.getHeight() / 2) / 2;

        double width = resolution.getWidth() / 3;
        double height = resolution.getHeight() / 8;

        int index = 0;
        for(String buttonName : buttonNames) {
            menuButtons.add(new ImageComponent(x - width / 2.0, (y + height / 2.0) + (menuButtonVerticalGap*index) , width, 60));
            this.menuButtons.get(index).setText(buttonName);
            this.menuButtons.get(index).getAppearance().setForeColor(new Color(215,82,82));
            this.menuButtons.get(index).getAppearanceHovered().setForeColor(new Color(253,184,184));
            index++;
        }
    }

    public Iterable<ImageComponent> getMenuButtons() {
        return menuButtons;
    }

    public int getNumberOfButtons() {return numberOfButtons;}

}
