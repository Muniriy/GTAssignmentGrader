package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Agent which try to use a smart approach for wining games.
 * (Global maximum approach instead of local maximum in Greedy).
 * <p>
 * This agent store story of opponent last moves (boolean values) and 
 * previous state of fields. Then on each move it try to determine how opponent
 * will play using <i>opponentMoves</i> variable.
 * <p>
 * <i>opponentMoves</i> using in a following way. It stores boolean values 
 * where <i>true</i> means that opponent choose local maximum and <i>false</i> otherwise. 
 * <p>
 * Then on each turn agent decide between two strategies:
 * <ol>
 * <li>choose local maximum;</li>
 * <li>choose second local maximum ( i.e. [4, 3, 3] => (2 | 3) )
 * <p>
 * 		(but if only one maximum and other fields' characteristic equal zero
 * 		 agent choose maximum in order to reduce opponent's payoff).</li>
 * </ol>
 * <p>
 * If number of <i>true</i>'s greater than number of <i>false</i>'s 
 * then agent choose first strategy, otherwise second.
 * <p>
 * If number of <i>true</i>'s equal number of <i>false</i>'s 
 * then agent can randomly choose the strategy (1st or 2nd with equal probability)
 * <p>
 * With a really small probability (1e-6) agent can choose randomly
 * between two strategies with equal probability, like in a previous example.
 * 		(this point make in order to add some miscommunication between agents,
 * 		 i.e. if this agent will fight with himself there is a small probability that
 *		 one agent wins another, but if this probability will be higher agent start doing 
 *		 mistakes and lose games).
 *
 // * @see com.company.Greedy
 */
public class EvgeniyMuravevCode implements Player {

	/** 
	 * <p>Reset class state</p>
	 * 
	 * Reinitialize <i>opponentMoves</i> and <i>prevFIeld</i> variables.
	 */
	@Override
	public void reset() {
		opponentMoves = new ArrayList<>();
		prevField = new int[3];
	}
	
	/**
	 * <p>Return move of agent</p>
	 * 
	 * For more details look at the comments above.
	 * <p>
	 * @param opponentLastMove opponent last move
	 * @param xA characteristic of a first field
	 * @param xB characteristic of a second field
	 * @param xC characteristic of a third field
	 * 
	 * @return integer value between 1 and 3
	 */
	@Override
	public int move(int opponentLastMove, int xA, int xB, int xC) {
		int mv = 1;
		
		if (opponentLastMove == 0) {
			mv = new Random(System.currentTimeMillis()).ints(1, 4).findFirst().getAsInt();
		} else {
			/*
			 * agent start works only from second move
			 * because there are not enough information for it
			 */
			opponentMoves.add(isMaxIndex(opponentLastMove));
			if ((xA == xB) && (xB == xC)) {
				mv = new Random(System.currentTimeMillis()).ints(1, 4).findFirst().getAsInt();
			} else {
				if (chooseStrategy()) {
					mv = minIndex(xA, xB, xC);
				} else {
					mv = maxIndex(xA, xB, xC);
				}
			}
		}
		
		/*
		 * store state of the field
		 */
		prevField[0] = xA;
		prevField[1] = xB;
		prevField[2] = xC;
		
		/*
		 * if all fields have same characteristic agent choose random 
		 */
		if ((xA == xB) && (xB == xC)) { 
			mv = new Random(System.currentTimeMillis()).ints(1, 4).findFirst().getAsInt();
		}
		
		return mv;
	}
	
	/**
	 * Check is turn maximum or not
	 * 	
	 * @param turn integer variable which store opponent last move
	 * 
	 * @return <i>true</i> if turn maximum <i>false</i> otherwise
	 */
	private boolean isMaxIndex(int turn) {
		boolean res = false; 
		
		/*
		 * since we have only three fields here all possible cases for function
		 */
		if ((prevField[0] == prevField[1]) && (prevField[1] == prevField[2])) {
			res = true;
		}
		
		if ((prevField[0] > prevField[1]) && (prevField[0] > prevField[2])) {
			res = turn == 1;
		}
		
		if ((prevField[1] > prevField[0]) && (prevField[1] > prevField[2])) {
			res = turn == 2;
		}
		
		if ((prevField[2] > prevField[1]) && (prevField[2] > prevField[0])) {
			res = turn == 3;
		}
		
		if ((prevField[0] == prevField[1]) && (prevField[1] > prevField[2])) {
			res = (turn == 1) || (turn == 2);
		}

		if ((prevField[0] == prevField[2]) && (prevField[0] > prevField[1])) {
			res = (turn == 1) || (turn == 3);
		}

		if ((prevField[1] == prevField[2]) && (prevField[1] > prevField[0])) {
			res = (turn == 2) || (turn == 3);
		}

		return res;
	}

	/**
	 * Initialize <i>opponentMoves</i> and <i>prevField</i> variables.
	 */
	public EvgeniyMuravevCode() {
		opponentMoves = new ArrayList<>();
		prevField = new int[3];
	}

	/**
	 * Returns maximum index of fields.
	 * <p>
	 * If there are more than one maximum it returns random number from maximum set.
	 *
	 * @param xA characteristic of a first field
	 * @param xB characteristic of a second field
	 * @param xC characteristic of a third field
	 * @return integer value between 1 and 3 which is number of maximum field
	 * <p>
	 * //	 * @see com.company.GreedyImproved
	 */
	private int maxIndex(int xA, int xB, int xC) {
		int mv = 1;

		int max = Math.max(xA, Math.max(xB, xC));

		if (max == xA) {
			mv = 1;
			if (xA == xB) {
				mv = new Random(System.currentTimeMillis()).ints(1, 3).findFirst().getAsInt();
			} else if (xA == xC) {
				mv = new Random(System.currentTimeMillis()).ints(1, 3).findFirst().getAsInt() == 1 ? 1 : 3;
			}
		} else if (max == xB) {
			if (xB == xC) {
				mv = new Random(System.currentTimeMillis()).ints(2, 4).findFirst().getAsInt();
			}
			mv = 2;
		} else if (max == xC) {
			mv = 3;
		}

		return mv;
	}

	/**
	 * Choose strategy for agent.
	 * <p>
	 * If number of <i>true</i>'s greater than number of <i>false</i>'s
	 * then agent choose first strategy, otherwise second.
	 * <p>
	 * If number of <i>true</i>'s equal number of <i>false</i>'s
	 * then agent can randomly choose the strategy (1st or 2nd with equal probability)
	 * <p>
	 * With a really small probability (1e-6) agent can choose randomly
	 * between two strategies with equal probability, like in a previous example.
	 * (this point make in order to add some miscommunication between agents,
	 * i.e. if this agent will fight with himself there is a small probability that
	 * one agent wins another, but if this probability will be higher agent start doing
	 * mistakes and lose games).
	 *
	 * @return <i>true</i> for aggressive strategy <i>false</i> for passive strategy.
	 */
	private boolean chooseStrategy() {
		if (new Random(System.currentTimeMillis()).ints(1, 1000001).findFirst().getAsInt() == 1) {
			return new Random(System.currentTimeMillis()).ints(1, 3).findFirst().getAsInt() == 1;
		} else {
			double fnum = opponentMoves.stream().filter(a -> a == false).count() / opponentMoves.size();
			double tnum = opponentMoves.stream().filter(a -> a == true).count() / opponentMoves.size();

			if (fnum > tnum) {
				return false;
			} else if (fnum == tnum) {
				return new Random(System.currentTimeMillis()).ints(1, 3).findFirst().getAsInt() == 1;
			} else {
				return true;
			}
		}
	}

	/**
	 * Returns minimum index of fields.
	 * <p>
	 * If there are more than one minimum it returns random number from minimum set.
	 * <p>
	 * If there are only one nonzero characteristic of a field it return number of
	 * the field this non-zero characteristic.
	 * <p>
	 * I.E. [0, 5, 0] => 2
	 *
	 * @param xA characteristic of a first field
	 * @param xB characteristic of a second field
	 * @param xC characteristic of a third field
	 *
	 * @return integer value between 1 and 3 which is number of minimum field
	 *
	//	 * @see com.company.GreedyImproved
	 */
	private int minIndex(int xA, int xB, int xC) {
		int mv = 1;

		if ((xA == xB) && (xB == 0)) {
			return 3;
		}
		if ((xB == xC) && (xB == 0)) {
			return 1;
		}
		if ((xA == xC) && (xC == 0)) {
			return 2;
		}

		if (((xA == xB) && (xC == 0)) ||
				((xA == xC) && (xB == 0)) ||
				((xB == xC) && (xA == 0))) {
			return maxIndex(xA, xB, xC);
		}

		if ((xA > xB) && (xA > xC)) {
			if (xB > xC) {
				mv = 2;
			} else if (xC == xB) {
				mv = new Random(System.currentTimeMillis()).ints(2, 4).findFirst().getAsInt();
			} else {
				mv = 3;
			}
		}

		if ((xB > xA) && (xB > xC)) {
			if (xA > xC) {
				mv = 1;
			} else if (xA == xC) {
				mv = new Random(System.currentTimeMillis()).ints(1, 3).findFirst().getAsInt() == 1 ? 1 : 3;
			} else {
				mv = 3;
			}
		}

		if ((xC > xA) && (xC > xB)) {
			if (xA > xB) {
				mv = 1;
			} else if (xA == xB) {
				mv = new Random(System.currentTimeMillis()).ints(1, 3).findFirst().getAsInt();
			} else {
				mv = 2;
			}
		}

		if ((xA == xB) && (xB > xC)) {
			mv = 3;
		}

		if ((xA == xC) && (xA > xB)) {
			mv = 2;
		}

		if ((xB == xC) && (xC > xA)) {
			mv = 1;
		}

		return mv;
	}

	/**
	 * <p>Return email</p>
	 *
	 * @return string value with email
	 */
	public String getEmail() {
		return "e.muravev@innopolis.ru";
	}

	private ArrayList<Boolean> opponentMoves;
	private int[] prevField;
}
