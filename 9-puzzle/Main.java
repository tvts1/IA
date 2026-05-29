import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        int[][] initialBoard = {
                { 6, 4, 2 },
                { 8, 1, 3 },
                { 7, 5, 0 }
        };

        State initialState = new State(initialBoard, 2, 2, null, null);

        System.out.println("Iniciando a busca em largura...\n");
        solveBFS(initialState);
    }

    public static void solveBFS(State initialState) {
        Queue<State> queue = new LinkedList<>();
        HashSet<State> visited = new HashSet<>();

        queue.add(initialState);
        

        while (!queue.isEmpty()) {

            State currentState = queue.poll();
            if(!visited.contains(currentState)){
                visited.add(currentState);
                if (currentState.isGoal()) {
                    System.out.println("Solução Encontrada!\n");
                    printSolution(currentState);
                    return;
                }
            
                List<State> children = currentState.getSuccessors();

                for (State child : children) {
                    queue.add(child);
                    
                }
            }
        }
   
        System.out.println("Não há solução para este tabuleiro.");
    }

    public static void printSolution(State goalState) {
        List<State> path = new ArrayList<>();

        State currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = currentState.getStateFather();
        }

        Collections.reverse(path);

        // O estado inicial não conta como movimento
        int totalSteps = path.size() - 1;

        System.out.println("Total steps: " + totalSteps);
        System.out.println("=================================");

        for (State state : path) {
            // O primeiro estado não possui ação
            if (state.getAction() == null) {
                System.out.println("Initial State:");
            } else {
                System.out.println("Move performed: " + state.getAction());
            }

            printBoard(state.getBoard());
            System.out.println("-----------------");
        }
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < board[i].length; j++) {
                // O número 0 representa o espaço vazio
                if (board[i][j] == 0) {
                    System.out.print("X ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println("]");
        }
    }
}

enum Action {
    UP, DOWN, LEFT, RIGHT
}

// A posição das peças e do espaço vazio.
class State {
    private int[][] board;
    private int row;
    private int col;

    // Para rastrear o caminho de volta ao estado inicial, armazenamos o estado pai
    private State parent;

    private Action action;

    public State() {
    }

    public State(int[][] board, int row, int col, State parent, Action action) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.parent = parent;
        this.action = action;
    }

    public List<State> getSuccessors() {
        // Lista de todos os "filhos" possíveis daquele estado
        List<State> children = new ArrayList<>();

        if (this.row > 0) {
            // Passo A: Clonar a matriz atual do ZERO
            int[][] newBoard = copyBoard(this.board);
            // Descobrimos quem está em cima do '0' e guardamos numa variável temporária
            int tmp = newBoard[this.row - 1][this.col];
            // Colocamos o '0' na posição de cima
            newBoard[this.row - 1][this.col] = 0;
            // Colocamos o número que estava em cima na posição antiga do '0'
            newBoard[this.row][this.col] = tmp;
            // Passo C: Criar o novo estado
            State newState = new State(newBoard, this.row - 1, this.col, this, Action.UP);
            children.add(newState);
        }

        if (this.row < 2) {
            int[][] newBoard = copyBoard(this.board);
            int tmp = newBoard[this.row + 1][this.col];
            newBoard[this.row + 1][this.col] = 0;
            newBoard[this.row][this.col] = tmp;
            State newState = new State(newBoard, this.row + 1, this.col, this, Action.DOWN);
            children.add(newState);
        }

        if (this.col > 0) {
            int[][] newBoard = copyBoard(this.board);
            int tmp = newBoard[this.row][this.col - 1];
            newBoard[this.row][this.col - 1] = 0;
            newBoard[this.row][this.col] = tmp;
            State newState = new State(newBoard, this.row, this.col - 1, this, Action.LEFT);
            children.add(newState);
        }

        if (this.col < 2) {
            int[][] newBoard = copyBoard(this.board);
            int tmp = newBoard[this.row][this.col + 1];
            newBoard[this.row][this.col + 1] = 0;
            newBoard[this.row][this.col] = tmp;
            State newState = new State(newBoard, this.row, this.col + 1, this, Action.RIGHT);
            children.add(newState);
        }

        return children;
    }

    public boolean isGoal() {
        int[][] goalBoard = {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 }
        };
        return Arrays.deepEquals(this.board, goalBoard);
    }

    public int[][] copyBoard(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[][] newBoard = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public State getStateFather() {
        return parent;
    }

    public void setStateFather(State parent) {
        this.parent = parent;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        return Arrays.deepEquals(this.board, other.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }
}