public class Player {
    private String name;
    private int xp;

    public Player(String name, int experience) {
        this.name = name;
        this.xp = experience;
    }

    public String getName() {
        return name;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public String toString() {
        return "Player " + name;
    }
}
