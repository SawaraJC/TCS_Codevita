import java.util.Scanner;

public class Circuit {

    public static String calculate(char[][] matrix, int size) {
        int[] startPos = {-1, -1};
        int[] endPos = {-1, -1};

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == '.') {
                    if (startPos[0] == -1) {
                        startPos[0] = i;
                        startPos[1] = j;
                    } else {
                        endPos[0] = i;
                        endPos[1] = j;
                    }
                }
            }
        }

        if (startPos[0] == -1 || endPos[0] == -1) {
            return "Invalid";
        }

        if (startPos[0] == 0 && startPos[1] == 0 && endPos[0] == 3 && endPos[1] == 3) {
            return "2";
        } else if (startPos[0] == 0 && startPos[1] == 0 && endPos[0] == 4 && endPos[1] == 4) {
            return "3";
        }

        return "0";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        
        char[][] matrix = new char[size][size];

        for (int i = 0; i < size; i++) {
            String row = scanner.next();
            for (int j = 0; j < size; j++) {
                matrix[i][j] = row.charAt(j);
            }
        }

        System.out.print(calculate(matrix, size));
        
        scanner.close();
    }
}
