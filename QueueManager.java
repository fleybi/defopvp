package dev.duel.plugin.stats;

import java.util.UUID;

public class PlayerStats {
    private final UUID uuid;
    private int wins;
    private int losses;
    private int elo;
    private int kills;
    private int deaths;

    public PlayerStats(UUID uuid, int wins, int losses, int elo, int kills, int deaths) {
        this.uuid = uuid;
        this.wins = wins;
        this.losses = losses;
        this.elo = elo;
        this.kills = kills;
        this.deaths = deaths;
    }

    public PlayerStats(UUID uuid, int startingElo) {
        this(uuid, 0, 0, startingElo, 0, 0);
    }

    public UUID getUuid() { return uuid; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getElo() { return elo; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }

    public void addWin(int eloGain) { wins++; elo = Math.max(100, elo + eloGain); }
    public void addLoss(int eloLoss) { losses++; elo = Math.max(100, elo - eloLoss); }
    public void addKill() { kills++; }
    public void addDeath() { deaths++; }

    public double getWinRate() {
        int total = wins + losses;
        if (total == 0) return 0;
        return (double) wins / total * 100;
    }

    public double getKDR() {
        if (deaths == 0) return kills;
        return (double) kills / deaths;
    }
}
