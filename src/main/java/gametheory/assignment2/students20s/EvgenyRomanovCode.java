package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class EvgenyRomanovCode implements Player {
    private IERAgentBrain _brain;

    // base constructor with final solution
    public EvgenyRomanovCode() {
        _brain = new RandomBrain(); // we trust in chaos
    }

    // constructor for custom agent brain
    public EvgenyRomanovCode(IERAgentBrain brain) {
        _brain = brain;
    }

    @Override
    public void reset() {
        _brain.Reset();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return _brain.move(opponentLastMove - 1, xA, xB, xC) + 1; // +1 -1 in order to transform coords for 1 2 3 to 0 1 2 indexing
    }

    @Override
    public String getEmail() {
        return "e.romanov@innopolis.ru";
    }

    // base interface for agent brain
    public interface IERAgentBrain {
        void Reset();

        int move(int opponentLastMove, int xA, int xB, int xC);
    }

    // interface for node comparer
    public interface IGreedyNodeComparer {
        boolean IsBetter(GreedyAgent.GreedyNode a, GreedyAgent.GreedyNode b);
    }

    // greedy agent wtih prediction of next steps
    public class GreedyAgent implements IERAgentBrain {
        private IGreedyNodeComparer _comarer;
        private int _depth;
        private int _prevMove = -1;
        private WorldState _worldState;

        public GreedyAgent(int depth, IGreedyNodeComparer comparer) {
            _depth = depth;
            _worldState = new WorldState();
            _comarer = comparer;
        }

        @Override
        public void Reset() {
            _worldState = new WorldState();
            _prevMove = -1;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // first step, no reason to not use random
            if (_prevMove == -1) {
                _prevMove = new Random().nextInt(3);
                return _prevMove;
            }

            // next steps, update local wolrd state
            _worldState.Iterate(opponentLastMove, _prevMove);

            // list of future leaf nodes of a game tree
            List<GreedyNode> nodes = new LinkedList<GreedyNode>();
            // root node with a current state
            GreedyNode root = new GreedyNode(null, 0, _worldState.TargetScore, _worldState, _prevMove);
            // grow a game tree
            root.Grow(_depth, nodes);

            // get the best branch
            GreedyNode max = nodes.get(0);
            for (GreedyNode node : nodes) {

                // use custom comparer to choose the best leaf node
                if (_comarer.IsBetter(node, max)) {
                    max = node;
                }
            }

            // traverse up to the first child of a root, to get required move id
            // Parent with null parent is the root node of current state
            while (max.Parent.Parent != null) {
                max = max.Parent;
            }

            _prevMove = max.TargetMove;

            return _prevMove;
        }

        public class GreedyNode {
            public GreedyNode Parent;
            public int Depth;
            public double Payoff;
            public WorldState State;
            public int TargetMove;

            public GreedyNode(GreedyNode parent, int depth, double payoff, WorldState state, int targetMove) {
                Parent = parent;
                Depth = depth;
                Payoff = payoff;
                State = state;
                TargetMove = targetMove;
            }

            // recursievly build a game tree
            public void Grow(int maxDepth, List<GreedyNode> leafs) {
                if (Depth >= maxDepth) {
                    leafs.add(this);
                    return;
                }

                // recursievly grow of a game tree
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        WorldState newState = State.Clone();
                        newState.Iterate(i, j);
                        GreedyNode node = new GreedyNode(this, _depth + 1, newState.TargetScore, newState, j);
                        node.Grow(maxDepth, leafs);
                    }
                }

            }
        }

        // comparer which chooses the node with a best payoff or with a less opponent score in case of equal payoff
        public class PayoffMaximizerNodeComparer implements IGreedyNodeComparer {
            @Override
            public boolean IsBetter(GreedyNode a, GreedyNode b) {
                return (a.Payoff > b.Payoff || (Math.abs(b.Payoff - a.Payoff) < 0.001f && b.State.OpponentScore > a.State.OpponentScore));
            }
        }

        // comparer, which chooses the node with a higher difference between opponent score and agent score
        public class CompetitiveNodeComparer implements IGreedyNodeComparer {
            @Override
            public boolean IsBetter(GreedyNode a, GreedyNode b) {
                return (a.State.OpponentScore - a.Payoff > b.State.OpponentScore - b.Payoff);
            }
        }

        // comparer, which chooses the node with a lower opponent score :)
        public class OpponentPayoffMinimizerNodeComparer implements IGreedyNodeComparer {
            @Override
            public boolean IsBetter(GreedyNode a, GreedyNode b) {
                return (a.State.OpponentScore < b.State.OpponentScore);
            }
        }
    }

    // dumb AI behaviour aka Random brain
    // inspired by idea that the worst opponent is unpredictable opponent
    // and it also easy to implement
    public class RandomBrain implements IERAgentBrain {

        @Override
        public void Reset() {

        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            return new Random().nextInt(3);
        }
    }


    // environment representation
    // used for simulation in greedy agent brain
    public class WorldState {
        public int[] Regions;
        public double OpponentScore;
        public double TargetScore;
        public int Iteration;

        public WorldState() {
            Regions = new int[]{1, 1, 1};
            OpponentScore = 0.0;
            TargetScore = 0.0;
            Iteration = 0;
        }

        public WorldState(int[] regions, double opponentScore, double targetScore, int iteration) {
            Regions = regions;
            OpponentScore = opponentScore;
            TargetScore = targetScore;
            Iteration = iteration;
        }

        // process next step
        // ordering starts from 0
        public WorldState Iterate(int opponentMove, int targetMove) {
            Iteration += 1;

            if (opponentMove == targetMove) {
                Regions[targetMove] = Math.max(0, Regions[targetMove] - 1);
            } else {
                OpponentScore += GrowFunction(Regions[opponentMove]);
                TargetScore += GrowFunction(Regions[targetMove]);
                Regions[targetMove] = Math.max(0, Regions[targetMove] - 1);
                Regions[opponentMove] = Math.max(0, Regions[opponentMove] - 1);

            }

            for (int i = 0; i < Regions.length; i++) {
                if (i != targetMove && i != opponentMove) {
                    Regions[i] += 1;
                }
            }

            return this;
        }

        // grow function for a region inside environment
        public double GrowFunction(int x) {
            return (10.0 * Math.exp(x)) / (1 + Math.exp(x));
        }

        public void ApplyRegions(int xA, int xB, int xC) {
            Regions[0] = xA;
            Regions[1] = xB;
            Regions[2] = xC;
        }

        // creates copy of an environment
        public WorldState Clone() {
            return new WorldState(Regions.clone(), OpponentScore, TargetScore, Iteration);
        }
    }
}



