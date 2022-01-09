package com.example.a2d_game_template;

public class Utils {

    //Returns distance between 2D points p1 and p2
    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt(Math.pow(p1x - p2x, 2) + Math.pow(p1y - p2y, 2));
    }
}
