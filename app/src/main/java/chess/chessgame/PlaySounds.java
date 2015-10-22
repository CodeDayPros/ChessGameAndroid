package chess.chessgame;

import com.gamedev.framework.Audio;
import com.gamedev.framework.Sound;

public class PlaySounds
{
    public static void buttonSound(Audio audio)
    {
        playSound("CH.mp3", audio);
    }

    public static void winSound(Audio audio)
    {

    }

    public static void loseSound(Audio audio)
    {

    }

    public static void moveSound(Audio audio)
    {
        playSound("move.mp3", audio);
    }

    private static void playSound(String filename, Audio audio)
    {
        Sound s = audio.createSound(filename);
        s.play(10);
    }
}
