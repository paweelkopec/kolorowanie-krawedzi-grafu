/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isk.kkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pawel
 */
public class Graf {

    private int liczbaKrawedzi;
    private int liczbaWierzcholkow;
    private int struktura[][];
    private String mozliweKolory[];
    private static Graf instancja;

    public Graf(File file) throws FileNotFoundException {

        Scanner scan = new Scanner(file);
        this.liczbaKrawedzi = scan.nextInt(); //<liczba-krawędzi>

        this.struktura = new int[this.liczbaKrawedzi][];
        this.struktura[0] = new int[this.liczbaKrawedzi];
        this.struktura[1] = new int[this.liczbaKrawedzi];
        this.struktura[2] = new int[this.liczbaKrawedzi];
        
        for (int wiersz = 0; wiersz < this.liczbaKrawedzi; wiersz++) {
            
            for (int kolumna = 0; kolumna < 3; kolumna++) { //<numer-krawędzi> <numer-węzła-źródłowego> <numer-węzła-docelowego>   
                this.struktura[kolumna][wiersz] = scan.nextInt();
            }
        }
        scan.close();
        this.generujMozliweKolory();
        this.policzLiczbeWierzcholkow();
        
        Graf.instancja = this;
    }

    @Override
    public String toString() {
        String s = "";

        for(int j =0; j< 3; j++){
            s += "\n" + Arrays.toString(struktura[j]);
        }
        return "Graf{" + "liczbaKrawedzi=" + liczbaKrawedzi + ", struktura=" + s +
                ", kolory=" + Arrays.toString(mozliweKolory) + ", liczbaWierzcholkow="+liczbaWierzcholkow+"}";
    }

    /**
     * Liczba kolorow ktore mogą byc uzyte po przez generyczny algorytm
     */
    private void generujMozliweKolory() {
        this.mozliweKolory = new String[this.liczbaKrawedzi];
        for (int i = 1; i <= this.liczbaKrawedzi; i++) {
            this.mozliweKolory[i-1] = " " + i;
        }
    }

    private void policzLiczbeWierzcholkow() {
        
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.liczbaKrawedzi; i++) {
            if (!list.contains("" + struktura[1][i])) {
                list.add("" + struktura[1][i]);
            }
            if (!list.contains("" + struktura[2][i])) {
                list.add("" + struktura[2][i]);
            }
        }
        this.liczbaWierzcholkow = list.size();
        
    }

    public String[] getMozliweKolory() {
        return mozliweKolory;
    }

    public int getLiczbaKrawedzi() {
        return liczbaKrawedzi;
    }

    public void setLiczbaKrawedzi(int liczbaKrawedzi) {
        this.liczbaKrawedzi = liczbaKrawedzi;
    }

    public int[][] getStruktura() {
        return struktura;
    }

    public void setStruktura(int[][] struktura) {
        this.struktura = struktura;
    }

    public int getLiczbaWierzcholkow() {
        return liczbaWierzcholkow;
    }

    public static Graf getInstancja() {
        return instancja;
    }
    
    
    

}
