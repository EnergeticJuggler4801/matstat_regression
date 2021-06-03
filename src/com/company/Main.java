package com.company;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.*;
import java.math.*;

public class Main {

    public static void fill(double[] a) {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < a.length; i++) {
            a[i] = in.nextDouble();
        }
    }

    public static void print(double[] a) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i < a.length; i++) {
            String result = decimalFormat.format(a[i]);
            System.out.print(result + " ");
        }
    }

    public static int indexOfMax(double[] a) {
        int indexOfMax = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > a[indexOfMax]) {
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }

    public static double ch_avg(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i] * b[i];
        }
        return sum / 100;
    }

    public static double avg_square(double[] a, double[] b) {
        double sumSquare = 0;
        for (int i = 0; i < a.length; i++) {
            sumSquare = sumSquare + a[i] * a[i] * b[i];
        }
        return sumSquare / 100;
    }

    public static String rounding(double a) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String result = decimalFormat.format(a);
        return result;

    }

    public static void main(String[] args) {
        double[] x = new double[6];
        double[] y = new double[8];
        double[] vx = new double[6];
        double[] vy = new double[8];
        double[] u = new double[6];
        double[] v = new double[8];
        double[][] vxy = new double[6][8];


        System.out.println("Enter x: ");
        fill(x);
        System.out.println("Enter vx: ");
        fill(vx);
        System.out.println("Enter y: ");
        fill(y);
        System.out.println("Enter vy: ");
        fill(vy);
        System.out.println("Enter vxy: ");

        Scanner in = new Scanner(System.in);
        for (int i = 0; i < vxy.length; i++) {
            for (int j = 0; j < vxy[0].length; j++) {
                //System.out.println("Enter [" + i + "][" + j + "]: ");
                vxy[i][j] = in.nextDouble();
            }
        }

        System.out.println("X:");
        print(x);
        System.out.println("\nY:");
        print(y);
        System.out.println("\nV xy: ");

        DecimalFormat decimalFormat = new DecimalFormat("#");

        for (int i = 0; i < vxy.length; i++) {
            for (int j = 0; j < vxy[0].length; j++) {
                if (vxy[i][j] == 0) {
                    System.out.print("-\t");
                } else {
                    String result = decimalFormat.format(vxy[i][j]);
                    System.out.print(result + "\t");
                }
            }
            System.out.println("");
        }

        for (int i = 0; i < x.length; i++) {
            u[i] = (x[i] - x[indexOfMax(vx)]) / (x[2] - x[1]);
        }
        for (int i = 0; i < y.length; i++) {
            v[i] = (y[i] - y[indexOfMax(vy)]) / (y[2] - y[1]);
        }

        decimalFormat = new DecimalFormat("#.##");
        String result = decimalFormat.format((y[2] - y[1]));
        System.out.println("\nu = (x - " + x[indexOfMax(vx)] + ")/" + rounding(x[2] - x[1]));
        System.out.println("v = (y - " + y[indexOfMax(vy)] + ")/" + rounding(y[2] - y[1]));

        System.out.println("\nU:");
        print(u);
        System.out.println("\nV:");
        print(v);

        double unfixed_u = (avg_square(u, vx)) - (ch_avg(u, vx) * ch_avg(u, vx));
        double unfixed_v = (avg_square(v, vy)) - (ch_avg(v, vy) * ch_avg(v, vy));

        System.out.println("\n\naverage u: " + rounding(ch_avg(u, vx)));
        System.out.println("average square u: " + rounding(avg_square(u, vx)));
        System.out.println("unfixed dispersion u: " + rounding(unfixed_u));

        System.out.println("\naverage v: " + ch_avg(v, vy));
        System.out.println("average square v: " + avg_square(v, vy));
        System.out.println("unfixed dispersion v: " + rounding(unfixed_v));

        System.out.println("\nv*Vxy: ");
        double sum;
        for (int i = 0; i < vxy.length; i++) {
            sum = 0;
            for (int j = 0; j < vxy[0].length; j++) {
                vxy[i][j] = vxy[i][j] * v[j];
                sum = sum + vxy[i][j];
            }
            System.out.print(rounding(sum) + "\t");
        }
        System.out.println("");

        System.out.println("\nu*v*Vxy: ");
        double sumAll = 0;
        for (int i = 0; i < vxy.length; i++) {
            sum = 0;
            for (int j = 0; j < vxy[0].length; j++) {
                vxy[i][j] = vxy[i][j] * u[i];
                sum = sum + vxy[i][j];
                sumAll = sumAll + vxy[i][j];
            }
            System.out.print(rounding(sum) + "\t");
        }
        double uv_avg = sumAll / 100;
        double xy_avg = (x[2] - x[1]) * (y[2] - y[1]) * uv_avg + (x[2] - x[1]) * y[indexOfMax(vy)] * ch_avg(u, vx) + x[indexOfMax(vx)] * (y[2] - y[1]) * ch_avg(v, vy) + x[indexOfMax(vx)] * y[indexOfMax(vy)];
        double x_avg = (x[2] - x[1]) * ch_avg(u, vx) + x[indexOfMax(vx)];
        double y_avg = (y[2] - y[1]) * ch_avg(v, vy) + y[indexOfMax(vy)];
        double unfixed_x = (x[2] - x[1]) * (x[2] - x[1]) * unfixed_u;
        double unfixed_y = (y[2] - y[1]) * (y[2] - y[1]) * unfixed_v;

        System.out.println("\n\naverage uv: " + uv_avg);
        System.out.println("average x: " + x_avg);
        System.out.println("average y: " + y_avg);
        System.out.println("average xy: " + xy_avg);
        System.out.println("unfixed dispersion x: " + rounding(unfixed_x));
        System.out.println("unfixed dispersion y: " + rounding(unfixed_y));
        System.out.println("");

        double coef_x = (xy_avg - x_avg * y_avg) / unfixed_x;
        double coef_y = (xy_avg - x_avg * y_avg) / unfixed_y;
        double ost_x = (xy_avg - x_avg * y_avg) * (-1) * x_avg / unfixed_x + y_avg;
        double ost_y = (xy_avg - x_avg * y_avg) * (-1) * y_avg / unfixed_y + x_avg;

        System.out.println("x(y) = " + rounding(coef_y) + "y + " + rounding(ost_y));
        System.out.println("y(x) = " + rounding(coef_x) + "x + " + rounding(ost_x));
        System.out.println("\nx(" + y[0] + ") = " + rounding(coef_y * y[0] + ost_y));
        System.out.println("x(" + y[y.length - 1] + ") = " + rounding(coef_y * y[y.length - 1] + ost_y));
        System.out.println("\ny(" + x[0] + ") = " + rounding(coef_x * x[0] + ost_x));
        System.out.println("y(" + x[x.length - 1] + ") = " + rounding(coef_x * x[x.length - 1] + ost_x));
    }
}
