package com.example;


import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class MovieTheater {

    static int numRows = 10;
    static int seatsPerRow = 20;
    static int totalSeats = numRows * seatsPerRow;
    static int remainingSeats = totalSeats;
    static String [][] seating = new String[numRows][seatsPerRow];
    static Map<String, Integer> reservations = new HashMap<>();

    public static void main(String [] args) {


        System.out.println("Reading file: \"C:\\Users\\Paul\\Desktop\\Wal-Mart\\input1.txt\"");

        String data = null;
        try {
            data = readFile("C:\\Users\\Paul\\Desktop\\Wal-Mart\\input1.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error reading file!");
            e.printStackTrace();
        }
        System.out.println("The input is: \n" + data);

        makeSeatingArrangement(data);

        constructOutputFile();
    }

    /*
        Method to read the input file data
        @param fileName (String) - the path to the file that will be read
        @return data (String) - the file data parsed to a String
     */
    private static String readFile(String fileName) throws Exception
    {
        String data = "";

        File inputFile = new File("C:\\Users\\Paul\\Desktop\\Wal-Mart\\input1.txt");
        Scanner reader = new Scanner(inputFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            String [] entry = line.split(" ");
            // check for valid data
            if (entry.length != 2) {
                System.out.println("Invalid input entry!");
                break;
            }
            String reservationName = entry[0];
            int seatsRequested = Integer.parseInt(entry[1]);
            if (seatsRequested > 20 || seatsRequested > remainingSeats) {
                System.out.println("Requested amount of seats for reservation cannot all fit in a row!");
                break;
            }

            reservations.put(reservationName, seatsRequested);

            remainingSeats -= seatsRequested;

            data += line + "\n";
        }
        reader.close();

        return data;
    }

    /*
        Method to run the algorithm to make the seating arrangement
        @param data - a String containing the reservation requests
        @return
    */
    private static void makeSeatingArrangement(String data) {
        // 10 rows (A-I), 20 columns (0-19)
        int row = 0;
        int column = 0;

        for (Map.Entry<String, Integer> me : reservations.entrySet()) {
            String resName = me.getKey();
            int amountOfSeats = me.getValue();
            if (amountOfSeats < (seatsPerRow - column + 1)) {
                for (int i = 1; i <= amountOfSeats; i++) {
                    seating[row][column] = resName;
                    column++;
                }

                if (column < 18) {
                    // make 3 buffer seats
                    for (int j = 1; j <= 3; j++) {
                        seating[row][column] = "b";
                        column++;
                    }
                }
                if (column == seatsPerRow) {
                    column = 0;
                    row++;
                }
            }
        }

        /* for (String [] s : seating) {
            for (String j : s) {
                System.out.print(j + " ");
            }
            System.out.println();
        } */
    }

    private static void constructOutputFile() {
        // HashMap to match indices to letter equivalents
        Map<Integer, String> indexToLetter = new HashMap<>();
        indexToLetter.put(0, "A");
        indexToLetter.put(1, "B");
        indexToLetter.put(2, "C");
        indexToLetter.put(3, "D");
        indexToLetter.put(4, "E");
        indexToLetter.put(5, "F");
        indexToLetter.put(6, "G");
        indexToLetter.put(7, "H");
        indexToLetter.put(8, "I");
        indexToLetter.put(9, "J");

        String outputLine = "";
        for (int a = 0; a < numRows; a++) {
            for (int b = 0; b < seatsPerRow; b++) {
                if (seating[a][b] != null && seating[a][b] != "b") {
                    outputLine += seating[a][b] + " ";
                    while(seating[a][b] != "b" && b < seatsPerRow) {
                        outputLine += indexToLetter.get(a) + Integer.toString(b + 1) + ",";
                        b++;
                    }
                    outputLine = outputLine.substring(0, outputLine.length() - 1) + "\n";
                }

            }
        }
        System.out.println("The output file would look like: \n" + outputLine);
    }
}
