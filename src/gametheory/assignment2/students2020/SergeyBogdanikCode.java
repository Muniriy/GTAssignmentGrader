package gametheory.assignment2.students2020;


import gametheory.assignment2.Player;

public class SergeyBogdanikCode implements Player {

	private int lastMove = 0;

	private int returnMove(double p, double q) {
		double result = Math.random();
		if (result < p) {
			lastMove = 1;
		} else if (result < p + q) {
			lastMove = 2;
		} else {
			lastMove = 3;
		}
		;
		return lastMove;
	}

	;

	private double getPayoff(int x) {
		return (x > 10) ? (5) : (10 * Math.exp(x) / (1 + Math.exp(x)) - 5);
	}

	;
	
	private double[] getParams(int p1, int p2, int xA, int xB, int xC){
		int[] x = new int[]{ xA, xB, xC };
		// reduce the x of the field where the first moose is by 1
		x[p1 - 1] -= 1;
		// if mooses are in diffenert fields
		if(p1 != p2){
			// reduce the x of the field where the second moose is by 1
			x[p2 - 1] -= 1;
		};
		// make negative x's zeros
		for(int i = 0; i < 3; i++){
			if(x[i] < 0){
				x[i] = 0;
			};
		};
		// return a, b, and c
		return new double[]{ getPayoff(x[0]), getPayoff(x[1]), getPayoff(x[2]) };
	};
	
	private double[] getNash(double a, double b, double c){
		if(a == 0 && b == 0 && c == 0){
			// when all fields are 0 then move with equaly distributed probability
			return new double[]{ 1/3.0, 1/3.0 };
		}else if(a == 0 && b == 0 && c != 0){
			// if only C is not 0 then move to C
			return new double[]{ 0, 0 };
		}else if(a == 0 && c == 0 && b != 0){
			// if only B is not 0 then move to B
			return new double[]{ 0, 1 };
		}else if(b == 0 && c == 0 && a != 0){
			// if only A is not 0 then move to A
			return new double[]{ 1, 0 };
		}else if(a == 0){
			// if only A is zero move to B or C with probability corresponding to the relation of b and c
			return new double[]{ 0, b / (b + c) };
		}else if(b == 0){
			// if only B is zero move to A or C with probability corresponding to the relation of a and c
			return new double[]{ a / (a + c), 0 };
		}else if(c == 0){
			// if only C is zero move to A or B with probability corresponding to the relation of a and b
			return new double[]{ a / (a + b), b / (a + b) };
		}else{
			// if none of a, b, and c is zero then compute p and q
			return new double[]{ (a * (b + c) - b * c) / (a * (b + c) + b * c), (a * (b - c) + b * c) / (a * (b + c) + b * c) };
		}
	};
	
	public void reset(){
		lastMove = 0;
	};
	
	public int move(int opponentLastMove, int xA, int xB, int xC){
		if(opponentLastMove == 0){
			// first move equaly distributed probability
			return returnMove(1/3.0, 1/3.0);
		};
		// get payoffs a, b, c
		double[] params = getParams(opponentLastMove, lastMove, xA, xB, xC);
		// get probability for MSNE
		double[] nash = getNash(params[0], params[1], params[2]);
		// return a move
		return returnMove(nash[0], nash[1]);
	};
	
	public String getEmail(){
		return "s.bogdanik@innopolis.ru";
	};
};
