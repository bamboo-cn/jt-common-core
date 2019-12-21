package com.bamboo.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoTools {


    public static byte[] readBytes(InputStream input) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = input.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static String toString(Object[] a) {
        if (a == null) {
            return null;
        } else {
            int iMax = a.length - 1;
            if (iMax == -1) {
                return "";
            } else {
                StringBuilder b = new StringBuilder();
                int i = 0;

                while (true) {
                    b.append(String.valueOf(a[i]));
                    if (i == iMax) {
                        return b.toString();
                    }

                    b.append(", ");
                    ++i;
                }
            }
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        long count = 0L;

        int n1;
        for(boolean var6 = false; -1 != (n1 = input.read(buffer)); count += (long)n1) {
            output.write(buffer, 0, n1);
        }

        return count;
    }

}
