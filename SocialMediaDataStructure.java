import java.util.*;

public class SocialMediaDataStructure {
    // User class to represent each user in the social network
    public static class User {
        String username;
        Set<String> connections;
        List<Post> postsAuthored;
        List<Post> postsRead;
        List<Comment> comments;
        Map<String, String> attributes;

        public User(String username) {
            this.username = username;
            this.connections = new HashSet<>();
            this.postsAuthored = new ArrayList<>();
            this.postsRead = new ArrayList<>();
            this.comments = new ArrayList<>();
            this.attributes = new HashMap<>();
        }

        public void addConnection(String connection) {
            connections.add(connection);
        }

        public void addPost(Post post) {
            postsAuthored.add(post);
        }

        public void addReadPost(Post post) {
            postsRead.add(post);
        }

        public void addComment(Comment comment) {
            comments.add(comment);
        }

        public void setAttribute(String key, String value) {
            attributes.put(key, value);
        }
    }

    // Post class to represent posts in the network
    public static class Post {
        String content;
        String authorUsername;
        List<Comment> comments;
        List<String> viewedBy;
        Date creationDate;
        int viewCount; //how many views
        int commentCount; //how many comments

        public Post(String content, String authorUsername, Date creationDate) {
            this.content = content;
            this.authorUsername = authorUsername;
            this.comments = new ArrayList<>();
            this.viewedBy = new ArrayList<>();
            this.creationDate = creationDate;
            this.viewCount = 0;
            this.commentCount = 0;
        }

        public void addComment(Comment comment) {
            comments.add(comment);
            commentCount++; //increase the comment count
        }

        public void addView(String username) {
            if (!viewedBy.contains(username)) {
                viewedBy.add(username);
                viewCount++; //increase the view count
            }
        }
    }

    // Comment class for comments made on posts
    public static class Comment {
        String content;
        String authorUsername;
        Date creationDate;

        public Comment(String content, String authorUsername, Date creationDate) {
            this.content = content;
            this.authorUsername = authorUsername;
            this.creationDate = creationDate;
        }
    }

    // SocialMediaData structure to manage users and posts
    private Map<String, User> users;
    private List<Post> posts;

    public SocialMediaDataStructure() {
        users = new HashMap<>();
        posts = new ArrayList<>();
    }

    public void addUser(String username) {
        users.putIfAbsent(username, new User(username));
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addPost(Post post) {
        posts.add(post);
        User author = getUser(post.authorUsername);
        if (author != null) {
            author.addPost(post);
        }
    }

    public List<Post> getAllPosts() {
        return posts;
    }

    //Find the trending posts
    public List<Post> getTrendingPosts(String sortBy) {
        List<Post> sortedPosts = new ArrayList<>(posts);
        
        // Sort the posts based on views or comments
        if (sortBy.equals("views")) {
            sortedPosts.sort((p1, p2) -> Integer.compare(p2.viewCount, p1.viewCount));
        } else if (sortBy.equals("comments")) {
            sortedPosts.sort((p1, p2) -> Integer.compare(p2.commentCount, p1.commentCount));
        }
        
        return sortedPosts;
    }
}
