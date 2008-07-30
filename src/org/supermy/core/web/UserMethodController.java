package org.supermy.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;

@Controller  //<——①
@RequestMapping("/muser.do")  // <—— ① 指定控制器对应URL请求
public class UserMethodController {

    @Autowired
    private IUserService userService;

 // <—— ② 如果URL请求中包括"method=listAllBoard"的参数，由本方法进行处理
    @RequestMapping(params = "method=listUsers")
    public String listAllUsers() {
        userService.findUsers();
        System.out.println("call listAllBoard method.");
        return "listUsers";
    }

    // <—— ③ 如果URL请求中包括"method=viewUser"的参数，由本方法进行处理
    @RequestMapping(params = "method=viewUser")
    public String getUser(long userId) {
        User u=userService.loadUser(userId);
        System.out.println("call listBoardTopic method.");
        return "viewUser";
    }
    

}
