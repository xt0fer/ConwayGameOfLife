package com.zipcodeconway;

import java.util.Arrays;

public class ConwayGameOfLife {

    private int[][] currentGen;
    private int[][] nextGen;
    private SimpleWindow displayWindow;

    public ConwayGameOfLife(Integer dimension) {
        this.displayWindow = new SimpleWindow(dimension);
        currentGen = createRandomStart(dimension);
       nextGen = new int[dimension][dimension];
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.displayWindow = new SimpleWindow(dimension);
        currentGen = startmatrix;
        nextGen = new int[dimension][dimension];

    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(50);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; i++) {
                currentGen[i][j] = (int) Math.round(Math.random());
            }
        }
        return currentGen;
    }

    public int[][] simulate(Integer maxGenerations) {
        int count = 0;
        //count has to be less than OR equal to maxGen because it has to be equal to the number of times it has to run
        while (count <= maxGenerations) {
            displayWindow.display(currentGen, count);
            //update nextGen from currentGen
            for (int row = 0; row < currentGen.length; row++) {
                for (int column = 0; column < currentGen[row].length; column++) {
                    nextGen[row][column] = isAlive(row, column, currentGen);
                }
            }
            copyAndZeroOut(nextGen, currentGen);
            displayWindow.sleep(125);
            count++;
        }
        return currentGen;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for(int i = 0; i < next.length; i++) {
            for(int j = 0; j < next.length; j++) {
                current[i][j] = next[i][j];
                next[i][j] = 0;
            }
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {
        int north = col -1;
        int south = col +1;
        int west = row -1;
        int east = row +1;

        if(north < 0) {
            north = world[row].length -1;
        }
        if (south == world[row].length) {
            south = 0;
        }
        if (west < 0) {
            west = world[col].length -1;
        }
        if (east == world[col].length) {
            east = 0;
        }

        int numOfLiveNeighbors = world[row][north] + world[east][north] + world[west][north] +
                world[row][south] + world[east][south] + world[west][south]
                + world[row][west] + world[row][east];

        if (numOfLiveNeighbors < 2 || numOfLiveNeighbors > 3)
            return 0;

        if (world[row][col] == 0 && numOfLiveNeighbors == 3)
            return 1;

        // if this cell we are looking at is alive
        // and my live neighbors = 2 or three
        if (world[row][col] == 1 && ((numOfLiveNeighbors == 2) || (numOfLiveNeighbors == 3)));
            return 1;
    }
}
