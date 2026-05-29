public class Main {
    public static void main(String[] args) {
        char[][] board = new char[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
               board[i][j] = '.';         
            }
        }

        printBoard(board);
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public static boolean isSafe(char[][] board, int row, int col) {
        
        for (int searchRow = row - 1; searchRow >= 0; searchRow--) {
            if (board[searchRow][col] == 'Q') {
                return false;                
            }
        }

        int searchRow = row - 1;
        int searchCol = col - 1;
        
        while (searchRow >= 0 && searchCol >= 0) {
            
            if (board[searchRow][searchCol] == 'Q') {
                return false;
            }
            
            searchRow--;
            searchCol--;
        }


        searchRow = row - 1;
        searchCol = col + 1;

        while (searchRow >= 0 && searchCol < board.length) {
            
            if (board[searchRow][searchCol] == 'Q') {
                return false;
            }
            
            searchRow--;
            searchCol++;
        }
        
        return true;
    }
}
