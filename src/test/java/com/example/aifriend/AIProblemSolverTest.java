package com.example.aifriend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIProblemSolverTest {

    @Test
    void testStudyIntent() {
        String resp = AIProblemSolver.getAIResponse("Mujhe padhai mein problem aa rahi hai");
        assertTrue(resp.contains("padhai mushkil"), "Expected study-related response");
    }

    @Test
    void testTimeIntent() {
        String resp = AIProblemSolver.getAIResponse("Mera time manage nahi ho raha");
        assertTrue(resp.toLowerCase().contains("time manage") || resp.contains("time"), "Expected time-management response");
    }

    @Test
    void testDefault() {
        String resp = AIProblemSolver.getAIResponse("xyz");
        assertTrue(resp.contains("Thoda detail me likho") || resp.contains("Main aapki baat samajh raha hoon"), "Expected default fallback");
    }
}
