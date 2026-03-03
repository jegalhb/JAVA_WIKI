package poolsandwich.toopool;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Scanner;

public class L04ExceptionEx {

    // 나이 계산
    public static int parseAge(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        return Period.between(birthDate, now).getYears();
    }

    // 살아온 시간 출력
    public static void printLivingTime(LocalDate birthDate) {
        LocalDateTime birthDateTime = birthDate.atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        // 기간 (년/월/일)
        Period period = Period.between(birthDate, now.toLocalDate());

        // 시간 (초 단위)
        Duration duration = Duration.between(birthDateTime, now);
        long totalSeconds = duration.getSeconds();

        long days = totalSeconds / (24 * 60 * 60);
        long hours = (totalSeconds % (24 * 60 * 60)) / (60 * 60);
        long minutes = (totalSeconds % (60 * 60)) / 60;
        long seconds = totalSeconds % 60;

        System.out.println(" 지금까지 살아온 기간");
        System.out.println(period.getYears() + "년 "
                + period.getMonths() + "개월 "
                + period.getDays() + "일");

        System.out.println("⏱ 시간으로 계산하면");
        System.out.println(days + "일 "
                + hours + "시간 "
                + minutes + "분 "
                + seconds + "초");

        System.out.println(" 총 " + totalSeconds + "초 동안 생존 중");
    }

    public static LocalDate parseBirthDate(String year, String month, String day) throws Exception {
        try {
            int y = Integer.parseInt(year);
            int m = Integer.parseInt(month);
            int d = Integer.parseInt(day);

            LocalDate birthDate = LocalDate.of(y, m, d);

            if (birthDate.isAfter(LocalDate.now()))
                throw new IllegalArgumentException("미래에서 오셨군요...?");

            return birthDate;

        } catch (NumberFormatException e) {
            throw new Exception("생년월일은 숫자로만 입력하세요.");
        } catch (Exception e) {
            throw new Exception("올바른 날짜를 입력하세요.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" 나이 & 생존시간 계산기 (exit 입력 시 종료)");

        while (true) {
            System.out.print("태어난 해 (yyyy) : ");
            String year = scanner.next();
            if (year.equalsIgnoreCase("exit")) break;

            System.out.print("태어난 월 (mm) : ");
            String month = scanner.next();

            System.out.print("태어난 일 (dd) : ");
            String day = scanner.next();

            try {
                LocalDate birthDate = parseBirthDate(year, month, day);

                int age = parseAge(birthDate);
                System.out.println(" 당신의 나이는 " + age + "살");

                printLivingTime(birthDate);

            } catch (Exception e) {
                System.out.println(" 오류 : " + e.getMessage());
            }

            System.out.println("----------------------------------");
        }

        System.out.println("프로그램 종료");
    }
}

