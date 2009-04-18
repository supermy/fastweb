20090417
	1、建立业务单据；2、开始流程并且绑定单据Id,单据绑定实例Id;3、开始提交任务；
	4、节点任务处理(弹出并且审核 业务界面,填写审核意见)；5、任务流转；6、任务结束
	
	所有权限
	     	
	web流程
	并发子流程，会签
20090416
	完成资源的上传管理，可以上传工作流的压缩包；可以手动进行发布管理；
	
	
20090415
	流程管理 workflow:流程列表;流程上传，发布；
	任务管理 task:任务列表；新建任务,改变任务,审批任务,当前任务流程图；
	
20090328
	ext 可以用于管理后台
	ext-debug.html ext测试使用
	hql-debug.actin hql查询测试使用
	
20090320
		Concurrency Level:      1
		Time taken for tests:   96.549572 seconds
		Complete requests:      1000
		Failed requests:        0
		Write errors:           0
		Total transferred:      2345000 bytes
		HTML transferred:       2110000 bytes
		Requests per second:    10.36 [#/sec] (mean)
		Time per request:       96.550 [ms] (mean)
		Time per request:       96.550 [ms] (mean, across all concurrent requests)
		Transfer rate:          23.72 [Kbytes/sec] received
		
		Connection Times (ms)
		              min  mean[+/-sd] median   max
		Connect:        0    0   0.0      0       0
		Processing:    64   95  25.1     91     440
		Waiting:       63   94  24.7     90     440
		Total:         64   95  25.1     91     440


20090319
	hibernate session close;
	
20090319
	lazy 机制去掉，使用缓存。
	注解结合表格实现rails的crud功能	
	
20090314
	配置mysql数据库  准备写测试类 验证
	
20090313
	升级版本 只使用hql(辅助sql) ;
	hql提供分页，sql只是提供复杂查询的接口;	
	
	sql查询返回一个map对象，返回List<Map>,返回List<HibernateObject>
	sql分页查询 TODO	

20080904
	使用sql查询，引入jdbcTemplate，mvc和逻辑层直接使用Map当作POJO。
	详见UserService的方法展示
2008.09.04之前

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

