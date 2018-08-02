package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
@Api(value = "FollowController",description = "关注接口")
@Controller
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private IFollowService followService;

    @ResponseBody
    @ApiOperation(value = "关注和取关接口",notes = "关注接口")
    @RequestMapping(value = "/saveFollow",method =RequestMethod.POST)
    public JSONObject saveFollow(@RequestParam("vid") String vid, @RequestParam("uid") String userid) {


        boolean res = followService.save(Integer.parseInt(vid),Integer.parseInt(userid));
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("status",1);
            return  jsonObject;
        }else {
            jsonObject.append("status",0);
            return  jsonObject;
        }


    }

    @ResponseBody
    @ApiOperation(value = "我的关注列表--取关接口",notes = "关注接口")
    @RequestMapping(value = "/deleFollow",method =RequestMethod.POST)
    public JSONObject deleFollow(@RequestParam("authorid") String authorid, @RequestParam("uid") String userid) {


        boolean res = followService.daleFollow(Integer.parseInt(authorid),Integer.parseInt(userid));
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("status",1);
            return  jsonObject;
        }else {
            jsonObject.append("status",0);
            return  jsonObject;
        }


    }


    @ResponseBody
    @ApiOperation(value = "查询是否关注接口",notes = "关注接口")
    @RequestMapping(value = "/queryFollowYes",method =RequestMethod.GET)
    public JSONObject queryFollowYes(@RequestParam("vid") String vid, @RequestParam("uid") String userid) {


        boolean res = followService.queryFollowYes(Integer.parseInt(vid),Integer.parseInt(userid));
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("status",1);
            return  jsonObject;
        }else {
            jsonObject.append("status",0);
            return  jsonObject;
        }


    }

    @ResponseBody
    @ApiOperation(value = "查询我的收藏接口",notes = "收藏接口")
    @RequestMapping(value = "/queryMyFollow",method =RequestMethod.GET)
    public JSONObject queryMyCollect(@RequestParam("uid") String userid,@RequestParam("size") String size,@RequestParam("page") String page) {


        JSONObject res = followService.queryMyFollow(Integer.parseInt(userid),Integer.parseInt(page),Integer.parseInt(size));

        if (!res.isEmpty()){
            res.append("status","1");
            return  res;

        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.append("status","2");
            return jsonObject1;
        }
    }

}

