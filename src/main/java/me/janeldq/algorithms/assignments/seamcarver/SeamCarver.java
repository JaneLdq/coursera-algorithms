package me.janeldq.algorithms.assignments.seamcarver;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

/**
 * Algorithms Part 2 Week 2 Programming Assignment: Seam Carving
 * Create a data type that resizes a W-by-H image using the seam-carving technique.
 *
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/seam.html
 *
 * @author Jane
 *
 */
public class SeamCarver {

    private int[][][] pixals;

    private int[][][] transPixals;

    private int width;

    private int height;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture can not be null.");
        }
        width = picture.width();
        height = picture.height();
        pixals = new int[height][width][3];
        transPixals = new int[width][height][3];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = picture.get(j, i);
                pixals[i][j][0] = c.getRed();
                pixals[i][j][1] = c.getGreen();
                pixals[i][j][2] = c.getBlue();
                transPixals[j][i][0] = c.getRed();
                transPixals[j][i][1] = c.getGreen();
                transPixals[j][i][2] = c.getBlue();
            }
        }
    }

    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] rgb = pixals[i][j];
                pic.set(j, i, new Color(rgb[0], rgb[1], rgb[2]));
            }
        }
        return pic;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IllegalArgumentException("Index out of range");
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return 1000.0;
        int[] cXL = pixals[y][x-1];
        int[] cXR = pixals[y][x+1];
        int[] cXU = pixals[y-1][x];
        int[] cXD = pixals[y+1][x];
        double deltaX = Math.pow(cXL[0] - cXR[0], 2)
                + Math.pow(cXL[1] - cXR[1], 2)
                + Math.pow(cXL[2] - cXR[2], 2);
        double deltaY = Math.pow(cXU[0] - cXD[0], 2)
                + Math.pow(cXU[1] - cXD[1], 2)
                + Math.pow(cXU[2] - cXD[2], 2);
        return Math.sqrt(deltaX + deltaY);
    }

    public int[] findHorizontalSeam() {
        transposePicture();
        int[] seam = findVerticalSeam();
        transposePicture();
        return seam;
    }

    public int[] findVerticalSeam() {
        double[][] energy = new double[height][width];
        double[][] distTo = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energy[i][j] = energy(j, i);
                distTo[i][j] = i == 0 ? energy[i][j] : Double.POSITIVE_INFINITY;
            }
        }
        int[][] edgeTo = new int[height][width];
        int[] seam = new int[height];
        // Topological order
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                // get Picture[i][j]'s adj [i+1][j-1], [i+1][j], [i][j+1]
                relax(energy, distTo, edgeTo, j, i);
            }
        }
        double[] minEnergy = new double[width];

        for (int i = 0; i < width; i++) {
            minEnergy[i] = energy[height - 1][i];
            int w = i;
            for (int j = height - 1; j > 0; j--) {
                w -= edgeTo[j][w];
                minEnergy[i] += energy[j-1][w];
            }
        }
        int k = 0;
        for (int i = 1; i < minEnergy.length; i++) {
            if (minEnergy[i] < minEnergy[k]) k = i;
        }
        seam[height - 1] = k;
        for (int i = height - 1; i > 0; i--) {
            k = k - edgeTo[i][k];
            seam[i-1] = k;
        }
        return seam;
    }

    private void relax(double[][] energy, double[][] distTo, int[][] edgeTo, int x, int y) {
        if (x > 0) {
            if (distTo[y+1][x-1] > distTo[y][x] + energy[y+1][x-1]) {
                distTo[y+1][x-1] = distTo[y][x] + energy[y+1][x-1];
                edgeTo[y+1][x-1] = -1;
            }
        }
        if (distTo[y+1][x] > distTo[y][x] + energy[y+1][x]) {
            distTo[y+1][x] = distTo[y][x] + energy[y+1][x];
            edgeTo[y+1][x] = 0;
        }
        if (x < width - 1) {
            if (distTo[y+1][x+1] > distTo[y][x] + energy[y+1][x+1]) {
                distTo[y+1][x+1] = distTo[y][x] + energy[y+1][x+1];
                edgeTo[y+1][x+1] = 1;
            }
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        if (height() <= 1 || seam == null || seam.length != width() || !checkSeam(seam, height()-1)) {
            throw new IllegalArgumentException("seam is null or invalid.");
        }
        transposePicture();
        removeVerticalSeam(seam);
        transposePicture();
    }

    public void removeVerticalSeam(int[] seam) {
        if (width() <= 1 || seam == null || seam.length != height() || !checkSeam(seam, width()-1)) {
            throw new IllegalArgumentException("seam is null or invalid");
        }
        for (int i = 0; i < height; i++) {
            for (int j = seam[i]; j < width - 1; j++) {
                pixals[i][j] = pixals[i][j+1];
            }
        }
        width--;
    }

    private void transposePicture() {
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                transPixals[col][row] = pixals[row][col];
            }
        }
        int[][][] tmpPixals = pixals;
        pixals = transPixals;
        transPixals = tmpPixals;

        int tmpWidth = width;
        width = height;
        height = tmpWidth;
    }

    private boolean checkSeam(int[] seam, int max) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] < 0 || seam[i] > max || Math.abs(seam[i] - seam[i+1]) > 1)
                return false;
        }
        if (seam[seam.length - 1] < 0 || seam[seam.length-1] > max) return false;
        return true;
    }
}
