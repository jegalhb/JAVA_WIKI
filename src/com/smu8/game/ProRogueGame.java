package com.smu8.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ProRogueGame {
    public static void main(String[] args) {
        new GameEngine().run();
    }
}

class GameEngine {
    private static final int WIDTH = 34;
    private static final int HEIGHT = 18;
    private static final int TARGET_FLOOR = 5;

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final Deque<String> log = new ArrayDeque<String>();
    private final Player player = new Player("Aegis", new Position(0, 0));

    private DungeonMap map;
    private List<Monster> monsters;
    private int floor = 1;
    private boolean running = true;

    public void run() {
        pushLog("Entered the Abyss Tower. Goal: clear floor " + TARGET_FLOOR + ".");
        generateFloor();

        while (running) {
            render();
            if (!player.isAlive()) {
                System.out.println("You died on floor " + floor + ".");
                break;
            }
            if (floor > TARGET_FLOOR) {
                System.out.println("Victory. Tower conquered.");
                break;
            }

            System.out.print("Command (w/a/s/d move, f shockwave, q potion, > descend, h help, x quit): ");
            if (!scanner.hasNextLine()) {
                running = false;
                break;
            }
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                pushLog("Input a command.");
                continue;
            }
            handlePlayerCommand(input.charAt(0));
        }
    }

    private void handlePlayerCommand(char command) {
        boolean consumedTurn = false;
        switch (command) {
            case 'w':
                consumedTurn = tryMovePlayer(0, -1);
                break;
            case 'a':
                consumedTurn = tryMovePlayer(-1, 0);
                break;
            case 's':
                consumedTurn = tryMovePlayer(0, 1);
                break;
            case 'd':
                consumedTurn = tryMovePlayer(1, 0);
                break;
            case 'f':
                consumedTurn = castShockwave();
                break;
            case 'q':
                consumedTurn = usePotion();
                break;
            case '>':
                consumedTurn = tryDescend();
                break;
            case 'h':
                pushLog("Move: w/a/s/d, Skill: f(mana 15), Potion: q, Descend: >");
                break;
            case 'x':
                running = false;
                break;
            default:
                pushLog("Unknown command.");
        }

        if (consumedTurn && running) {
            resolveMonstersTurn();
            player.regenerate();
            cleanupDeadMonsters();
            if (monsters.isEmpty()) {
                pushLog("All enemies defeated. Move to exit and descend.");
            }
        }
    }

    private boolean tryMovePlayer(int dx, int dy) {
        Position next = player.position.translate(dx, dy);
        if (!map.isWalkable(next)) {
            pushLog("A wall blocks your path.");
            return false;
        }

        Monster target = getMonsterAt(next);
        if (target != null) {
            combat(player, target);
            return true;
        }

        player.position = next;
        if (map.consumePotion(next)) {
            player.potions++;
            pushLog("Picked a potion. (" + player.potions + " total)");
        }
        if (next.equals(map.exit)) {
            pushLog("Exit discovered. Press '>' to descend.");
        }
        return true;
    }

    private boolean castShockwave() {
        final int manaCost = 15;
        if (player.mana < manaCost) {
            pushLog("Not enough mana.");
            return false;
        }

        List<Monster> around = new ArrayList<Monster>();
        for (Monster m : monsters) {
            if (!m.isAlive()) {
                continue;
            }
            int dist = Math.abs(m.position.x - player.position.x) + Math.abs(m.position.y - player.position.y);
            if (dist == 1) {
                around.add(m);
            }
        }
        if (around.isEmpty()) {
            pushLog("No adjacent enemy.");
            return false;
        }

        player.mana -= manaCost;
        pushLog("Shockwave activated.");
        for (Monster m : around) {
            int dmg = 8 + random.nextInt(8) + player.level * 2;
            m.hp -= dmg;
            pushLog(m.name + " takes " + dmg + " damage.");
            if (!m.isAlive()) {
                onMonsterKilled(m);
            }
        }
        return true;
    }

    private boolean usePotion() {
        if (player.potions <= 0) {
            pushLog("No potion left.");
            return false;
        }
        player.potions--;
        int heal = 28 + random.nextInt(12);
        int before = player.hp;
        player.hp = Math.min(player.maxHp, player.hp + heal);
        pushLog("Potion used. HP " + before + " -> " + player.hp);
        return true;
    }

    private boolean tryDescend() {
        if (!player.position.equals(map.exit)) {
            pushLog("Stand on the exit tile first.");
            return false;
        }
        if (!monsters.isEmpty()) {
            pushLog("Enemies remain on this floor.");
            return false;
        }

        floor++;
        if (floor <= TARGET_FLOOR) {
            pushLog("Descending to floor " + floor + ".");
            generateFloor();
        }
        return true;
    }

    private void resolveMonstersTurn() {
        for (Monster m : monsters) {
            if (!m.isAlive()) {
                continue;
            }

            int dist = manhattan(m.position, player.position);
            if (dist == 1) {
                combat(m, player);
                if (!player.isAlive()) {
                    return;
                }
                continue;
            }

            Position next = null;
            if (dist <= m.detectRange) {
                next = map.nextStepToward(m.position, player.position, collectOccupiedPositions(m));
            } else if (random.nextDouble() < 0.35) {
                next = randomNeighbor(m.position, collectOccupiedPositions(m));
            }

            if (next != null && next.equals(player.position)) {
                combat(m, player);
                if (!player.isAlive()) {
                    return;
                }
            } else if (next != null) {
                m.position = next;
            }
        }
    }

    private Position randomNeighbor(Position origin, Set<Position> blocked) {
        List<Position> candidates = new ArrayList<Position>();
        candidates.add(origin.translate(1, 0));
        candidates.add(origin.translate(-1, 0));
        candidates.add(origin.translate(0, 1));
        candidates.add(origin.translate(0, -1));
        Collections.shuffle(candidates, random);

        for (Position p : candidates) {
            if (map.isWalkable(p) && !blocked.contains(p) && !p.equals(player.position)) {
                return p;
            }
        }
        return null;
    }

    private Set<Position> collectOccupiedPositions(Monster self) {
        Set<Position> blocked = new HashSet<Position>();
        for (Monster other : monsters) {
            if (other != self && other.isAlive()) {
                blocked.add(other.position);
            }
        }
        return blocked;
    }

    private void combat(Actor attacker, Actor defender) {
        int base = attacker.attack + random.nextInt(5) - 2;
        int mitigated = base - defender.defense / 2;
        int damage = Math.max(1, mitigated);
        boolean critical = random.nextDouble() < 0.12;
        if (critical) {
            damage *= 2;
        }
        defender.hp -= damage;

        String tag = critical ? " (CRIT)" : "";
        pushLog(attacker.name + " -> " + defender.name + " : " + damage + " damage" + tag);

        if (!defender.isAlive() && defender instanceof Monster) {
            onMonsterKilled((Monster) defender);
        }
    }

    private void onMonsterKilled(Monster m) {
        pushLog(m.name + " eliminated.");
        player.gainExp(m.expReward, new LogSink() {
            @Override
            public void log(String message) {
                pushLog(message);
            }
        });
        if (random.nextDouble() < 0.28) {
            map.spawnPotionNear(m.position, player.position);
            pushLog("A potion dropped.");
        }
    }

    private void cleanupDeadMonsters() {
        List<Monster> alive = new ArrayList<Monster>();
        for (Monster m : monsters) {
            if (m.isAlive()) {
                alive.add(m);
            }
        }
        monsters = alive;
    }

    private void generateFloor() {
        map = DungeonMap.generate(WIDTH, HEIGHT, random);
        player.position = map.spawn;
        monsters = spawnMonsters();
        player.restoreForNewFloor();
        pushLog("[Floor " + floor + "] Enemy signal: " + monsters.size());
    }

    private List<Monster> spawnMonsters() {
        int count = Math.min(6 + floor * 2, 20);
        List<Position> floors = map.getFloorTilesExceptSpecial();
        Collections.shuffle(floors, random);

        List<Monster> list = new ArrayList<Monster>();
        int spawned = 0;
        for (Position p : floors) {
            if (spawned >= count) {
                break;
            }
            if (manhattan(p, player.position) < 5) {
                continue;
            }
            list.add(MonsterFactory.createForFloor(floor, p, random));
            spawned++;
        }
        return list;
    }

    private Monster getMonsterAt(Position p) {
        for (Monster m : monsters) {
            if (m.isAlive() && m.position.equals(p)) {
                return m;
            }
        }
        return null;
    }

    private int manhattan(Position a, Position b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private void pushLog(String message) {
        if (log.size() >= 8) {
            log.removeFirst();
        }
        log.addLast(message);
    }

    private void render() {
        clearScreen();
        System.out.println("=== PRO ROGUELIKE ===");
        System.out.println("Floor: " + floor + "/" + TARGET_FLOOR
                + "   HP: " + player.hp + "/" + player.maxHp
                + "   MP: " + player.mana + "/" + player.maxMana
                + "   LV: " + player.level
                + "   XP: " + player.exp + "/" + player.nextExp
                + "   Potion: " + player.potions
                + "   Enemies: " + monsters.size());
        System.out.println();

        for (int y = 0; y < map.height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.width; x++) {
                Position p = new Position(x, y);
                char ch = map.cell(p);
                if (player.position.equals(p)) {
                    ch = '@';
                } else {
                    Monster m = getMonsterAt(p);
                    if (m != null) {
                        ch = m.glyph;
                    }
                }
                line.append(ch);
            }
            System.out.println(line.toString());
        }

        System.out.println();
        System.out.println("---- LOG ----");
        for (String message : log) {
            System.out.println(message);
        }
        System.out.println();
    }

    private void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}

class DungeonMap {
    final int width;
    final int height;
    final char[][] grid;
    final Position spawn;
    final Position exit;
    private final Set<Position> potions;

    private DungeonMap(int width, int height, char[][] grid, Position spawn, Position exit, Set<Position> potions) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.spawn = spawn;
        this.exit = exit;
        this.potions = potions;
    }

    static DungeonMap generate(int width, int height, Random random) {
        char[][] grid = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = '#';
            }
        }

        Position spawn = new Position(width / 2, height / 2);
        int carveX = spawn.x;
        int carveY = spawn.y;
        grid[carveY][carveX] = '.';
        int steps = width * height * 4;
        for (int i = 0; i < steps; i++) {
            int dir = random.nextInt(4);
            if (dir == 0) {
                carveX++;
            } else if (dir == 1) {
                carveX--;
            } else if (dir == 2) {
                carveY++;
            } else {
                carveY--;
            }
            carveX = clamp(carveX, 1, width - 2);
            carveY = clamp(carveY, 1, height - 2);
            grid[carveY][carveX] = '.';
        }

        Position exit = pickFarthestFloorTile(grid, spawn);
        grid[spawn.y][spawn.x] = '.';
        grid[exit.y][exit.x] = '>';

        Set<Position> potions = new HashSet<Position>();
        List<Position> candidates = collectFloors(grid);
        Collections.shuffle(candidates, random);
        int potionCount = Math.max(2, (width * height) / 120);
        for (Position p : candidates) {
            if (potions.size() >= potionCount) {
                break;
            }
            if (!p.equals(spawn) && !p.equals(exit)) {
                potions.add(p);
            }
        }
        return new DungeonMap(width, height, grid, spawn, exit, potions);
    }

    private static Position pickFarthestFloorTile(char[][] grid, Position from) {
        List<Position> floors = collectFloors(grid);
        Collections.sort(floors, new Comparator<Position>() {
            @Override
            public int compare(Position a, Position b) {
                int da = Math.abs(a.x - from.x) + Math.abs(a.y - from.y);
                int db = Math.abs(b.x - from.x) + Math.abs(b.y - from.y);
                return db - da;
            }
        });
        return floors.isEmpty() ? from : floors.get(0);
    }

    private static List<Position> collectFloors(char[][] grid) {
        List<Position> list = new ArrayList<Position>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == '.') {
                    list.add(new Position(x, y));
                }
            }
        }
        return list;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    boolean isWalkable(Position p) {
        if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) {
            return false;
        }
        char c = grid[p.y][p.x];
        return c == '.' || c == '>';
    }

    char cell(Position p) {
        if (potions.contains(p)) {
            return '!';
        }
        return grid[p.y][p.x];
    }

    boolean consumePotion(Position p) {
        return potions.remove(p);
    }

    void spawnPotionNear(Position around, Position avoid) {
        List<Position> points = new ArrayList<Position>();
        points.add(around);
        points.add(around.translate(1, 0));
        points.add(around.translate(-1, 0));
        points.add(around.translate(0, 1));
        points.add(around.translate(0, -1));
        for (Position p : points) {
            if (isWalkable(p) && !p.equals(avoid) && !p.equals(exit)) {
                potions.add(p);
                return;
            }
        }
    }

    List<Position> getFloorTilesExceptSpecial() {
        List<Position> result = new ArrayList<Position>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position p = new Position(x, y);
                if (grid[y][x] == '.' && !p.equals(spawn) && !p.equals(exit)) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    Position nextStepToward(Position from, Position target, Set<Position> blocked) {
        if (from.equals(target)) {
            return from;
        }

        Position[][] prev = new Position[height][width];
        boolean[][] visited = new boolean[height][width];
        ArrayDeque<Position> queue = new ArrayDeque<Position>();
        queue.add(from);
        visited[from.y][from.x] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Position cur = queue.poll();
            if (cur.equals(target)) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                Position next = new Position(cur.x + dx[i], cur.y + dy[i]);
                if (next.x < 0 || next.x >= width || next.y < 0 || next.y >= height) {
                    continue;
                }
                if (visited[next.y][next.x]) {
                    continue;
                }
                if (!isWalkable(next) && !next.equals(target)) {
                    continue;
                }
                if (blocked.contains(next) && !next.equals(target)) {
                    continue;
                }
                visited[next.y][next.x] = true;
                prev[next.y][next.x] = cur;
                queue.add(next);
            }
        }

        if (!visited[target.y][target.x]) {
            return null;
        }

        Position cursor = target;
        while (prev[cursor.y][cursor.x] != null && !prev[cursor.y][cursor.x].equals(from)) {
            cursor = prev[cursor.y][cursor.x];
        }
        return cursor;
    }
}

class Position {
    final int x;
    final int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Position translate(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position p = (Position) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }
}

abstract class Actor {
    final String name;
    Position position;
    int hp;
    int maxHp;
    int attack;
    int defense;

    Actor(String name, Position position, int maxHp, int attack, int defense) {
        this.name = name;
        this.position = position;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
    }

    boolean isAlive() {
        return hp > 0;
    }
}

class Player extends Actor {
    int level = 1;
    int exp = 0;
    int nextExp = 36;
    int mana = 30;
    int maxMana = 30;
    int potions = 2;

    Player(String name, Position position) {
        super(name, position, 84, 14, 8);
    }

    void gainExp(int amount, LogSink sink) {
        exp += amount;
        while (exp >= nextExp) {
            exp -= nextExp;
            level++;
            nextExp = (int) (nextExp * 1.35) + 10;
            maxHp += 12;
            attack += 3;
            defense += 2;
            maxMana += 5;
            hp = maxHp;
            mana = maxMana;
            sink.log("Level up! LV " + level + " (HP/ATK/DEF/MP increased)");
        }
    }

    void regenerate() {
        hp = Math.min(maxHp, hp + 1);
        mana = Math.min(maxMana, mana + 2);
    }

    void restoreForNewFloor() {
        hp = Math.min(maxHp, hp + 8);
        mana = Math.min(maxMana, mana + 8);
    }
}

class Monster extends Actor {
    final int expReward;
    final int detectRange;
    final char glyph;

    Monster(String name, Position position, int hp, int atk, int def, int expReward, int detectRange, char glyph) {
        super(name, position, hp, atk, def);
        this.expReward = expReward;
        this.detectRange = detectRange;
        this.glyph = glyph;
    }
}

class MonsterFactory {
    static Monster createForFloor(int floor, Position position, Random random) {
        int typeRoll = random.nextInt(100);

        if (typeRoll < 50) {
            return new Monster(
                    "Raider",
                    position,
                    20 + floor * 4,
                    9 + floor * 2,
                    4 + floor,
                    10 + floor * 2,
                    6,
                    'r'
            );
        } else if (typeRoll < 85) {
            return new Monster(
                    "Sentinel",
                    position,
                    30 + floor * 5,
                    8 + floor * 2,
                    8 + floor,
                    14 + floor * 3,
                    7,
                    's'
            );
        } else {
            return new Monster(
                    "Warden",
                    position,
                    45 + floor * 7,
                    12 + floor * 3,
                    10 + floor,
                    24 + floor * 4,
                    8,
                    'W'
            );
        }
    }
}

interface LogSink {
    void log(String message);
}



