/* Homework #5, Christopher Tjahjo */

import java.util.Scanner;

public class Christopher_Tjahjo_6 {

    //implemented a stack using an array

    static class Stack {
        private int maxSize;
        private int top;
        private int[] stackArray;

        public Stack(int size) {
            maxSize = size;
            top = -1;
            stackArray = new int[maxSize];
        }

        public void push(int item) {
            if (top == maxSize - 1) {
                return;
            }
            stackArray[++top] = item;
        }

        public int pop() {
            if (top == -1) {
                return -1;
            }
            return stackArray[top--];
        }

        public int peek() {
            if (top == -1) {
                System.out.println("Stack is empty");
                return -1;
            }
            return stackArray[top];
        }

        public boolean isEmpty() {
            return (top == -1);
        }
    }


    static class Graph {

        //amount of nodes provided in the assignment
        private int amount_nodes = 9;

        //making an array of chars calling nodes to represent a stack later on
        private char[] nodes = new char[amount_nodes];

        //array to check if a node/path has been visited or not
        private boolean[][] adjacencyMatrix = new boolean[amount_nodes][amount_nodes];
        private int size = 0;

        public void addNode(char node) {
            nodes[size++] = node;
        }

        public void addEdge(char start, char[] ends) {
            int startIndex = getNodeIndex(start);
            if (startIndex != -1) {
                for (char end : ends) {
                    int endIndex = getNodeIndex(end);
                    if (endIndex != -1) {
                        adjacencyMatrix[startIndex][endIndex] = true;
                    }
                }
            }
        }




        public void depthFirstSearch(char start) {
            int startIndex = -1;
            for (int i = 0; i < size; i++) {
                if (nodes[i] == start) {
                    startIndex = i;
                    break;
                }
            }

            if (startIndex == -1) {
                System.out.println("Node not found in the graph.");
                return;
            }

            boolean[] visited = new boolean[amount_nodes];
            Stack stack = new Stack(amount_nodes);

            System.out.print("Depth-First Search: ");

            stack.push(startIndex);
            visited[startIndex] = true;

            while (!stack.isEmpty()) {
                int currentIndex = stack.pop();
                System.out.print(nodes[currentIndex] + " ");

                for (int i = 0; i < size; i++) {
                    if (adjacencyMatrix[currentIndex][i] && !visited[i]) {
                        stack.push(i);
                        visited[i] = true;
                    }
                }
            }
            System.out.println();
        }



        private int getNodeIndex(char node) {
            for (int i = 0; i < size; i++) {
                if (nodes[i] == node) {
                    return i;
                }
            }
            return -1;
        }

        public void minimumPathSearch(char start, char end) {
            int startIndex = getNodeIndex(start);
            int endIndex = getNodeIndex(end);
            if (startIndex == -1 || endIndex == -1) {
                System.out.println("Node not found in the graph.");
                return;
            }

            int[] distance = new int[amount_nodes];
            int[] previous = new int[amount_nodes];
            boolean[] visited = new boolean[amount_nodes];

            for (int i = 0; i < amount_nodes; i++) {
                distance[i] = Integer.MAX_VALUE;
                previous[i] = -1;
            }
            distance[startIndex] = 0;

            int[] queue = new int[amount_nodes];
            int front = 0, rear = 0;
            queue[rear++] = startIndex;

            while (front != rear) {
                int u = queue[front++];
                if (front == amount_nodes) {
                    front = 0;
                }
                visited[u] = true;

                for (int v = 0; v < size; v++) {
                    if (!visited[v] && adjacencyMatrix[u][v] && distance[u] != Integer.MAX_VALUE &&
                            distance[u] + 1 < distance[v]) {
                        distance[v] = distance[u] + 1;
                        previous[v] = u;
                        queue[rear++] = v;
                        if (rear == amount_nodes) {
                            rear = 0;
                        }
                    }
                }
            }


            int node = endIndex;
            int[] path = new int[amount_nodes];
            int pathSize = 0;
            while (node != -1) {
                path[pathSize++] = node;
                node = previous[node];
            }

            if (distance[endIndex] != Integer.MAX_VALUE) {
                for (int i = pathSize - 1; i >= 0; i--) {
                    System.out.print(nodes[path[i]] + " ");
                }
                System.out.println();
            }
        }


    }

    //main method to initialize the graph and handles the userInterface
    public static void main(String[] args){
        Scanner scrn = new Scanner(System.in);
        Graph newgraph = new Graph();
        for (char node = 'A'; node <= 'I'; node++) {
            newgraph.addNode(node);
        }
        //adding stuff to premade graph specifified
        char[] nodesToAddForA = {'B', 'C', 'D'};
        newgraph.addEdge('A', nodesToAddForA);

        char[] nodesToAddForB = {'E'};
        newgraph.addEdge('B',nodesToAddForB);

        char[] nodesToAddForC = {'B', 'G'};
        newgraph.addEdge('C',nodesToAddForC);

        char[] nodesToAddForD = {'C','G'};
        newgraph.addEdge('D',nodesToAddForD);

        char[] nodesToAddForE = {'C','F'};
        newgraph.addEdge('E',nodesToAddForE);

        char[] nodesToAddForF = {'C','H'};
        newgraph.addEdge('F',nodesToAddForF);

        char[] nodesToAddForG = {'F','H','I'};
        newgraph.addEdge('G',nodesToAddForG);

        char[] nodesToAddForH = {'E','I'};
        newgraph.addEdge('H',nodesToAddForH);

        char[] nodesToAddForI = {'F'};
        newgraph.addEdge('I',nodesToAddForI);




        String userInput;
        boolean isRunning = true;
        while (isRunning) {
        System.out.print("\t\t\tM E N U\n" +
                "Depth-First Search (0), Minimum Path Search (1)\n" +
                "Exit Program (3)\n" +
                "\t\t\tChoose? ");
        try {
            userInput = scrn.nextLine();
            String[] userChoice = userInput.split("\\s+");
            switch (Integer.parseInt(userChoice[0])) {
                case 0:
                    //deapth first
                    try {
                        newgraph.depthFirstSearch(userChoice[1].toUpperCase().charAt(0));
                    } catch (Exception e) {
                        System.out.println("Invalid input your input should be as follows: 0 [letter] please try again");
                    }
                    break;
                case 1:
                    //min search path
                    try {
                        newgraph.minimumPathSearch(userChoice[1].toUpperCase().charAt(0), userChoice[2].toUpperCase().charAt(0));
                    } catch (Exception e) {
                        System.out.println("Invalid input your input should be as follows: 1 [letter] [letter] please try again");

                    }
                    break;
                case 3:
                    isRunning = false;
                    break;

            }
        }catch (Exception e){
            System.out.println("Invalid Input try again.");
        }
        }
    }
}
