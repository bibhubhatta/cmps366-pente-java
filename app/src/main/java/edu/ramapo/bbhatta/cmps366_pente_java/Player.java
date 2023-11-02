package edu.ramapo.bbhatta.cmps366_pente_java;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Player class represents a player in the game.
 * It is immutable.
 * <p>
 * The player is represented by a name.
 * </p>
 */
public class Player {

    public static final Player HUMAN = new Player("Human");
    public static final Player COMPUTER = new Player("Computer");

    protected String name;

    public Player(String name) {
        this.name = name;
    }

    public Player(Player player) {
        this.name = player.name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
