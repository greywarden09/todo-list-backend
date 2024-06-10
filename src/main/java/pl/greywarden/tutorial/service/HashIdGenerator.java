package pl.greywarden.tutorial.service;

import jakarta.inject.Singleton;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

@Singleton
public class HashIdGenerator {
    private static final int HASH_LENGTH = 12;

    public String generateHashId() {
        final var source = new byte[HASH_LENGTH];
        new Random().nextBytes(source);
        return DigestUtils.md5Hex(source).substring(0, HASH_LENGTH - 1);
    }
}
