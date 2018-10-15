package main.java.Mastermind;

import main.java.utils;

import java.util.*;

class Computer {
    private List <Integer> sureNumber;
    private int loop;
    private HashSet<String> population;

    Computer() {
        init();
    }

    private void init () {
        sureNumber = new ArrayList<>();
        loop = 0;
    }

    /**
     * Analyze the previous proposal and construct a table of the numbers present in the secret code and make a proposal
     * @param proposal The previous proposal
     * @param present The amount of proposal's number in the secret code
     * @param goodNumber The amount of proposal's number which are in the same place as those of the secret code
     * @return The proposal of the computer
     */
    int[] computerProcessing(int[] proposal, int present, int goodNumber) {
        int[] computerProposal = proposal;
        int[] pattern = new int[] {goodNumber,present};

        if (goodNumber != 0 && sureNumber.size() < (utils.getSecretCodeLength("Mastermind"))) {
            for (int i = 0; i < goodNumber; i++) {
                sureNumber.add(computerProposal[0]);
            }
        }

        if (sureNumber.size() < (utils.getSecretCodeLength("Mastermind"))) {
            if (present != -1) {
                int newValue = computerProposal[0] + 1;
                for (int i = 0; i < utils.getSecretCodeLength("Mastermind"); i++) {
                    computerProposal[i] = newValue;
                }
            }
        } else {
            computerProposal = this.geneticAlgorithm(sureNumber, computerProposal, pattern);
        }

        return computerProposal;
    }

    /**
     * Genetic algorithm used for resolved Mastermind
     * @param numberAvailable The list of numbers contained in the secret code
     * @param computerProposal The last proposal of the computer
     * @param pattern The pattern of black and white pins associated with the last proposal
     * @return The new proposal of the computer
     */
    private int[] geneticAlgorithm (List numberAvailable, int[] computerProposal, int[] pattern) {
        if (loop == 0) {
            for (int i = 0; i < sureNumber.size(); i++) {
                computerProposal[i] = sureNumber.get(i);
            }
            population = initPopulation(numberAvailable, 100);
            loop++;
        } else {
            computerProposal = utils.stringToCombination(this.fitness(computerProposal, (HashSet<String>)population, pattern));
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
            if (population.size() != utils.numberPossibleCombination(numberAvailable.size(),numberDifferentColors,numberRepeatColors)) {
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
            if (!compare(computerProposal, utils.stringToCombination((String)individual), pattern)) {
                delList.add(individual);
            }
        }

        minimum.put(delList.size(), utils.combinationToString(computerProposal));

        for (Object del : delList)
            population.remove(del.toString());

        for (Object individual : population) {
            delList = new ArrayList<>();
            for (Object oldIndividual : populationBuffer) {
                if (!compare(utils.stringToCombination((String)individual), utils.stringToCombination((String)oldIndividual), pattern)) {
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
            if (utils.tableContains(proposal, individual[i])) {
                if (proposal[i] == individual[i]) {
                    individualBlackPins++;
                    proposalTemp[i] = -1;
                }
            }
        }

        List<Integer> listTemp = new ArrayList<>();
        for (int item : individual) {
            if (utils.tableContains(proposalTemp,item) && !(listTemp.contains(item)))
                listTemp.add(item);
        }

        for (int item : listTemp)
            individualWhitePins += utils.countOccurrences(proposalTemp, item);

        System.out.println(individualBlackPins + " " + individualWhitePins);
        isSame = pattern[0] == individualBlackPins && pattern[1] == individualWhitePins;

        return isSame;
    }
}

