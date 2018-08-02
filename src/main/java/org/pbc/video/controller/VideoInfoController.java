package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.model.VideoInfo;
import org.pbc.video.service.IVideoInfoService;
import org.pbc.video.util.ConvertVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */

@Api(value = "VideoInfoController",description = "视频信息接口")
@Controller
@RequestMapping("/videoIfo")
public class VideoInfoController {

    @Autowired
    private IVideoInfoService videoInfoService;


    @ResponseBody
    @ApiOperation(value = "查询首页视频信息",notes = "查询接口")
    @RequestMapping(value = "/TopModuleVideo",method =RequestMethod.GET)
    public List<VideoInfo> getTopModuleVideo(@RequestParam("size") String size){
        int int_size = Integer.parseInt(size);
        List<VideoInfo> list = videoInfoService.topMuduleVideo(int_size);

        return list;
    }

    @ResponseBody
    @ApiOperation(value = "根据ID查询视频信息",notes = "查询接口")
    @RequestMapping(value = "/getVideoById",method =RequestMethod.GET)
    public VideoInfo getVideoInfoById(@RequestParam("vid") String vid){



        int id = Integer.parseInt(vid);
        System.out.println(id);
        VideoInfo VideoInfo = videoInfoService.getVideoInfoById(id);
        return VideoInfo;
    }

    @ResponseBody
    @ApiOperation(value = "根据ID查询视频信息",notes = "查询接口")
    @RequestMapping(value = "/addCilkTimes",method =RequestMethod.POST)
    public JSONObject addCilkTimes(@RequestParam("vid") String vid){
        int id = Integer.parseInt(vid);
        System.out.println(id);
        boolean res = videoInfoService.addCilkTimes(id);
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("status",1);
            return jsonObject;
        }else {
            jsonObject.append("status",0);
            return jsonObject;
        }
    }


    @ResponseBody
    @ApiOperation(value = "根据类型id分页查询视频信息",notes = "查询接口")
    @RequestMapping(value = "/getVideoByTid",method =RequestMethod.GET)//&searchContent=雷军&time=1&order_by=0
    public JSONObject getVideoInfoByTid(@RequestParam("tid") String tid, @RequestParam("page") String page,
                                        @RequestParam("size") String size,
                                        @RequestParam("searchContent") String searchContent,
                                        @RequestParam("time") String time,
                                        @RequestParam("order_by") String order_by
    ){
        int id = 0;
        if (tid!=""&&tid!=null){
            id = Integer.parseInt(tid);
        }else {
            id = 0;
            System.out.println("id:"+id);
        }
        System.out.println(id);
        int pageNum = Integer.parseInt(page);
        int sizeNum = Integer.parseInt(size);
        JSONObject jsonObject = videoInfoService.getVideoInfoByTid(id,pageNum,sizeNum,searchContent,time,order_by);
        if (!jsonObject.isEmpty()) {
            jsonObject.append("status", "1");
            return jsonObject;
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.append("status","2");
            return jsonObject1;
        }

    }

    @ResponseBody
    @ApiOperation(value = "上传视频视频",notes = "上传接口")
    @RequestMapping(value = "/uploadVideo",method =RequestMethod.POST,produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JSONObject uploadVideo(HttpSession session, @RequestParam("mf")MultipartFile file,@RequestParam("videoType") String videoType, @RequestParam("des") String des, @RequestParam("tag") String tag, @RequestParam("title") String title){

        String name = file.getOriginalFilename();
        System.out.println(file.getSize());
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        System.out.println("后缀："+suffix);
        String contentpath = this.getClass().getResource("/").getPath();
        contentpath = contentpath.substring(1,contentpath.indexOf("/WEB-INF/classes/"));

        if(session.getAttribute("userid")==null){
            return null;
        }else {
            // 保存到临时文件夹
            String date = String.valueOf(new Date().getTime());
            String path = "/" + date + "." + suffix;
            String newpath = "/" + date + ".jpg";
            System.out.println(contentpath + path);

            File savefile = new File(contentpath +"/web/videoFile"+ path);

            try {
                file.transferTo(savefile);
                ConvertVideo convertVideo = new ConvertVideo();
                int res = convertVideo.otherToMp4(contentpath +"/web/videoFile"+ path,contentpath +"/web/videoFile/img"+ newpath);
                JSONObject json = videoInfoService.saveVideoIfon(des,tag,videoType,"/videoFile/img"+ newpath,"videoFile"+ path,title,Integer.parseInt(session.getAttribute("userid").toString()),res);
               System.out.println("1");
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.append("res","/videoFile"+ path);

            return jsonObject;

        }







    }
}

