package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Arrays;

//interface Player {
//    void reset();
//    int move(int opponentLastMove, int xA, int xB, int xC);
//    String getEmail();
//   }

public class NikitaKostenkoCode implements Player {
    public String getEmail() {
        return "n.kostenko@innopolis.ru";
    }

    int myLastMove = 0;
    int maxLastMove = 0;
    int maxLastValue = 0;
    boolean isOpponentChooseBestMove = false;
    boolean amIChooseBestMove = false;
    // first value is just placeholder for easier work with moves, that starts from one
    int[] lastMoves = new int[]{1337, 0, 0, 0};
    // What opponent will choose in case of equality of fields
    int lastRoundNotUniqueMaxValues = 1;
    int equalMaxValuesFailCounterInitialValue = 2;
    int equalMaxValuesFailCounter = this.equalMaxValuesFailCounterInitialValue;
    boolean definedEqualMaxValuesStrategy = false;
    // 0 stays for left, 1 stays for right
    int twoEqualMaxValuesMove = 0;
    // 1 2 3 - left, middle, right
    int threeEqualMaxValuesMove = 2;

    // Possible strategies:
    // =================================
    //greedy - always chooses the highest value.
    //optimal - take turns for the highest value.
    //chicken - doesn't take the highest value.
    //random - not the one from above (maybe he has some strategy, but I don't understand it)
    String supposedOpponentStrategy = "undefined";
    // for the first move we always choose highest value, because we have no idea about opponent strategy.
    // The second reason is that, according to the first assignment results, 
    //most of students prefer to play  more safely, that's why choosing not the highest value potentially 
    //has a higher chance to fail (in other words: "oh, 1000% probability that this stupid greedy guy 
    //will choose highest value, so I'll decide between the rest two")
    boolean myTurnForHighestValue = true;
    boolean highestValueRandomChoice = false;
    boolean chanceForChicken = true;
    
    public void reset() {
        this.myLastMove = 0;
        this.myLastMove =  0;
        this.maxLastMove = 0;
        this.maxLastValue = 0;
        this.isOpponentChooseBestMove = false;
        this.amIChooseBestMove = false;
        this.lastMoves = new int[]{1337,0,0,0};
        this.lastRoundNotUniqueMaxValues = 1;
        this.equalMaxValuesFailCounterInitialValue = 2;
        this.equalMaxValuesFailCounter = this.equalMaxValuesFailCounterInitialValue;
        this.definedEqualMaxValuesStrategy = false;
        this.twoEqualMaxValuesMove = 0;
        this.threeEqualMaxValuesMove = 2;
        this.supposedOpponentStrategy = "undefined";
        this.myTurnForHighestValue = true;
        this.highestValueRandomChoice = false;
        this.chanceForChicken = true;
    }
            
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        
        this.isOpponentChooseBestMove = (opponentLastMove != 0 ) && (this.maxLastMove == opponentLastMove);
        this.amIChooseBestMove = (opponentLastMove != 0 ) && (this.maxLastMove == this.myLastMove);
        //side-effecty funcs
        if (opponentLastMove != 0) {
            if (this.lastRoundNotUniqueMaxValues > 1) {
                this.analyzeOpponentEqualValuesStrategy(opponentLastMove);
            } else {
                this.analyzeMaxValueStrategy(opponentLastMove);
            }
        }
        
        int myMove;
        int maxMove;
        int maxValue = Math.max(Math.max(xA,xB), xC);
        
        // if max value is not unique, my strategy is to try to predict 
        // which of possible fields opponent will choose and try another one. 
        if (this.isMaxValueNotUnique(xA,xB,xC,maxValue)) {
            this.lastRoundNotUniqueMaxValues = findAmountOfEqualValues(xA,xB,xC);
            maxMove = this.chooseMoveFromEqualValues(xA,xB,xC);
            myMove = maxMove;
        } else {
            maxMove = this.findMaxMove(xA, xB, xC);
            this.lastRoundNotUniqueMaxValues = 1;
            switch (this.supposedOpponentStrategy) {
                case "greedy":
                    myMove = this.playWithGreedy(xA,xB,xC);
                    break;
                case "optimal":
                    myMove = this.playWithOptimal(xA,xB,xC);
                    break;
                case "chicken":
                    myMove = this.playWithChicken(xA,xB,xC);
                    break;
                //if it's impossible to predict - just choose best possible;
                case "random":
                    myMove = this.findMaxMove(xA,xB,xC);
                    break;
                default:
                    //initially assume that our agent is optimal
                    myMove = this.playWithOptimal(xA, xB, xC);
                    break;
            }
        }
        this.myLastMove =  myMove;
        this.maxLastMove = maxMove;
        this.maxLastValue = maxValue;
        this.lastMoves = new int[]{1337,xA,xB,xC};
        return myMove;
    }

    int playWithChicken(int xA, int xB, int xC) {
        return this.findMaxMove(xA, xB, xC);
    }

    //Play with optimal opponent. Optimal strategy is to take turns for the highest value
    int playWithOptimal(int xA, int xB, int xC) {
        if ((xA * xB + xA * xC == 0) && (xB * xC == 0)) {
            return this.findMaxMove(xA, xB, xC);
        } else {
            if (this.myTurnForHighestValue) {
            this.myTurnForHighestValue = false;
            return this.findMaxMove(xA, xB, xC);
            } else {
                this.myTurnForHighestValue = true;
                if (this.findAmountOfEqualValues(xA, xB, xC) > 1) {
                    return this.chooseMoveFromEqualValues(xA, xB, xC);
                } else {
                    return this.findMiddleMove(xA, xB, xC);
                }
            }
        }
        
    }

    //Play with greedy opponent. Optimal strategy is to choose second highest value
    // If it is not unique, then choose variant according to multiple equal values startegy
    int playWithGreedy(int xA, int xB, int xC) {
        //if there are two zero values - it's better to play greedy with greedy
        if ((xA * xB + xA * xC == 0) && (xB * xC == 0)) {
            return this.findMaxMove(xA, xB, xC);
        } else {
            if (this.findAmountOfEqualValues(xA, xB, xC) > 1) {
                return this.chooseMoveFromEqualValues(xA, xB, xC);
            } else {
                return this.findMiddleMove(xA, xB, xC);
            }
        }
    }

    int findMiddleMove(int xA, int xB, int xC) {
        int maxValue = Math.max(Math.max(xA,xB), xC);
        if (xA == maxValue) {
            if (xB > xC) {
                return 2;
            } else {
                return 3;
            }
        } else {
            if (xB > xC) {
                return 3;
            } else {
                return 2;
            }
        }
    }

    int findMaxMove(int xA, int xB, int xC) {
        if (xA > xB && xA > xC) {
            return 1;
        } else if (xB > xC) {
            return 2;
        } else {
            return 3;
        }
    }

    int findAmountOfEqualValues(int xA, int xB, int xC) {
        if (xA == xB && xA == xC){
            return 3;
        } else if (xA == xB || xA == xC || xB == xC) {
            return 2;
        } else {
            return 1;
        }
    }

    boolean isMaxValueNotUnique(int xA, int xB, int xC, int maxValue) {
        return ((xA == maxValue && xB == maxValue) 
        || (xB == maxValue && xC == maxValue) 
        || (xA == maxValue && xC == maxValue));
    }


    // in case of equality of some fields, I'll choose the middle (in case of 3 equal) or lefter(in case of 3 equal)
    // one for the first time, because I expect other students to make the comparisons like "if xA > xB then xA else xB",
    // so in case of equality they'll choose righter one. The only source of that desicion is what I've use in my code
    // and what I've seen in the code of other people mostly often. For the future moves I'll try to analyze opponent's
    // previous moves to choose best possible variant
    int chooseMoveFromEqualValues(int xA, int xB, int xC) {
        int myMove = 0;
        if (xA == xB && xA == xC) {
            myMove = this.threeEqualMaxValuesMove;
        } else if (xA == xB) {
            if (this.twoEqualMaxValuesMove == 1){
                myMove = 2;
            } else {
                myMove = 1;
            }
        } else if (xA == xC) {
            if (this.twoEqualMaxValuesMove == 1){
                myMove = 3;
            } else {
                myMove = 1;
            }
        } else if (xB == xC) {
            if (this.twoEqualMaxValuesMove == 1){
                myMove = 3;
            } else {
                myMove = 2;
            }
        }
        return myMove;
    }

    void analyzeOpponentEqualValuesStrategy(int opponentLastMove) {
        // if we still didn't find optimal strategy for the equal max values - we need to find it
        if (!this.definedEqualMaxValuesStrategy) {
        
            //This is the case, where we could find optimal equalMaxValuesStrategy
            if (this.myLastMove != opponentLastMove){
                //defining strategy or three equal situation
                this.threeEqualMaxValuesMove = this.myLastMove;
                // and for two equal situation
                if (this.myLastMove < opponentLastMove) {
                    this.twoEqualMaxValuesMove = 0;
                } else {
                    this.twoEqualMaxValuesMove = 1;
                }
                this.definedEqualMaxValuesStrategy = true;
            } else {
                //The bast way to avoid collision in future is random:)
                    this.threeEqualMaxValuesMove =  1 + (int) (Math.random() * 3);
                    this.twoEqualMaxValuesMove = Math.random() < 0.5 ? 0 : 1;
            }
        } else {
            //the case where I mistakely predicted, that opponnent understoods my strategy
            if (this.myLastMove == opponentLastMove) {
                if (this.equalMaxValuesFailCounter <= 0) {
                    this.definedEqualMaxValuesStrategy = false;
                    this.equalMaxValuesFailCounter = this.equalMaxValuesFailCounterInitialValue;
                } else {
                    this.equalMaxValuesFailCounter -= 1;
                }
            }
        }
    } 

    void analyzeMaxValueStrategy(int opponentLastMove) {
        // if we not have some predictions about opponent's strategy from previous move, but we need to prove it
        if (this.supposedOpponentStrategy.charAt(0) == '?') {
            String[] strategies = this.supposedOpponentStrategy.substring(1).split("_");
            if (!this.amIChooseBestMove && !this.isOpponentChooseBestMove) {
                //if I haven't chosen best move - I'll choose for next move, couse otherwise we could fall in random hole
                //here I could not say unambiguously, because was random
                if (this.highestValueRandomChoice) {
                    if (!Arrays.asList(strategies).contains("chicken")) {
                        this.supposedOpponentStrategy += "_chicken";
                    } 
                // if it was not random choice - that mean, that I let opponent to choose best value and he haven't chosen it
                } else {
                    if (Arrays.asList(strategies).contains("chicken")) {
                        this.supposedOpponentStrategy = "chicken";
                    } else {
                        this.supposedOpponentStrategy = "random";
                    }
                }
                this.myTurnForHighestValue = true;
                this.highestValueRandomChoice = false;

            } else if (this.amIChooseBestMove && this.isOpponentChooseBestMove) {
                // if it was random - mean that it is possible, that both of us choose max value randomly
                if (this.highestValueRandomChoice) {
                    this.myTurnForHighestValue = Math.random() < 0.5;
                    this.highestValueRandomChoice = true;
                    if (!Arrays.asList(strategies).contains("greedy")) {
                        this.supposedOpponentStrategy += "_greedy";
                    } 
                // if it was not random from my side - mean that I expeted him to let me choose max value
                } else {
                    this.myTurnForHighestValue = true;
                    this.highestValueRandomChoice = false;
                    if (Arrays.asList(strategies).contains("greedy")) {
                        this.supposedOpponentStrategy = "greedy";
                    } else {
                        this.supposedOpponentStrategy = "random";

                    }
                }
            } else if (this.amIChooseBestMove && !this.isOpponentChooseBestMove) {
                //even if it was random from my side - it's possible that is was not random from the side of my opponent,
                // so either optimal, or chicken
                if (this.highestValueRandomChoice) {
                    if (!Arrays.asList(strategies).contains("chicken")) {
                        this.supposedOpponentStrategy += "_chicken";
                    } 
                // if it was not random - it's optimal
                } else {
                    this.supposedOpponentStrategy = "optimal";
                }
                this.myTurnForHighestValue = false;
                this.highestValueRandomChoice = false;
            } else if (!this.amIChooseBestMove && this.isOpponentChooseBestMove) {
                //even if it was random from my side - it's possible that is was not random from the side of my opponent,
                // so either optimal, or greedy
                if (this.highestValueRandomChoice) {
                    if (!Arrays.asList(strategies).contains("greedy")) {
                        this.supposedOpponentStrategy += "_greedy";
                    } 
                // if it was not random - it's optimal
                } else {
                    this.supposedOpponentStrategy = "optimal";
                }
                this.myTurnForHighestValue = true;
                this.highestValueRandomChoice = false;
            }
        } 
        else if (this.supposedOpponentStrategy == "undefined") {
            //it's honest situation, but with possibility of greedy strategy
            if (this.amIChooseBestMove && this.isOpponentChooseBestMove) {
                this.myTurnForHighestValue = Math.random() < 0.5;
                this.highestValueRandomChoice = true;
                this.supposedOpponentStrategy = "?greedy_optimal";
            // Opponent is not greedy, but ...
            } else if (this.amIChooseBestMove && !this.isOpponentChooseBestMove) {
                this.myTurnForHighestValue = false;
                this.highestValueRandomChoice = false;
                this.supposedOpponentStrategy = "?optimal_chicken";
            }
            //this 'else' for the case if we mistakely determined opponents strategy, will be rechecked after every iteration
        } else {
            switch (this.supposedOpponentStrategy) {
                case "greedy":
                    //Label 'greedy' is assigned only in case the opponents didn't let me take Best move when I did it for him
                    // and by greedy strategy I assume that he will always take the best move. So, if the opponent labeled as 'greedy'
                    // and didn't choose the best move, that mean's that I couldn't determine his strategy and mark him as 'random' now
                    if (!this.isOpponentChooseBestMove) {
                        this.supposedOpponentStrategy = "random";

                    }
                    break;
                case "optimal":
                    //Label 'optimal' is assigned in case that we with my opponent will take turns for the Best move (one turn it for me,
                    //one turn it is for him), that means that one of us shoud take the best move every turn. So, in case none (or both)
                    //of us have taken the Best move, that meant that mean's that I couldn't determine his strategy and mark him as 'random' now
                    if (this.amIChooseBestMove && this.isOpponentChooseBestMove
                        || !this.amIChooseBestMove && !this.isOpponentChooseBestMove) {
                        this.supposedOpponentStrategy = "random";

                        }
                    break;
                case "chicken":
                    //Label 'chicken' is assigned in case that opponent doesn choose the best move when I let expect him to do it
                    //I assume the case, that I could label opponent as 'chicken' a bit early, so in case he is trying to take Best move -
                    //I'll give him a chance - maybe he is optimal, but with another strategy  - and let him take best value one more time
                    //But only once.
                    if (this.isOpponentChooseBestMove && this.chanceForChicken) {
                        this.supposedOpponentStrategy = "?optimal_chicken";
                        this.myTurnForHighestValue = false;
                        this.highestValueRandomChoice = false;
                        this.chanceForChicken = false;
                    }
            }
        }
    }
}