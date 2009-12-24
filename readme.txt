最佳实践：全部注解，开发过程中不用写任何配置文件；在Hibernate中完成数据库的设计，验证和索引（TODO），自动生成数据库。
只从Service层测试驱动开发。
一、每个类一张表；
二、只允许使用m2o关联；
三、页面Include 用户状态工具条 同时完成是否会员用户的权限管理；
四、事件验证和程序级别验证；
	应用hibernate验证，将hibernate验证自动转换到js验证；TODO
五、启用自动索引；TODO
六、cglib的升级 废弃 asm.jar asm-attrs.jar cglib-2.1.3.jar
   使用spring cglib-nodep.jar  asm2.*版本；	
七、技术穿刺完成，实现功能登陆；密码管理，用户管理；实现用户角色管理。
八、在service层，实现类必须声明，@Service；
九、关联对象为null,DetachedCriteria查询返回，会出现类违例错误；
十、Spring MVC中，不同的URL,相同的URL不同的参数，spring controller 的配置；
	让请求处理方法处理特定的 HTTP 请求方法
	@RequestMapping(params = "method=createUser",method = RequestMethod.POST)
	使用 JavaBean 作为请求处理方法的入参
	通过注解指定绑定的 URL 参数 public String listUser(@RequestParam("id") int topicId,User user)
	使用一个action来解决针对一个对象操作的所有,并且尽量符合rest风格。
	
十一、yui直接使用yahoo的，标准和兼容性强的CSS和JS。
十二、完成AutoScanAnnSpring...，hibernate domain注解完成，只需在springs.xml指出包名称；	 	
十三、引入acegi ,不同的权限页面位于不同的目录 待测试；
十四、口令采用md5加密，加密方法位于domain中，便于复用，是最终的重构结果；

