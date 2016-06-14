/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isk.kkg;

/**
 *
 * @author pawel
 */
public class Konfiguracja {
    
    private int population;
    private double crossoverProbability;
    private int randomSelectionChance;
    private int maxGenerations;
    private double mutationProbablity;
    private int numberOfPossibleGeneValues;
    
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public int getRandomSelectionChance() {
        return randomSelectionChance;
    }

    public void setRandomSelectionChance(double randomSelectionChance) {
        this.randomSelectionChance = (int)(randomSelectionChance*100);
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public double getMutationProbablity() {
        return mutationProbablity;
    }

    public void setMutationProbablity(double mutationProbablity) {
        this.mutationProbablity = mutationProbablity;
    }
    
    public void setNumberOfPossibleGeneValues(int numberOfPossibleGeneValues) {
        this.numberOfPossibleGeneValues = numberOfPossibleGeneValues;
    }
    
    public int getNumberOfPossibleGeneValues() {
		return numberOfPossibleGeneValues;
    }
}
