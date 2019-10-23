package controller;

import com.alibaba.fastjson.JSON;
import database.DataSet;
import message.Messages;
import message.StateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users/admin")
@RestController
public class AdminCtl {
    @Autowired
    private DataSet ds;
    Messages.Message[] messages = new Messages().getMessages();
    @GetMapping("/all")
    public String all(){
        Messages.Message msg = messages[Messages.GET];
        msg.setMsg("operation successfully")
                .setData(ds.getUserList())
                .setCode(StateCode.OK);
        return JSON.toJSONString(msg);
    }
}
