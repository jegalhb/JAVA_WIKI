package poolsandwich.toopool;

import java.io.*;
import java.util.*;

public class UltimateRPG {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    // ================= PLAYER =================
    static class Player implements Serializable {
        int x = 0, y = 0;
        int level = 1, exp = 0;
        int hp, maxHp, atk, def;
        int gold = 100;
        int potion = 3;
        boolean poisoned = false;
        Weapon weapon = new Weapon("주먹", 0);
        Armor armor = new Armor("누더기", 0);

        Player(int diff) {
            maxHp = 120 - diff * 20;
            hp = maxHp;
            atk = 10;
            def = 5;
        }
    }

    // ================= ITEM =================
    static class Weapon implements Serializable {
        String name;
        int atk;
        Weapon(String n, int a) { name = n; atk = a; }
    }

    static class Armor implements Serializable {
        String name;
        int def;
        Armor(String n, int d) { name = n; def = d; }
    }

    // ================= MONSTER =================
    static class Monster {
        String name;
        int hp, atk;
        boolean boss;

        Monster(String n, int h, int a, boolean b) {
            name = n; hp = h; atk = a; boss = b;
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        System.out.println("난이도 선택 1.EASY 2.NORMAL 3.HARD");
        int diff = sc.nextInt();

        Player p = load();
        if (p == null) p = new Player(diff);

        while (p.hp > 0) {
            System.out.println("\nWASD 이동 | 1.상태 2.상점 3.저장 0.종료");
            String cmd = sc.next();

            if (cmd.equals("1")) status(p);
            else if (cmd.equals("2")) shop(p);
            else if (cmd.equals("3")) save(p);
            else if (cmd.equals("0")) break;
            else move(p, cmd);

            if (rand.nextInt(100) < 35) {
                battle(p, randomMonster(diff));
            }

            if (p.x == 4 && p.y == 4) {
                battle(p, boss(diff));
                ending(p);
                return;
            }
        }

        System.out.println("💀 영구 사망. 게임 종료");
    }

    // ================= MOVE =================
    static void move(Player p, String cmd) {
        if (cmd.equalsIgnoreCase("W")) p.y--;
        if (cmd.equalsIgnoreCase("S")) p.y++;
        if (cmd.equalsIgnoreCase("A")) p.x--;
        if (cmd.equalsIgnoreCase("D")) p.x++;
        System.out.println("현재 위치 (" + p.x + "," + p.y + ")");
    }

    // ================= BATTLE =================
    static void battle(Player p, Monster m) {
        System.out.println("⚔ " + m.name + " 등장!");

        while (p.hp > 0 && m.hp > 0) {

            if (p.poisoned) {
                p.hp -= 5;
                System.out.println("☠ 독 데미지 -5");
            }

            System.out.println("1.공격 2.스킬 3.포션 4.도망");
            int c = sc.nextInt();

            if (c == 1) {
                int dmg = p.atk + p.weapon.atk;
                m.hp -= dmg;
                System.out.println("공격 " + dmg);
            } else if (c == 2) {
                int dmg = (p.atk + p.weapon.atk) * 2;
                m.hp -= dmg;
                System.out.println("🔥 스킬 " + dmg);
            } else if (c == 3 && p.potion > 0) {
                p.hp += 30;
                if (p.hp > p.maxHp) p.hp = p.maxHp;
                p.potion--;
            } else if (c == 4) return;

            if (m.hp > 0) {
                int dmg = m.atk - (p.def + p.armor.def);
                if (rand.nextInt(10) < 2) p.poisoned = true;
                p.hp -= Math.max(dmg, 1);
            }
        }

        if (p.hp > 0) reward(p, m.boss);
    }

    // ================= REWARD =================
    static void reward(Player p, boolean boss) {
        int exp = boss ? 50 : 20;
        int gold = boss ? 200 : 50;
        p.exp += exp;
        p.gold += gold;
        System.out.println("보상 EXP+" + exp + " GOLD+" + gold);

        if (p.exp >= p.level * 50) {
            p.level++;
            p.exp = 0;
            p.maxHp += 20;
            p.atk += 5;
            p.def += 2;
            p.hp = p.maxHp;
            System.out.println("⭐ 레벨업!");
        }
    }

    // ================= SHOP =================
    static void shop(Player p) {
        System.out.println("1.포션(30G) 2.검(ATK+5,100G) 3.갑옷(DEF+5,100G)");
        int c = sc.nextInt();

        if (c == 1 && p.gold >= 30) { p.potion++; p.gold -= 30; }
        if (c == 2 && p.gold >= 100) { p.weapon = new Weapon("검",5); p.gold -= 100; }
        if (c == 3 && p.gold >= 100) { p.armor = new Armor("갑옷",5); p.gold -= 100; }
    }

    // ================= STATUS =================
    static void status(Player p) {
        System.out.println("Lv." + p.level +
                " HP:" + p.hp + "/" + p.maxHp +
                " ATK:" + (p.atk + p.weapon.atk) +
                " DEF:" + (p.def + p.armor.def) +
                " GOLD:" + p.gold +
                " POTION:" + p.potion);
    }

    // ================= MONSTER =================
    static Monster randomMonster(int diff) {
        return new Monster("고블린", 40 + diff * 20, 10 + diff * 5, false);
    }

    static Monster boss(int diff) {
        return new Monster("🐉 드래곤", 200, 30 + diff * 10, true);
    }

    // ================= SAVE / LOAD =================
    static void save(Player p) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));
            oos.writeObject(p);
            oos.close();
            System.out.println("💾 저장 완료");
        } catch (Exception e) {
            System.out.println("저장 실패");
        }
    }

    static Player load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
            Player p = (Player) ois.readObject();
            ois.close();
            System.out.println("📂 불러오기 성공");
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    // ================= ENDING =================
    static void ending(Player p) {
        if (p.level >= 10)
            System.out.println("🏆 진엔딩: 전설의 영웅");
        else
            System.out.println("⚔ 노멀 엔딩");
    }
}