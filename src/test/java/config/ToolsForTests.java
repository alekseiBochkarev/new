package config;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class ToolsForTests {
    public static String getRandomString(final int length, final boolean useLetters, final boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static int randomIntWithInterval(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
