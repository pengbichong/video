package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.service.IZanService;
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
 * @since 2018-04-07
 */
@Api(value = "ZanController",description = "点赞接口")
@Controller
@RequestMapping("/zan")
public class ZanController {
    @Autowired
    private IZanService zanService;

    @ResponseBody
    @ApiOperation(value = "查询点赞数量接口",notes = "点赞接口")
    @RequestMapping(value = "/queryZanNum",method =RequestMethod.GET)
    public JSONObject queryZanNum(@RequestParam("vid") String vid) {

        int num = zanService.quryZanNum(Integer.parseInt(vid));
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("num",num);
        return jsonObject;
    }

    @ResponseBody
    @ApiOperation(value = "查询是否已赞接口",notes = "点赞接口")
    @RequestMapping(value = "/queryYes",method =RequestMethod.GET)
    public JSONObject queryZanYes(@RequestParam("vid") String vid,String userid) {


        boolean res = zanService.quryZanYes(Integer.parseInt(vid),Integer.parseInt(userid));
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("zanYes",1);
            return  jsonObject;
        }else {
            jsonObject.append("zanYes",0);
            return  jsonObject;
        }


    }


    @ResponseBody
    @ApiOperation(value = "保存赞接口",notes = "点赞接口")
    @RequestMapping(value = "/saveZan",method =RequestMethod.POST)
    public JSONObject saveZan(@RequestParam("vid") String vid,@RequestParam("uid") String userid) {


        boolean res = zanService.save(Integer.parseInt(vid),Integer.parseInt(userid));
        JSONObject jsonObject = new JSONObject();
        if (res){
            jsonObject.append("status",1);
            return  jsonObject;
        }else {
            jsonObject.append("status",0);
            return  jsonObject;
        }


    }



}

