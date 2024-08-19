package com.kousenit.after;

import com.kousenit.ResponseFormat;
import com.kousenit.TextToSpeechService;
import com.kousenit.Voice;
import jakarta.validation.constraints.*;

public record TTSRequest(
        @Pattern(regexp = "tts-1(-hd)?") String model,
        @NotBlank @Size(max = 4096) String input,
        Voice voice,
        ResponseFormat responseFormat,
        @DecimalMin("0.25") @DecimalMax("4.0") double speed
) {
    // Overloaded constructors
    public TTSRequest(String model, String input, Voice voice) {
        this(model, input, voice, ResponseFormat.MP3, 1.0);
    }

    public TTSRequest(String input, Voice voice) {
        this(TextToSpeechService.TTS, input, voice, ResponseFormat.MP3, 1.0);
    }

    public TTSRequest(String input) {
        this(TextToSpeechService.TTS, input, Voice.randomVoice(), ResponseFormat.MP3, 1.0);
    }

    // Compact constructor for validation and transformation
    public TTSRequest {
        model = model != null ? model : TextToSpeechService.TTS;
        responseFormat = responseFormat != null ? responseFormat : ResponseFormat.MP3;

        if (speed < 0.25 || speed > 4.0) {
            throw new IllegalArgumentException("Speed must be between 0.25 and 4.0: " + speed);
        }
    }
}
