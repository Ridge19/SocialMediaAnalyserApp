package com.example.socialmediaanalyser;

//getters and setters
public class Posts implements Comparable<Posts> {
    public int PostID;
    public String Content;
    public String Author;
    public int Likes;
    public int Shares;
    public String dateTime;

    //posts (for arraylist)
    public Posts(int PostID, String Content, String Author, int Likes, int Shares, String dateTime) {
        this.PostID = PostID;
        this.Content = Content;
        this.Author = Author;
        this.Likes = Likes;
        this.Shares = Shares;
        this.dateTime = dateTime;
    }

    // Constructor for creating Posts objects with a value for the Likes field
    public Posts(int PostID, String Content, int Likes, int Shares) {
        this.PostID = PostID;
        this.Content = Content;
        this.Likes = Likes;
        this.Shares = Shares;
    }

    public Posts(int PostID, String Content, int Shares) {
        this.PostID = PostID;
        this.Content = Content;
        this.Shares = Shares;
    }

    //setters
    public void setPostID(int PostID) {
        this.PostID = PostID;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
    public void setAuthor(String Author) {
        this.Author = Author;
    }
    public void setLikes(int Likes) {
        this.Likes = Likes;
    }
    public void setShares(int Shares) {
        this.Shares = Shares;
    }
    public void setdateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    //getters
    public int getPostID() {
        return PostID;
    }
    public String getContent() {
        return Content;
    }
    public String getAuthor() {
        return Author;
    }
    public int getLikes() {
        return Likes;
    }
    public int getShares() {
        return Shares;
    }
    public String getdateTime() {
        return dateTime;
    }

    //toString method for output
    @Override
    public String toString() {
        return "Post ID |" + PostID + "\n" +
                "Post Content | " + Content + "\n" +
                "Post Author | " + Author + "\n" +
                "Post Likes | " + Likes + "\n" +
                "Post Shares | " + Shares + "\n" +
                "Post Date and Time | " + dateTime + "\n" +
                "-----------------------------------" + "\n\n";
    }

    @Override
    //sort by likes/shares
    public int compareTo(Posts p) {
        //returns posts in ascending order (lowest > highest by likes)
        return this.Likes - p.Likes;
    }

    public void add(Posts post) {
    }

}