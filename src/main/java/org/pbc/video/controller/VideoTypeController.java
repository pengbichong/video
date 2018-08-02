package org.pbc.video.controller;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.model.VideoType;
import org.pbc.video.service.IVideoTypeService;
import org.pbc.video.service.impl.VideoTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Api(value = "VideoTypeController",description = "视频分类接口")
@Controller
@RequestMapping("/videoType")
public class VideoTypeController {
    @Autowired
    private IVideoTypeService videoTypeService;

    @ResponseBody
    @ApiOperation(value = "查询所有视频分类",notes = "查询")
    @RequestMapping(value = "/queryVideoType",method =RequestMethod.GET)
    public List<VideoType> queryVideoType(){

       List<VideoType> list = videoTypeService.queryAllVideoType();

        return  list;
    }



}

