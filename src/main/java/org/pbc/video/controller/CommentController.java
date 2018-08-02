package org.pbc.video.controller;


import com.xiaoleilu.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pbc.video.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pbc
 * @since 2018-04-11
 */


@Api(value = "CommentController",description = "评论接口")
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;


    @ResponseBody
    @ApiOperation(value = "保存评论接口",notes = "评论接口")
    @RequestMapping(value = "/saveComment",method =RequestMethod.POST)
    public JSONObject saveComment(HttpSession session, @RequestParam("comment") String comment,
                                  @RequestParam("vid") String vid) {
        if (session.getAttribute("userid") != null) {
            int userid = Integer.parseInt(session.getAttribute("userid").toString());
            String res = commentService.save(userid,comment,vid);
            if ("error".equals(res)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","1");
                jsonObject.append("msg","评论失败，稍后重试！");
                return  jsonObject;
            }else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.append("result","2");
                jsonObject.append("msg","评论成功！");
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
    @ApiOperation(value = "查询评论接口",notes = "评论接口")
    @RequestMapping(value = "/getComment",method =RequestMethod.GET)
    public JSONObject getComment(@RequestParam("vid") String vid,@RequestParam("page") String page,
                                 @RequestParam("size") String size) {
        int v_id= Integer.parseInt(vid);
        int pageNum = Integer.parseInt(page);
        int sizeNum = Integer.parseInt(size);
       JSONObject jsonObject =commentService.getComment(v_id,pageNum,sizeNum);
        if (!jsonObject.isEmpty()) {
            jsonObject.append("status", "1");
            return jsonObject;
        }else {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.append("status","2");
            return jsonObject1;
        }

    }
}

