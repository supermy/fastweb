package org.supermy.core.web;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-31 上午11:10:51
 * 
 */
@Controller
// ①让ModelMap的currUser属性拥有session级作用域
@SessionAttributes("currUser")
public class UserController {

	private static Log log = LogFactory.getLog(UserController.class);

	// #@Autowired
	// #private DefaultBeanValidator beanValidator;

	@Autowired
	private IUserService us;

	// http://localhost/user.do?method=listUsers&userId=10&userName=tom
	// tom and 10 将会自动绑定到user上
	/**
	 * 显示所有的用户
	 * 
	 * @return
	 */
	@RequestMapping("/users.do")
	@ModelAttribute("users")
	public Set<User> usersHandler() {
		return this.us.findUsers(0, 13);
	}

	/**
	 * 显示单个用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/user.do")
	public ModelMap userHandler(@RequestParam("userId")
	long id) {
		return new ModelMap(this.us.load(User.class, id));
	}

	/**
	 * 删除单个用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delUser.do")
	public String delUser(long userId) {
		us.delete(User.class, userId);
		return "listUsers";
	}

	/**
	 * 增加用户准备
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addUser.do", method = RequestMethod.GET)
	public String addUser(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "userForm";
	}

	/**
	 * 编辑用户准备
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/editUser.do")
	public String editUser(@RequestParam("userId")
	long userId, Model model) {
		User u = (User) this.us.load(User.class, userId);
		u.setPasswd2(u.getPasswd());
		model.addAttribute(u);
		return "userForm";
	}

	/**
	 * 保存以后，切换到单数据浏览页面。
	 * 
	 * @param user
	 * @param status
	 * @return
	 */
	@RequestMapping("/saveUser.do")
	public String processSubmit(@ModelAttribute
	User user,Model model, SessionStatus status) {
		StringBuffer emsg = new StringBuffer("");
		if (!user.passwordCheck()) {
			emsg.append("用户两次口令输入不一致；");
		}
		emsg.append(getErrorMsg(user));
		if (emsg.length() > 0) {
			//emsg.append("error info:");
			model.addAttribute("errors","error info ："+ emsg);
			model.addAttribute("user",user);
			return "userForm";
		}
		if (user.isOld()){this.us.merge(user);}else{this.us.save(user);}
		
		status.setComplete();
		return "redirect:user.do?userId=" + user.getId();
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "register";
	}

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String register(@ModelAttribute
	User user, BindingResult result, Model model, SessionStatus status) {
		StringBuffer emsg = new StringBuffer("");
		if (!user.passwordCheck()) {
			emsg.append("用户两次口令输入不一致；");
		}
		emsg.append(getErrorMsg(user));
		if (emsg.length() > 0) {
			//emsg.append("error info:");
			model.addAttribute("errors","error info ："+ emsg);
			model.addAttribute("user",user);
			return "register";
		}
		this.us.save(user);
		status.setComplete();
		return "redirect:user.do?userId=" + user.getId();
	}

/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(@ModelAttribute
	User user, BindingResult result, Model model, SessionStatus status) {
		User u=us.login(user.getEmail(),user.getPasswd());
		StringBuffer emsg = new StringBuffer("");
		if(u==null){
			emsg.append("user email or password error");
			model.addAttribute("errors","error info ："+ emsg);
			model.addAttribute("user",user);
			return "login";
		}
		model.addAttribute("currUser",u);
		model.addAttribute("loginMsg","login success id:"+u.getId()+" name: "+u.getEmail());
		status.setComplete();
		return "redirect:user.do?userId=" + u.getId();
	}

	/**
	 * 查找用户数据
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@RequestMapping("/findUsers.do")
	public String listUsers(ModelMap model) {
		Set<User> users = us.findUsers(1, 3);
		model.addAttribute("users", users);
		// model.addAttribute("currUser",user);
		return "listUsers";
	}
	public StringBuffer getErrorMsg(BaseDomain u) {
		ClassValidator v = new ClassValidator(u.getClass());
		InvalidValue[] msgs = v.getInvalidValues(u);
		StringBuffer errorMsg = new StringBuffer("");
		for (InvalidValue line : msgs) {
			errorMsg.append(line.getPropertyPath()).append(":").append(
					line.getMessage()).append("  ");
		}
		v=null;
		msgs=null;
		return errorMsg;
	}

}
