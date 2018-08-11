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

    private Picture picture;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture can not be null.");
        }
        this.picture = picture;
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IllegalArgumentException("Index out of range");
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return 1000.0;
        Color cXL = picture.get(x-1, y);
        Color cXR = picture.get(x+1, y);
        Color cXU = picture.get(x, y - 1);
        Color cXD = picture.get(x, y + 1);
        double deltaX = Math.pow(cXL.getRed() - cXR.getRed(), 2)
                + Math.pow(cXL.getGreen() - cXR.getGreen(), 2)
                + Math.pow(cXL.getBlue() - cXR.getBlue(), 2);
        double deltaY = Math.pow(cXU.getRed() - cXD.getRed(), 2)
                + Math.pow(cXU.getGreen() - cXD.getGreen(), 2)
                + Math.pow(cXU.getBlue() - cXD.getBlue(), 2);
        return Math.sqrt(deltaX + deltaY);
    }

    public int[] findHorizontalSeam() {
        transposePicture();
        int[] seam = findVerticalSeam();
        transposePicture();
        return seam;
    }

    public int[] findVerticalSeam() {
        double[][] energy = new double[picture.height()][picture.width()];
        double[][] distTo = new double[picture.height()][picture.width()];
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                energy[i][j] = energy(j, i);
                distTo[i][j] = i == 0 ? energy[i][j] : Double.POSITIVE_INFINITY;
            }
        }
        int[][] edgeTo = new int[picture.height()][picture.width()];
        int[] seam = new int[picture.height()];
        // Topological order
        for (int i = 0; i < picture.height()-1; i++) {
            for (int j = 0; j < picture.width(); j++) {
                // get Picture[i][j]'s adj [i+1][j-1], [i+1][j], [i][j+1]
                relax(energy, distTo, edgeTo, j, i);
            }
        }
        double[] minEnergy = new double[picture.width()];

        for (int i = 0; i < picture.width(); i++) {
            minEnergy[i] = energy[picture.height()-1][i];
            int w = i;
            for (int j = picture.height() - 1; j > 0; j--) {
                w -= edgeTo[j][w];
                minEnergy[i] += energy[j-1][w];
            }
        }
        int k = 0;
        for (int i = 1; i < minEnergy.length; i++) {
            if (minEnergy[i] < minEnergy[k]) k = i;
        }
        seam[picture.height()-1] = k;
        for (int i = picture.height() - 1; i > 0; i--) {
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
        if (x < picture.width() - 1) {
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
        Picture newPicture = new Picture(width()-1, height());
        for (int i = 0; i < newPicture.height(); i++) {
            for (int j = 0; j < newPicture.width(); j++) {
                newPicture.set(j, i, picture.get(j >= seam[i] ? j+1 : j, i));
            }
        }
        picture = newPicture;
    }

    private void transposePicture() {
        Picture transposePicture = new Picture(picture().height(), picture().width());
        for (int col = 0; col < width(); col++)
            for (int row = 0; row < height(); row++)
                transposePicture.set(row, col, picture.get(col, row));
        picture = transposePicture;
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
