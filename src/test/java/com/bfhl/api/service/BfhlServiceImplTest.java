package com.bfhl.api.service;

import com.bfhl.api.dto.BfhlRequest;
import com.bfhl.api.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    @Test
    void testNormalInputWithMixedData() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("1", "334", "4", "A", "R", "@", "#"));
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertEquals("anushka_kashiv_03092005", response.getUser_id());
        assertEquals("anushkakashiv231170@acropolis.in", response.getEmail());
        assertEquals("0827AL231030", response.getRoll_number());
        
        assertEquals(Arrays.asList("1"), response.getOdd_numbers());
        assertEquals(Arrays.asList("334", "4"), response.getEven_numbers());
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());
        assertEquals(Arrays.asList("@", "#"), response.getSpecial_characters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcat_string());
    }

    @Test
    void testExampleA() {
        // Example A: input alphabets = ["A", "R"], reversed = ["R", "A"], alternating caps = "Ra"
        BfhlRequest request = new BfhlRequest(Arrays.asList("1", "334", "4", "A", "R", "@", "#"));
        BfhlResponse response = service.processRequest(request);

        assertEquals("Ra", response.getConcat_string());
    }

    @Test
    void testExampleB() {
        // Example B: input alphabets = ["A", "Y", "B"], reversed = ["B", "Y", "A"], alternating caps = "ByA"
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "Y", "B", "1", "2"));
        BfhlResponse response = service.processRequest(request);

        assertEquals(Arrays.asList("A", "Y", "B"), response.getAlphabets());
        assertEquals("ByA", response.getConcat_string());
    }

    @Test
    void testExampleC() {
        // Example C: input alphabets = ["A","ABCD","DOE"]
        // Flattened: A,A,B,C,D,D,O,E
        // Reversed: E,O,D,D,C,B,A,A
        // Alternating caps: E,o,D,d,C,b,A,a = "EoDdCbAa"
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE", "1"));
        BfhlResponse response = service.processRequest(request);

        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals("EoDdCbAa", response.getConcat_string());
    }

    @Test
    void testEmptyArray() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertEquals("anushka_kashiv_03092005", response.getUser_id());
        assertTrue(response.getOdd_numbers().isEmpty());
        assertTrue(response.getEven_numbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecial_characters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcat_string());
    }

    @Test
    void testOnlyAlphabets() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "B", "C", "xyz"));
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertTrue(response.getOdd_numbers().isEmpty());
        assertTrue(response.getEven_numbers().isEmpty());
        assertEquals(Arrays.asList("A", "B", "C", "XYZ"), response.getAlphabets());
        assertTrue(response.getSpecial_characters().isEmpty());
        assertEquals("0", response.getSum());
        // Flattened: A,B,C,X,Y,Z
        // Reversed: Z,Y,X,C,B,A
        // Alternating: Z,y,X,c,B,a = "ZyXcBa"
        assertEquals("ZyXcBa", response.getConcat_string());
    }

    @Test
    void testOnlyNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("1", "2", "3", "4", "5"));
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertEquals(Arrays.asList("1", "3", "5"), response.getOdd_numbers());
        assertEquals(Arrays.asList("2", "4"), response.getEven_numbers());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecial_characters().isEmpty());
        assertEquals("15", response.getSum());
        assertEquals("", response.getConcat_string());
    }

    @Test
    void testOnlySpecialCharacters() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("@", "#", "$", "%", "&"));
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertTrue(response.getOdd_numbers().isEmpty());
        assertTrue(response.getEven_numbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(Arrays.asList("@", "#", "$", "%", "&"), response.getSpecial_characters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcat_string());
    }

    @Test
    void testLargeNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("100", "200", "334"));
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertTrue(response.getOdd_numbers().isEmpty());
        assertEquals(Arrays.asList("100", "200", "334"), response.getEven_numbers());
        assertEquals("634", response.getSum());
    }

    @Test
    void testMixedAlphanumeric() {
        // Mixed alphanumeric strings should be treated as special characters
        BfhlRequest request = new BfhlRequest(Arrays.asList("A1", "B2", "C3"));
        BfhlResponse response = service.processRequest(request);

        assertTrue(response.isIs_success());
        assertTrue(response.getOdd_numbers().isEmpty());
        assertTrue(response.getEven_numbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(Arrays.asList("A1", "B2", "C3"), response.getSpecial_characters());
    }
}
