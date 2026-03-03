package com.smu8.game;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class BlackJaxGame {
    public class L06BlackJack {
        //점수 계산함수
        public static int setScore(String []deckArr){
            int sum = 0;
            for (int i = 0; i < deckArr.length; i++) {
                String card=deckArr[i];
                if(card==null) break;
                String [] cardArr=card.split("_"); //♣_13 => {"♣","13"}
                int num=Integer.parseInt(cardArr[1]);//"13"=>13
                if(num==1){ //카드 A 는 1점 OR 11점
                    if(sum+11<=21){ //11점으로 취급했을시 21점을 넘으면 1점 취금
                        sum+=11;
                    }else {
                        sum+=1;
                    }
                } else if (num>10) { //J 11,Q 12,K 13 모두 10점
                    sum+=10;
                }else {
                    sum+=num;
                }
            }
            return sum;

        }

        public static void main(String[] args) {


            game : while (true){
                System.out.println("""
블랙잭 게임

[게임 목표]
카드의 합이 21에 가장 가까운 사람이 승리합니다.
21을 초과하면 즉시 패배합니다.

[카드 구성]
- 카드는 "무늬_숫자" 형식으로 구성됩니다. (예: ♣_13)
- 숫자는 1(A)부터 13(K)까지 존재합니다.

[카드 점수 규칙]
- A(1)  : 1점 또는 11점 현재 합계에 따라 21을 넘지 않으면 11점,
          넘으면 1점으로 계산됩니다.
- 2~10 : 숫자 그대로 점수로 계산됩니다.
- J, Q, K (11~13) : 모두 10점으로 계산됩니다.

[게임 진행]
1. 유저와 딜러는 처음에 카드 2장씩 받습니다.
2. 유저의 카드와 점수를 먼저 공개합니다.
3. 딜러의 카드는 게임이 끝날 때 공개됩니다.

[카드 더 받기 (Hit)]
- 유저는 자신의 차례에 카드를 한 장 더 받을 수 있습니다. (최대 11개)
- 카드를 더 받으면 현재 카드 뒤에 추가됩니다.
- 카드의 합을 다시 계산합니다.

[게임 종료 (Stand / Bust)]
- 유저가 더 이상 카드를 받지 않겠다고 선택하면 게임을 종료합니다. (Stand)
- 카드 합이 21을 초과하면 즉시 게임이 종료되며 패배합니다. (Bust)

[승패 판정]
- 유저 점수가 21을 초과하면 유저 패배(Bust)
- 딜러 점수가 21을 초과하면 유저 승리
- 둘 다 21 이하라면 점수가 높은 쪽이 승리
- 점수가 같으면 무승부입니다.


""");
                String[] deckArr = {
                        "♠_1","♠_2","♠_3","♠_4","♠_5","♠_6","♠_7","♠_8","♠_9","♠_10","♠_11","♠_12","♠_13",
                        "♥_1","♥_2","♥_3","♥_4","♥_5","♥_6","♥_7","♥_8","♥_9","♥_10","♥_11","♥_12","♥_13",
                        "♦_1","♦_2","♦_3","♦_4","♦_5","♦_6","♦_7","♦_8","♦_9","♦_10","♦_11","♦_12","♦_13",
                        "♣_1","♣_2","♣_3","♣_4","♣_5","♣_6","♣_7","♣_8","♣_9","♣_10","♣_11","♣_12","♣_13"
                };//52개 0~51
                String[] shuffleDeckArr=new String[52]; //섞어서 참조할 곳
                //유저와 딜러는 각각 최대 11개까지의 카드를 받을 수 있다.
                String[] userDeckArr=new String[11];
                String[] dealerDeckArr=new String[11];
                Random random=new Random();
                int cnt=0;

                //카드 셔플
                for (int i = 0; i < deckArr.length; i++) {
                    String card=deckArr[i];
                    while (true){
                        int randomIndex=random.nextInt(52);
                        if(shuffleDeckArr[randomIndex]==null){ //shuffleDeckArr 의 랜덤한 위치에 카드를 추가하는데 이미 있으면 안함
                            shuffleDeckArr[randomIndex]=card;
                            break;
                        }
                    }
                }
                System.out.println("셔플된 카드 : "+Arrays.toString(shuffleDeckArr));
                //최초 유저와 딜러는 카드 2개씩가짐
                userDeckArr[0]=shuffleDeckArr[0];
                dealerDeckArr[0]=shuffleDeckArr[1];
                userDeckArr[1]=shuffleDeckArr[2];
                dealerDeckArr[1]=shuffleDeckArr[3];
                System.out.println("현재 유저 덱 :"+Arrays.toString(userDeckArr));
                System.out.println("현재 딜러 덱 :"+Arrays.toString(dealerDeckArr));
                int userSum=setScore(userDeckArr);
                System.out.println("유저 점수 합 :"+userSum);
                int dealerSum=setScore(dealerDeckArr); //점수 계산을 함수로 변경
                System.out.println("딜러 점수 합 :"+dealerSum);
                //최초에 21점을 넘을 수 없음
                if(userSum==21 && dealerSum<21){
                    System.out.println("유저승리");
                    break;
                } else if (dealerSum==21 && userSum<21) {
                    System.out.println("딜러승리");
                    break;
                }
                int shuffleIndex=4; //최초 유저와 딜러가 2개씩 나눔
                while (true){

                    System.out.print("다음 중 입력, Hit=0, Stand=1, Exit=2:");
                    Scanner scanner=new Scanner(System.in);
                    int input=scanner.nextInt();
                    if(input==2){
                        break game; //제일 밖 게임 반복문 종료
                    }else if(input==0){ //카드 1장씩 받기
                        //4/2=2
                        //5/2=2
                        userDeckArr[shuffleIndex/2]=shuffleDeckArr[shuffleIndex]; //셔플된 카드의 다음카드를 유저에게
                        ++shuffleIndex;
                        dealerDeckArr[shuffleIndex/2]=shuffleDeckArr[shuffleIndex]; //셔플된 카드의 다음카드를 딜러에게
                        System.out.println("현재 유저 덱 :"+Arrays.toString(userDeckArr));
                        System.out.println("현재 딜러 덱 :"+Arrays.toString(dealerDeckArr));
                        userSum=setScore(userDeckArr);
                        System.out.println("유저 점수 합 :"+userSum);
                        dealerSum=setScore(dealerDeckArr); //점수 계산을 함수로 변경
                        System.out.println("딜러 점수 합 :"+dealerSum);
                        if(userSum>21 && dealerSum<=21){ //유저가 21점이 넘어서 패배
                            System.out.println("Bust 유저 패배");
                            break game;
                        } else if (dealerSum>21 && userSum<=21) {//딜러가 21점이 넘어서 승리
                            System.out.println("유저 승리");
                            break  game;
                        }else if(dealerSum>21 && userSum>21) {  //둘다 21점이 넘어서 무승부
                            System.out.println("무승부");
                            break game;
                        }//나머진 다시 게임 진행

                    }else if(input==1) { //Stand 그대로 점수 계산
                        if(userSum < dealerSum){
                            System.out.println("유저 패배");
                        } else if (userSum> dealerSum) {
                            System.out.println("유저 승리");
                        }else if(dealerSum==dealerSum) {
                            System.out.println("무승부");
                        }
                        break game;
                    }


                }

            }

        }
    }
}
