一、每个类一张表，只是继承超类；
二、只允许使用m2o关联，并且设置ObjectId属性；
三、Include 用户状态工具条 同时完成是否会员用户的权限管理；
四、事件验证和程序级别验证；
	js验证 hibernate validator to spring-commons validator
	hibernate 含默认中文校验信息
五、启用自动索引；
六、cglib的升级 废弃 asm.jar asm-attrs.jar cglib-2.1.3.jar
   使用spring cglib-nodep.jar  asm2.*版本；	
七、技术穿刺完成，实现功能登陆；密码管理，用户管理；实现用户角色管理。
注意事项：
	一、在service层，实现类必须声明，@Service；
	二、关联对象为null,DetachedCriteria查询返回，会出现类违例错误；
	三、不同的URL,相同的URL不同的参数，spring controller 的配置
	让请求处理方法处理特定的 HTTP 请求方法
	@RequestMapping(params = "method=createUser",method = RequestMethod.POST)
	使用 JavaBean 作为请求处理方法的入参
	通过注解指定绑定的 URL 参数 public String listUser(@RequestParam("id") int topicId,User user)
	使用一个action来解决针对一个对象操作的所有,并且尽量符合rest风格。
	
	yui直接使用yahoo的。
 八、完成AutoScanAnnSpring...	 	
九、引入acegi ,不同的权限页面位于不同的目录
done upload file ...
