// === TextToSpeechService.java ===
package com.receptro.mediapipeline.service.synthesize;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import javax.sound.sampled.AudioFileFormat.Type;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import java.io.File;

@Service
public class TextToSpeechService {

    public String synthesize(String text, String filename) throws Exception {
        // Get absolute path to static/audio
        File audioDir = new ClassPathResource("static/audio").getFile();
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }

        String fullPath = new File(audioDir, filename + ".wav").getAbsolutePath();

        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice == null) throw new IllegalStateException("Voice not found");

        voice.allocate();

        SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer(fullPath.replace(".wav", ""), Type.WAVE);
        voice.setAudioPlayer(audioPlayer);
        voice.speak(text);
        audioPlayer.close();
        voice.deallocate();

        return "/audio/" + filename + ".wav"; // browser-accessible path
    }
}