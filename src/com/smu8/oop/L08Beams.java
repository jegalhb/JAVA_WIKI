package com.smu8.oop;

// 쇼핑몰 => 고객 관리용 자료형
// 데이터(필드)만 관리하는 자료형 => Beans , Dto(데이터전송) , Entity(데이터베이스의 테이블과 유사한 구조)
class CustomerBean{
    private String id;
    private String name;
    private int age;

    //나이 겟터셋터 ㄷㄷ
    public void setAge(int age){ // 나이를 받아와야하니 매개변수 작성
        if (age >= 0 && age <=130){
            this.age=age;
        } else {
            System.out.println("이 나이는 말이 안대");
        }
    }
    public int getAge(){
        return this.age;
    }

    // getter setter 캡슐화 : 갯터 셋터!
    public void setName(String name){
        this.name=name;
    }
    public String getName() {
        return this.name;
    }

    public void setId(String id){
        //id는 4자 이상이여야해
        if (id.length()>3){
            this.id=id;
        }else {
            throw new IllegalArgumentException("id는 4자 이상"); // throw == 고의로 오류를 생성 ㄷㄷ
        }
    }
    public String getId(){
        return this.id;
    }
}



//고객 관리 어플
public class L08Beams {
    public static void main(String[] args) {
        CustomerBean c= new CustomerBean();
        c.setId("제갈이코딩");
        System.out.println(c.getId());

        c.setAge(-1);
        c.setAge(40);
        c.setName("제가리리리");
        System.out.println(c.getAge());
        System.out.println(c.getName());
        System.out.println(c.getId());


        // c.id = "제갈코딩";
        // c.name = "제갈";
        // c.age = 12;
        // 고객관리 어플에서 고객의 정보 ( 필드) 를 마음대ㅔ로 접근가능해서 데이털에 문제가 생김
        // 이러한 문제를 private하게 관리한 것
    }
}
