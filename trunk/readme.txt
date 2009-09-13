20090911
	如果你的数据表没有主键，那么count(1)比count(*)快
	如果有主键的话，那主键（联合主键）作为count的条件也比count(*)要快
	如果你的表只有一个字段的话那count(*)就是最快的啦
	count(*) count(1) 两者比较。主要还是要count(1)所相对应的数据字段。
	如果count(1)是聚索引,id,那肯定是count(1)快。但是差的很小的。
	因为count(*),自动会优化指定到那一个字段。所以没必要去count(?)，用count(*),sql会帮你完成优化的


20090903
	displaytag i18n 应用sturts 资源文件
	
20090825
	domain service 构造类完成
	web 类和页面构造 继续
	
	
20090819
	filepattern 需要自定义exporter来处理；
	类名
	
	power desigener 设计：表名规则，MY_TIGER；字段名称规则，_MY_TIGER； 
20090802
	继续处理,代码自动生成

	
20090728
	增加组织机构管理：
	可以同时管理几个机构：党政工青团
	每个机构的层次不一样，每个层次的名称通过类别来管理，类别和公司相关联；
	公司和层次节点是m2m的关系，转为o2m m2o来处理中间增加类别管理；
	
	
	
20090711
	启用Hibernate 的lazy,lazy的数据通过cache server来处理，性能会得到较大提高；
	
	
20090423
	引入userrole and department
	
20090421
	引入cxf
	
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

