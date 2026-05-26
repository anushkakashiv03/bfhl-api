package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final String USER_ID = "anushka_kashiv_03092005";
    private static final String EMAIL = "anushkakashiv231170@acropolis.in";
    private static final String ROLL_NUMBER = "0827AL231030";

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        BfhlResponse response = new BfhlResponse();
        
        // Set hardcoded values
        response.setUser_id(USER_ID);
        response.setEmail(EMAIL);
        response.setRoll_number(ROLL_NUMBER);
        response.setIs_success(true);

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;

        List<String> data = request.getData();
        if (data != null) {
            for (String item : data) {
                if (item == null || item.isEmpty()) {
                    continue;
                }

                if (isNumeric(item)) {
                    // Numeric item
                    long num = Long.parseLong(item);
                    sum += num;
                    if (num % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } else if (isAlphabetic(item)) {
                    // Alphabetic item
                    alphabets.add(item.toUpperCase());
                } else if (isSpecialCharacter(item)) {
                    // Special character item (no alphanumeric)
                    specialCharacters.add(item);
                } else {
                    // Mixed alphanumeric - treat as special
                    specialCharacters.add(item);
                }
            }
        }

        response.setOdd_numbers(oddNumbers);
        response.setEven_numbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecial_characters(specialCharacters);
        response.setSum(String.valueOf(sum));
        response.setConcat_string(generateConcatString(alphabets));

        return response;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isAlphabetic(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSpecialCharacter(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private String generateConcatString(List<String> alphabets) {
        if (alphabets == null || alphabets.isEmpty()) {
            return "";
        }

        // Flatten all characters from all alphabetic items
        StringBuilder allChars = new StringBuilder();
        for (String item : alphabets) {
            allChars.append(item);
        }

        // Reverse the full character sequence
        String reversed = allChars.reverse().toString();

        // Apply alternating caps: index 0 = uppercase, index 1 = lowercase, etc.
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }
}
