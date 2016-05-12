/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isk.kkg;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GrafGenerator {

    private static int krawedzie;

    /*
	 * How many wierzcholki are created based on number of edges, bottom limit and upper limit 
	 * For example for wierzcholkiBottomLimit = 0.3 and wierzcholkiUpperLimit = 0.6 and krawedzie = 100,
	 * the number of vertices will be between 30 and 60;
     */
    private static final double wierzcholkiDolnyLimit = 0.3;
    private static final double wierzcholkiGornyLimit = 0.3;

    public static void main(String[] args) throws IOException {

        File plik = new File("src/com/isk/kkg/dane/graf_2");
        GrafGenerator.krawedzie = 500;
        
        plik.createNewFile();
        if (plik.exists()) {
            System.out.println("Plik grafu: " + plik.toString());
        }

        int array[][] = new int[3][];
        array[0] = new int[krawedzie];
        array[1] = new int[krawedzie];
        array[2] = new int[krawedzie];

        generateEdges(array);

        PrintWriter writer = new PrintWriter(plik);
        writer.println(krawedzie);

        for (int i = 0; i < krawedzie; i++) {
            writer.print(array[0][i]);
            writer.print("  ");
            writer.print(array[1][i]);
            writer.print("  ");
            writer.print(array[2][i]);
            writer.print("\n");
        }
        writer.close();
        System.out.println("Graf został utworzony");
    }


    private static void generateEdges(int array[][]) {
        int wierzcholki = (int) ((Math.random() * (((wierzcholkiGornyLimit - wierzcholkiDolnyLimit) * krawedzie) + 1)) + wierzcholkiDolnyLimit * krawedzie);

        for (int i = 0; i < krawedzie; i++) {
            array[0][i] = i + 1;
        }
        int i = 0;
        int temp1 = -1, temp2 = -1;

        for (; i < wierzcholki - 1; i++) {
            array[1][i] = i + 1;
            array[2][i] = i + 2;
        }
        for (; i < krawedzie; i++) {
            temp1 = (int) (Math.random() * wierzcholki) + 1;
            temp2 = (int) (Math.random() * wierzcholki) + 1;
            while (temp1 == temp2) {
                temp2 = (int) (Math.random() * wierzcholki) + 1;
            }

            array[1][i] = temp1;
            array[2][i] = temp2;
        }
        System.out.println("Liczba krawedzi: " + krawedzie);
        System.out.println("Liczba wierzchołków: " + wierzcholki);
    }

}
