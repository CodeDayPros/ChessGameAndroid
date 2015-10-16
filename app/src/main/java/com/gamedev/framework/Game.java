package com.gamedev.framework;

public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public boolean isPortrait();

    public int getWidth();

    public int getHeight();
}