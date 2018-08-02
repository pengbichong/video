package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.dao.UserInfoMapper;
import org.pbc.video.model.UserInfo;
import org.pbc.video.dao.VideoInfoMapper;
import org.pbc.video.model.VideoInfo;
import org.pbc.video.service.IVideoInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Service
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements IVideoInfoService {

    @Autowired
    private VideoInfoMapper videoInfoMapper;
    @Autowired
    private  UserInfoMapper userInfoMapper;

    //查询首页展示视频
    @Override
    public List<VideoInfo> topMuduleVideo(int size) {

        List<VideoInfo> List = videoInfoMapper.selectPage(

                new Page<VideoInfo>(1,size),
                new EntityWrapper<VideoInfo>().eq("state","1")
                        .orderBy("review_date",false)
        );
        return List;
    }

    @Override
    public VideoInfo getVideoInfoById(int id) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setvId(id);
        videoInfo.setState("1");
        VideoInfo VideoInfo = videoInfoMapper.selectById(videoInfo.getvId());

        return VideoInfo;
    }

    @Override
    public boolean addCilkTimes(int vid) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setvId(vid);

        VideoInfo res = videoInfoMapper.selectOne(videoInfo);
        res.setClickTime(res.getClickTime()+1);
        int rs2 = videoInfoMapper.updateById(res);
        if (rs2>0){
            return true;
        }

        return false;
    }

    //分页和条件查询视频
    @Override
    public JSONObject getVideoInfoByTid(int tid, int page, int size,String searchContent,String time,String order_by) {
        System.out.println(page+"---"+size+"---"+searchContent);
        if ((searchContent.equals("null")||searchContent.equals(""))&&(time.equals("null")||time.equals("0"))&&(order_by.equals("null")||order_by.equals("0"))) {
            List<VideoInfo> List = videoInfoMapper.selectPage(
                    //设置页码、每页数量
                    new Page<VideoInfo>(page, size),
                    //根据类别查询视频
                    new EntityWrapper<VideoInfo>().eq("t_id", tid).eq("state","1")
                            .orderBy("upload_date", false)
            );
            int count = videoInfoMapper.selectList(
                    new EntityWrapper<VideoInfo>().eq("t_id",tid).eq("state","1")
            ).size();
            JSONObject jsonObject= new JSONObject();
            jsonObject.append("count",count);
            jsonObject.append("list",List);
            return jsonObject;

        }
        if (!searchContent.equals("null")&&!searchContent.equals("")&&time.equals("0")&&order_by.equals("0")){
            List<VideoInfo> List = new ArrayList<VideoInfo>();

            if (tid==0){
                List = videoInfoMapper.selectPage(
                        new Page<VideoInfo>(page, size),
                        new EntityWrapper<VideoInfo>().like("title",searchContent).eq("state","1").orderBy("upload_date", false)
                );

                int count = videoInfoMapper.selectList(
                        new EntityWrapper<VideoInfo>().like("title",searchContent).eq("state","1")
                ).size();
                JSONObject jsonObject= new JSONObject();
                jsonObject.append("count",count);
                jsonObject.append("list",List);

                return jsonObject;
            }else {
                List = videoInfoMapper.selectPage(
                        new Page<VideoInfo>(page, size),
                        new EntityWrapper<VideoInfo>().eq("t_id", tid).eq("state","1").like("title",searchContent).orderBy("upload_date", false)
                );

                int count = videoInfoMapper.selectList(
                        new EntityWrapper<VideoInfo>().eq("t_id",tid).eq("state","1").like("title",searchContent)
                ).size();
                JSONObject jsonObject= new JSONObject();
                jsonObject.append("count",count);
                jsonObject.append("list",List);

                return jsonObject;
            }


        }
        if (!searchContent.equals("null")&&!searchContent.equals("")&&!time.equals("null")&&!order_by.equals("null")){
            String order = "";
            if (order_by.equals("0")){
                order = "upload_date";
            }else {
                order = "clickTime";
            }
            int bt1 = 0;
            int bt2 = 0;
            if (time.equals("0")){
                bt1 = 0;
                bt2 = 999999999;
            }else if (time.equals("1")){
                bt1 = 0;
                bt2 = 600;
            }else if (time.equals("2")){
                bt1 = 600;
                bt2 = 1800;
            }else if (time.equals("3")){
                bt1 = 1800;
                bt2 = 3600;
            }else if (time.equals("4")){
                bt1 = 3600;
                bt2 = 999999999;
            }
            List<VideoInfo> List = new ArrayList<VideoInfo>();
            if (tid==0) {
                List = videoInfoMapper.selectPage(
                        new Page<VideoInfo>(page, size),
                        new EntityWrapper<VideoInfo>().like("title", searchContent).eq("state","1").gt("v_time", bt1).lt("v_time", bt2).orderBy(order, false)
                );

                int count = videoInfoMapper.selectList(
                        new EntityWrapper<VideoInfo>().like("title", searchContent).eq("state","1")
                ).size();
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("count", count);
                jsonObject.append("list", List);

                return jsonObject;
            }else {
                List = videoInfoMapper.selectPage(
                        new Page<VideoInfo>(page, size),
                        new EntityWrapper<VideoInfo>().eq("t_id", tid).eq("state","1").like("title", searchContent).gt("v_time", bt1).lt("v_time", bt2).orderBy(order, false)
                );

                int count = videoInfoMapper.selectList(
                        new EntityWrapper<VideoInfo>().eq("t_id", tid).eq("state","1").like("title", searchContent)
                ).size();
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("count", count);
                jsonObject.append("list", List);

                return jsonObject;
            }


        }
        if ((searchContent.equals("null")||searchContent.equals(""))&&!time.equals("null")&&!order_by.equals("null")){
            String order = "";
            if (order_by.equals("0")){
                order = "upload_date";
            }else {
                order = "clickTime";
            }
            int bt1 = 0;
            int bt2 = 0;
            if (time.equals("0")){
                bt1 = 0;
                bt2 = 999999999;
            }else if (time.equals("1")){
                bt1 = 0;
                bt2 = 600;
            }else if (time.equals("2")){
                bt1 = 600;
                bt2 = 1800;
            }else if (time.equals("3")){
                bt1 = 1800;
                bt2 = 3600;
            }else if (time.equals("4")){
                bt1 = 3600;
                bt2 = 999999999;
            }
            List<VideoInfo> List = new ArrayList<VideoInfo>();
            if (tid==0){
                List = videoInfoMapper.selectPage(
                        new Page<VideoInfo>(page, size),
                        new EntityWrapper<VideoInfo>().gt("v_time",bt1).lt("v_time",bt2).eq("state","1").orderBy(order, false)
                );

                int count = videoInfoMapper.selectList(
                        new EntityWrapper<VideoInfo>().gt("v_time",bt1).lt("v_time",bt2).eq("state","1")
                ).size();
                JSONObject jsonObject= new JSONObject();
                jsonObject.append("count",count);
                jsonObject.append("list",List);

                return jsonObject;
            }else {
                List = videoInfoMapper.selectPage(
                        new Page<VideoInfo>(page, size),
                        new EntityWrapper<VideoInfo>().eq("t_id", tid).gt("v_time",bt1).lt("v_time",bt2).eq("state","1").orderBy(order, false)
                );

                int count = videoInfoMapper.selectList(
                        new EntityWrapper<VideoInfo>().eq("t_id",tid).gt("v_time",bt1).lt("v_time",bt2).eq("state","1")
                ).size();
                JSONObject jsonObject= new JSONObject();
                jsonObject.append("count",count);
                jsonObject.append("list",List);

                return jsonObject;
            }



        }


        return null;


    }

    @Override
    public JSONObject saveVideoIfon(String des, String tag, String videoType, String imgpath, String videopath, String title, int userid,int time) {
        UserInfo entity = new UserInfo();
        entity.setUserId(userid);

        UserInfo userInfo = userInfoMapper.selectOne(entity);


        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setState("0");
        videoInfo.settId(Integer.parseInt(videoType));
        videoInfo.setDetail(des);
        videoInfo.setAuthorId(userid);
        videoInfo.setImgPath(imgpath);
        videoInfo.setTag(tag);
        videoInfo.setTitle(title);
        videoInfo.setUploadDate(new Date());
        videoInfo.setVideoPath(videopath);
        videoInfo.setvTime(time);
        videoInfo.setAuthorName(userInfo.getNickName());

        videoInfoMapper.insert(videoInfo);

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("videoInfo",videoInfo);

        return jsonObject;
    }
}
