package poolsandwich.toopool;

public class 연습용13 {
    public static void main(String[] args) {

        String s = "j" + "a" + "v" + "a";
        System.out.println(s);

        System.out.println("=========================");

        String a = "abcdef";

        System.out.println(a.substring(2));
        System.out.println(a.substring(1, 4));

        System.out.println("=========================");

        String data = "apple,banana,orange";
        String[] arr = data.split(",");

        System.out.println(arr.length);
        System.out.println(arr[1]);

        System.out.println("=========================");

        /*int[] score = {80, 75, 90, 100, 65};
        int sum = 0;
        int svg = 0;
        for (int i = 0; i < score.length; i++) {
            // { System.out.println(score[i]);}
            sum+=score[i];

        }
        System.out.println(sum);


        int [] reword = {54,84,62,12,78,93};
        int summ = 0;
        for (int i = 0; i < reword.length; i++) {
            summ+=reword[i];
        }
        System.out.println(summ);

         */
        /*
        int [] score = {80,75,90,100,65};
        int sum = 0;
        for (int i = 0; i < score.length; i++) {
            sum+=score[i]; // SUM에 축적!!
        }
        System.out.println("점수 총합: " + sum + " 점수의 평균 " + sum/score.length);

         */
        int[] score = {80, 75, 90, 100, 65};
        int sum = 0;
        int avg = 0;
        for (int i = 0; i < score.length; i++) {
            sum += score[i]; // sum = sum + score [i]
            avg = sum / score.length;
        }
        System.out.println("모든 점수의 합 : " + sum + "모든 점수의 평균" + avg);

        System.out.println(" =================== ");



        /*

        int[] productCodes = {101, 203, 305, 410, 512};
        final int FIND_CODE = 305;
        boolean isFind = false;
        for (int i = 0; i < productCodes.length; i++){
            if (productCodes[i] == FIND_CODE){
                isFind=true;
                break;
            }
        }
        String msg = isFind ? FIND_CODE+"찾음" : FIND_CODE + "없음";
        System.out.println(msg);

         */
        int [] productCodes = {101,203,305,410,512};
        // 찾고 싶은 코드는 305 존재하면 존재 없으면 없다
        final int FIND_CORD = 305;
        boolean find = false;
        for (int i = 0; i < productCodes.length; i++) {
            if (productCodes[i] == FIND_CORD){
                find = true;
                break;
            }

        }
        String msg = find ? FIND_CORD+ "존재" : FIND_CORD+ "없음";
        System.out.println(msg);

        int [] codes = {101,102,103,104,105,};
        final int cod = 106;
        boolean findcod=false;
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] == cod){
                findcod = true;
                break;
            }
        }
        String MSG = findcod ? cod + "존재" : cod + "없음";
        System.out.println(MSG);

        int [] ages = {25,30,-3,45,200,18};
        int cat = 0;
        for (int i = 0; i < ages.length; i++) {
            if (!(ages[i]<=120 && ages[i]>=0)){
                System.out.println("잘못된 나이" + ages[i]);
                cat ++;
            }
        }
        System.out.println("잘못된 나이 총수" + cat);











    }
}