import java.util.*;

public class EightQueensSolver {

    private static boolean isSafe(List<Integer> board, int newRow, int newCol) {
        for (int row = 0; row < board.size(); row++) {
            int col = board.get(row);

            if (col == newCol || Math.abs(col - newCol) == Math.abs(row - newRow)) {
                return false;
            }
        }
        return true;
    }

    public static void solveDFS(List<Integer> initialState, String testName) {    
        Stack<List<Integer>> frontierStack = new Stack<>();
        Set<List<Integer>> visitedStates = new HashSet<>();

        boolean isFound = false;
        int nodesGenerated = 1;
        List<Integer> solution = null;

        frontierStack.push(initialState);

        if (!frontierStack.isEmpty()) {
            do {
                List<Integer> currentState = frontierStack.pop();

                if (!visitedStates.contains(currentState)) {
                    visitedStates.add(currentState);

                    if (currentState.size() == 8) {
                        isFound = true;
                        solution = currentState; 
                        break; 
                    }
                    else {
                        int currentRow = currentState.size();
                        List<List<Integer>> children = new ArrayList<>();

                        for (int col = 0; col < 8; col++) {
                            if (isSafe(currentState, currentRow, col)) {
                                List<Integer> newNode = new ArrayList<>(currentState);
                                newNode.add(col);
                                children.add(newNode);
                                nodesGenerated++;
                            }
                        }

                        for (List<Integer> child : children) {
                            frontierStack.push(child);
                        }
                    }
                }
            } while (!isFound && !frontierStack.isEmpty());
        }

        printResult(testName, isFound, solution, nodesGenerated);
    }

    private static void printResult(String testName, boolean isFound, List<Integer> solution, int nodesGenerated) {
        System.out.println("--- " + testName + " ---");
        
        // CORREÇÃO: O '!' foi removido. Agora ele imprime corretamente.
        if (isFound) {
            System.out.println("Solution found: " + solution);
        } else {
            System.out.println("No solution found.");
        }
        
        System.out.println("Nodes generated: " + nodesGenerated + "\n");
    }

    public static void main(String[] args) {

        Map<String, List<Integer>> testCases = new LinkedHashMap<>();

        testCases.put("Empty Board", new ArrayList<>());
        testCases.put("1 Queen at (0,0)", Arrays.asList(0));
        testCases.put("2 Queens at (0,0), (1,4)", Arrays.asList(0, 4));
        testCases.put("3 Queens at (0,0), (1,4), (2,7)", Arrays.asList(0, 4, 7));

        for (Map.Entry<String, List<Integer>> test : testCases.entrySet()) {
            String testName = test.getKey();
            List<Integer> initialState = test.getValue();

            solveDFS(initialState, testName);
        }
    }
}