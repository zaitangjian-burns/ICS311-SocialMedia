import java.util.*;
import java.util.stream.Collectors;

public class WordCloudGenerator {
    private SocialMediaDataStructure data;

    public WordCloudGenerator(SocialMediaDataStructure data) {
        this.data = data;
    }

    public Map<String, Integer> generateWordCloud(Set<String> usernames, Set<String> keywordsToInclude, Set<String> keywordsToExclude) {
        // Create a word frequency map
        Map<String, Integer> wordFrequency = new HashMap<>();

        // Filter posts by user attributes (if usernames are provided)
        List<SocialMediaDataStructure.Post> filteredPosts = data.getAllPosts().stream()
            .filter(post -> usernames == null || usernames.contains(post.authorUsername))
            .collect(Collectors.toList());

        // Process each post and count word frequencies
        for (SocialMediaDataStructure.Post post : filteredPosts) {
            String[] words = post.content.split("\\s+");
            for (String word : words) {
                word = word.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                if ((keywordsToInclude == null || keywordsToInclude.contains(word)) &&
                    (keywordsToExclude == null || !keywordsToExclude.contains(word))) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }
        return wordFrequency;
    }

    public String generateWordCloudList(Map<String, Integer> wordFrequency) {
        StringBuilder outputBuilder = new StringBuilder();
        wordFrequency.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Sort by frequency descending
            .forEach(entry -> outputBuilder.append(entry.getValue()).append(" ").append(entry.getKey()).append("\n"));
        return outputBuilder.toString().trim();
    }
}
