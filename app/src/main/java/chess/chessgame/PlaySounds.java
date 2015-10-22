package chess.chessgame;

import com.gamedev.framework.Audio;
import com.gamedev.framework.Game;
import com.gamedev.framework.Sound;

/**
 * Created by Home on 10/22/2015.
 */
public class PlaySounds {
        private static Audio audio;
        public static void buttonSound(Game g)
        {
            audio= g.getAudio();
            playSound("CH.mp3");
        }
        public static void winSound(Game g)
        {

        }
        public static void loseSound(Game g)
        {

        }
        public static void moveSound(Game g)
        {
            audio=g.getAudio();
            playSound("move.mp3");
        }
    
        private static void playSound(String filename)
        {
            Sound s= audio.createSound(filename);
            s.play(10);
        }
}
