package ue.edu.co.consumoapivolley.poo;

public class Post {
        private int userId;
        private int id;
        private String title;
        private String body;

        // Constructor
        public Post(int userId, int id, String title, String body) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.body = body;
        }

        // Getters en camelCase
        public int getUserId() {
            return userId;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }
    }


