/*
 * This is the source code of DMPLayer for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry, 2015.
 */
package com.dmplayer.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.dmplayer.ApplicationDMPlayer;
import com.dmplayer.models.SongDetail;

public class FavoritePlayTableHelper {//收藏 数据库操作类

    public static final String TABLENAME = "ResentPlay";//最近播放

    public static final String ID = "_id";
    public static final String ALBUM_ID = "album_id";//专辑id
    public static final String ARTIST = "artist";//艺术家
    public static final String TITLE = "title";//标题
    public static final String DISPLAY_NAME = "display_name";//显示名字
    public static final String DURATION = "duration";//时长
    public static final String PATH = "path";//路径
    public static final String AUDIOPROGRESS = "audioProgress";//进度条
    public static final String AUDIOPROGRESSSEC = "audioProgressSec";//进度条二
    public static final String LastPlayTime = "lastplaytime";//上次播放
    public static final String ISFAVORITE = "isfavorite";//是否收藏

    private static DMPLayerDBHelper dbHelper = null;
    private static FavoritePlayTableHelper mInstance;
    private SQLiteDatabase sampleDB;


    public static synchronized FavoritePlayTableHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FavoritePlayTableHelper(context);
        }
        return mInstance;
    }

    public Context context;

    public FavoritePlayTableHelper(Context context_) {
        this.context = context_;
        if (dbHelper == null) {
            dbHelper = ((ApplicationDMPlayer) context.getApplicationContext()).DB_HELPER;
        }
    }

    public void inserSong(SongDetail songDetail, int isFav) {//插入歌曲
        try {

            sampleDB = dbHelper.getDB();
            sampleDB.beginTransaction();

            String sql = "Insert or Replace into " + TABLENAME + " values(?,?,?,?,?,?,?,?,?,?,?);";
            SQLiteStatement insert = sampleDB.compileStatement(sql);

            try {
                if (songDetail != null) {
                    insert.clearBindings();
                    insert.bindLong(1, songDetail.getId());
                    insert.bindLong(2, songDetail.getAlbum_id());
                    insert.bindString(3, songDetail.getArtist());
                    insert.bindString(4, songDetail.getTitle());
                    insert.bindString(5, songDetail.getDisplay_name());
                    insert.bindString(6, songDetail.getDuration());
                    insert.bindString(7, songDetail.getPath());
                    insert.bindString(8, songDetail.audioProgress + "");
                    insert.bindString(9, songDetail.audioProgressSec + "");
                    insert.bindString(10, System.currentTimeMillis() + "");
                    insert.bindLong(11, isFav);

                    insert.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sampleDB.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("XML:", e.toString());
        } finally {
            sampleDB.endTransaction();
        }
    }

    private void closeCurcor(Cursor cursor) {//关闭光标
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

    public Cursor getFavoriteSongList() {//得到收藏列表
        Cursor mCursor = null;
        try {
            String sqlQuery = "Select * from " + TABLENAME + " where " + ISFAVORITE + "=1";
            sampleDB = dbHelper.getDB();
            mCursor = sampleDB.rawQuery(sqlQuery, null);
        } catch (Exception e) {
            closeCurcor(mCursor);
            e.printStackTrace();
        }
        return mCursor;
    }

    public boolean getIsFavorite(SongDetail mDetail) {//判断是否收藏
        Cursor mCursor = null;
        try {
            String sqlQuery = "Select * from " + TABLENAME + " where " + ID + "=" + mDetail.getId() + " and " + ISFAVORITE + "=1";
            sampleDB = dbHelper.getDB();
            mCursor = sampleDB.rawQuery(sqlQuery, null);
            if (mCursor != null && mCursor.getCount() >= 1) {
                closeCurcor(mCursor);
                return true;
            }
        } catch (Exception e) {
            closeCurcor(mCursor);
            e.printStackTrace();
        }
        return false;
    }
}
