package com.moong.notice.pratice;

import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyOptionalTest {

    @Test
    public void isOptionalOfIfElseTest(){
        MyEnum param = MyEnum.VALUE_A;

        List<MyEnum> list = new ArrayList<>();

        // of 메소드를 Optional로 전환하기
        Optional optOption = Optional.of(param)
                .filter(myEnum -> myEnum.equals(MyEnum.VALUE_A))
                .map(myEnum -> list.add(myEnum))
                ;



        System.out.println(
                optOption
        );

        System.out.println(list);


    }

    private MyEnum of(int val){
        switch (val){
            case 1 : return MyEnum.VALUE_A;
            case 2 : return MyEnum.VALUE_B;
            case 3 : return MyEnum.VALUE_C;
            default: throw new RuntimeException("고유한 값이 없습니다.");
        }
    }

    private enum MyEnum{
         VALUE_A(1)
        ,VALUE_B(2)
        ,VALUE_C(3);
        private final int val;

        MyEnum(int val) {
            this.val = val;
        }
    }
}
