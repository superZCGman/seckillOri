package com.limai.user.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio工具类
 */
public class NIOUtil {

    /**
     * nio读取文件到byte数组
     *
     * @param filepath
     * @return byte[]
     * @throws IOException
     */
    public static byte[] file2Byte(String filepath) throws IOException {

        // 检查文件路径
        File file = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException(filepath);
        }

        FileChannel channel = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            channel = inputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {}

            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * nio读取文件到byte数组, 内存映射读取大文件效率高
     *
     * @param filepath
     * @return byte[]
     * @throws IOException
     */
    public static byte[] bigFile2Byte(String filepath) throws IOException {

        FileChannel channel = null;
        try {
            channel = new RandomAccessFile(filepath, "r").getChannel();
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                    channel.size()).load();
            byte[] result = new byte[(int) channel.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * nio将byte数组写到文件
     *
     * @param by, filepath
     * @throws IOException
     */
    public static void byte2File(byte[] by, String filepath) throws IOException {
        FileChannel channel = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(by);
            channel = new FileOutputStream(filepath).getChannel();
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将inputStream转为byte数组
     *
     * @param input
     * @return byte[]
     * @throws IOException
     */
    public static byte[] input2Byte(InputStream input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] by = new byte[1024 * 4];
        int n = 0;

        while ((n = input.read(by)) != -1) {
            out.write(by, 0, n);
        }

        byte[] res = out.toByteArray();

        out.flush();
        out.close();

        return res;
    }

    /**
     * nio将inputStream写到文件
     *
     * @param input, filepath
     * @throws IOException
     */
    public static void input2File(InputStream input, String filepath) throws IOException {
        byte2File(input2Byte(input), filepath);
    }

    /**
     * byte数组转String
     *
     * @param by
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String byte2String(byte[] by, String... charset) throws UnsupportedEncodingException {
        if (charset.length > 0) {

            return new String(by, charset[0]);
        } else {

            return new String(by, "UTF-8");
        }
    }

}
