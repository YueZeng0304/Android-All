/*
 * This is the source code of DMPLayer for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry, 2015.
 */
package com.dmplayer.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.dmplayer.models.SongDetail;
import com.dmplayer.phonemidea.PhoneMediaControl;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MusicPreferance {//存储

    public static ArrayList<SongDetail> playlist = new ArrayList<SongDetail>();
    public static ArrayList<SongDetail> shuffledPlaylist = new ArrayList<SongDetail>();
    public static SongDetail playingSongDetail;

    public static SharedPreferences getPreferanse(Context context) {
        return context.getSharedPreferences("DMPlayer", Activity.MODE_PRIVATE);
    }

    public static void saveLastSong(Context context, SongDetail mDetail) {//保存上一首歌
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        Gson mGson = new Gson();
        String lastplaySong = mGson.toJson(mDetail);
        editor.putString("lastplaysong", lastplaySong);
        editor.commit();
    }

    public static SongDetail getLastSong(Context context) {//得到上一首歌
        if (playingSongDetail == null) {
            SharedPreferences mSharedPreferences = getPreferanse(context);
            String lastplaySong = mSharedPreferences.getString("lastplaysong", "");
            Gson mGson = new Gson();
            playingSongDetail = mGson.fromJson(lastplaySong, SongDetail.class);
        }
        return playingSongDetail;
    }

    public static void saveLastSongListType(Context context, int type) {//保存上一首歌 列表形式
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        editor.putInt("songlisttype", type);
        editor.commit();
    }

    public static int getLastSongListType(Context context) {
        SharedPreferences mSharedPreferences = getPreferanse(context);
        return mSharedPreferences.getInt("songlisttype", 0);
    }

    public static void saveLastAlbID(Context context, int id) {//保存上一首歌专辑ID
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        editor.putInt("lastalbid", id);
        editor.commit();
    }

    public static int getLastAlbID(Context context) {
        SharedPreferences mSharedPreferences = getPreferanse(context);
        return mSharedPreferences.getInt("lastalbid", 0);
    }

    public static void saveLastPosition(Context context, int positon) {//保存上一首歌位置
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        editor.putInt("lastposition", positon);
        editor.commit();
    }

    public static int getLastPosition(Context context) {
        SharedPreferences mSharedPreferences = getPreferanse(context);
        return mSharedPreferences.getInt("lastposition", 0);
    }

    public static void saveLastPath(Context context, String path) {//保存上一首歌路径
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        editor.putString("path", path);
        editor.commit();
    }

    public static String getLastPath(Context context) {
        SharedPreferences mSharedPreferences = getPreferanse(context);
        return mSharedPreferences.getString("path", "");
    }


    public static ArrayList<SongDetail> getPlaylist(Context context) {//歌曲详情列表
        if (playlist == null || playlist.isEmpty()) {
            int type = getLastSongListType(context);
            int id = getLastAlbID(context);
            String path = getLastPath(context);
            MediaController.getInstance().type = type;//类型
            MediaController.getInstance().id = id;//id
            MediaController.getInstance().currentPlaylistNum = getLastPosition(context);//当前播放列表
            MediaController.getInstance().path = path;//路径
            playlist = PhoneMediaControl.getInstance().getList(context, id, PhoneMediaControl.SonLoadFor.values()[type], path);
        }
        return playlist;
    }

    public static ArrayList<SongDetail> getPlaylist(Context context, String path) {
        MediaController.getInstance().type = PhoneMediaControl.SonLoadFor.Musicintent.ordinal();
        MediaController.getInstance().id = -1;
        MediaController.getInstance().currentPlaylistNum = 0;
        MediaController.getInstance().path = path;
        playlist = PhoneMediaControl.getInstance().getList(context, -1, PhoneMediaControl.SonLoadFor.Musicintent, path);
        if (playlist != null && !playlist.isEmpty())
            playingSongDetail = playlist.get(0);
        return playlist;
    }

    public static void setRepeat(Context context, int isOn) {//重复
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        editor.putInt("repeat", isOn);
        editor.commit();
    }

    public static int getRepeat(Context context) {
        SharedPreferences mSharedPreferences = getPreferanse(context);
        return mSharedPreferences.getInt("repeat", 0);
    }


    public static void setShuffel(Context context, boolean isOn) {//设置随机模式
        SharedPreferences.Editor editor = getPreferanse(context).edit();
        editor.putBoolean("shuffel", isOn);
        editor.commit();
    }

    public static boolean getShuffel(Context context) {
        SharedPreferences mSharedPreferences = getPreferanse(context);
        return mSharedPreferences.getBoolean("shuffel", false);
    }

}