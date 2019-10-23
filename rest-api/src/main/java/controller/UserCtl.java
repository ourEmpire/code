package controller;

import com.alibaba.fastjson.JSON;
import config.MailConfig;
import database.DataSet;
import database.User;
import helper.JWTUtil;
import message.Messages.Message;
import message.Messages;
import message.StateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserCtl implements Ctl{
    @Autowired
    private DataSet ds;
    Message[] messages = new Messages().getMessages();

    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password){
        Message msg = messages[Messages.POST];
        User user = ds.getUserMap().get(id);
        if(user == null || !user.getPassword().equals(password)){
            msg.setCode(StateCode.NOT_FOUND)
                    .setMsg("id or password error")
                    .setData(null);
            return JSON.toJSONString(msg);
        }
        String token = new JWTUtil().sign(id);
        msg.setCode(StateCode.OK)
                .setMsg("login successfully")
                .setData(token);
        return JSON.toJSONString(msg);
    }

    @GetMapping("")
    public String view(@RequestParam String id){
        User user = ds.getUserMap().get(id);
        Message message = messages[Messages.GET];
        if(user==null){
            return JSON.toJSONString(message.setMsg("the user isn't found")
                                            .setCode(StateCode.NOT_FOUND)
                                            .setData(null));
        }

        message.setMsg("operation successfully")
                .setCode(StateCode.OK)
                .setData(user);
        return JSON.toJSONString(message);
    }
    @PostMapping("/create")
    public String create(@RequestParam String id,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String verify) {
        Message message = messages[Messages.POST];
        EmailCtl emailCtl = new EmailCtl();
        long nowTime = System.currentTimeMillis();
        int v = Integer.parseInt(verify);
        if(!emailCtl.check(email,v,nowTime)){
            return JSON.toJSONString(message.setCode(StateCode.FORBIDDEN)
                                                .setMsg("验证码失效或错误！")
                                                .setData(null));
        }

        //查找数据库中是否有id的用户
        if(ds.getUserMap().get(id) != null){
            return JSON.toJSONString(message.setMsg("the user is existed")
                    .setCode(StateCode.FORBIDDEN)
                    .setData(null));
        }
        User newUser = new User(id,username,password,email);
        ds.getUserMap().put(id,newUser);
        ds.getUserList().add(newUser);
        return JSON.toJSONString(message.setMsg("option successfully")
                .setCode(StateCode.CREATED)
                .setData(newUser));
    }

    @PutMapping("")
    public String update(@RequestParam String  id,
                         @RequestParam(required = false) String username,
                         @RequestParam(required = false) String password,
                         @RequestParam(required = false) String email){
        Message message = messages[Messages.PUT];
        User user = ds.getUserMap().get(id);
        if(user == null){
            return JSON.toJSONString(message.setMsg("the user isn't found")
                                            .setCode(StateCode.NOT_FOUND)
                                            .setData(null));
        }
        //记录用户在UserList中的位置，用与修改UserList中的用户信息
        int index = ds.getUserList().indexOf(user);
        if(username != null)user.setUsername(username);
        if(password != null)user.setPassword(password);
        if(email != null) user.setEmail(email);
        ds.getUserList().set(index,user);
        return JSON.toJSONString(message.setMsg("option successfully")
                                        .setCode(StateCode.OK)
                                        .setData(user));
    }


    @DeleteMapping("")
    public String delete(@RequestParam String id){
        Message message = messages[Messages.DELETE];
        User user = ds.getUserMap().get(id);
        if(user == null){
            return JSON.toJSONString(message.setMsg("the user isn't existed")
                                            .setCode(StateCode.NOT_FOUND)
                                            .setData(null));
        }
        ds.getUserMap().remove(id);
        ds.getUserList().remove(user);
        return JSON.toJSONString(message.setMsg("option successfully")
                                        .setCode(StateCode.OK)
                                        .setData(user));
    }



}



