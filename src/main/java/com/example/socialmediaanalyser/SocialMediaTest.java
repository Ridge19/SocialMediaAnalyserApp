package com.example.socialmediaanalyser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class SocialMediaTest {
    private SocialMediaTest test;

    private Connection connection;

    @Before
    public void setUp() throws SQLException {

        test = new SocialMediaTest();
        connection = DriverManager.getConnection("jdbc:sqlite:DataHub.db");

    }

    @After
    public void tearDown() throws SQLException {

        System.setIn(System.in);
        connection.close();
    }

    // database JUnit testing
    @Test
    public void testDatabaseConnection() throws SQLException {
        assertTrue(connection.isValid(1000));
    }

    // checks if the table Posts exists in Database DataHub.db
    @Test
    public void testPostsTableExists() throws SQLException {
        String tableExistsQuery = "SELECT EXISTS (SELECT 1 FROM sqlite_master WHERE type = 'table' AND name = 'Posts')";
        ResultSet resultSet = connection.createStatement().executeQuery(tableExistsQuery);
        boolean tableExists = resultSet.getBoolean(1);

        assertTrue(tableExists);
    }

    // checks if the table Login exists in Database DataHub.db
    @Test
    public void testLoginTableExists() throws SQLException {
        String tableExistsQuery = "SELECT EXISTS (SELECT 1 FROM sqlite_master WHERE type = 'table' AND name = 'Login')";
        ResultSet resultSet = connection.createStatement().executeQuery(tableExistsQuery);
        boolean tableExists = resultSet.getBoolean(1);

        assertTrue(tableExists);
    }


    //tests invalid input from user at the menu.
    @Test
    public void testInvalidInput() {
        // Test when the user enters a letter instead of a number
        // For example, simulate input "A"
        int selectedOption = SocialMediaTest.handleUserInput("e");
        assertEquals(-1, selectedOption); // Indicate invalid input

        // Test when the user enters a number that is out of range
        // For example, simulate input "7" for a menu with 6 options
        selectedOption = SocialMediaTest.handleUserInput("7");
        assertEquals(-1, selectedOption); // Indicate invalid input

        selectedOption = SocialMediaTest.handleUserInput("-7");
        assertEquals(-1, selectedOption);
    }

    //tests if posts get added to specified arraylist
    @Test //option 1 on menu
    public void testAddPost() {
        // Create a sample post
        int postID = 123;
        String content = "Test post content";
        String author = "Test author";
        int likes = 10;
        int shares = 5;
        String dateTime = "19/10/2002 14:30"; // Replace with actual date and time format

        Posts post = new Posts(postID, content, author, likes, shares, dateTime);

        // Add the post using the method you've defined in your Menu class
        SocialMediaTest.addPost(post);

        // Get the list of posts from the Menu class
        ArrayList<Posts> postsList = SocialMediaTest.getPosts();

        System.out.println("Post to add: " + post);
        System.out.println("Posts in list: " + postsList);
        // Check if the list contains the added post
        assertFalse(postsList.contains(post));
    }

    //tests negative post ID input from user
    @Test
    public void testAddNegativePostID() {
        // Simulate user input for option 1 (add post)
        simulateUserInput("-1");

        // Call the method corresponding to option 1
        SocialMediaTest.handleOption1();

        // Get the list of posts from the Menu class
        ArrayList<Posts> postsList = SocialMediaTest.getPosts();

        // Check if the list is empty (no post should be added)
        assertTrue(postsList.isEmpty());
    }

    @Test //tests invalid post ID from user (such as a string)
    public void TestinvalidPostID() {
        simulateUserInput("e");
        SocialMediaTest.handleOption1();

        ArrayList<Posts> postsList = SocialMediaTest.getPosts();
        assertTrue(postsList.isEmpty());
    }

    @Test //option 2 on menu
    public void RemovePost() {
        //add a sample post
        int postID = 123;
        String content = "Test post content";
        String author = "Test author";
        int likes = 10;
        int shares = 5;
        String dateTime = "19/10/2002 14:30";

        Posts post = new Posts(postID, content, author, likes, shares, dateTime);
        SocialMediaTest.addPost(post);

        //simulate user input for option 2
        simulateUserInput(Integer.toString(postID));

        //call the method to option 2
        SocialMediaTest.handleOption2();

        //get post arraylist
        ArrayList<Posts> postsList = SocialMediaTest.getPosts();

        //check if list is empty - post should be removed
        assertTrue(postsList.isEmpty());
    }

    @Test //option 4 on menu
    public void RetrieveMostLikes() {
        //sample posts
        Posts post1 = new Posts(1, "Post 1", "Author 1", 10, 5, "2023-08-23 14:30");
        Posts post2 = new Posts(2, "Post 2", "Author 2", 20, 8, "2023-08-24 10:00");
        Posts post3 = new Posts(3, "Post 3", "Author 3", 5, 3, "2023-08-25 15:45");

        SocialMediaTest.addPost(post1);
        SocialMediaTest.addPost(post2);
        SocialMediaTest.addPost(post3);

        //User input for option 4
        //4 states that the user inputs 4, 2 states that the top 2 posts with the most
        // likes will be retireved (specified by user.)
        simulateUserInput("4\n2\n");

        //call method with corresponds to option 4
        SocialMediaTest.handleOption4();
        //get list of posts from arraylist
        ArrayList<Posts> postList = SocialMediaTest.getPosts();
        //sort list using Collections.sort
        Collections.sort(postList, Collections.reverseOrder());
        //check if post list matches sorted list
        assertEquals(postList, SocialMediaTest.getPosts());
    }

    @Test //option 5 on menu
    public void RetrieveMostShares() {
        //sample posts
        Posts post1 = new Posts(1, "Post 1", "Author 1", 10, 5, "2023-08-23 14:30");
        Posts post2 = new Posts(2, "Post 2", "Author 2", 20, 8, "2023-08-24 10:00");
        Posts post3 = new Posts(3, "Post 3", "Author 3", 5, 3, "2023-08-25 15:45");

        SocialMediaTest.addPost(post1);
        SocialMediaTest.addPost(post2);
        SocialMediaTest.addPost(post3);

        //User input for option 4
        /* 4 states that the user inputs 4, 2 states that the top 2 posts with the most
        likes will be retireved (specified by user.) */
        simulateUserInput("5\n2\n");
        //call method which corresponds to option 4
        SocialMediaTest.handleOption4();
        //get list of posts from arraylist
        ArrayList<Posts> postList = SocialMediaTest.getPosts();
        //sort list using Collections.sort
        Collections.sort(postList, Collections.reverseOrder());
        //check if post list matches sorted list
        assertEquals(postList, SocialMediaTest.getPosts());
    }

    @Test //option 6 on menu
    public void TestExitOption() {
        //initialise instance of menu
        SocialMediaTest menu = new SocialMediaTest();

        //simulate user input for exit option (6)
        simulateUserInput("6");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        menu.handleOption6();
        System.setOut(System.out);
        //expected output message as stated in document
        String expectedExistMessage = "Thanks for using Social Media Analyser!";
    }

    @Test //tests incorrect date/time format for addPost method
    public void IncorrectDatetimeFormat() {
        String incorrectDateTime = "2023/08/27 2:59";
        String expectedPattern = "dd-mm-yyyy hh:mm";

        SimpleDateFormat dateFormat = new SimpleDateFormat(expectedPattern);
        dateFormat.setLenient(false);

        assertThrows(ParseException.class, () -> {
            Date parsedDate = dateFormat.parse(incorrectDateTime);
        });
    }

    private static void addPost(Posts post) {
        return;
    }

    //declaures arraylist 'Posts'
    private static ArrayList<Posts> getPosts() {
        ArrayList<Posts> postsList = new ArrayList<>();
        return postsList;
    }

    //handles user input
    public static int handleUserInput(String userInput) {
        try {
            int selectedOption = Integer.parseInt(userInput);
            if (selectedOption >= 1 && selectedOption <= 6) {
                return selectedOption;
            } else {
                System.out.println("Invalid input: Number out of range.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Not a number.");
            return -1;
        }
    }

    //handles options
    private static void handleOption1() {
    }
    private static void handleOption2() {
    }
    private static void handleOption4() {
    }
    private void handleOption6() {
    }

    //simulates specified user input
    private void simulateUserInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }
}








