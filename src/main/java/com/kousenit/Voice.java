package com.kousenit;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

public enum Voice {
    @SerializedName("alloy") ALLOY,
    @SerializedName("echo") ECHO,
    @SerializedName("fable") FABLE,
    @SerializedName("onyx") ONYX,
    @SerializedName("nova") NOVA,
    @SerializedName("shimmer") SHIMMER;

    public static Voice randomVoice()  {
        Random random = new Random();
        Voice[] voices = values();
        return voices[random.nextInt(voices.length)];
    }
}
