package com.example.socialmediaanalyser;

//getters and setters
public class Posts {
    public int PostID;
    public String Content;
    public String Author;
    public int Likes;
    public int Shares;
    public String dateTime;

    // posts (compareTo)
    public Posts(int PostID, int Likes, int Shares) {
        this.PostID = PostID;
        this.Likes = Likes;
        this.Shares = Shares;
    }

    //posts (for arraylist)
    public Posts(int PostID, String Content, String Author, int Likes, int Shares, String dateTime) {
        this.PostID = PostID;
        this.Content = Content;
        this.Author = Author;
        this.Likes = Likes;
        this.Shares = Shares;
        this.dateTime = dateTime;
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
        return "Post ID: " + PostID + "\n" +
                "Post Content: " + Content + "\n" +
                "Post Author: " + Author + "\n" +
                "Post Likes: " + Likes + "\n" +
                "Post Shares: " + Shares + "\n" +
                "Post Date and Time: " + dateTime + "\n" +
                "-----------------------------------" + "\n";
    }

}
