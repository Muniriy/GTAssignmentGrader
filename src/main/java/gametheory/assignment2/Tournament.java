package gametheory.assignment2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Tournament {

    private static final Logger log = LogManager.getLogger(Tournament.class);
    private static final String PARTICIPANTS_TYPE = "20s";
    private static final String INPUT_PATH = "./src/main/java/gametheory/assignment2/students" + PARTICIPANTS_TYPE;
    private static final String OUTPUT_PATH = "./outputs/" + PARTICIPANTS_TYPE + "/grades.csv";
    private static final int ROUNDS = 100;

    /**
     * Entry point of the Tournament program.
     * It takes players of students and makes
     * a tournament between them
     *
     * @param args arguments of the program
     */
    public static void main(String[] args) {
        String[] studentClasses;
        Tournament tournament = new Tournament();
        studentClasses = tournament.getAllStudents(INPUT_PATH);
        tournament.runTournament(studentClasses);
    }

    /**
     * This method decrements the X of field if it is not null
     * because the grass can be eaten be one Moose or partially
     * destroyed during the fight between both Moose
     *
     * @param x current field state
     * @return updated field state
     */
    private int grassDecrementing(int x) {
        return x > 0 ? --x : x;
    }

    /**
     * This method calculates the amount of vegetation
     * eaten by the Moose for the current field
     *
     * @param x current field state
     * @return amount of eaten grass
     */
    private double sigmoid(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x)) - 5;
    }

    /**
     * This method checks if the Player made valid move
     *
     * @param x move of Player should be 1, 2 or 3 for being valid
     * @return true or false
     */
    private boolean isValidMove(int x) {
        return x == 1 || x == 2 || x == 3;
    }

    /**
     * This method trims the ending of the file names from "." till the end
     *
     * @param str file name
     * @return shortened file name
     */
    private String shortenName(String str) {
        return str.substring(0, str.indexOf("."));
    }

    /** This method gets the list of all files in the given directory
     * and puts student files to the String array
     *
     * @param path the directory with files
     * @return the String array with all student file names
     */
    private String[] getAllStudents(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String[] studentFiles;

        assert listOfFiles != null;
        studentFiles = new String[listOfFiles.length];
        log.info("Number of student files is {}", listOfFiles.length);
        int studentNo = 0;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                log.info("File {}", listOfFile.getName());
                if ((!listOfFile.getName().equals("Tournament.java")) &&
                        (!listOfFile.getName().equals("Player.java")) &&
                        (!listOfFile.getName().equals(".DS_Store"))
                ) {
                    studentFiles[studentNo++] = shortenName(listOfFile.getName());
                }
            } else if (listOfFile.isDirectory()) {
                log.info("Unexpected directory: {}\n", listOfFile.getName());
            }
        }

        log.info("Student files:\n");
        for (String s : studentFiles) {
            log.info(s);
        }
        return studentFiles;
    }

    /**
     * This method takes the objects of 2 students' classes and
     * runs the match between them
     *
     * @param player1 the object of the first player
     * @param player2 the object of the second player
     * @return the scores of both players.
     * Zeroth element is for the first player,
     * first element for the second player
     */
    private double[] runMatch(Player player1, Player player2) {
        int xA = 1;
        int xB = 1;
        int xC = 1;
        int playerOneMove = 0;
        int playerTwoMove = 0;
        int previousPlayerOneMove;
        int previousPlayerTwoMove;
        double[] scores = new double[2];

        player1.reset();
        player2.reset();

        for (int i = 0; i < ROUNDS; i++) {

            // Get the previous player moves
            previousPlayerOneMove = playerOneMove;
            previousPlayerTwoMove = playerTwoMove;

            playerOneMove = player1.move(previousPlayerTwoMove, xA, xB, xC);
            if (!isValidMove(playerOneMove)) {
                log.error("Impossible {} move is {}", player1.getClass().getSimpleName(),
                        playerOneMove);
                return scores;
            }

            playerTwoMove = player2.move(previousPlayerOneMove, xA, xB, xC);
            if (!isValidMove(playerTwoMove)) {
                log.error("Impossible {} move is {}", player2.getClass().getSimpleName(),
                        playerTwoMove);
                return scores;
            }

            // if players made the same moves
            if (playerOneMove == playerTwoMove) {
                int[] xFields = runRoundWithEqualMoves(playerOneMove, xA, xB, xC);
                xA = xFields[0];
                xB = xFields[1];
                xC = xFields[2];
            } else {
                // updating first player values
                double[] xFieldsAndScore = runRoundWithDifferentMoves(playerOneMove, xA, xB, xC,
                        player1.getClass().getSimpleName());
                xA = (int) xFieldsAndScore[0];
                xB = (int) xFieldsAndScore[1];
                xC = (int) xFieldsAndScore[2];
                scores[0] += xFieldsAndScore[3];

                // updating second player values
                xFieldsAndScore = runRoundWithDifferentMoves(playerTwoMove, xA, xB, xC,
                        player2.getClass().getSimpleName());
                xA = (int) xFieldsAndScore[0];
                xB = (int) xFieldsAndScore[1];
                xC = (int) xFieldsAndScore[2];
                scores[1] += xFieldsAndScore[3];

                // third field incrementation
                if (playerOneMove + playerTwoMove == 3) {
                    xC++;
                } else if (playerOneMove + playerTwoMove == 4) {
                    xB++;
                } else if (playerOneMove + playerTwoMove == 5) {
                    xA++;
                } else {
                    log.error("Impossible move was returned");
                }
            }
        }
        return scores;
    }

    /**
     * This method allows to run the round between players
     * with equal moves
     *
     * @param move the field to which players move
     * @param xA   the argument X for a field A
     * @param xB   the argument X for a field B
     * @param xC   the argument X for a field C
     */
    private int[] runRoundWithEqualMoves(int move, int xA, int xB, int xC) {
        switch (move) {
            case 1:
                xA = grassDecrementing(xA);
                xB++;
                xC++;
                break;
            case 2:
                xA++;
                xB = grassDecrementing(xB);
                xC++;
                break;
            case 3:
                xA++;
                xB++;
                xC = grassDecrementing(xC);
                break;
            default:
                log.error("Impossible move '{}' was returned by both players", move);
                break;
        }
        int[] res = new int[3];
        res[0] = xA;
        res[1] = xB;
        res[2] = xC;
        return res;
    }

    /**
     * This method allows to run the round between players
     * with equal moves
     *
     * @param move the field to which players move
     * @param xA   the argument X for a field A
     * @param xB   the argument X for a field B
     * @param xC   the argument X for a field C
     * @return the double array with X fields and points
     * by which the score has to increased
     */
    private double[] runRoundWithDifferentMoves(int move, int xA, int xB, int xC, String playerName) {
        double score = 0;
        switch (move) {
            case 1:
                score += sigmoid(xA);
                xA = grassDecrementing(xA);
                break;
            case 2:
                score += sigmoid(xB);
                xB = grassDecrementing(xB);
                break;
            case 3:
                score += sigmoid(xC);
                xC = grassDecrementing(xC);
                break;
            default:
                log.error("Impossible move was returned by player {}", playerName);
                break;
        }
        double[] res = new double[4];
        res[0] = xA;
        res[1] = xB;
        res[2] = xC;
        res[3] = score;
        return res;
    }

    /**
     * This method runs the tournament between all players.
     *
     * @param classes String array with names of all student classes
     */
    private void runTournament(String[] classes) {
        double[] scores = new double[classes.length];
        String[] emails = new String[classes.length];
        Player[] players = new Player[classes.length];

        // generate players
        try {
            for (int playerNo = 0; playerNo < classes.length; playerNo++) {
                Class<?> studentClass = Class.forName("gametheory.assignment2.students" +
                        PARTICIPANTS_TYPE + "." + classes[playerNo]);
                Object studentObject = studentClass.getDeclaredConstructor().newInstance();
                players[playerNo] = (Player) studentObject;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage());
        }

        for (int i = 0; i < classes.length; i++) {
            emails[i] = players[i].getEmail();
            for (int j = i + 1; j < classes.length; j++) {

                log.info("\nFight between {} ({}) and {} ({})",
                        players[i].getClass().getSimpleName(), i,
                        players[j].getClass().getSimpleName(), j);
                double[] pvpGrades = runMatch(players[i], players[j]);
                scores[i] += pvpGrades[0];
                scores[j] += pvpGrades[1];
                log.info("{} got {}, and {} got {}",
                        players[i].getClass().getSimpleName(), pvpGrades[0],
                        players[j].getClass().getSimpleName(), pvpGrades[1]);
            }
        }

        log.info("\nTournament grades:");
        for (int i = 0; i < classes.length; i++) {
            log.info("{} {}", emails[i], scores[i]);
        }
        generateGradesFile(emails, scores);
    }

    /** This method generates the file grades.csv with student grades
     * @param emails String array with all student emails
     * @param scores array with all scores
     */
    private void generateGradesFile(String[] emails, double[] scores) {
        try (FileWriter csvWriter = new FileWriter(OUTPUT_PATH)) {
            csvWriter.flush();
            csvWriter.append("#");
            csvWriter.append(",");
            csvWriter.append("Email");
            csvWriter.append(",");
            csvWriter.append("Grade");
            csvWriter.append("\n");

            for (int i = 0; i < emails.length; i++) {
                csvWriter.append(String.valueOf(i));
                csvWriter.append(",");
                csvWriter.append(emails[i]);
                csvWriter.append(",");
                csvWriter.append(String.valueOf(scores[i]));
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

