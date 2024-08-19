package com.kousenit;

import com.kousenit.after.TTSRequest;
import javazoom.jl.player.Player;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

class TextToSpeechServiceTest {
    private final TextToSpeechService tts = new TextToSpeechService();

    @Test
    void testGenerateMp3_v2() throws Exception {
        var ttsRequest = new TTSRequest("""
                The YouTube channel, 'Tales from the jar side', is your best
                source for learning about Java, Spring, and other open source
                technologies, especially when combined with AI tools.
                The companion newsletter is also a lot of fun.
            """);
        Path path = tts.generateMp3_v2(ttsRequest);
        System.out.println("Generated audio file: " + path);

        try (var inputStream = Files.newInputStream(path)) {
            new Player(inputStream).play();
        }
    }

    @Test
    void testGenerateMp3_v1() throws Exception {
        Path path = tts.generateMp3_v1(TextToSpeechService.TTS_HD, """
                The YouTube channel, 'Tales from the jar side', is your best
                source for learning about Java, Spring, and other open source
                technologies, especially when combined with AI tools.
                The companion newsletter is also a lot of fun.
                """, "fable");
        System.out.println("Generated audio file: " + path);

        try (var inputStream = Files.newInputStream(path)) {
            new Player(inputStream).play();
        }
    }


}