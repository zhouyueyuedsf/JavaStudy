package io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BufferTest {
    static int i = 0;
    public static void main(String[] args) {
        try {
            i = 1;
            i = 2;
            InputStream in = new BufferedInputStream(new FileInputStream("123"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
