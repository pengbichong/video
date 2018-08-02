package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.service.ICollectService;
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
@Api(value = "CollectController",description = "收藏接口")
@Controller
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private ICollectService collectService;

    @ResponseBody
    @ApiOperation(value = "保存收藏接口",notes = "收藏接口")
    @RequestMapping(value = "/saveCollect",method =RequestMethod.POST)
    public JSONObject saveCollect(@RequestParam("vid") String vid, @RequestParam("uid") String userid) {


        boolean res = collectService.saveCollect(Integer.parseInt(vid),Integer.parseInt(userid));
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
    @RequestMapping(value = "/queryMyCollect",method =RequestMethod.GET)
    public JSONObject queryMyCollect(@RequestParam("uid") String userid,@RequestParam("size") String size,@RequestParam("page") String page) {


        JSONObject res = collectService.queryMyCollect(Integer.parseInt(userid),Integer.parseInt(page),Integer.parseInt(size));

        if (!res.isEmpty()){
            res.append("status","1");
            return  res;

        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.append("status","2");
            return jsonObject1;
        }
    }

    @ResponseBody
    @ApiOperation(value = "删除收藏接口",notes = "收藏接口")
    @RequestMapping(value = "/deleCollect",method =RequestMethod.POST)
    public JSONObject deleCollect(@RequestParam("uid") String userid,@RequestParam("vid") String v_id) {
        int uid = Integer.parseInt(userid);
        int vid = Integer.parseInt(v_id);


        boolean res = collectService.deleCollect(vid,uid);
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("status","1");
            jsonObject.append("msg","成功");
            return jsonObject;
        }else {
            jsonObject.append("status","0");
            jsonObject.append("msg","失败，请稍后重试");
            return jsonObject;
        }


    }

}

