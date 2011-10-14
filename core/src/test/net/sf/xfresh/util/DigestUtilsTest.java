package net.sf.xfresh.util;

import junit.framework.TestCase;

import java.math.BigInteger;

/**
 * Author: Olga Bolshakova (obolshakova@yandex-team.ru)
 * Date: 02.01.11 21:15
 */
public class DigestUtilsTest extends TestCase {

    public void testMd5ToString() {
        final byte[] md5 = DigestUtils.md5("blablabla");
        System.out.println(new BigInteger(md5).toString(16));
    }

}
