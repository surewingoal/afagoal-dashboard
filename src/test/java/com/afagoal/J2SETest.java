package com.afagoal;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by BaoCai on 18/5/27.
 * Description:
 */
public class J2SETest {

    @Test
    public void testBigDecimal(){
        DecimalFormat df2 =new DecimalFormat("#.00000000");
        BigDecimal bigDecimal = new BigDecimal(100.123);
        System.out.println(df2.format(bigDecimal));
    }

}

