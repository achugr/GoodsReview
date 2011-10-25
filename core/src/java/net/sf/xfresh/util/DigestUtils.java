package net.sf.xfresh.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {
    private static final MessageDigest MD5_PROTO = safeGetInstance("MD5");
    private static final MessageDigest SHA1_PROTO = safeGetInstance("SHA-1");

    private static MessageDigest safeGetInstance(String functionName) {
        try {
            return MessageDigest.getInstance(functionName);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static MessageDigest getMd5Digest() {
        return cloneMessageDigest(MD5_PROTO);
    }

    public static MessageDigest getSha1Digest() {
        return cloneMessageDigest(SHA1_PROTO);
    }

    private static MessageDigest cloneMessageDigest(final MessageDigest digest) {
        try {
            return (MessageDigest) digest.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private static byte[] applyHashFunction(final MessageDigest digest, final byte[]... parts) {
        final MessageDigest d = cloneMessageDigest(digest);
        for (byte[] b : parts) d.update(b);
        return d.digest();
    }

    public static byte[] md5(final byte[]... parts) {
        return applyHashFunction(MD5_PROTO, parts);
    }

    public static String md5String(final String in) {
        return String.format("%032x", new BigInteger(1, md5(in)));
    }

    public static byte[] md5(final InputStream is) throws IOException {
        final MessageDigest d = cloneMessageDigest(MD5_PROTO);

        final byte[] buf = new byte[65536];
        while (0 < (is.read(
                buf, 0,
                buf.length))) {
            d.update(buf);
        }
        return d.digest();
    }

    public static byte[] md5(final String s) {
        if (s == null) {
            throw new NullPointerException("Can't get hash from null string");
        }
        return md5(s.getBytes());
    }

    public static byte[] sha1(final byte[]... parts) {
        return applyHashFunction(SHA1_PROTO, parts);
    }
}
