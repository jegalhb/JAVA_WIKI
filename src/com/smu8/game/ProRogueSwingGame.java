package com.smu8.game;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProRogueSwingGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame();
                frame.setVisible(true);
            }
        });
    }
}

class GameFrame extends JFrame {
    GameFrame() {
        setTitle("PRO ROGUELIKE - Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

class GamePanel extends JPanel {
    private static final int MAP_W = 30;
    private static final int MAP_H = 20;
    private static final int TILE = 30;
    private static final int HUD_H = 130;
    private static final int TARGET_FLOOR = 5;

    private final Random random = new Random();
    private final Deque<String> log = new ArrayDeque<String>();
    private final Font hudFont = new Font("Consolas", Font.PLAIN, 16);
    private final Font mapFont = new Font("Consolas", Font.BOLD, 14);

    private char[][] grid;
    private Set<Point> potions;
    private Point spawn;
    private Point exit;
    private Point player;

    private List<Monster2D> monsters;

    private int floor = 1;
    private int level = 1;
    private int exp = 0;
    private int nextExp = 36;

    private int maxHp = 84;
    private int hp = 84;
    private int maxMana = 30;
    private int mana = 30;
    private int attack = 14;
    private int defense = 8;
    private int potionsInBag = 2;

    private boolean gameOver = false;
    private boolean victory = false;

    GamePanel() {
        setPreferredSize(new Dimension(MAP_W * TILE, MAP_H * TILE + HUD_H));
        setBackground(new Color(14, 16, 20));
        setFocusable(true);

        bindKey("W", 0, -1);
        bindKey("UP", 0, -1);
        bindKey("S", 0, 1);
        bindKey("DOWN", 0, 1);
        bindKey("A", -1, 0);
        bindKey("LEFT", -1, 0);
        bindKey("D", 1, 0);
        bindKey("RIGHT", 1, 0);
        bindAction("F", new Runnable() {
            @Override
            public void run() {
                if (!gameOver && !victory) {
                    castShockwave();
                }
            }
        });
        bindAction("Q", new Runnable() {
            @Override
            public void run() {
                if (!gameOver && !victory) {
                    usePotion();
                }
            }
        });
        bindAction("E", new Runnable() {
            @Override
            public void run() {
                if (!gameOver && !victory) {
                    descendFloor();
                }
            }
        });
        bindAction("R", new Runnable() {
            @Override
            public void run() {
                restart();
            }
        });

        new Timer(80, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        }).start();

        pushLog("Welcome. Clear floor " + TARGET_FLOOR + " to win.");
        generateFloor();
    }

    private void bindKey(String key, final int dx, final int dy) {
        bindAction(key, new Runnable() {
            @Override
            public void run() {
                if (!gameOver && !victory) {
                    movePlayer(dx, dy);
                }
            }
        });
    }

    private void bindAction(String key, final Runnable action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void restart() {
        floor = 1;
        level = 1;
        exp = 0;
        nextExp = 36;
        maxHp = 84;
        hp = 84;
        maxMana = 30;
        mana = 30;
        attack = 14;
        defense = 8;
        potionsInBag = 2;
        gameOver = false;
        victory = false;
        monsters = new ArrayList<Monster2D>();
        log.clear();
        pushLog("Run restarted.");
        generateFloor();
    }

    private void generateFloor() {
        grid = new char[MAP_H][MAP_W];
        for (int y = 0; y < MAP_H; y++) {
            for (int x = 0; x < MAP_W; x++) {
                grid[y][x] = '#';
            }
        }

        spawn = new Point(MAP_W / 2, MAP_H / 2);
        int cx = spawn.x;
        int cy = spawn.y;
        grid[cy][cx] = '.';
        int steps = MAP_W * MAP_H * 4;
        for (int i = 0; i < steps; i++) {
            int d = random.nextInt(4);
            if (d == 0) {
                cx++;
            } else if (d == 1) {
                cx--;
            } else if (d == 2) {
                cy++;
            } else {
                cy--;
            }
            cx = clamp(cx, 1, MAP_W - 2);
            cy = clamp(cy, 1, MAP_H - 2);
            grid[cy][cx] = '.';
        }

        exit = pickFarthestFloor(spawn);
        grid[exit.y][exit.x] = '>';

        player = new Point(spawn.x, spawn.y);
        spawnPotions();
        spawnMonsters();

        hp = Math.min(maxHp, hp + 8);
        mana = Math.min(maxMana, mana + 8);

        pushLog("Floor " + floor + " generated. Enemies: " + monsters.size());
    }

    private void spawnPotions() {
        potions = new HashSet<Point>();
        List<Point> floors = floorTiles();
        Collections.shuffle(floors, random);
        int amount = Math.max(2, (MAP_W * MAP_H) / 140);
        for (Point p : floors) {
            if (potions.size() >= amount) {
                break;
            }
            if (!p.equals(spawn) && !p.equals(exit)) {
                potions.add(p);
            }
        }
    }

    private void spawnMonsters() {
        List<Point> floors = floorTiles();
        Collections.shuffle(floors, random);
        monsters = new ArrayList<Monster2D>();

        int count = Math.min(6 + floor * 2, 22);
        for (Point p : floors) {
            if (monsters.size() >= count) {
                break;
            }
            if (manhattan(p, player) < 6 || p.equals(exit)) {
                continue;
            }
            monsters.add(Monster2D.create(floor, p, random));
        }
    }

    private Point pickFarthestFloor(Point from) {
        List<Point> floors = floorTiles();
        Point far = from;
        int best = -1;
        for (Point p : floors) {
            int d = manhattan(p, from);
            if (d > best) {
                best = d;
                far = p;
            }
        }
        return far;
    }

    private List<Point> floorTiles() {
        List<Point> list = new ArrayList<Point>();
        for (int y = 0; y < MAP_H; y++) {
            for (int x = 0; x < MAP_W; x++) {
                if (grid[y][x] == '.') {
                    list.add(new Point(x, y));
                }
            }
        }
        return list;
    }

    private void movePlayer(int dx, int dy) {
        Point next = new Point(player.x + dx, player.y + dy);
        if (!isWalkable(next)) {
            pushLog("Blocked by wall.");
            return;
        }

        Monster2D enemy = monsterAt(next);
        if (enemy != null) {
            attackMonster(enemy);
            endTurn();
            return;
        }

        player = next;
        if (potions.remove(player)) {
            potionsInBag++;
            pushLog("Potion picked. bag=" + potionsInBag);
        }
        if (player.equals(exit)) {
            pushLog("At exit. Press E to descend.");
        }
        endTurn();
    }

    private void castShockwave() {
        if (mana < 15) {
            pushLog("Not enough mana.");
            return;
        }

        List<Monster2D> adj = new ArrayList<Monster2D>();
        for (Monster2D m : monsters) {
            if (m.alive && manhattan(m.pos, player) == 1) {
                adj.add(m);
            }
        }
        if (adj.isEmpty()) {
            pushLog("No adjacent target for shockwave.");
            return;
        }

        mana -= 15;
        pushLog("Shockwave cast.");
        for (Monster2D m : adj) {
            int dmg = 8 + random.nextInt(8) + level * 2;
            m.hp -= dmg;
            pushLog(m.name + " took " + dmg);
            if (m.hp <= 0 && m.alive) {
                killMonster(m);
            }
        }
        endTurn();
    }

    private void usePotion() {
        if (potionsInBag <= 0) {
            pushLog("No potion in bag.");
            return;
        }
        potionsInBag--;
        int heal = 28 + random.nextInt(12);
        int before = hp;
        hp = Math.min(maxHp, hp + heal);
        pushLog("Potion used: HP " + before + " -> " + hp);
        endTurn();
    }

    private void descendFloor() {
        if (!player.equals(exit)) {
            pushLog("Stand on exit tile to descend.");
            return;
        }
        if (aliveMonsters() > 0) {
            pushLog("Enemies remain on this floor.");
            return;
        }

        floor++;
        if (floor > TARGET_FLOOR) {
            victory = true;
            pushLog("Tower conquered.");
            return;
        }
        pushLog("Descending to floor " + floor);
        generateFloor();
    }

    private void attackMonster(Monster2D m) {
        int base = attack + random.nextInt(5) - 2;
        int dmg = Math.max(1, base - m.def / 2);
        if (random.nextDouble() < 0.12) {
            dmg *= 2;
        }
        m.hp -= dmg;
        pushLog("You hit " + m.name + " for " + dmg);
        if (m.hp <= 0 && m.alive) {
            killMonster(m);
        }
    }

    private void killMonster(Monster2D m) {
        m.alive = false;
        pushLog(m.name + " defeated.");
        gainExp(m.exp);
        if (random.nextDouble() < 0.25) {
            potions.add(new Point(m.pos.x, m.pos.y));
            pushLog("Enemy dropped a potion.");
        }
    }

    private void gainExp(int amount) {
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
            pushLog("LEVEL UP -> " + level);
        }
    }

    private void endTurn() {
        moveMonsters();
        cleanDeadMonsters();

        hp = Math.min(maxHp, hp + 1);
        mana = Math.min(maxMana, mana + 2);

        if (hp <= 0) {
            gameOver = true;
            pushLog("You are dead. Press R to restart.");
        }
        if (aliveMonsters() == 0 && !victory) {
            pushLog("All enemies down. Reach exit and press E.");
        }
    }

    private void moveMonsters() {
        for (Monster2D m : monsters) {
            if (!m.alive) {
                continue;
            }

            int dist = manhattan(m.pos, player);
            if (dist == 1) {
                monsterAttack(m);
                if (hp <= 0) {
                    return;
                }
                continue;
            }

            Point next = null;
            if (dist <= m.detectRange) {
                next = nextStepToward(m.pos, player, occupiedWithout(m));
            } else if (random.nextDouble() < 0.35) {
                next = randomNeighbor(m.pos, occupiedWithout(m));
            }

            if (next != null && next.equals(player)) {
                monsterAttack(m);
                if (hp <= 0) {
                    return;
                }
            } else if (next != null) {
                m.pos = next;
            }
        }
    }

    private void monsterAttack(Monster2D m) {
        int base = m.atk + random.nextInt(5) - 2;
        int dmg = Math.max(1, base - defense / 2);
        if (random.nextDouble() < 0.10) {
            dmg *= 2;
        }
        hp -= dmg;
        pushLog(m.name + " hit you for " + dmg);
    }

    private Point randomNeighbor(Point origin, Set<Point> blocked) {
        List<Point> cands = new ArrayList<Point>();
        cands.add(new Point(origin.x + 1, origin.y));
        cands.add(new Point(origin.x - 1, origin.y));
        cands.add(new Point(origin.x, origin.y + 1));
        cands.add(new Point(origin.x, origin.y - 1));
        Collections.shuffle(cands, random);

        for (Point p : cands) {
            if (isWalkable(p) && !blocked.contains(p) && !p.equals(player)) {
                return p;
            }
        }
        return null;
    }

    private Set<Point> occupiedWithout(Monster2D self) {
        Set<Point> blocked = new HashSet<Point>();
        for (Monster2D m : monsters) {
            if (m != self && m.alive) {
                blocked.add(m.pos);
            }
        }
        return blocked;
    }

    private Point nextStepToward(Point from, Point target, Set<Point> blocked) {
        if (from.equals(target)) {
            return from;
        }

        Point[][] prev = new Point[MAP_H][MAP_W];
        boolean[][] visited = new boolean[MAP_H][MAP_W];
        ArrayDeque<Point> q = new ArrayDeque<Point>();
        q.add(from);
        visited[from.y][from.x] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Point cur = q.poll();
            if (cur.equals(target)) {
                break;
            }
            for (int i = 0; i < 4; i++) {
                Point n = new Point(cur.x + dx[i], cur.y + dy[i]);
                if (!inBounds(n) || visited[n.y][n.x]) {
                    continue;
                }
                if (!isWalkable(n) && !n.equals(target)) {
                    continue;
                }
                if (blocked.contains(n) && !n.equals(target)) {
                    continue;
                }
                visited[n.y][n.x] = true;
                prev[n.y][n.x] = cur;
                q.add(n);
            }
        }

        if (!visited[target.y][target.x]) {
            return null;
        }

        Point cursor = target;
        while (prev[cursor.y][cursor.x] != null && !prev[cursor.y][cursor.x].equals(from)) {
            cursor = prev[cursor.y][cursor.x];
        }
        return cursor;
    }

    private void cleanDeadMonsters() {
        List<Monster2D> alive = new ArrayList<Monster2D>();
        for (Monster2D m : monsters) {
            if (m.alive) {
                alive.add(m);
            }
        }
        monsters = alive;
    }

    private int aliveMonsters() {
        int count = 0;
        for (Monster2D m : monsters) {
            if (m.alive) {
                count++;
            }
        }
        return count;
    }

    private Monster2D monsterAt(Point p) {
        for (Monster2D m : monsters) {
            if (m.alive && m.pos.equals(p)) {
                return m;
            }
        }
        return null;
    }

    private void pushLog(String msg) {
        if (log.size() >= 7) {
            log.removeFirst();
        }
        log.addLast(msg);
    }

    private boolean inBounds(Point p) {
        return p.x >= 0 && p.x < MAP_W && p.y >= 0 && p.y < MAP_H;
    }

    private boolean isWalkable(Point p) {
        return inBounds(p) && (grid[p.y][p.x] == '.' || grid[p.y][p.x] == '>');
    }

    private int manhattan(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawMap(g2);
        drawUnits(g2);
        drawHud(g2);

        g2.dispose();
    }

    private void drawMap(Graphics2D g2) {
        for (int y = 0; y < MAP_H; y++) {
            for (int x = 0; x < MAP_W; x++) {
                int px = x * TILE;
                int py = y * TILE;

                if (grid[y][x] == '#') {
                    g2.setColor(new Color(37, 43, 50));
                } else {
                    g2.setColor(new Color(64, 72, 82));
                }
                g2.fillRect(px, py, TILE, TILE);

                if (grid[y][x] == '>') {
                    g2.setColor(new Color(118, 218, 143));
                    g2.fillOval(px + 8, py + 8, TILE - 16, TILE - 16);
                }

                if (potions.contains(new Point(x, y))) {
                    g2.setColor(new Color(112, 219, 255));
                    g2.fillRoundRect(px + 10, py + 7, TILE - 20, TILE - 14, 8, 8);
                }

                g2.setColor(new Color(20, 24, 28, 80));
                g2.drawRect(px, py, TILE, TILE);
            }
        }
    }

    private void drawUnits(Graphics2D g2) {
        g2.setFont(mapFont);

        int pX = player.x * TILE;
        int pY = player.y * TILE;
        g2.setColor(new Color(102, 163, 255));
        g2.fillOval(pX + 5, pY + 5, TILE - 10, TILE - 10);
        g2.setColor(new Color(234, 245, 255));
        g2.drawString("@", pX + 11, pY + 20);

        for (Monster2D m : monsters) {
            if (!m.alive) {
                continue;
            }
            int mx = m.pos.x * TILE;
            int my = m.pos.y * TILE;
            g2.setColor(m.color);
            g2.fillOval(mx + 5, my + 5, TILE - 10, TILE - 10);
            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(m.glyph), mx + 10, my + 20);
        }
    }

    private void drawHud(Graphics2D g2) {
        int y0 = MAP_H * TILE;
        g2.setColor(new Color(11, 13, 16));
        g2.fillRect(0, y0, MAP_W * TILE, HUD_H);

        g2.setColor(new Color(86, 98, 115));
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(0, y0, MAP_W * TILE, y0);

        g2.setFont(hudFont);
        g2.setColor(new Color(232, 239, 248));

        String stat = "Floor " + floor + "/" + TARGET_FLOOR
                + "   HP " + hp + "/" + maxHp
                + "   MP " + mana + "/" + maxMana
                + "   LV " + level
                + "   XP " + exp + "/" + nextExp
                + "   ATK " + attack
                + "   DEF " + defense
                + "   Potions " + potionsInBag
                + "   Enemies " + aliveMonsters();
        g2.drawString(stat, 12, y0 + 26);

        g2.setColor(new Color(180, 195, 210));
        g2.drawString("Keys: WASD/Arrows move | F shockwave | Q potion | E descend | R restart", 12, y0 + 50);

        int line = 0;
        g2.setColor(new Color(154, 172, 190));
        for (String s : log) {
            g2.drawString(s, 12, y0 + 76 + line * 18);
            line++;
        }

        if (gameOver || victory) {
            g2.setColor(new Color(0, 0, 0, 155));
            g2.fillRect(0, 0, MAP_W * TILE, MAP_H * TILE);
            g2.setColor(victory ? new Color(133, 244, 167) : new Color(255, 126, 126));
            g2.setFont(new Font("Consolas", Font.BOLD, 38));
            String title = victory ? "VICTORY" : "GAME OVER";
            int tw = g2.getFontMetrics().stringWidth(title);
            g2.drawString(title, (MAP_W * TILE - tw) / 2, MAP_H * TILE / 2 - 8);

            g2.setColor(new Color(235, 240, 245));
            g2.setFont(new Font("Consolas", Font.PLAIN, 20));
            String sub = "Press R to restart";
            int sw = g2.getFontMetrics().stringWidth(sub);
            g2.drawString(sub, (MAP_W * TILE - sw) / 2, MAP_H * TILE / 2 + 28);
        }
    }
}

class Monster2D {
    final String name;
    Point pos;
    final int atk;
    final int def;
    final int exp;
    final int detectRange;
    final char glyph;
    final Color color;
    int hp;
    boolean alive = true;

    Monster2D(String name, Point pos, int hp, int atk, int def, int exp, int detectRange, char glyph, Color color) {
        this.name = name;
        this.pos = new Point(pos.x, pos.y);
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.exp = exp;
        this.detectRange = detectRange;
        this.glyph = glyph;
        this.color = color;
    }

    static Monster2D create(int floor, Point pos, Random random) {
        int roll = random.nextInt(100);
        if (roll < 50) {
            return new Monster2D(
                    "Raider",
                    pos,
                    20 + floor * 4,
                    9 + floor * 2,
                    4 + floor,
                    10 + floor * 2,
                    6,
                    'r',
                    new Color(226, 102, 102)
            );
        }
        if (roll < 85) {
            return new Monster2D(
                    "Sentinel",
                    pos,
                    30 + floor * 5,
                    8 + floor * 2,
                    8 + floor,
                    14 + floor * 3,
                    7,
                    's',
                    new Color(224, 160, 92)
            );
        }
        return new Monster2D(
                "Warden",
                pos,
                45 + floor * 7,
                12 + floor * 3,
                10 + floor,
                24 + floor * 4,
                8,
                'W',
                new Color(189, 124, 247)
        );
    }
}
