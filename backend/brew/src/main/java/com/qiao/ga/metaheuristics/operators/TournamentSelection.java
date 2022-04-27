package com.qiao.ga.metaheuristics.operators;

import java.util.List;
import java.util.Random;
import com.qiao.ga.fixture.Fixture;

public class TournamentSelection {

	public static Random random = new Random();

	public static int ROUNDS = 4;

	/**
	 * This method compute the tournament winner (tournament with n.rounds = ROUNDS)
	 * @param population from which to select the winner
	 * @return the winner fixture in the current population
	 */
	public static Fixture getWinnerFixture(List<Fixture> population) {
		int new_num = random.nextInt(population.size());
		int winnerIndex = new_num;

		FixtureComparator fc = new FixtureComparator();

		int round = 0;

		while (round < ROUNDS - 1) {
			new_num = random.nextInt(population.size());
			Fixture selected = population.get(new_num);
			if (fc.compare(selected, population.get(winnerIndex)) < 0) {
				winnerIndex = new_num;
			}
			round++;
		}

		return population.get(winnerIndex);
	}
}
