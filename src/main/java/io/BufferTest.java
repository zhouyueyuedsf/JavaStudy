package io;

import java.io.*;

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

    private void test1() {
        InputStream in = null;
        try {
            //得到输入流
            in = new FileInputStream("E:\\test\\a.txt");
            //得到输出流
            File file = new File("E:\\test\\b.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file, true);
            int i;//从输入流读取一定数量的字节，返回 0 到 255 范围内的 int 型字节值
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
