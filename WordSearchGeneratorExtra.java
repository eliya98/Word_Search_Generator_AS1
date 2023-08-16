/*
 * WordSearchGeneratorExtra.java
 * This class provides functionality to generate word search puzzles, interact with users,
 * print puzzles to console, save them to files, and more.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class WordSearchGeneratorExtra {

    // Two 2D character arrays to store the puzzle and the solution.
    private static char[][] puzzle;
    private static char[][] solution;
    
    // This is the main method where execution starts. It provides an interactive menu to
    // the user for generating, printing and saving word search puzzles.
    public static void main(String[] args) {
        printIntro();

        Scanner sc = new Scanner(System.in);
        char option = '\0';

        do {
            System.out.println("|=========================================|");
            System.out.println("|      Please select an option below      |");
            System.out.println("|_________________________________________|");
            System.out.println("|    Generate a new word search----(g)    |");
            System.out.println("|    Print out your word search----(p)    |");
            System.out.println("|    Show the solution words-------(s)    |");
            System.out.println("|    Quit the program--------------(q)    |");
            System.out.println("|                                         |");
            System.out.println("|SAVE OPTIONS:____________________________|");
            System.out.println("|    Save word search to file------(w)    |");
            System.out.println("|    Save solution to a file-------(x)    |");
            //System.out.println("|                                         |");
            System.out.println("|_________________________________________|");
            System.out.println();

            option = sc.next().charAt(0);

            switch (option) {
                case 'g':
                    System.out.println("| How would you like to select the words? |");
                    System.out.println("|-----------------------------------------|");
                    System.out.println("|    Input words manually----------(m)    |");
                    System.out.println("|    Read from a file--------------(f)    |");
                    System.out.println("|_________________________________________|");

                    char subOption = sc.next().charAt(0);
                    if (subOption == 'm') {
                        generate(sc);
                    } else if (subOption == 'f') {
                        generateFromFile(sc);
                    } else {
                        System.out.println("|    Invalid option. Please try again.    |");
                        System.out.println("|_________________________________________|");
                    }
                    break;
                case 'p':
                    print();
                    break;
                case 's':
                    showSolution();
                    break;
                case 'w':
                    saveToFile(sc, false);
                    break;
                case 'x':
                    saveToFile(sc, true);
                    break;
                case 'q':
                    System.out.println("|          Exiting the program.           |");
                    System.out.println("|_________________________________________|");
                    break;
                default:
                    System.out.println("|    Invalid option. Please try again!    |");
                    System.out.println("|_________________________________________|");
                    break;
            }

        } while (option != 'q');

        sc.close();
    }

    // This method prints the introduction message to the console.
    public static void printIntro() {
        System.out.println("|_________________________________________|");
        System.out.println("|  Welcome to my word search generator!   |");
        System.out.println("|  This program will allow you to         |");
        System.out.println("|  generate your own word search puzzle.  |");
        //System.out.println("|_________________________________________|");
    }

    //This method generates a word search puzzle from words inputted manually by the user.
    public static void generate(Scanner sc) {
        System.out.println("| How many words would you like to enter? |");
        
        int numWords = sc.nextInt();
        String[] words = new String[numWords];
        int maxLen = 0;
        for (int i = 0; i < numWords; i++) {
            System.out.println("|    Please enter word number " + (i + 1)+"           |");
            words[i] = sc.next().toUpperCase();
            maxLen = Math.max(maxLen, words[i].length());
        }

        createPuzzle(words, maxLen, numWords);
    }

    /*
     * This method generates a word search puzzle from words read from a file provided by
     * the user.
     */
    public static void generateFromFile(Scanner sc) {
        System.out.println("|_________________________________________|");
        System.out.println("|          Enter the file path:           |");
        String filePath = sc.next();
        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);
            String[] words = fileScanner.nextLine().split(" ");
            fileScanner.close();

            int maxLen = 0;
            for (String word : words) {
                maxLen = Math.max(maxLen, word.length());
            }
            createPuzzle(words, maxLen, words.length);
        } catch (FileNotFoundException e) {
            System.out.println("|             File not found!             |");
            System.out.println("|_________________________________________|");
        }
    }

    /*
     * This private method creates the puzzle and solution arrays using the words and their
     * lengths provided.
     */
    private static void createPuzzle(String[] words, int maxLen, int numWords) {
        puzzle = new char[numWords][maxLen];
        solution = new char[numWords][maxLen];
        Random rand = new Random();
        for (int i = 0; i < numWords; i++) {
            for (int j = 0; j < maxLen; j++) {
                char letter = (char) (rand.nextInt(26) + 'A');
                puzzle[i][j] = (j < words[i].length()) ? words[i].charAt(j) : letter;
                solution[i][j] = (j < words[i].length()) ? words[i].charAt(j) : 'X';
            }
        }
        System.out.println("|         Word Search Generated!          |");
        System.out.println("|_________________________________________|");
    }

    // This method prints the current word search puzzle to the console.
    public static void print() {
        if (puzzle == null) {
            System.out.println("|_________________________________________|");
            System.out.println("|   Please generate a word search first   |");
            return;
        }
        printArray(puzzle);
    }

    // This method prints the solution to the current word search puzzle to the console.
    public static void showSolution() {
        if (solution == null) {
            System.out.println("|_________________________________________|");
            System.out.println("|   Please generate a word search first   |");
            return;
        }
        printArray(solution);
    }

    /*
     * This method saves the current word search puzzle or its solution (depending on the 
     * isSolution parameter) to a file specified by the user.
     */
    public static void saveToFile(Scanner sc, boolean isSolution) {
        if (isSolution && solution == null) {
            System.out.println("|_________________________________________|");
            System.out.println("|   Please generate a word search first   |");
            return;
        } else if (!isSolution && puzzle == null) {
            System.out.println("|_________________________________________|");
            System.out.println("|   Please generate a word search first   |");
            return;
        }
        System.out.println("|_________________________________________|");
        System.out.println("|       Enter the output file path:       |");
        String filePath = sc.next();

        try {
            PrintWriter pw = new PrintWriter(filePath);
            char[][] toSave = isSolution ? solution : puzzle;
            for (char[] row : toSave) {
                for (char c : row) {
                    pw.print(c);
                    pw.print(' ');
                }
                pw.println();
            }
            pw.close();
            System.out.println("|        File saved successfully!         |");
            System.out.println("|_________________________________________|");
        } catch (FileNotFoundException e) {
            System.out.println("|        Unable to save the file!         |");
            System.out.println("|_________________________________________|");
        }
    }

    /*
     * This private method prints a 2D character array to the console. It is used to print 
     * the word search puzzle and its solution.
     */
    private static void printArray(char[][] arr) {
        for (char[] row : arr) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println("| ");
        }
    }
}
