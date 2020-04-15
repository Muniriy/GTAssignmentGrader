package com.company;

import com.sun.javafx.scene.traversal.Direction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament {

    /** This method decrements the X of field if it is not null
     * @param x current field state
     * @return updated field state
     */
    private int grassEating(int x) {
        if (x > 0) return --x;
        else return x;
    }

    /** This method calculates the amount of vegetation eaten by the Moose for the current field
     * @param x current field state
     * @return amount of eaten grass
     */
    private double sigmoid(int x) {
        double result = (10 * Math.exp(x)) / (1 + Math.exp(x)) - 5;
        return result;
    }

    /** This method checks if the Player made valid move
     * @param x move of Player should be 1, 2 or 3 for being valid
     * @return true or false
     */
    private boolean isValidMove(int x) {
        return x == 1 || x == 2 || x == 3;
    }

    /** This method trims the ending of the file names from "." till the end
     * @param str file name
     * @return shortened file name
     */
    private String shortenName (String str) {
        return str.substring(0, str.indexOf("."));
    }

    /** This method gets the list of all files in the given directory
     * and puts student files to the String array
     * @param path the directory with files
     * @return the String array with all student file names
     */
    private String[] getAllStudents(String path) {

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String[] studentFiles;

        assert listOfFiles != null;
        studentFiles = new String[listOfFiles.length - 3];
        System.out.println(listOfFiles.length);
        int studentNo = 0;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                System.out.println("File " + listOfFile.getName());
                if ((!listOfFile.getName().equals("Tournament.java")) && (!listOfFile.getName().equals("Player.java")) &&
                        (!listOfFile.getName().equals(".DS_Store"))) studentFiles[studentNo++] = shortenName(listOfFile.getName());
            } else if (listOfFile.isDirectory()) {
                System.out.println("Directory " + listOfFile.getName());
            }
        }
        System.out.println();
        for (String s : studentFiles) System.out.println(s);
        return studentFiles;
    }

    /** This method generates the code of student objects creation.
     * It is used firstly, the outputs have to copied to runTournament() method
     * and then this method can be commented
     * @param classes String array with names of all student classes
     */
    private void generateCode(String[] classes) {
        String[] generatedStrings = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            generatedStrings[i] = classes[i] + " obj" + classes[i] + " = new " + classes[i] + "();\n" +
            "player[" + i + "] = obj" + classes[i] + ";\n";
        }
        for(String s : generatedStrings) System.out.println(s);
    }

    /** This method takes the objects of 2 students' classes and
     * provides the fight between them
     * @param player1 the object of the first player
     * @param player2 the object of the second player
     * @return the scores of both players. Zeroth element is for the first player,
     * first element for the second player
     */
    private double[] pvp(Player player1, Player player2) {
        int rounds = 100;
        int xA = 1;
        int xB = 1;
        int xC = 1;
        int playerOneMove = 0;
        int playerTwoMove = 0;
        int previousPlayerOneMove;
        int previousPlayerTwoMove;
        double[] scores = new double[2];

        for (int i = 0; i < rounds; i++) {

            // Get the previous player moves
            previousPlayerOneMove = playerOneMove;
            previousPlayerTwoMove = playerTwoMove;

//            System.out.println();
//            System.out.println("Round " + i + ": xA = " + xA + " xB = " + xB + " xC = " + xC + "; previous move of P1 is " + previousPlayerOneMove + "; previous move of P2 is " + previousPlayerTwoMove);

            playerOneMove = player1.move(previousPlayerTwoMove, xA, xB, xC);
            if (!isValidMove(playerOneMove)) {
                System.out.println("ERROR: impossible " + player1.getClass() + " move is " + playerOneMove);
                return scores;
            }
            player1.reset();
//            System.out.println("    1st player move is " + playerOneMove);

            playerTwoMove = player2.move(previousPlayerOneMove, xA, xB, xC);
            if (!isValidMove(playerTwoMove)) {
                System.out.println("ERROR: impossible " + player2.getClass() + " move is " + playerTwoMove);
                return scores;
            }
            player2.reset();
//            System.out.println("    2nd player move is " + playerTwoMove);

            if (playerOneMove == playerTwoMove) {
                switch(playerOneMove) {
                    case 1: xA = grassEating(xA);
                        xB++;
                        xC++;
                        break;
                    case 2: xA++;
                        xB = grassEating(xB);
                        xC++;
                        break;
                    case 3: xA++;
                        xB++;
                        xC = grassEating(xC);
                        break;
                    default: break;
                }
            } else {
                boolean aVisited = false;
                boolean bVisited = false;
//                boolean cVisited = false;
                switch (playerOneMove) {
                    case 1: scores[0]+=sigmoid(xA);
                        xA = grassEating(xA);
                        aVisited = true;
                        break;
                    case 2: scores[0]+=sigmoid(xB);
                        xB = grassEating(xB);
                        bVisited = true;
                        break;
                    case 3: scores[0]+=sigmoid(xC);
                        xC = grassEating(xC);
//                        cVisited = true;
                        break;
                    default: System.out.println("Something went wrong. Impossible move was returned by " + player1.getClass());
                        break;
                }
                switch (playerTwoMove) {
                    case 1: scores[1]+=sigmoid(xA);
                        xA = grassEating(xA);
                        aVisited = true;
                        break;
                    case 2: scores[1]+=sigmoid(xB);
                        xB = grassEating(xB);
                        bVisited = true;
                        break;
                    case 3: scores[1]+=sigmoid(xC);
                        xC = grassEating(xC);
//                        cVisited = true;
                        break;
                    default: System.out.println("Something went wrong. Impossible move was returned by " + player2.getClass());
                        break;
                }
                if (!aVisited) xA++;
                else if (!bVisited) xB++;
                else xC++;
            }
//            System.out.println("Score of " + player1.getClass() + " is " + scores[0]);
//            System.out.println("Score of " + player2.getClass() + " is " + scores[1]);
        }
        return scores;
    }

    /** This method runs the tournament between all players.
     * Before running it, run generateCode() method and paste
     * the outputs inside this method
     * @param classes String array with names of all student classes
     */
    private void runTournament(String[] classes) {

        double[] scores = new double[classes.length];
        String[] emails = new String[classes.length];
        Player[] player = new Player[classes.length];

        // Copy generated code here

        MikhailTkachenkoCode objMikhailTkachenkoCode = new MikhailTkachenkoCode();
        player[0] = objMikhailTkachenkoCode;

        TemurKholmatovCode objTemurKholmatovCode = new TemurKholmatovCode();
        player[1] = objTemurKholmatovCode;

        ArtemiiBykovCode objArtemiiBykovCode = new ArtemiiBykovCode();
        player[2] = objArtemiiBykovCode;

        // End of generated code

        for (int i = 0; i < classes.length; i++) {
            emails[i] = player[i].getEmail();
            for (int j = i + 1; j < classes.length; j++) {
                System.out.println();
                System.out.println("Fight between " + player[i].getClass() + " and " + player[j].getClass());
                double[] pvpGrades = pvp(player[i], player[j]);
                scores[i] += pvpGrades[0];
                scores[j] += pvpGrades[1];
            }
        }

        System.out.println();
        for (int i = 0; i < classes.length; i++) System.out.println(emails[i] + " " + scores[i]);
        generateGradesFile(emails, scores);
    }

    /** This method generates the file grades.csv with student grades
     * @param emails String array with all student emails
     * @param scores array with all scores
     */
    private void generateGradesFile(String[] emails, double[] scores) {
        try {
            FileWriter csvWriter = new FileWriter("grades.csv");
            csvWriter.flush();
            csvWriter.append("#");
            csvWriter.append(",");
            csvWriter.append("Email");
            csvWriter.append(",");
            csvWriter.append("Grade");
            csvWriter.append("\n");

            for (int i = 0; i < emails.length; i++) {
                csvWriter.append(i + "");
                csvWriter.append(",");
                csvWriter.append(emails[i] + "");
                csvWriter.append(",");
                csvWriter.append(scores[i] + "");
                csvWriter.append("\n");
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** Entry point of the program
     * @param args arguments of the program
     */
    public static void main(String[] args) {
        String path = "./src/com/company";
        String[] studentClasses;
        Tournament tournament = new Tournament();
        studentClasses = tournament.getAllStudents(path);

        // Uncomment firstly to get list the generated code and copy to  and then comment
        //        tournament.generateCode(studentClasses);

        // Comment firstly and then uncomment to run the program
        tournament.runTournament(studentClasses);
    }
}

