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
            Sound s = audio.createSound("CH.mp3");
            s.play(10);
        }
}
