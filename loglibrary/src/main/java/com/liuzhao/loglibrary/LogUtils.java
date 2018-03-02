package com.liuzhao.loglibrary;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * LOG 工具类，记录app操作log、飞行航线log，写入文件
 *
 */
public class LogUtils {
    /**
     * server token string
     */
    public static final String SERVER_TOKEN_HEADER = "token";

    public final static String LOG_ROOT_PATHE = Environment.getExternalStorageDirectory()
            + File.separator + "tanven" + File.separator + "log"
            + File.separator;
    public final static String APP_LOG_PATHE = "app" + File.separator;
    public final static String ROUTE_LOG_PATHE = "route" + File.separator;

    public enum LogLevel {
        VERBOSE(Log.VERBOSE),
        DEBUG(Log.DEBUG),
        INFO(Log.INFO),
        WARN(Log.WARN),
        ERROR(Log.ERROR),
        ASSERT(Log.ASSERT);

        private int mValue;
        LogLevel(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    private static final SimpleDateFormat LOG_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static ExecutorService sLogExecutor = Executors.newSingleThreadExecutor();

    private static boolean sLogEnable = true;
    private static LogLevel sLogLevel = LogLevel.DEBUG;
    private static LogFilesUtils sLogFilesApp,sLogFilesRoute;

    /**
     * 设置Log开关
     *
     * @param enable 开关
     */
    public static void setEnable(boolean enable) {
        sLogEnable = enable;
    }

    /**
     * 设置Log级别
     *
     * @param level 枚举VERBOSE,DEBUG,INFO..
     */
    public static void setLogLevel(LogLevel level) {
        sLogLevel = level;
    }

    /**
     * 设置航线写入log的文件夹
     *
     * @param dirPath 文件夹地址
     */
    public static void setRouteLogDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        sLogFilesRoute = new LogFilesUtils(dirPath);
    }


    /**
     * 设置航线写入log的文件夹
     *
     * @param dirPath 文件夹地址
     * @param appendFileName 文件名附加SN/UserID TIME+appendFileName
     * @param logFileCount log文件数量
     * @param logFileSize 单个log文件大小MB，-1不限制
     */
    public static void setRouteLogDir(String dirPath ,String appendFileName,int logFileCount,int logFileSize) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        sLogFilesRoute = new LogFilesUtils(dirPath,appendFileName,logFileCount,logFileSize);
    }

    public static void setAppLogDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        sLogFilesApp = new LogFilesUtils(dirPath);
    }

    /**
     * 设置APP写入log的文件夹
     *
     * @param dirPath 文件夹地址
     * @param appendFileName 文件名附加SN/UserID TIME+appendFileName
     * @param logFileCount log文件数量
     * @param logFileSize 单个log文件大小MB，-1不限制
     */
    public static void setAppLogDir(String dirPath,String appendFileName,int logFileCount,int logFileSize) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        sLogFilesApp = new LogFilesUtils(dirPath,appendFileName,logFileCount,logFileSize);
    }


    /**
     * app log for debug
     *
     * @param message log message
     * @param tag     tag
     * @see Log#d(String, String)
     */
    public static void d(String tag, String message) {
        if (sLogEnable) {
            Log.d(tag, message);
            writeToFileIfNeeded(tag, message, LogLevel.DEBUG,sLogFilesApp);
        }
    }
    /**
     * Route log for debug
     *
     * @param message log message
     * @param tag     tag
     * @see Log#d(String, String)
     */
    public static void dRoute(String tag, String message) {
        if (sLogEnable) {
            Log.d(tag, message);
            writeToFileIfNeeded(tag, message, LogLevel.DEBUG,sLogFilesRoute);
        }
    }


    /**
     * app log for warning
     *
     * @param msg log message
     * @param tag     tag
     * @see Log#w(String, String)
     */
    public static void w(String tag, String msg) {
        if (sLogEnable) {
            Log.w(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.WARN, sLogFilesApp);
        }
    }
    /**
     * Route log for warning
     *
     * @param msg log message
     * @param tag     tag
     * @see Log#w(String, String)
     */
    public static void wRoute(String tag, String msg) {
        if (sLogEnable) {
            Log.w(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.WARN, sLogFilesRoute);
        }
    }

    /**
     * app log for error
     *
     * @param msg message
     * @param tag     tag
     * @see Log#i(String, String)
     */
    public static void e(String tag, String msg) {
        if (sLogEnable) {
            Log.e(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.ERROR, sLogFilesApp);
        }
    }
    /**
     * Route log for error
     *
     * @param msg message
     * @param tag     tag
     * @see Log#i(String, String)
     */
    public static void eRoute(String tag, String msg) {
        if (sLogEnable) {
            Log.e(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.ERROR, sLogFilesRoute);
        }
    }

    /**
     * App log for information
     *
     * @param msg message
     * @param tag     tag
     * @see Log#i(String, String)
     */
    public static void i(String tag, String msg) {
        if (sLogEnable) {
            Log.i(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.INFO, sLogFilesApp);
        }
    }
    /**
     * Route log for information
     *
     * @param msg message
     * @param tag     tag
     * @see Log#i(String, String)
     */
    public static void iRoute(String tag, String msg) {
        if (sLogEnable) {
            Log.i(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.INFO, sLogFilesRoute);
        }
    }

    /**
     * APP log for verbos
     *
     * @param msg log message
     * @param tag     tag
     * @see Log#v(String, String)
     */
    public static void v(String tag, String msg) {
        if (sLogEnable) {
            Log.v(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.VERBOSE,sLogFilesApp);
        }
    }
    /**
     * Route log for verbos
     *
     * @param msg log message
     * @param tag     tag
     * @see Log#v(String, String)
     */
    public static void vRoute(String tag, String msg) {
        if (sLogEnable) {
            Log.v(tag, msg);
            writeToFileIfNeeded(tag, msg, LogLevel.VERBOSE,sLogFilesRoute);
        }
    }

    private static void writeToFileIfNeeded(final String tag, final String msg, LogLevel logLevel, final LogFilesUtils logFilesUtils) {
        if (logLevel.getValue() < sLogLevel.getValue() || logFilesUtils == null) {
            return;
        }
        sLogExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String logMsg = formatLog(tag, msg);
                logFilesUtils.writeLogToFile(logMsg);
            }
        });
    }

    private static String formatLog(String tag, String msg) {
        return String.format("%s pid=%d %s: %s\n", LOG_DATE_TIME_FORMAT.format(new Date(
                System.currentTimeMillis())), android.os.Process.myPid(), tag, msg);
    }


}
