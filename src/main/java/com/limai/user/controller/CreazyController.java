package com.limai.user.controller;


import com.github.pagehelper.util.StringUtil;
import com.limai.user.util.NIOUtil;
import com.limai.user.util.RedisUtil;
import com.limai.user.util.RestHttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CreazyController {
    private static final Logger logger = LoggerFactory.getLogger(CreazyController.class);

    @Autowired
    private RedisUtil redisUtil;

    private int cnt ;

    private String pre = "keys_";

    List<Integer> numbers = new ArrayList<>();

    private static HttpURLConnection http = null;


    @PostMapping("/buy")
    @ApiOperation(value = "/buy",notes = "秒杀活动")
    public synchronized String secKill(HttpServletRequest request,@ApiParam(value = "填写自己的代号") @RequestParam(required = false) String username){
        try {
            String userIp = request.getRemoteAddr();
            Integer num = new Random().nextInt(cnt);

            if(numbers.size()==cnt){
                return "活动已结束！";
            }
            while(numbers.contains(num)){
                num = new Random().nextInt(cnt);
            }
            numbers.add(num);

            logger.info(num.toString());

            String product = (String) redisUtil.get(pre + num);
            redisUtil.remove(pre + num);

            String result = "恭喜！！！！" + userIp + "用户: "+username+" 1元秒杀了====》" + product;

            logger.info(result);

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return "系统繁忙，请重新抢购！";
        }
    }


    @GetMapping("/refresh")
    public String refresh(@RequestParam(required = false) int count){
        logger.info("刷新前库存总量："+cnt);
        for(int j = 0;j<cnt;j++){
            redisUtil.remove(pre+j);
        }
        numbers.clear();
        for(int i = 0;i < count-1;i++){
            redisUtil.set(pre + i, "全自动洗屁股马桶！");
        }
        int best = count-1;
        redisUtil.set(pre+best,"iphone X");
        this.cnt = count;
        logger.info("刷新后库存总量："+cnt);
        return "货品已更新,更新数量："+count+"个！";
    }

    @ApiOperation(value = "/image",notes = "获取路径")
    @RequestMapping(value = "/image",method = RequestMethod.POST)
    public String getPath(HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("");
        String webPath = request.getContextPath();
        logger.info(webPath);
        return "realPath:=======>"+realPath + "\n" + "webPath:=========>" + webPath;
    }

    @RequestMapping(value = "/gotoIndex",method = RequestMethod.GET)
    public String gotoIndex(){
        return "/index.html";
    }


    @ApiOperation(value = "/savePicture",notes = "从微信获取图片")
    @GetMapping("/savePicture")
    public String savePicture(@ApiParam(value = "测试") @RequestParam(required = false) String mediaId, HttpServletResponse response, HttpSession session, HttpServletRequest request){
        String filename=null;
        if(StringUtil.isNotEmpty(mediaId)){
            filename = saveImageToDisk(mediaId,session,response,request);
        }
        return filename;
    }

    /**
     * 获取临时素材
     */
    private byte[] getMedia(String mediaId, HttpServletResponse response, HttpSession session) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/get?";
        String accessToken = "11_djQ79qRpLlRajLSACwBIa-p81llYDb_qyNZdVGDSUbPiwrWzrgcFfmZKDpGIobDteV0gol4B1eb-FDIan3Lr1OWAtNW8weLxf1TGgG7_4XSx3QfCySSUkuq7tWA6trf-yAjRYby7Z7JgLZaKZGIiADALSU";
        //accessToken不存在的时候，通过判断去存，然后再取到作为参数使用
        String params = "access_token=" + accessToken + "&media_id=" + mediaId;
        String finalStr =url+params;
        byte[] data = null;
        try {
            ResponseEntity<byte[]> res = RestHttpUtil.INSTANCE.restGetByte(finalStr,null,null);
            data = res.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


    /**
     * 保存图片至服务器
     * @param mediaId
     * @return 文件名
     */
    public String saveImageToDisk(String mediaId,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        String filename = "test111.jpg";
        String savePath = "/EDT/telmall/pics/wxDownload";
        byte[] data = getMedia(mediaId, response, session);
        try{
            if(data!=null) {
                NIOUtil.byte2File(data,savePath+filename);
            }else{
                return "获取文件流失败！";
            }
            System.out.println(filename);
            return filename;
        }catch (Exception e){
            e.printStackTrace();
        }
        return filename;
    }

}
