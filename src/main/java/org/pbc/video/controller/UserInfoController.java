package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.model.UserInfo;
import org.pbc.video.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Api(value = "UserInfoController",description = "用户信息接口")
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;


    @ResponseBody
    @ApiOperation(value = "上传头像接口",notes = "头像接口")
    @RequestMapping(value = "/upHead",method =RequestMethod.POST)
    public JSONObject upHead(HttpSession session,@RequestParam("image") String data){


        if (session.getAttribute("userid") != null) {
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
            String res = userInfoService.upHead(userid,data);
            if ("error".equals(res)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","1");
                jsonObject.append("msg","上传失败，稍后重试！");
                return  jsonObject;
            }else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","ok");
                jsonObject.append("msg","上传成功！");
                jsonObject.append("file",res);
                return  jsonObject;
            }
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("result","0");
            jsonObject.append("msg","请先登录！");
            return  jsonObject;
        }


    }


    @ResponseBody
    @ApiOperation(value = "查询用户信息接口",notes = "用户信息接口")
    @RequestMapping(value = "/queryUserInfo",method =RequestMethod.GET)
    public JSONObject upHead(HttpSession session) {

        if (session.getAttribute("userid") != null) {
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
            UserInfo userInfo = userInfoService.queryUserInfo(userid);
            if (userInfo!=null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","1");
                jsonObject.append("msg","成功！");
                jsonObject.append("userInfo",userInfo);
                return  jsonObject;
            }else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","2");
                jsonObject.append("msg","失败！");
                return  jsonObject;

            }
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("result","0");
            jsonObject.append("msg","请先登录！");
            return  jsonObject;
        }


    }

    //nickname:nickname,telephone:telephone,email:email,sex:sex

    @ResponseBody
    @ApiOperation(value = "修改用户信息接口",notes = "用户信息接口")
    @RequestMapping(value = "/modifyUserInfo",method =RequestMethod.POST)
    public JSONObject modifyUserInfo(HttpSession session,@RequestParam("nickname") String nickname,
                                     @RequestParam("telephone") String telephone,
                                     @RequestParam("email") String email,
                                     @RequestParam("sex") String sex) {
        if (session.getAttribute("userid") != null) {
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
            String res = userInfoService.modifyUserInfo(userid,email,telephone,nickname,sex);
            if (res=="success"){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","1");
                jsonObject.append("msg","修改成功！");
                return  jsonObject;
            }else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","2");
                jsonObject.append("msg","修改失败！");
                return  jsonObject;
            }
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("result","0");
            jsonObject.append("msg","请先登录！");
            return  jsonObject;
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据视频号查询作者接口",notes = "作者信息接口")
    @RequestMapping(value = "/queryAuthorInfo",method =RequestMethod.GET)
    public JSONObject queryAuthor(@RequestParam("vid") String vid) {

        int id = Integer.parseInt(vid);

        UserInfo userInfo = userInfoService.queryAuthor(id);
        userInfo.setTelephone("");
        userInfo.setLastloginDate(null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("authorInfo",userInfo);

        return jsonObject;
    }

}

