import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.math.*;

// solution for https://www.codingame.com/ide/puzzle/connect-four
class Solution {
    public static final int NUMBER_TO_WIN = 4;
    public static final int NUMBER_OF_ROWS = 6;
    public static final int NUMBER_OF_COLUMNS = 7;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        char[][] gameBoard = new char[6][7];
        for (int i = 0; i < 6; i++) {
            String line = in.next();
            gameBoard[i] = line.toCharArray();
        }

        List<Integer> player1Results = getPotentialWins(gameBoard, '1');
        List<Integer> player2Results = getPotentialWins(gameBoard, '2');
        
        System.out.println(
          player1Results.size() > 0 ? 
            player1Results.stream().map(x -> x.toString()).collect(Collectors.joining(" ")) 
            : "NONE"
        );
        System.out.println(
          player2Results.size() > 0 ? 
            player2Results.stream().map(x -> x.toString()).collect(Collectors.joining(" ")) 
            : "NONE"
        );
    }

    private static List<Integer> getPotentialWins(char[][]gameBoard, char playerNumber){
        List<Integer> winPositions = new ArrayList<>();
        
        for(int c = 0; c<NUMBER_OF_COLUMNS;c++){
            if(isWin(playerNumber, getPotentialPlayBoard(gameBoard, c, playerNumber))){
                winPositions.add(c);
            }
        }

        return winPositions;
    }

    private static boolean isWin(char playerNumber, char[][] gameBoard) {

        return checkHorizontal(gameBoard, playerNumber) 
            || checkVertical(gameBoard, playerNumber) 
            || checkDiagonal(gameBoard, playerNumber);
    }

    private static boolean checkHorizontal(char[][] gameBoard, char playerNumber) {
        boolean win = false;

        for(char[] row : gameBoard){
            StringBuilder sb = new StringBuilder();
            for(char c : row) {
                sb.append(c);
            }

            win |= checkForRepeats(sb.toString(), playerNumber, NUMBER_TO_WIN);
        }

        return win;
    }

    private static boolean checkVertical(char[][] gameBoard, char playerNumber) {
        boolean win = false;

        for(int c = 0; c < NUMBER_OF_COLUMNS; c++) {
            StringBuilder sb = new StringBuilder(NUMBER_OF_ROWS);

            for(int r =0; r<NUMBER_OF_ROWS;r++) {
                sb.append(gameBoard[r][c]);
            }

            win |= checkForRepeats(sb.toString(), playerNumber, NUMBER_TO_WIN);
        }

        return win;
    }

    private static boolean checkDiagonal(char[][] gameBoard, char playerNumber) {
        boolean win = false;

        // Top to bottom L->R
        // First half
        for(int rowStart = 0; rowStart<NUMBER_OF_ROWS; rowStart++){
                int column = 0;
                StringBuilder sb = new StringBuilder();
                for (int r = rowStart; r<NUMBER_OF_ROWS && column < NUMBER_OF_COLUMNS;r++) {
                    sb.append(gameBoard[r][column++]);
                }
                
                win |= checkForRepeats(sb.toString(), playerNumber, NUMBER_TO_WIN);
        }

        // Second half
        for(int columnStart = 1; columnStart<NUMBER_OF_COLUMNS; columnStart++) {
            int row = 0;
                StringBuilder sb = new StringBuilder();
                for (int c = columnStart; c<NUMBER_OF_COLUMNS && row < NUMBER_OF_ROWS;c++) {
                    sb.append(gameBoard[row++][c]);
                }

                win |= checkForRepeats(sb.toString(), playerNumber, NUMBER_TO_WIN);
        }

        // Top to bottom R->L
        //First half
        for(int rowStart = 0; rowStart<NUMBER_OF_ROWS; rowStart++){
                int column = NUMBER_OF_COLUMNS-1;
                StringBuilder sb = new StringBuilder();
                for (int r = rowStart; r<NUMBER_OF_ROWS && column >= 0;r++) {
                    sb.append(gameBoard[r][column--]);
                }

                win |= checkForRepeats(sb.toString(), playerNumber, NUMBER_TO_WIN);
        }

        // Second half
        for(int columnStart = NUMBER_OF_COLUMNS-2; columnStart>=0; columnStart--) {
            int row = 0;
                StringBuilder sb = new StringBuilder();
                for (int c = columnStart; c>=0 && row < NUMBER_OF_ROWS;c--) {
                    sb.append(gameBoard[row++][c]);
                }

                win |= checkForRepeats(sb.toString(), playerNumber, NUMBER_TO_WIN);
        }

        return win;
    }

    private static boolean checkForRepeats(String inputString, char character, int numberOfRepeats) {
        StringBuilder sb = new StringBuilder(numberOfRepeats);

        for(int i = 0;i<numberOfRepeats;i++) {
            sb.append(character);
        }

        return inputString.indexOf(sb.toString()) > -1;
    }

    private static char[][] getPotentialPlayBoard(char[][] gameBoard, int column, char playerNumber) {
        char[][] newGameBoard = Arrays.stream(gameBoard).map(char[]::clone).toArray(char[][]::new);

        for(int row=NUMBER_OF_ROWS - 1; row >= 0; row--){
            if (newGameBoard[row][column] == '.'){
                newGameBoard[row][column] = playerNumber;
                break;
            }
        }

        return newGameBoard;
    }
}
