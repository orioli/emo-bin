import javax.sound.midi.*;
import javax.swing.JOptionPane;
import java.net.URL;

class PlayMidi {
    
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://pscode.org/media/EverLove.mid");
        
        Sequence sequence = MidiSystem.getSequence(url);
        Sequencer sequencer = MidiSystem.getSequencer();
        
        sequencer.open();
        sequencer.setSequence(sequence);
        
        sequencer.start();
        JOptionPane.showMessageDialog(null, "Everlasting Love");
    }
}

