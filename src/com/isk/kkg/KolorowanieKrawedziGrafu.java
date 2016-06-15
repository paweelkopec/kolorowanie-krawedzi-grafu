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
                graf.getMozliweKolory(conf.getNumberOfPossibleGeneValues()), //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                true); //compute statisitics?
        
    }

    public double getFitness(int iChromIndex) {
                
        ChromStrings chromosome = (ChromStrings) this.getChromosome(iChromIndex);
        int uzyteKolory = liczbaUzytychKolorow(chromosome);
        int bledy  = this.liczbaPrzyleglychKrawedziTakiegoSamegoKoloru(chromosome);
        
       
        double fitness;
        if(bledy>0) 
            fitness =  1.0/bledy;
        else
            fitness =  1 + (1.0/uzyteKolory);
        
        return fitness;
        
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
     * Zwraca liczba razy ile dany kolor powtorzyl
     * @param tab
     * @param n
     * @return 
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

            String plikGraf1 = "src/com/isk/kkg/dane/graf_100_40";
            String plikGraf2 = "src/com/isk/kkg/dane/graf_200_60";
            String plikGraf3 = "src/com/isk/kkg/dane/graf_500_150";

            Graf graf = new Graf(plikGraf3);
            //System.out.println(graf.toString());
            Konfiguracja config = new Konfiguracja();

            config.setPopulation(600);
            config.setMaxGenerations(2000);
            config.setCrossoverProbability(0.35);
            config.setMutationProbablity(0.55);
            config.setRandomSelectionChance(0.02);
            config.setNumberOfPossibleGeneValues(graf.getStopien());

            long start = System.currentTimeMillis();
            KolorowanieKrawedziGrafu kkg = new KolorowanieKrawedziGrafu(config, graf);
            Thread threadKkg = new Thread(kkg);
            threadKkg.start();
            
            threadKkg.join();
            long end= System.currentTimeMillis();
            long duration = (end-start)/1000;
            System.out.println("Parametry");
            System.out.println("Graf: " + graf.getPlikGrafu());
            System.out.println("Liczba krawędzi: " + graf.getLiczbaKrawedzi());
            System.out.println("Liczba wierzchołków grafu: " + graf.getLiczbaWierzcholkow());
            System.out.println("Stopień grafu: " + graf.getStopien());
            int deg = graf.getStopien();
            System.out.println("Rozwiązanie optymalne: " + deg + "-" + (deg * 3 / 2));
            System.out.println("Populacja: " + config.getPopulation());
            System.out.println("Liczba generacji: " + config.getMaxGenerations() );
            System.out.println("Krzyżowanie: " + config.getCrossoverProbability());
            System.out.println("Mutacja: " + config.getMutationProbablity());
            System.out.println("Losowy wybór: " + config.getRandomSelectionChance() + "%");
            System.out.println("Wynik");
            ChromStrings chrom = (ChromStrings) kkg.getFittestChromosome();
            System.out.println("Ilość błędów w kolorowaniu: " + kkg.liczbaPrzyleglychKrawedziTakiegoSamegoKoloru(chrom));
            System.out.println("Ilość użytych kolorów: " + kkg.liczbaUzytychKolorow(chrom));
            System.out.println("Zakończono na generacji: " + kkg.getFinalGeneration());
            System.out.println("Fitness: " + kkg.getFittestChromosomesFitness());
            System.out.println("Czas: " + duration + " s");

        } catch (FileNotFoundException ex) {
            System.out.println("Błąd wczytywania pliku: " + ex.getMessage());
            //Logger.getLogger(KolorowanieKrawedziGrafu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GAException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(KolorowanieKrawedziGrafu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}
