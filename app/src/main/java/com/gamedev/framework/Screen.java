package com.gamedev.framework;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    /*
    Return true if the back button should still pause the app, false if it shouldn't.
     */
    public abstract boolean backButton();

    protected int getOffsetX()
    {
        return game.isPortrait() ? 0 : (game.getWidth() - 800) / 2;
    }

    protected int getOffsetY()
    {
        return game.isPortrait() ? (game.getHeight() - 800) / 2 : 0;
    }
}

