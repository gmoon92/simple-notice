package com.moong.notice.repository;

import com.moong.notice.service.dto.SelectOptions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by gmun0929.work@gmail.com
 * Blog : https://gmun.github.io
 * Github : https://github.com/gmun
 *
 * @author : Moon Gyeom(moong)
 * @since : 2019-08-04
 */
public class EnumsTest {
    @Test
    public void isEnums(){

        final List<SelectOptions> keywordOptions = Arrays.asList(  SelectOptions.TITLE, SelectOptions.CONTENTS, SelectOptions.WRITER );
        final List<SelectOptions> dateOptions    = Arrays.asList( SelectOptions.CREATED_DATE, SelectOptions.MODIFED_DATE ) ;

        SelectOptions option1 = SelectOptions.TITLE;
        System.out.println(option1.equals(SelectOptions.TITLE));
        System.out.println(option1.equals(SelectOptions.CONTENTS));


        Predicate<SelectOptions> pd = option -> keywordOptions.contains(option);
//        System.out.println(pd.test(SelectOptions.TITLE));
//        System.out.println(pd.test(SelectOptions.CONTENTS));
//        System.out.println(pd.test(SelectOptions.CREATED_DATE));

//        System.out.println(Stream.of(SelectOptions.CREATED_DATE).anyMatch(pd));
//        System.out.println(Stream.of(SelectOptions.TITLE).anyMatch(pd));

        System.out.println(Stream.of(SelectOptions.TITLE)
                                 .anyMatch(option -> keywordOptions.contains(option)));

    }
}
