package com.example.aifriend;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class AIProblemSolver {

    private static final class Response {
        final String accept;
        final String reason;
        final String solution;
        final String ending;

        Response(String accept, String reason, String solution, String ending) {
            this.accept = accept;
            this.reason = reason;
            this.solution = solution;
            this.ending = ending;
        }

        String build() {
            return accept + "\n\n"
                    + "🔍 Reason:\n" + reason + "\n\n"
                    + "✅ Solution:\n" + solution + "\n\n"
                    + "🙂 " + ending;
        }
    }

    // Intent patterns and their responses (ordered: first match wins)
    private static final List<Intent> INTENTS = new ArrayList<>();

    static {
        // Study / School
        INTENTS.add(new Intent(
                Pattern.compile("\\b(padhai|study|exam)\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
                new Response(
                        "Main samajh raha hoon ki padhai mushkil lag rahi hai.",
                        "Zyada pressure aur sahi plan na hone se aisa hota hai.",
                        "Roz thoda-thoda padho, 25 minute padhai aur 5 minute break lo.",
                        "Dheere-dheere improvement aayega 👍"
                )
        ));

        // Time management
        INTENTS.add(new Intent(
                Pattern.compile("\\b(time|samay|time manage|time-management)\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
                new Response(
                        "Lagta hai aap time manage nahi kar pa rahe ho.",
                        "Jab sab kaam ek saath hota hai to confusion hota hai.",
                        "Ek simple list banao aur pehle important kaam karo.",
                        "Aadat ban jaayegi, tension mat lo 🙂"
                )
        ));

        // Confusion / not understanding
        INTENTS.add(new Intent(
                Pattern.compile("\\b(confuse|confused|samajh nahi|samajh nahi aata|samajh nahin)\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
                new Response(
                        "Main samajh sakta hoon ki aap confuse ho.",
                        "Basics clear na hone se doubt aata hai.",
                        "Problem ko chhote steps me tod kar samjho.",
                        "Clarity zaroor aayegi 👍"
                )
        ));

        // Motivation / tired / bored
        INTENTS.add(new Intent(
                Pattern.compile("\\b(mann nahi|bore|thak|tired|boring|motivation)\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE),
                new Response(
                        "Aisa feel hona normal hai.",
                        "Zyada pressure aur rest kam hone se motivation girta hai.",
                        "Thoda rest lo aur phir fresh mind se start karo.",
                        "Aap kar sakte ho, bharosa rakho 🙂"
                )
        ));
    }

    // Helper holder
    private static final class Intent {
        final Pattern pattern;
        final Response response;

        Intent(Pattern pattern, Response response) {
            this.pattern = pattern;
            this.response = response;
        }
    }

    // 4-Step Answer Builder (kept public for backward compatibility)
    public static String buildAIAnswer(String accept, String reason, String solution, String ending) {
        return accept + "\n\n"
                + "🔍 Reason:\n" + reason + "\n\n"
                + "✅ Solution:\n" + solution + "\n\n"
                + "🙂 " + ending;
    }

    // Main AI Logic
    public static String getAIResponse(String input) {
        if (input == null) {
            return "Apni problem likhiye, main madad karunga 🙂";
        }

        String normalized = normalize(input);
        if (normalized.isEmpty()) {
            return "Apni problem likhiye, main madad karunga 🙂";
        }

        for (Intent intent : INTENTS) {
            if (intent.pattern.matcher(normalized).find()) {
                return intent.response.build();
            }
        }

        // Default
        return buildAIAnswer(
                "Main aapki baat samajh raha hoon.",
                "Shayad problem thodi incomplete hai.",
                "Thoda detail me likho taaki main sahi madad kar sakun.",
                "Main yahin hoon madad ke liye 🤝"
        );
    }

    // Normalize whitespace, Unicode, and map to lower-case in a consistent locale
    private static String normalize(String s) {
        if (s == null) return "";
        String trimmed = s.trim();
        // Normalize unicode forms (e.g., composed vs decomposed characters)
        String normalized = Normalizer.normalize(trimmed, Normalizer.Form.NFKC);
        // Lower-case using ROOT to avoid locale-specific mapping surprises
        return normalized.toLowerCase(Locale.ROOT);
    }
}
