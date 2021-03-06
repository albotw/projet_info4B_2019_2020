package com.generic.core;

import com.generic.gameplay.AbstractGame;


public class IceBlock extends MapBlock {

    public IceBlock(int x, int y) {
        super(x, y);
        this.type = "IceBlock";
        sprite.loadImage(type);
    }

    public void destroy(MapObject killer) {
        AbstractGame.instance.getMap().release(x, y);

        try {
            Thread.currentThread().sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onGlideEnded() {}
}
