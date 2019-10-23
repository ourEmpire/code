package controller;

import com.alibaba.fastjson.JSON;
import helper.MailService;
import message.Messages;
import message.Messages.Message;
import message.StateCode;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Random;

/**
 * 用于创建新用户发送邮件
 */
@RequestMapping("/users")
@RestController
public class EmailCtl implements Ctl {

    private Message[] messages = new Messages().getMessages();
    private static HashMap<String,Repo> repos = new HashMap<>();
    private MailService mailService;
    //设置验证码的有效时间 ： 单位 ms
    private static final long MAX_LIVE = 1000 * 60 * 5L;
    @GetMapping({"/create","/{id}/update"})
    public String sendEmail(@RequestParam String email){
        mailService = new MailService();
        Message message = messages[Messages.GET];
        Repo repo;
        //查看email用户是否已发送过邮件
        if(repos.containsKey(email)){
            repo = repos.get(email);
            repo.setRepo();
            repos.put(email,repo);
        }else{
            repo = new Repo();
            repos.put(email,repo);
        }
        boolean success = mailService.sendSimpleMail(email,repo.verify);
        if(success){
            return JSON.toJSONString(message.setCode(StateCode.OK)
                                            .setMsg("验证码已成功发送")
                                            .setData(null));
        }
        return JSON.toJSONString(message.setCode(StateCode.INTERNAL_SERVER_ERROR)
                                        .setMsg("验证码发送失败")
                                        .setData(null));
    }

    //存储验证码,产生时间戳,并记录产生次数
    private class Repo{
        int verify;
        long timeStamp;
        int count = 0;
        Repo(){
            setRepo();
        }

        void setRepo(){
            //随机产生一个六位数的验证码
            this.verify = new Random().nextInt(1000000 - 100000) + 100000;
            this.timeStamp  = System.currentTimeMillis();
            this.count++;
        }
    }
    //检验验证码
    public boolean check(String email,int verify,long nowTime){
        Repo repo = repos.get(email);
        if(repo.verify == verify && nowTime - repo.timeStamp < MAX_LIVE){
            return true;
        }
        return false;
    }



}
