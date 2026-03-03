package com.smu8.homework;
/*Animal 부모 클래스
Dog, Cat 자식 클래스
test(Animal a) 메서드에서 sound()는 공통 호출
Dog인 경우에만 fetch()를 추가로 실행(다운캐스팅 필요)
instanceof 패턴 매칭(instanceof Dog d) 형태로 작성
 */
class Animal {
    String sound() {
        System.out.println("동물");
        return null;
    }

    class Dog extends Animal {
        String sound() {
            String name="코에";
            System.out.println("왕왕");
            return null;
        }

        void fetch() {
            System.out.println("물어오쇼");
        }
        /*@Override
        public String toString(){
            return "Dog { name="+ this.name+"}";
        }

         */

        class Cat extends Animal {
            String sound() {
                System.out.println("야옹야옹");
                return null;
            }
        }
    }

    static void test(Animal a) {
        a.sound();
        if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.fetch();
        }
    }

    public class H20TypePolymorphism {
        public static void main(String[] args) {

        }
    }
}
