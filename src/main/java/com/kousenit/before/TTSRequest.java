package com.kousenit.before;

public record TTSRequest(
        String model,
        String input,
        String voice,
        String responseFormat,
        double speed
) {
}
