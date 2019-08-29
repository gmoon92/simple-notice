package com.moong.notice.service.dto;

import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

public class SelectOptionsTest {
    private final Integer input = 1;

    @Test @Ignore
    public void testOf(){
        SelectOptions option = newOf(input);
        System.out.println(option);
        assertThat(option).isEqualTo( oldOf(input) );

        Arrays.stream(SelectOptions.values())
                .filter(o -> o.getOption() == 1)
        ;
    }

    @Test
    public void testDistinct() {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
        System.out.println(

                numbers.stream()
                        .allMatch(new HashSet<>()::add)
        );
    }

    private SelectOptions newOf(int option) {
        final SelectOptions[] options = SelectOptions.values();
        return Stream.of(options)
                .filter(o -> o.getOption() == option)
                .findFirst()
                .orElseThrow(() -> {
                    throw new SelectOptionNotFoundException(option);
                });
    }

    private SelectOptions oldOf(int option) {
        switch (option) {
            case 1: return SelectOptions.TITLE;
            case 2: return SelectOptions.CREATED_DATE;
            case 3: return SelectOptions.WRITER;
            case 4: return SelectOptions.MODIFED_DATE;
            case 5: return SelectOptions.CONTENTS;
            default: throw new SelectOptionNotFoundException(option);
        }
    }
}
