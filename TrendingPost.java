import java.util.*;
import java.util.stream.Collectors;

public class TrendingPost {
    private final SocialMediaDataStructure socialMediaData;

    public TrendingPost(SocialMediaDataStructure socialMediaData) {
        this.socialMediaData = socialMediaData;
    }

   // Trending posts based on views, comments, and attributes
    public List<SocialMediaDataStructure.Post> getTrendingPosts(
            String sortBy,
            Date startDate,
            Date endDate,
            Map<String, String> userFilters
    ) {
        List<SocialMediaDataStructure.Post> posts = socialMediaData.getAllPosts();

        // Filters posts depending on the date 
        if (startDate != null || endDate != null) {
            posts = posts.stream().filter(post -> {
                boolean afterStart = startDate == null || post.creationDate.after(startDate);
                boolean beforeEnd = endDate == null || post.creationDate.before(endDate);
                return afterStart && beforeEnd;
            }).collect(Collectors.toList());
        }

        // Filters posts depending on attributes
        if (userFilters != null && !userFilters.isEmpty()) {
            posts = posts.stream().filter(post -> {
                SocialMediaDataStructure.User author = socialMediaData.getUser(post.authorUsername);
                if (author == null) return false;

                for (Map.Entry<String, String> filter : userFilters.entrySet()) {
                    String attributeValue = author.attributes.get(filter.getKey());
                    if (attributeValue == null || !attributeValue.equals(filter.getValue())) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
        }

        // Sorts posts
        posts.sort((p1, p2) -> {
            if ("views".equalsIgnoreCase(sortBy)) {
                return Integer.compare(p2.viewCount, p1.viewCount);
            } else if ("comments".equalsIgnoreCase(sortBy)) {
                return Integer.compare(p2.commentCount, p1.commentCount);
            }
            return 0;
        });

        return posts;
    }

    // Shows the trending posts
    public void displayTrendingPosts(List<SocialMediaDataStructure.Post> trendingPosts) {
        for (SocialMediaDataStructure.Post post : trendingPosts) {
            System.out.println(
                    "Post: " + post.content +
                    " | Views: " + post.viewCount +
                    " | Comments: " + post.commentCount +
                    " | Author: " + post.authorUsername +
                    " | Date: " + post.creationDate
            );
        }
    }
}
