import java.util.ArrayList;
import java.util.Random;

public class Matcher {
    public static void main(String[] args) {
        // experience should be treated similar to a star rating (range 1-5)
        PLAYERS.add(new Player("Aaron", 5));
        PLAYERS.add(new Player("Bene", 4));
        PLAYERS.add(new Player("Anoki", 3));
        PLAYERS.add(new Player("David", 3));
        PLAYERS.add(new Player("Erik", 1));
        PLAYERS.add(new Player("Paul", 5));
        PLAYERS.add(new Player("Jan", 5));
        PLAYERS.add(new Player("Natalie", 1));
        PLAYERS.add(new Player("Fiona", 2));
        PLAYERS.add(new Player("Filip", 3));
        PLAYERS.add(new Player("Bennett", 3));
        PLAYERS.add(new Player("Laurenz", 3));

        double averageExperience = Math.floor(getAvgXP(PLAYERS));
        System.out.println("Average Experience of Players: " + averageExperience);
        System.out.println("Number of players: " + PLAYERS.size());
        if (PLAYERS.size() % TEAMSIZE != 0) {
            System.out.println("Players cannot be divided equally because of given team-size, teams might be more unbalanced...");
        }
        System.out.println("Generating " + (int) Math.ceil((double) PLAYERS.size() / TEAMSIZE) + " teams of size " + TEAMSIZE + ((PLAYERS.size() % TEAMSIZE != 0) ? " (or less)" : ""));
        ArrayList<Player> result = teamMatch(PLAYERS, TEAMSIZE);
        ArrayList<ArrayList<Player>> teams = parseTeams(result, TEAMSIZE);
        printTeams(teams);
    }

    public static ArrayList<Player> PLAYERS = new ArrayList<>();
    public static int TEAMSIZE = 2;

    // A higher number tends to be accompanied by higher matching accuracy but less variety and a longer runtime
    public static int MISMATCH_TOLERANCE = 40;

    /**
     * Recursive algorithm for matching teams based on skill
     * @param players array containing all participating players
     * @param teamSize the wanted size of the teams
     * @return an arrayList with corresponding team-members rowed after each other
     */
    public static ArrayList<Player> teamMatch(ArrayList<Player> players, int teamSize) {
        if (teamSize < 2) throw new IllegalArgumentException("TeamSize must at least be 2");
        if (players.isEmpty()) return new ArrayList<>();

        double avgBound = 1;
        int misMatches = 0;
        boolean teamAccepted = false;
        ArrayList<Player> team = new ArrayList<>();
        Random rand = new Random();

        do {
            if (players.size() > teamSize) {
                // Create random team
                for (int i = 0; i < teamSize; i++) {
                    int randPlayerIndex = rand.nextInt(players.size());
                    team.add(players.get(randPlayerIndex));
                    players.remove(randPlayerIndex);
                }

                // Analyze team
                double avgTeamXp = getAvgXP(team);
                double playerRemainderXP = getAvgXP(players);
                if ((avgTeamXp <= playerRemainderXP + avgBound) &&
                        (avgTeamXp >= playerRemainderXP - avgBound)) {
                    // Team fits
                    teamAccepted = true;
                } else {
                    // Team does not fit, reset arrays and increment misMatchCounter
                    players.addAll(team);
                    team.clear();
                    misMatches++;
                    // If algorithm failed a specific amount of times, allow more deviation
                    if (misMatches > MISMATCH_TOLERANCE) {
                        avgBound+=0.5;
                        misMatches = 0;
                    }
                }
            } else {
                return players;
            }
        } while(!teamAccepted);

        team.addAll(teamMatch(players, teamSize));
        return team;
    }

    public static int getTeamXP(ArrayList<Player> team) {
        int xp = 0;
        for (Player player : team) {
            xp += player.getXp();
        }
        return xp;
    }

    public static void printTeams(ArrayList<ArrayList<Player>> teams) {
        System.out.println("\nThe random teams are\n");
        int teamNr = 1;
        StringBuilder teamString;
        for (ArrayList<Player> team : teams) {
            teamString = new StringBuilder("[TEAM " + teamNr + "][XP " + getTeamXP(team) + "]: ");
            for (Player player : team) {
                teamString.append(player.getName()).append(" ");
            }
            System.out.println(teamString);
            teamNr++;
        }
    }

    public static ArrayList<ArrayList<Player>> parseTeams(ArrayList<Player> players, int teamSize) {
        ArrayList<ArrayList<Player>> result = new ArrayList<>();
        int teamCount = (int) Math.ceil((float) players.size() / teamSize);

        // Fill arrayList with empty teams
        for (int i = 0; i < teamCount; i++) {
            result.add(i, new ArrayList<>());
        }

        int teamNr = -1;
        for (int i = 0; i < players.size(); i++) {
            if (i % teamSize == 0) {
                teamNr++;
            }
            result.get(teamNr).add(players.get(i));
        }
        return result;
    }

    public static double getAvgXP(ArrayList<Player> players) {
        int enumerator = 0;
        int denumerator = players.size();
        for (Player player: players) {
            enumerator+=player.getXp();
        }
        return (double) enumerator / denumerator;
    }
}
