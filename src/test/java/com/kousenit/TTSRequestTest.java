package com.kousenit;

import com.kousenit.after.TTSRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TTSRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validRequestShouldPassValidation() {
        TTSRequest request = new TTSRequest(TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Valid request should have no violations");
    }


    // "Model" tests
    @ParameterizedTest
    @ValueSource(strings = {TextToSpeechService.TTS, TextToSpeechService.TTS_HD})
    void validModelsShouldPassValidation(String model) {
        TTSRequest request = new TTSRequest(model, "Hello", Voice.ALLOY, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Valid model " + model + " should have no violations");
    }

    @Test
    void invalidModelShouldFailValidation() {
        TTSRequest request = new TTSRequest("tts-2", "Hello", Voice.ALLOY, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Invalid model should have violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("model")),
                "Violation should be on the 'model' field");
    }

    // "Input" tests
    @Test
    void validInputShouldPassValidation() {
        TTSRequest request = new TTSRequest(TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Valid input should have no violations");
    }

    @Test
    void blankInputShouldFailValidation() {
        TTSRequest request = new TTSRequest(
                TextToSpeechService.TTS, "  ", Voice.ALLOY, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Blank input should have violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("input")),
                "Violation should be on the 'input' field");
    }

    @Test
    void longInputShouldFailValidation() {
        TTSRequest request = new TTSRequest(
                TextToSpeechService.TTS, "x".repeat(4097), Voice.ALLOY, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Long input should have violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("input")),
                "Violation should be on the 'input' field");
    }

    // "Speed" tests
    @Test
    void validSpeedsShouldPassValidation() {
        assertAll(
                // Upper boundary
                () -> assertDoesNotThrow(() -> new TTSRequest(
                        TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 0.25)),
                // Lower boundary
                () -> assertDoesNotThrow(() -> new TTSRequest(
                        TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 4.0)),
                // Middle
                () -> assertDoesNotThrow(() -> new TTSRequest(
                        TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 1.0))
        );
    }

    @Test
    void invalidSpeedsShouldFailValidation() {
        assertAll(
                // Below lower boundary
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new TTSRequest(
                                TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 0.24)),
                // Above upper boundary
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new TTSRequest(
                                TextToSpeechService.TTS, "Hello", Voice.ALLOY, ResponseFormat.MP3, 4.01))
        );
    }

    // "Response format" tests
    @ParameterizedTest
    @EnumSource(ResponseFormat.class)
    void validResponseFormatsShouldPassValidation(ResponseFormat format) {
        TTSRequest request = new TTSRequest(
                TextToSpeechService.TTS, "Hello", Voice.ALLOY, format, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(),
                "Valid response format " + format + " should have no violations");
    }

    // "Voice" tests
    @ParameterizedTest
    @EnumSource(Voice.class)
    void validVoicesShouldPassValidation(Voice voice) {
        TTSRequest request = new TTSRequest(
                TextToSpeechService.TTS, "Hello", voice, ResponseFormat.MP3, 1.0);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(),
                "Valid voice " + voice + " should have no violations");
    }

    // Constructor tests
    @Test
    void testThreeArgConstructor() {
        TTSRequest request = new TTSRequest(TextToSpeechService.TTS, "Hello", Voice.ALLOY);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Valid request should have no violations");
    }

    @Test
    void testTwoArgConstructor() {
        TTSRequest request = new TTSRequest("Hello", Voice.ALLOY);
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Valid request should have no violations");
    }

    @Test
    void testOneArgConstructor() {
        TTSRequest request = new TTSRequest("Hello");
        Set<ConstraintViolation<TTSRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Valid request should have no violations");
    }

}