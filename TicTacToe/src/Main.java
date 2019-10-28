import java.util.Scanner;

public class Main {


    static final Scanner scanner = new Scanner(System.in);
    static boolean stamp = false;
    static int moveCounter = 0;

    private static void printState(char[][] twoDimArr) {
        System.out.println("---------");
        for (char[] charRow : twoDimArr) {
            System.out.print("| ");
            for (char element : charRow) {
                System.out.print(element + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    private static void makeMove(char[][] board) {
        boolean wrongMove;
        char[][] battleField = board;
        do {
            System.out.print("Enter the coordinates: ");
            String strMoveX = scanner.next();
            String strMoveY = scanner.next();
            try {
                int moveX = Integer.parseInt(strMoveX) - 1;
                int moveY = Integer.parseInt(strMoveY) - 1;
                // rotate coordinate
                int moveXTmp = Math.abs(2-moveY);
                int moveYTmp = moveX;

                if (moveX > 2 || moveX < 0 || moveY > 2 || moveY < 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    wrongMove = true;
                } else if (battleField[moveXTmp][moveYTmp] != '_' && battleField[moveXTmp][moveYTmp] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                    wrongMove = true;
                } else {
                    battleField[moveXTmp][moveYTmp] =  stamp ? 'O' : 'X';
                    wrongMove = false;
                    stamp = !stamp;
                    moveCounter++;
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                wrongMove = true;
            }
        } while (wrongMove);
    }

    private static boolean isGameEnded(char[][] board) {
        char[][] battleField = board;
        boolean stop = false;
        char winner = ' ';
        int counter = 0;
        //int moveCounter = 0;
        //SSystem.out.println(moveCounter);

        if (battleField[0][0] == battleField[1][1]
                && battleField[1][1] == battleField[2][2]
                && battleField[1][1] != ' '
                || battleField[2][0] == battleField[1][1]
                && battleField[1][1] == battleField[0][2]
                && battleField[1][1] != ' ') {
            stop = true;
            winner = battleField[1][1];
        }

        for (int i = 0; i < battleField.length && !stop; i++) {
            // scanning cols
            char element = battleField[i][0];
            counter = 0;
            for (int j = 0; j < battleField.length; j++) {
                if (battleField[i][j] == ' ') {
                    break;
                } else if (element == battleField[i][j]) {
                    counter++;
                }
                if (counter == 3) {
                    stop = true;
                    winner = element;
                    break;
                }
            }
            // scanning rows (replaced i and j)
            element = battleField[0][i];
            counter = 0;
            for (int j = 0; j < battleField.length && !stop; j++) {
                if (battleField[j][i] == ' ') {
                    break;
                } else if (element == battleField[j][i]) {
                    counter++;
                }
                if (counter == 3) {
                    stop = true;
                    winner = element;
                    break;
                }
            }
        }

        if (stop) {
            System.out.println(winner +" wins");
        } else if (!stop && moveCounter == 9) {
            stop = true;
            System.out.println("Draw");
        }

        return stop;
    }

    public static void main(String[] args) {
        // battlefield initialization
        char[][] battleField = new char[3][3];
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField.length; j++) {
                battleField[i][j] = ' ';
            }
        }
        printState(battleField);

        // game
        do {
            makeMove(battleField);
            printState(battleField);
        } while(!isGameEnded(battleField));
    }
}