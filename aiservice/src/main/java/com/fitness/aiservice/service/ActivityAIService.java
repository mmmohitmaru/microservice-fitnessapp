package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendations(prompt);
        //String aiResponse = performaRecommendations();

        log.info("RESPONSE FROM AI {} ", aiResponse);
        return processAIResponse(activity, aiResponse);
    }

    private String performaRecommendations() {
        return "{\n" +
                "  \"candidates\": [\n" +
                "    {\n" +
                "      \"content\": {\n" +
                "        \"parts\": [\n" +
                "          {\n" +
                "            \"text\": \"```json\\n{\\n    \\\"analysis\\\": {\\n        \\\"overall\\\": \\\"This activity indicates a steady, low-to-moderate intensity run. With a duration of 51 minutes and a distance of 5.2 km, the pace is quite relaxed. The heart rate of 129 bpm aligns with a comfortable aerobic zone, suggesting the individual was able to maintain a conversational pace throughout. The calorie burn of 306 is consistent with this level of effort and duration. This appears to be a good base-building or recovery run.\\\",\\n        \\\"pace\\\": \\\"The pace calculates to approximately 9 minutes 49 seconds per kilometer (or 6 minutes 1 second per mile), which is a very relaxed, easy jogging pace. This suggests either a beginner runner, someone focusing on endurance without speed, or an intentional recovery run. While excellent for building an aerobic base, there's significant room to incorporate faster segments for performance improvement.\\\",\\n        \\\"heartRate\\\": \\\"A heart rate of 129 bpm typically falls within the Zone 2 (aerobic base) or low Zone 3 (moderate intensity) for most healthy adults, depending on age and individual maximum heart rate. This indicates a sustainable effort that primarily trains the cardiovascular system to become more efficient at utilizing oxygen. It's an ideal zone for improving endurance and fat burning, but to improve speed and cardiovascular capacity, higher intensity zones should also be targeted periodically.\\\",\\n        \\\"caloriesBurned\\\": \\\"Burning 306 calories in 51 minutes while covering 5.2 km is consistent with a low-to-moderate intensity run. The actual number of calories burned can vary based on individual factors like weight, age, and metabolism, but this figure is reasonable for the reported pace and heart rate. To increase calorie expenditure in future runs, the duration or intensity (pace/heart rate) would need to be increased.\\\"\\n    },\\n    \\\"improvements\\\": [\\n        {\\n            \\\"area\\\": \\\"Endurance and Duration\\\",\\n            \\\"recommendation\\\": \\\"Given the comfortable pace and heart rate, you could gradually increase the duration of your long runs. Aim to add 5-10 minutes to one run per week, maintaining this easy, conversational pace. This will further enhance your aerobic capacity and stamina.\\\"\\n        },\\n        {\\n            \\\"area\\\": \\\"Speed and Cardiovascular Fitness\\\",\\n            \\\"recommendation\\\": \\\"To improve your running speed and challenge your cardiovascular system more, consider incorporating one high-intensity interval session per week. For example, after a warm-up, run at a challenging (but sustainable) pace for 1-2 minutes, then jog slowly for 2-3 minutes. Repeat this cycle 4-6 times before a cool-down.\\\"\\n        },\\n        {\\n            \\\"area\\\": \\\"Running Economy and Strength\\\",\\n            \\\"recommendation\\\": \\\"Integrate 2-3 sessions of full-body strength training per week, focusing on compound movements like squats, lunges, deadlifts, and planks. Strengthening your core, glutes, and legs will improve your running form, reduce injury risk, and enhance your overall running efficiency.\\\"\\n        },\\n        {\\n            \\\"area\\\": \\\"Variability in Training\\\",\\n            \\\"recommendation\\\": \\\"Introduce different types of runs into your weekly routine beyond just steady-state runs. This could include tempo runs (maintaining a comfortably hard pace for 20-30 minutes), hill repeats, or fartlek (speed play) workouts to engage different energy systems and muscles.\\\"\\n        }\\n    ],\\n    \\\"suggestions\\\": [\\n        {\\n            \\\"workout\\\": \\\"Progressive Long Run\\\",\\n            \\\"description\\\": \\\"Start with a 10-minute warm-up at an easy pace. Then, run for 60-70 minutes, gradually increasing your pace every 15-20 minutes. Begin at your current comfortable pace (e.g., 9:49/km), then pick it up slightly (e.g., 9:30/km), and finish the last 15-20 minutes at a comfortably challenging pace (e.g., 9:00/km or faster if comfortable). Finish with a 5-10 minute cool-down.\\\"\\n        },\\n        {\\n            \\\"workout\\\": \\\"Fartlek (Speed Play) Session\\\",\\n            \\\"description\\\": \\\"After a 10-15 minute easy warm-up jog, perform 6-8 repetitions of the following: run 'hard but controlled' for 2-3 minutes (where you can only speak a few words), followed by an equal amount of easy jogging (recovery). Vary the duration of the 'hard' segments. Conclude with a 10-minute cool-down jog and stretching.\\\"\\n        },\\n        {\\n            \\\"workout\\\": \\\"Hill Repeats\\\",\\n            \\\"description\\\": \\\"Find a moderate hill that takes 60-90 seconds to run up. After a 15-minute easy warm-up, run up the hill at a strong, controlled effort (not a sprint) focusing on good form. Jog or walk back down for recovery. Repeat this 5-8 times. Finish with a 10-minute easy jog cool-down and stretching. This builds leg strength and power.\\\"\\n        }\\n    ],\\n    \\\"safety\\\": [\\n        \\\"Always perform a dynamic warm-up (e.g., leg swings, arm circles) before your run and a static cool-down (holding stretches) afterwards to prevent injuries.\\\",\\n        \\\"Listen to your body and do not push through sharp or persistent pain. Rest or seek professional advice if needed.\\\",\\n        \\\"Ensure you wear appropriate running shoes that are not overly worn out. Replace them every 500-800 kilometers (300-500 miles) or every 6-12 months, depending on use.\\\",\\n        \\\"Stay hydrated by drinking water throughout the day, especially before and after your runs.\\\",\\n        \\\"Gradually increase your mileage, duration, or intensity by no more than 10% per week to allow your body to adapt and minimize injury risk.\\\",\\n        \\\"Be aware of your surroundings, especially if running outdoors. Pay attention to traffic, uneven terrain, and weather conditions.\\\"\\n    ]\\n}\\n```\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"role\": \"model\"\n" +
                "      },\n" +
                "      \"finishReason\": \"STOP\",\n" +
                "      \"index\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"usageMetadata\": {\n" +
                "    \"promptTokenCount\": 250,\n" +
                "    \"candidatesTokenCount\": 1319,\n" +
                "    \"totalTokenCount\": 2570,\n" +
                "    \"promptTokensDetails\": [\n" +
                "      {\n" +
                "        \"modality\": \"TEXT\",\n" +
                "        \"tokenCount\": 250\n" +
                "      }\n" +
                "    ],\n" +
                "    \"thoughtsTokenCount\": 1001\n" +
                "  },\n" +
                "  \"modelVersion\": \"gemini-2.5-flash\",\n" +
                "  \"responseId\": \"B8gNaebFHrT2juMPpp3nwQs\"\n" +
                "}";
    }

    private Recommendation processAIResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();

            log.info("RESPONSE FROM CLEANED {} ", jsonContent);

            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.get("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories:");

            List<String> improvements = extractImprovements(analysisJson.get("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.get("suggestions"));
            List<String> safety = extractSafetyGuidelines(analysisJson.get("safety"));


            return Recommendation.builder().
                    activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().name())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder().
                activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().name())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness consultant"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if (safetyNode.isArray()) {
            safetyNode.forEach(item -> safety.add(item.asText()));
        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow general safety guidelines") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionsNode.isArray()) {
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestion provided.") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {

        List<String> improvements = new ArrayList<>();

        if (improvementsNode.isArray()) {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String details = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, details));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific improvements provided.") :
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
                        {
                            "analysis": {
                                "overall": "Overall analysis here",
                                "pace": "Pace analysis here",
                                "heartRate": "Heart reate analysis here",
                                "caloriesBurned": "Calories analysis here"
                            },
                            "improvements": [
                                {
                                    "area": "Area name",
                                    "recommendation": "Detailed recommendation"
                                }
                            ],
                            "suggestions": [
                                {
                                    "workout": "Workout name",
                                    "description": "Detailed workout description"
                                }
                            ],
                            "safety": [
                                "Safety point 1",
                                "Safety point 2"
                            ]
                        }
                        
                        Analyze this activity:
                        Activity Type: %s
                        Duration: %d minutes
                        Calories Burned: %d
                        Additional Metrics: %s
                        
                        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
                        Ensure the response follows the EXACT JSON format shown above.
                        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}
