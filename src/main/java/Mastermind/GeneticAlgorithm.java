package Mastermind;

import java.util.*;
import Utils.Utils;

public class GeneticAlgorithm {

    private HashSet<String> population;


    /**
     * Genetic algorithm used for resolved Mastermind
     * @param numberAvailable The list of numbers contained in the secret code
     * @param computerProposal The last proposal of the computer
     * @param pattern The pattern of black and white pins associated with the last proposal
     * @return The new proposal of the computer
     */
    public int[] geneticAlgorithm (List numberAvailable, int[] computerProposal, int[] pattern, int loop) {
        if (loop == 0) {
            for (int i = 0; i < numberAvailable.size(); i++) {
                computerProposal[i] = (int)numberAvailable.get(i);
            }
            population = initPopulation(numberAvailable, 100);
        } else {
            computerProposal = utilsMastermind.stringToCombination(fitness(computerProposal, population, pattern));
            if (population.size() == 1 && pattern[0] != numberAvailable.size())
                population = initPopulation(numberAvailable, 100);
        }

        return computerProposal;
    }

    /**
     * Initialize the population for the genetic algorithm
     * @param numberAvailable The list of number contained in the secret code
     * @param popSize The size of population created by the method
     * @return popSize length set containing different arrangement that can be the secret number
     */
    private HashSet<String> initPopulation (List numberAvailable, int popSize) {
        HashSet<String> population = new HashSet<>();

        List<Object> listTemp = new ArrayList<>();
        List<Object> listTemp2 = new ArrayList<>();
        for (Object item : numberAvailable) {
            if (!(listTemp.contains(item)))
                listTemp.add(item);
            else if (!(listTemp2.contains(item)))
                listTemp2.add(item);
        }
        int numberDifferentColors = listTemp.size()-1;
        int numberRepeatColors = listTemp2.size();

        while (population.size() < popSize) {
            if (population.size() != utilsMastermind.numberPossibleCombination(numberAvailable.size(),numberDifferentColors,numberRepeatColors)) {
                Collections.shuffle(numberAvailable);
                StringBuilder individual = new StringBuilder();
                for (Object obj : numberAvailable)
                    individual.append(obj);
                population.add(individual.toString());
            } else {
                break;
            }
        }

        return population;
    }

    /**
     * Calculate the new proposal of the computer with the previous proposal, the population previously initialized
     * and the pattern of black and white pegs
     * @param computerProposal The previous proposal of the computer
     * @param population The set of different arrangement that can be the secret code
     * @param pattern The pattern of black and white pegs to the latest proposal of the computer
     * @return The new proposal of the computer
     */
    private String fitness (int[] computerProposal, HashSet<String> population, int[] pattern) {
        List<Object> delList = new ArrayList<>();
        Set populationBuffer = new HashSet<>(population);
        Map<Integer, String> minimum = new HashMap<>();
        for (Object individual : population) {
            if (!compare(computerProposal, utilsMastermind.stringToCombination((String)individual), pattern)) {
                delList.add(individual);
            }
        }

        minimum.put(delList.size(), Utils.combinationToString(computerProposal));

        for (Object del : delList)
            population.remove(del.toString());

        for (Object individual : population) {
            delList = new ArrayList<>();
            for (Object oldIndividual : populationBuffer) {
                if (!compare(utilsMastermind.stringToCombination((String)individual), utilsMastermind.stringToCombination((String)oldIndividual), pattern)) {
                    delList.add(oldIndividual);
                }
            }
            minimum.put(delList.size(), (String)individual);
        }

        Set key = minimum.keySet();
        int maxValue = 0;

        for (Object i : key) {
            if((int)i > maxValue)
                maxValue = (int)i;
        }

        return minimum.get(maxValue);
    }

    /**
     * Part of fitness method that can compare the proposal with each individual of the population
     * @param proposal The previous proposal of the computer
     * @param individual One individual contained in the population
     * @param pattern The pattern of black and white pegs to the latest proposal of the computer
     * @return True if the pattern returned is the same as that of the previous proposal
     */
    private boolean compare (int[] proposal, int[] individual, int[] pattern) {
        int[] proposalTemp = proposal.clone();
        int individualBlackPins = 0;
        int individualWhitePins = 0;
        boolean isSame;

        for (int i = 0; i < proposal.length; i++) {
            if (utilsMastermind.tableContains(proposal, individual[i])) {
                if (proposal[i] == individual[i]) {
                    individualBlackPins++;
                    proposalTemp[i] = -1;
                }
            }
        }

        List<Integer> listTemp = new ArrayList<>();
        for (int item : individual) {
            if (utilsMastermind.tableContains(proposalTemp,item) && !(listTemp.contains(item)))
                listTemp.add(item);
        }

        for (int item : listTemp)
            individualWhitePins += utilsMastermind.countOccurrences(proposalTemp, item);

        isSame = pattern[0] == individualBlackPins && pattern[1] == individualWhitePins;

        return isSame;
    }
}
