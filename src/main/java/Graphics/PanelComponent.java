package Graphics;

import de.gurkenlabs.litiengine.gui.GuiComponent;
import groffse.App;

import java.awt.*;
import java.util.Random;


/*Solution one:
* Render rectangle
* Render number of adjacent bombs inside rectangle
* Render Bomb image inside rectangle*/

/*Solution two:
* Look at entities instead*/

/*Holy crap, we (the pope) are actually gonna do dis*/
public class PanelComponent extends GuiComponent {

    private int ID;
    public int adjacentBombs = 0; /*Negative value is bomb*/
    int posX, posY;
    public boolean clicked = false;
    public boolean flag;

    public PanelComponent(double posX, double posY,int ID) {
        super(posX,posY-17.5,40,40);
        this.posX = (int)posX;
        this.posY = (int)posY;
        this.ID = ID;
    }

    @Override
    public void render(final Graphics2D g) {

        g.setColor(Color.RED);
        g.fillRect(posX,posY,40,40);
        //System.out.println(10+g.getFontMetrics().stringWidth("8")); 22
        //System.out.println(10+g.getFontMetrics().getHeight()); 47

        drawSomeMore(g);
        super.render(g);
    }

    public int getID() {return ID;}

    public void drawSomeMore(Graphics2D g) {
        if(clicked) {
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(this.adjacentBombs), posX + (g.getFontMetrics().stringWidth("0")),posY + g.getFontMetrics().getHeight());
        }
        if(flag) {
            g.setColor(Color.GREEN);
            g.fillRect(posX,posY,40,40);
        }
    }

    public void toggleClicked () {clicked = !clicked;}
}
