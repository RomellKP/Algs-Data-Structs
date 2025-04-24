

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class MyMaze{
    public Cell[][] maze;
    public int startRow;
    public int endRow;

    //constructor instatiates maze, startRow and endRow
    public MyMaze(int rows, int cols, int startRow, int endRow) {
        this.maze = new Cell[rows][cols];
        this.startRow = startRow;
        this.endRow = endRow;

        //loop populates the maze double array
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[i].length; j++){
                maze[i][j] = new Cell();
            }
        }
        this.maze[endRow][maze[endRow].length-1].setRight(false);
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    //makes the maze
    public static MyMaze makeMaze(int level) {

        Random r = new Random();
        int randStart = r.nextInt(5);
        int randEnd = r.nextInt(5);
        int rRows;
        int cCols;

        //statements check the level and change rRows and cCols based on the level
        if(level == 1){
            rRows = 5;
            cCols = 5;
        }
        else if(level == 2){
            rRows = 5;
            cCols = 20;
        }
        else{
            rRows = 20;
            cCols = 20;
        }

        //creates a MyMaze object, a stack, pushes the start of the maze into the stack, and sets to true
        MyMaze mMaze = new MyMaze(rRows, cCols, randStart, randEnd);
        Stack1Gen<int[]> s = new Stack1Gen();
        s.push(new int[]{mMaze.startRow, 0});
        mMaze.maze[mMaze.startRow][0].setVisited(true);

        //creates a current position array with the location of the starting cell in it
        int[] curPos = new int[2];
        curPos[0] = mMaze.startRow;
        curPos[1] = 0;
        int randDir;

        //while there is still at least one item in the queue it
        while(!s.isEmpty()) {

            //creates an array of four numbers and counts
            int[] range = new int[4];
            int[] t = s.top();
            curPos = t;
            int counts = 0;

            //adds numbers to range(number of available directions) based on if in bounds and visited
            //updates counts
            if(0 < curPos[0]){
                if(mMaze.maze[curPos[0] - 1][curPos[1]].getVisited() == false){
                    range[counts] = 0;
                    counts++;
                }
            }
            if(mMaze.maze.length > curPos[0] + 1){
                if(mMaze.maze[curPos[0] + 1][curPos[1]].getVisited() == false){
                    range[counts] = 1;
                    counts++;
                }

            }
            if(mMaze.maze[0].length > curPos[1] + 1){
                if(mMaze.maze[curPos[0]][curPos[1] + 1].getVisited() == false) {
                    range[counts] = 2;
                    counts++;
                }
            }
            if (curPos[1] > 0){
                if(mMaze.maze[curPos[0]][curPos[1] - 1].getVisited() == false) {
                    range[counts] = 3;
                    counts++;
                }
            }

            //pops the stack if no diections were added to range
            //else chooses a direction and sets visited to true in that direction while also pushing the stack with more cell locations
            if(counts == 0) {
                s.pop();
            }
            else{
                randDir = range[r.nextInt(counts)];
                if (randDir == 0) {
                    mMaze.maze[curPos[0] - 1][curPos[1]].setVisited(true);
                    mMaze.maze[curPos[0] - 1][curPos[1]].setBottom(false);
                    s.push(new int[]{curPos[0] - 1, curPos[1]});
                }

                else if (randDir == 1) {
                    mMaze.maze[curPos[0] + 1][curPos[1]].setVisited(true);
                    mMaze.maze[curPos[0]][curPos[1]].setBottom(false);
                    s.push(new int[]{curPos[0] + 1, curPos[1]});
                }
                else if (randDir == 2) {
                    mMaze.maze[curPos[0]][curPos[1] + 1].setVisited(true);
                    mMaze.maze[curPos[0]][curPos[1]].setRight(false);
                    s.push(new int[]{curPos[0], curPos[1] + 1});
                }
                else if (randDir == 3) {
                    mMaze.maze[curPos[0]][curPos[1] - 1].setVisited(true);
                    mMaze.maze[curPos[0]][curPos[1] - 1].setRight(false);
                    s.push(new int[]{curPos[0], curPos[1] - 1});
                }
            }
        }

        //sets all of the visited to false for solveMaze() later
        for(int i = 0; i < mMaze.maze.length; i++){
            for(int j = 0; j < mMaze.maze[i].length; j++){
                mMaze.maze[i][j].setVisited(false);
            }
        }

        //returns the made maze
        return mMaze;
    }

    /* TODO: Print a representation of the maze to the terminal */
    //prints a visual representation of the maze and the solver solving it
    public void printMaze() {

        //prints the first line of maze
        for(int i = 0; i < maze[0].length; i++){
            System.out.print("|---");
        }
        System.out.print("|\n");

        //prints through every row until the last row
        for(int i = 0; i < maze.length; i++){
            //prints through every cell
            for(int j = 0; j < maze[i].length; j++){
                //If it is the start of the maze prints " ", if just start column, prints |
                if(i == startRow && j == 0){
                    System.out.print(" ");
                }
                if(i != startRow && j == 0){
                    System.out.print("|");
                }
                //prints * if visited, else prints " "
                if(maze[i][j].getVisited() == true){
                    System.out.print(" * ");
                }
                else{
                    System.out.print("   ");
                }
                //prints walls if there is any
                if(maze[i][j].getRight() == true){
                    System.out.print("|");
                }
                else{
                    System.out.print(" ");
                }
            }

            //prints last row
            System.out.println();
            for(int j = 0; j < maze[i].length; j++){
                if(i == maze.length-1){
                    System.out.print("|---");
                }
                else if(maze[i][j].getBottom() == true){
                    System.out.print("|---");
                }
                else{
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }

    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        //creates queue
        Q1Gen<int[]> mQueue = new Q1Gen<>();
        mQueue.add(new int[]{startRow, 0});

        //while queue isn't empty, keep visiting new cells
        while (mQueue.length() != 0) {
            //creates a queue and visits the cell in the queue
            int[] temp = mQueue.remove();
            maze[temp[0]][temp[1]].setVisited(true);
            //break thr loop if at the end of the maze
            if(temp[0] == endRow && temp[1] == maze[0].length-1){
                break;
            }
            //if statements if in bounds and hasn't visited adds to queue
            //up
            if(temp[0] > 0){
                if(maze[temp[0] - 1][temp[1]].getBottom() == false){
                    if(maze[temp[0] - 1][temp[1]].getVisited() == false){
                        mQueue.add(new int[]{temp[0] - 1, temp[1]});
                    }
                }
            }
            //down
            if(temp[0] < maze.length - 1){
                if(maze[temp[0]][temp[1]].getBottom() == false){
                    if(maze[temp[0] + 1][temp[1]].getVisited() == false){
                        mQueue.add(new int[]{temp[0] + 1, temp[1]});
                    }
                }
            }
            //right
            if(temp[1] < maze[0].length-1){
                if(maze[temp[0]][temp[1]].getRight() == false){
                    if(maze[temp[0]][temp[1] + 1].getVisited() == false){
                        mQueue.add(new int[]{temp[0], temp[1] + 1});
                    }
                }
            }
            //left
            if(temp[1] > 0){
                if(maze[temp[0]][temp[1] - 1].getRight() == false){
                    if(maze[temp[0]][temp[1] - 1].getVisited() == false){
                        mQueue.add(new int[]{temp[0], temp[1] - 1});
                    }
                }
            }
        }
        //calls prints maze to create a visual representation
        printMaze();
    }

    public static void main(String[] args){
        /* Use scanner to get user input for maze level, then make and solve maze */
        //creates scanner and says hi to user
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the maze project!");
        System.out.println("What level would you like to do (1, 2, or 3)");
        //determines level
        String lvl = s.nextLine();
        int level;
        level = Integer.parseInt(lvl);
        //creates a maze based on inputted level
        MyMaze mazer;
        mazer = makeMaze(level);
        //solves the maze
        mazer.solveMaze();
        //says bye to user
        System.out.println("We hope you enjoyed our maze :)");
    }
}
