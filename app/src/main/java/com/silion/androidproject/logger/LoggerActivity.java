package com.silion.androidproject.logger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.silion.androidproject.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * http://blog.csdn.net/QH_JAVA/article/details/46670361
 * 使用Logger, FileHandler, Formatter输出日志保存到文件
 */
public class LoggerActivity extends Activity {
    private static final String TAG = "LoggerActivity";
    private Logger mLogger;
    private AtomicInteger mId = new AtomicInteger(0);
    private FileHandler mFileHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);
        initLogger();
    }

    private void initLogger() {
        final int LOG_FILE_LIMIT = 1024 * 1024;
        final int LOG_FILE_MAX_COUNT = 4;

        try {
            // 通过name获取Logger,同一个name的Logger只创建一个
            mLogger = Logger.getLogger(TAG);

            StringBuilder logFilePath = new StringBuilder();
            logFilePath.append(Environment.getExternalStorageDirectory().getPath());
            logFilePath.append(File.separator);
            logFilePath.append(getPackageName());
            logFilePath.append(File.separator);
            logFilePath.append("log");

            // 如果保存log文件的目录不存在则创建
            File logDirectory = new File(logFilePath.toString());
            if (!logDirectory.exists()) {
                logDirectory.mkdirs();
            }

            logFilePath.append(File.separator);
            logFilePath.append(TAG);
            logFilePath.append("%g%u.log"); // 在.log前自动增加文件序号

            /*
            Handler 对象从 Logger 中获取日志信息，并将这些信息导出。
            例如，它可将这些信息写入控制台或文件中，也可以将这些信息发送到网络日志服务中，或将其转发到操作系统日志中。
            每个日志记录 Handler 都有关联的 Formatter。Formatter 接受 LogRecord，并将它转换为一个字符串
             */
            mFileHandler = new FileHandler(logFilePath.toString(), LOG_FILE_LIMIT, LOG_FILE_MAX_COUNT, true);
            mFileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getLevel() + "/" + TAG + ": " + record.getMessage();
                }
            });
            mLogger.addHandler(mFileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        // 使用Logger输出log
        mLogger.info("这是使用Logger输出的第 " + mId.incrementAndGet() + "条log" + "\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 内存泄漏
        mLogger.removeHandler(mFileHandler);
    }
}
