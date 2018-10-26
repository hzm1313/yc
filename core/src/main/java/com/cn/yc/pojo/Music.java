package com.cn.yc.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: jimmy
 * Date: 13-8-1
 * Time: ����5:58
 * To change this template use File | Settings | File Templates.
 */
public class Music {
    private String MusicUrl;
    private String HQMusicUrl;

    private String Title;
    private String Description;

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
