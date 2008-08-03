package org.supermy.core.web;

import java.util.Set;
import java.util.*;

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
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-8-1 下午01:03:58
 * 
 */
@Controller
@SessionAttributes("currUser")
public class UserController {

	private static Log log = LogFactory.getLog(UserController.class);

	@Autowired
		private IUserService us;

	/**
	 * @return
	 */
	@RequestMapping("/users.do")
		@ModelAttribute("users")
		public Set<User> listAllUsers() {
			return this.us.findUsers(0, 13);
		}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping("/user.do")
		public ModelMap viewUser(@RequestParam("userId")
				long id) {
			return new ModelMap(this.us.load(User.class, id));
				}

	/**
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delUser.do")
		public String delUser(long userId) {
			us.delete(User.class, userId);
			return "listUsers";
		}

	/**
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
	 * 保存以后，切换到单数据浏览页面。 SessionStatus status
	 * 
	 * @param user
	 * @param status
	 * @return
	 */
	@RequestMapping("/saveUser.do")
		public String saveUser(@ModelAttribute
				User user, Model model) {
			StringBuffer emsg = new StringBuffer("");
			if (!user.passwordCheck()) {
				emsg.append("用户两次口令输入不一致；");
			}
			emsg.append(getErrorMsg(user));
			if (emsg.length() > 0) {
				// emsg.append("error info:");
				model.addAttribute("errors", "error info ：" + emsg);
				model.addAttribute("user", user);
				return "userForm";
			}
			if (user.isOld()) {
				this.us.merge(user);
			} else {
				this.us.save(user);
			}

			// status.setComplete();
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
				User user, BindingResult result, Model model) {
			StringBuffer emsg = new StringBuffer("");
			if (!user.passwordCheck()) {
				emsg.append("用户两次口令输入不一致；");
			}
			emsg.append(getErrorMsg(user));
			if (emsg.length() > 0) {
				// emsg.append("error info:");
				model.addAttribute("errors", "error info ：" + emsg);
				model.addAttribute("user", user);
				return "register";
			}
			this.us.save(user);
			// status.setComplete();
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
				User user, BindingResult result, Model model) {
			User u = us.login(user.getEmail(), user.getPasswd());
			StringBuffer emsg = new StringBuffer("");
			if (u == null) {
				emsg.append("user email or password error");
				model.addAttribute("errors", "error info ：" + emsg);
				model.addAttribute("user", user);
				return "login";
			}
			model.addAttribute("currUser", u);
			model.addAttribute("loginMsg", "login success id:" + u.getId()
					+ " name: " + u.getEmail());
			// status.setComplete();
			return "redirect:user.do?userId=" + u.getId();
				}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/managerUserRoles.do", method = RequestMethod.GET)
		public String managerUserRoles(@ModelAttribute("currUser")
				User currUser, Model model) {
			Role role = new Role();
			Map m = new HashMap();
			m.put("admin", "manager");
			m.put("user", "common user");
			m.put("superadmin", "super manager ");
			model.addAttribute("allRoles", m);

			String[] roles = us.findRoleNamesByUserId(currUser.getId());
			role.setRoles(roles);
			model.addAttribute("role", role);
			return "managerUserRoles";
				}

	@RequestMapping(value = "/managerUserRoles.do", method = RequestMethod.POST)
		public String managerUserRoles(@ModelAttribute("currUser")
				User currUser, Role role, BindingResult result, Model model) {
			Set<Role> roles1 = us.findRolesByUserId(currUser.getId(), 0, 30);// FIXME
			us.delSome(roles1);
			for (String roleName : role.getRoles()) {
				Role r = new Role();
				r.setName(roleName);
				r.setUser(currUser);
				us.save(r);
			}
			return "redirect:user.do?userId=" + currUser.getId();
				}

	/**
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

	private StringBuffer getErrorMsg(BaseDomain u) {
		ClassValidator v = new ClassValidator(u.getClass());
		InvalidValue[] msgs = v.getInvalidValues(u);
		StringBuffer errorMsg = new StringBuffer("");
		for (InvalidValue line : msgs) {
			errorMsg.append(line.getPropertyPath()).append(":").append(
					line.getMessage()).append("  ");
		}
		v = null;
		msgs = null;
		return errorMsg;
	}

}
