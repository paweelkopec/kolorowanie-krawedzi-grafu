/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isk.kkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.softtechdesign.ga.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pawel
 */
public class KolorowanieKrawedziGrafu extends GAStringsSeq {


    /**
     * Konstruktor
     *
     * @param conf
     * @param graf
     * @throws GAException
     */
    public KolorowanieKrawedziGrafu(Konfiguracja conf, Graf graf) throws GAException {

        
        super(graf.getLiczbaKrawedzi(), //size of chromosome
                conf.getPopulation(), //population has N chromosomes
                conf.getCrossoverProbability(), //crossover probability
                conf.getRandomSelectionChance(), //random selection chance % (regardless of fitness)
                conf.getMaxGenerations(), //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                0, //max generations per prelim run
                conf.getMutationProbablity(), //chromosome mutation prob.
                0, //number of decimal places in chrom
                graf.getMozliweKolory(), //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                false); //compute statisitics?

    }

    public double getFitness(int iChromIndex) {
                
        ChromStrings chromosome = (ChromStrings) this.getChromosome(iChromIndex);
        int uzyteKolory = liczbaUzytychKolorow(chromosome);
        int reprezentacja  = this.liczbaPrzyleglychKrawedziTakiegoSamegoKoloru(chromosome);
        if(reprezentacja>0) 
            return  1.0/reprezentacja;
        else
            return  1 + (1.0/uzyteKolory);
        
    }

    /**
     * Zwraca liczbe kolorow uzytych do kolorowania grafu
     *
     * @param chromosome
     * @return int
     */
    public int liczbaUzytychKolorow(ChromStrings chromosome) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < chromosomeDim; i++) {
            if (!list.contains(chromosome.getGene(i))) {
                list.add(chromosome.getGene(i));
            }
        }
        return list.size();
    }

    /**
     * Zwraca liczbę przyległych krawędzi tego samego koloru w całym chromosomie
     * in whole chromosome
     *
     * @param chromosome
     * @return
     */
    protected int liczbaPrzyleglychKrawedziTakiegoSamegoKoloru(ChromStrings chromosome) {
        
      
        int count = 0;
        Graf graf = Graf.getInstancja();
        int[][] strukturaGrafu = graf.getStruktura();
        int krawedzie = this.chromosomeDim;
        int wierzcholki = graf.getLiczbaWierzcholkow();

        for (int i = 0; i < krawedzie; i++) {
            strukturaGrafu[0][i] = Integer.parseInt(chromosome.getGene(i).trim());
        }

        for (int v = 1; v <= wierzcholki; v++) {
            int n = 0;
            int[] temp = new int[krawedzie];
            for (int e = 0; e < krawedzie; e++) {
                if (strukturaGrafu[1][e] == v || strukturaGrafu[2][e] == v) {
                    temp[n] = strukturaGrafu[0][e];
                    n++;
                }
            }
            count += numberOfRepetitions(temp, n);
        }
        return count;
    }

    /**
     * Returns number of time each color in array has repeated itself.
     *
     * @param array of colors adjacent to given vertex
     * @param number of colors in array
     * @return number of repetitions
     */
    private int numberOfRepetitions(int tab[], int n) {
        int rep = 0;
        int[] tab2 = new int[n];
        for (int i = 0; i < n; i++) {
            tab2[i] = tab[i];
        }
        Arrays.sort(tab2);
        for (int i = 0; i < n - 1; i++) {
            if (tab2[i] == tab2[i + 1]) {
                rep++;
            }
        }
        return rep;
    }

    public static void main(String[] args) {

        System.out.println("Kolorowanie krawedzi grafu uruchomione");
        try {

            String plikGraf1 = "src/com/isk/kkg/dane/graf_1";
            String plikGraf2 = "src/com/isk/kkg/dane/2016_04_06-18_51_05.txt";
            
            Graf graf = new Graf(new File(plikGraf1));
            System.out.println(graf.toString());
            Konfiguracja config = new Konfiguracja();

            config.setPopulation(500);
            config.setMaxGenerations(1000);
            config.setCrossoverProbability(0.7);
            config.setMutationProbablity(0.2);
            config.setRandomSelectionChance(10);

            KolorowanieKrawedziGrafu kkg = new KolorowanieKrawedziGrafu(config, graf);            
            Thread threadKkg = new Thread(kkg);
            threadKkg.start();

        } catch (FileNotFoundException ex) {
            System.out.println("Błąd wczytywania pliku: " + ex.getMessage());
            //Logger.getLogger(KolorowanieKrawedziGrafu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GAException ex) {
            System.out.println(ex.getMessage());
        }

    }

    
}
