package me.janeldq.algorithms.assignments.baseball;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

/**
 * Algorithms Part 2 Week 3 Programming Assignment: Baseball Elimination
 * Given the standings in a sports division at some point during the season,
 * determine which teams have been mathematically eliminated from winning their division.
 *
 * Detailed Problem description can be found with the url below:
 * http://coursera.cs.princeton.edu/algs4/assignments/baseball.html
 *
 * @author Jane
 *
 */
public class BaseballElimination {

    private final int numberOfTeams;

    private final Map<String, Integer> teams;

    private final String[] teamIndexes;

    private final int[] wins;

    private final int[] losses;

    private final int[] remaining;

    private final int[][] games;

    private final Map<String, Bag<String>> certificates;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = in.readInt();
        teams = new HashMap<>();
        teamIndexes = new String[numberOfTeams];
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        games = new int[numberOfTeams][numberOfTeams];
        certificates = new HashMap<>();
        for (int i = 0; i < numberOfTeams; i++) {
            String team = in.readString();
            teams.put(team, i);
            teamIndexes[i] = team;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
                games[i][j] = in.readInt();
            }
        }
    }

    public int numberOfTeams() {
        return numberOfTeams;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException();
        return wins[teams.get(team)];
    }

    public int losses(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException();
        return losses[teams.get(team)];
    }

    public int remaining(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException();
        return remaining[teams.get(team)];
    }

    public int against(String team1, String team2) {
        if (!teams.containsKey(team1) || !teams.containsKey(team2)) throw new IllegalArgumentException();
        return games[teams.get(team1)][teams.get(team2)];
    }

    public boolean isEliminated(String team) {
        if (isTrivialElimination(team)) return true;
        int numberOfVertex = (numberOfTeams) * (numberOfTeams + 1) / 2 + numberOfTeams + 2;
        int index = teams.get(team);
        int offset = (numberOfTeams) * (numberOfTeams + 1) / 2 + 1;
        int v = 1;
        FlowNetwork network = new FlowNetwork(numberOfVertex);
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = 0; j <= i; j++) {
                // s->Gij games left between i and j
                if (i == index || j == index) network.addEdge(new FlowEdge(0, v, 0));
                else network.addEdge(new FlowEdge(0, v, games[i][j]));
                network.addEdge(new FlowEdge(v, offset + i, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(v, offset + j, Double.POSITIVE_INFINITY));
                v++;
            }
            // if i == index, means self to self, so set capacity to zero to cut off this path
            int capacity = i == index ? 0 : wins[index] + remaining[index] - wins[i];
            network.addEdge(new FlowEdge(offset + i, numberOfVertex-1, capacity));
        }
        int s = 0, t = numberOfVertex - 1;
        FordFulkerson ff = new FordFulkerson(network, s, t);
        Iterable<FlowEdge> edges = network.adj(s);
        for (FlowEdge edge: edges) {
            if (edge.residualCapacityTo(edge.other(s)) > 0) {
                getCertificateOfElimination(ff, team);
                return true;
            }
        }
        return false;
    }

    private void getCertificateOfElimination(FordFulkerson ff, String team) {
        int offset = (numberOfTeams) * (numberOfTeams + 1) / 2 + 1;
        for (String curTeam: teams()) {
            int idx = teams.get(curTeam);
            if (ff.inCut(idx + offset)) {
                Bag<String> bag = certificates.get(team);
                if (bag == null) bag = new Bag<>();
                bag.add(curTeam);
                certificates.put(team, bag);
            }
        }
    }

    private boolean isTrivialElimination(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException();
        int index = teams.get(team);
        boolean isEliminated = false;
        for (int i = 0; i < numberOfTeams; i++) {
            if (i == index) continue;
            if (wins[index] + remaining[index] < wins[i]) {
                Bag<String> bag = certificates.get(team);
                if (bag == null) bag = new Bag<>();
                bag.add(teamIndexes[i]);
                certificates.put(team, bag);
                isEliminated = true;
            }
        }
        return isEliminated;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team)) throw new IllegalArgumentException();
        if (certificates.get(team) == null) {
            isEliminated(team);
        }
        return certificates.get(team);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
