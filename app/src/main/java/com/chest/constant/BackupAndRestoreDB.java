package com.chest.constant;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.chest.database.DatabaseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


/**
 * Created by TrungDK on 1/3/2018.
 */

public class BackupAndRestoreDB {

    private static final String TAG = BackupAndRestoreDB.class.getSimpleName();

    private Context context;
    private String packageName;
    private String databaseName;
    private File databaseDirectory;
    private File importFile;
    private File databaseDirectoryDatabase;

    public BackupAndRestoreDB(Context context) {
        this.context = context;
        packageName = context.getPackageName();
        databaseName = DatabaseHandler.DATABASE_NAME;
        databaseDirectory = new File(Environment.getExternalStorageDirectory(), ".Chest");
        importFile = new File(databaseDirectory, databaseName);
        databaseDirectoryDatabase = new File(Environment.getDataDirectory() +
                "/data/" + packageName +
                "/databases/" + databaseName);
    }

    public boolean exportDb() {
        if (!SdIsPresent()) return false;

        File file = new File(databaseDirectory, databaseName);

        if (!databaseDirectory.exists()) {
            databaseDirectory.mkdirs();
        }

        try {
            file.createNewFile();
            copyFile(databaseDirectoryDatabase, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreDb() {
        if (!SdIsPresent()) return false;

        if (checkDbExist()) return true;

        if (!importFile.exists()) {
            Log.d(TAG, "File does not exist");
            return false;
        }

        try {
            databaseDirectoryDatabase.createNewFile();
            copyFile(importFile, databaseDirectoryDatabase);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkDbExist() {
        File dbFile = context.getDatabasePath(databaseName);
        return dbFile.exists();
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static boolean SdIsPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
