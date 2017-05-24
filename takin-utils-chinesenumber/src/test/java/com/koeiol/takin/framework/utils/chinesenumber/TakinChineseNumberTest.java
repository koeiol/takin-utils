package com.koeiol.takin.framework.utils.chinesenumber;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by koeiol@github.com on 2017/5/24.
 */
public class TakinChineseNumberTest {

    @Test
    public void toChineseMoney() throws Exception {
        String value = TakinChineseNumber.from("1234567890.12345").toChineseMoney();
        System.out.println(value);

        String v2 = TakinChineseNumber.from(new BigDecimal("1234567890.12345")).toChineseMoney();
        System.out.println(v2);
    }

}