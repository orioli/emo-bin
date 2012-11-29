import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;


class Playme {
    
    
    public static void playClip(String clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        
        class AudioListener implements LineListener {
            private boolean done = false;
            @Override public synchronized void update(LineEvent event) {
                Type eventType = event.getType();
                if (eventType == Type.STOP || eventType == Type.CLOSE) {
                    done = true;
                    notifyAll();
                }
            }
            public synchronized void waitUntilDone() throws InterruptedException {
                while (!done) { wait(); }
            }
        }
        
        AudioListener listener = new AudioListener();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( new File(clipFile));
        try {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(listener);
            clip.open(audioInputStream);
            try {
                clip.start();
                listener.waitUntilDone();
            } finally {
                clip.close();
            }
        } finally {
            audioInputStream.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        playClip(new File("wav/2.wav"));
        
    }

}
