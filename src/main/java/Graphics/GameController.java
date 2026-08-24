package Graphics;

import groffse.Grid;
import groffse.Panel;
import groffse.Settings;
import util.Pair;

import java.util.ArrayList;

/*
* This beauty is in control of everything!*/
public final class GameController {

    private static Grid panelGrid;
    private static GameState gameState;
    private static GameStatus gameStatus;

    public static void init() {
        panelGrid = new Grid();
        panelGrid.setHeight(Settings.height);
        panelGrid.setWidth(Settings.width);
        panelGrid.setNumberOfBombs(Settings.numberOfBombs);
        gameStatus = GameStatus.NEW;
    }

    public static Iterable<Pair<Integer,Integer>> click(int panelID) {
        // Handle exceptions here

        if(gameStatus == GameStatus.NEW) {
            panelGrid.setHeight(Settings.height);
            panelGrid.setWidth(Settings.width);
            panelGrid.setNumberOfBombs(Settings.numberOfBombs);
            panelGrid.generateBoard(panelID);
            gameStatus = GameStatus.ONGOING;
        }
        Pair<Integer,Integer> row_col = IDToPair(panelID);
        panelGrid.floodFill(row_col.first, row_col.second,0);
        panelGrid.printPanelGrid();
        return getRevealedPanels();
    }

    /*Returns true if flag can be set*/
    public static boolean setFlag(int panelID) {
        if(gameStatus != GameStatus.ONGOING)
            return false;

        Pair<Integer,Integer> row_col = IDToPair(panelID);
        Panel p = panelGrid.getPanelGrid().get(row_col.first).get(row_col.second);

        if(p.isRevealed() || p.isFlagged()) {
            p.setFlag(false);
            return false;
        }
        p.setFlag(true);
        return true;
    }

    public static void restart () {
        panelGrid.reset();
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gs) {
        GameController.gameState = gs;
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }

    public static void setGameStatus(GameStatus gs) {
        GameController.gameStatus = gs;
    }

    public static Iterable<Pair<Integer,Integer>> getRevealedPanels() {
        ArrayList<Pair<Integer,Integer>> revealedList = new ArrayList<>();
        for(ArrayList<Panel> row : panelGrid.getPanelGrid()) {
            for(Panel panel : row) {
                if(panel.isRevealed()) {
                    Pair<Integer,Integer> id_adj = new Pair<>();
                    id_adj.first = panel.getID();
                    id_adj.second = panel.getAdjacentBombs();
                    revealedList.add(id_adj);
                }
            }
        }
        return revealedList;
    }

    /*PanelComponent returns id. Turn this into a pair of x,y for Grid class*/
    public static Pair<Integer,Integer> IDToPair(int ID) {
        int row = ID / panelGrid.getHeight();
        int column = ID % panelGrid.getWidth();
        Pair<Integer,Integer> pair = new Pair<>();
        pair.first = row;
        pair.second = column;
        return pair;
    }
}
