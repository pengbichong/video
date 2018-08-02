package org.pbc.video.service;

import com.xiaoleilu.hutool.json.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import org.pbc.video.model.VideoInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
public interface IVideoInfoService extends IService<VideoInfo> {

    public List<VideoInfo> topMuduleVideo(int size);//查询参数查询最新的视频

    public  VideoInfo getVideoInfoById(int id);//根据id查询视频信息

    public boolean addCilkTimes(int vid);//点击次数加1

    public JSONObject getVideoInfoByTid(int tid, int page, int size,String searchContent,String time,String order_by);//根据类型tid查询视频信息

    public JSONObject saveVideoIfon(String des,String tag,String videoType,String imgpath,String videopath,String title,int userid,int time);
}
