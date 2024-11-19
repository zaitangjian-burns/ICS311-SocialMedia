
import java.util.Date;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SocialMediaDataStructure socialMediaData = new SocialMediaDataStructure();
        socialMediaData.addUser("john_doe");
        socialMediaData.addUser("jane_smith");
        socialMediaData.addUser("alex_brown");

        SocialMediaDataStructure.Post post1 = new SocialMediaDataStructure.Post(
            "Hello world! Love conquers all. Travel broadens the mind, and experience shapes the soul.", 
            "john_doe", 
            new Date()
        );
        SocialMediaDataStructure.Post post2 = new SocialMediaDataStructure.Post(
            "Liebe ist alles. Amor vincit omnia! Music is the universal language that connects people.", 
            "jane_smith", 
            new Date()
        );
        SocialMediaDataStructure.Post post3 = new SocialMediaDataStructure.Post(
            "Innovation drives progress. Technology impacts every facet of life. Collaboration brings growth.", 
            "alex_brown", 
            new Date()
        );
        SocialMediaDataStructure.Post post4 = new SocialMediaDataStructure.Post(
            "Nature inspires creativity. Art reflects culture and history. Books tell the stories of the human condition.", 
            "john_doe", 
            new Date()
        );

        // Add posts
        socialMediaData.addPost(post1);
        socialMediaData.addPost(post2);
        socialMediaData.addPost(post3);
        socialMediaData.addPost(post4);

        //Generating and displaying the word cloud
        WordCloudGenerator generator = new WordCloudGenerator(socialMediaData);
        Map<String, Integer> wordFrequency = generator.generateWordCloud(null, null, null);
        String formattedOutput = generator.generateWordCloudList(wordFrequency);

        System.out.println(formattedOutput); // Outputs the formatted word frequencies

        TrendingPost trendingPost = new TrendingPost(socialMediaData);

        // Get trending posts from exactly 7 days ago 
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date oneWeekAgo = cal.getTime();

        System.out.println("Trending Posts in the Last 7 Days by View Count:");
        List<SocialMediaDataStructure.Post> lastWeekTrending = trendingPost.getTrendingPosts(
                "views",
                oneWeekAgo,
                new Date(),
                null
        );
        trendingPost.displayTrendingPosts(lastWeekTrending);

        // Get trending posts by comments for users in the USA
        Map<String, String> userFilters = new HashMap<>();
        userFilters.put("location", "USA");

        System.out.println("\nTrending Posts by Comments for Users in the USA:");
        List<SocialMediaDataStructure.Post> filteredTrending = trendingPost.getTrendingPosts(
                "comments",
                null,
                null,
                userFilters
        );
        trendingPost.displayTrendingPosts(filteredTrending);

    }
}
