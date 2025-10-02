package example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter length of board row: ");
        int size = 8; // Default size

        try {
            size = scan.nextInt();
            if (size < 8) size = 8;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Using default size of 8.");
            scan.nextLine();
        }

        TowerChessboard board = new TowerChessboard(size);
        board.print();

        boolean continueAdding = true;
        while (continueAdding) {
            System.out.println("Enter row or column to place tower (0-indexed): ");
            System.out.print("Row: ");
            int row = scan.nextInt();
            System.out.print("Column: ");
            int column = scan.nextInt();

            if (!board.isValidPosition(row, column)) {
                System.out.println("Invalid position. Please enter valid row and column numbers.");
                continue;
            }

            try {
                board.put(row, column);
                System.out.println("Tower placed successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            board.update();
            board.print();

            System.out.print("Do you want to add another tower? (y/n): ");
            String choice = scan.next();
            continueAdding = choice.equalsIgnoreCase("y");
        }

        System.out.println("Program closed.");
    }
}
