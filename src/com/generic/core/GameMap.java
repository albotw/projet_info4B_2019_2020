package com.generic.core;

import com.generic.gameplay.OnlineGame;

import static com.generic.gameplay.CONFIG_GAME.ONLINE_MODE;

import com.generic.gameplay.AbstractGame;

public class GameMap {
    // SINGLETON OK

    // faire en sorte de "sémaphoriser" la classe.
    // Pas besoin car pas d'erreur de modification concurrente détéctée pour le
    // moment.
    private MapObject tab[][];
    private int width;
    private int height;

    public GameMap(int width, int height) {
        this.tab = new MapObject[width][height];
        this.width = width;
        this.height = height;
    }


    public MapObject getAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height || tab == null) {
            return null;
        } else {
            return tab[x][y];
        }
    }

    public synchronized void place(MapObject o, int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height && tab != null) {
            tab[x][y] = o;
        }

        if (ONLINE_MODE) {
            OnlineGame srv = (OnlineGame) (AbstractGame.instance);
            srv.overrideMap(x, y, o.getType());
        }
    }

    public synchronized void release(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height && tab != null) {
            tab[x][y] = null;
        }

        if (ONLINE_MODE) {
            OnlineGame srv = (OnlineGame) (AbstractGame.instance);
            srv.overrideMap(x, y, null);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tab[j][i] == null) {
                    s += ".";
                } else {
                    s += "X";
                }
            }
            s += "\n";
        }

        return s;
    }

    public void deleteMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tab[j][i] = null;
            }
        }
        tab = null;
    }
}
