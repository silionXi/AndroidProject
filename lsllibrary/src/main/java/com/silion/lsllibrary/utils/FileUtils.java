package com.silion.lsllibrary.utils;

import android.webkit.MimeTypeMap;

/**
 * Created by silion on 2017/6/26.
 */

public class FileUtils {
    /**
     * 根据传进来的本地文件路径或者Url，获取MimeType(判断文件是什么类型video/image)
     *
     * @param path 本地文件路径或者Url
     * @return Mime Type
     */
    public static String getMimeTypeFromPath(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return getMimeTypeFromExtension(extension);
    }

    /**
     * 根据传进来的文件后缀名，获取MimeType(判断文件是什么类型video/image)
     *
     * @param extension 文件后缀名
     * @return Mime Type
     */
    public static String getMimeTypeFromExtension(String extension) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
