package com.samsung.slibrary.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by silion on 2016/8/31.
 */
public class StreamUtils {

    private static final String _TAG = StreamUtils.class.getSimpleName();

    /**
     * 不允许创建实例
     */
    private StreamUtils() {
        throw new AssertionError();
    }

    /**
     * 解压zip并保存到指定的目录
     *
     * @param is                  zip输入流
     * @param parentDirectoryPath 保存的目录
     * @return 解压成功返回true
     */
    public static boolean unZip(InputStream is, String parentDirectoryPath) {
        if (is != null && parentDirectoryPath != null) {
            try (ZipInputStream dataZipInputStream = new ZipInputStream(new BufferedInputStream(is))) {
                ZipEntry zipEntry;
                byte[] buffer = new byte[1024];

                File parentDirectory = new File(parentDirectoryPath);
                if (!parentDirectory.exists()) {
                    parentDirectory.mkdirs();
                }

                while ((zipEntry = dataZipInputStream.getNextEntry()) != null) {
                    String filePath = parentDirectoryPath + File.separatorChar + zipEntry.getName();

                    File file = new File(filePath);
                    if (!file.getCanonicalPath().startsWith(parentDirectoryPath)) {
                        // 防止目录遍历攻击
                        throw new IOException("Directory traversal attack!");
                    }

                    if (zipEntry.isDirectory()) {
                        File directoryFile = new File(filePath);
                        directoryFile.mkdirs();
                        continue;
                    }

                    try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                        int count;
                        while ((count = dataZipInputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, count);
                        }
                    } catch (Exception e) {
                        Log.e(_TAG, e.getMessage(), e);
                        return false;
                    }

                    dataZipInputStream.closeEntry();
                }
            } catch (IOException e) {
                Log.e(_TAG, e.getMessage(), e);
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 从文件中读取Object
     *
     * @param filePath 文件路径
     * @return 返回Object或null
     */
    public static Object file2Object(String filePath) {
        File file = new File(filePath);
        return file2Object(file);
    }

    /**
     * 从文件中读取Object
     *
     * @param file 文件
     * @return 返回Object或null
     */
    public static Object file2Object(File file) {
        if (!file.exists() || !file.isFile()) {
            Log.e(_TAG, file.getName() + " is not exist or not a file.");
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        } catch (Exception e) {
            Log.e(_TAG, e.getMessage(), e);
            return null;
        }
    }
}
