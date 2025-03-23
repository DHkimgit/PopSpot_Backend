package io.devtab.popspot.domain.company.service.implement;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class CompanyAccountInviteCodeGenerator {
    private static final String PREFIX = "CP";
    private static final int RANDOM_LENGTH = 6;

    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateCode() {
        String randomPart = generateRandomString();

        return String.format("%s-%s", PREFIX, randomPart);
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder(RANDOM_LENGTH);
        for (int i = 0; i < RANDOM_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
