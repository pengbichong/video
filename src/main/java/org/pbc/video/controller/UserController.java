package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.model.User;
import org.pbc.video.service.IUserInfoService;
import org.pbc.video.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Api(value = "UserController",description = "用户接口")
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;


    @ResponseBody
    @ApiOperation(value = "注册接口",notes = "注册接口")
    @RequestMapping(value = "/registr",method =RequestMethod.POST)
    public String registr(@RequestParam("USERNAME") String username,
                                    @RequestParam("PASSWORD") String password,
                                    @RequestParam("NICKNAME") String nickname,
                                    @RequestParam("EMAIL") String email,
                                    @RequestParam("re_date") String re_date){

        System.out.println(username+"--"+password+"--"+nickname+"--"+email+"--"+re_date);
        String res = userService.registr(username,password,nickname,email,re_date);

        return res;
    }

    @ResponseBody
    @ApiOperation(value = "登录接口",notes = "登录接口")
    @RequestMapping(value = "/login",method =RequestMethod.POST)
    public JSONObject login(HttpSession session, @RequestParam("USERNAME") String username, @RequestParam("PASSWORD") String password){



        System.out.println(username+"--"+password);
        User res = userService.login(username,password);
        JSONObject jsonObject = new JSONObject();
        if (res.getUserId()!=null&&res.getUsername()!=null){
            jsonObject.append("msg","success");
            jsonObject.append("user",res);
            String headpath = "/headImg/hu.jpg";
            if (userInfoService.queryUserInfo(res.getUserId()).getHeadPath()!=null&&!""
                    .equals(userInfoService.queryUserInfo(res.getUserId()).getHeadPath())) {
                headpath = userInfoService.queryUserInfo(res.getUserId()).getHeadPath();
            }
            jsonObject.append("headpath",headpath);
            session.setAttribute("user",res.getUsername());
            session.setAttribute("userid",res.getUserId());
            return jsonObject;
        }else {
            jsonObject.append("msg","usererror");
            return  jsonObject;
        }


    }

    @ResponseBody
    @ApiOperation(value = "修改密码接口",notes = "修改密码接口")
    @RequestMapping(value = "/modifyPW",method =RequestMethod.POST)
    public JSONObject modifyPassword(HttpSession session, @RequestParam("password1") String password1, @RequestParam("password2") String password2){



        System.out.println(password1+"--"+password2+"---"+session.getAttribute("userid"));
        if (session.getAttribute("userid")==null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.append("msg","请重新登录！");
            jsonObject.append("status","0");
            return  jsonObject;
        }else {
            String res = userService.modifyPw(password1,password2,session.getAttribute("userid").toString());
            if ("pserror".equals(res)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("msg","原密码错误！");
                jsonObject.append("status","1");
                return jsonObject;
            }else if ("nouser".equals(res)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("msg","用户不存在！");
                jsonObject.append("status","2");
                return jsonObject;
            }else if ("success".equals(res)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("msg","修改成功！");
                jsonObject.append("status","3");
                return jsonObject;
            }

        }

        return null;
    }

}

