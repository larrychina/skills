Spring boot 内部所有应用使用Commmons Logging,但是底层默认配置支持多种日志实现， Java Util Logging, Log4J2, and Logback，logggers优先打印控制台输出，也可以选择设置文件输出。

### 1，Log日志格式
默认的日志输出格式如下：

	2014-03-05 10:57:51.112  INFO 45469 --- [main] org.apache.catalina.core.StandardEngine  :tarting Servlet Engine: Apache Tomcat/7.0.52
	2014-03-05 10:57:51.253  INFO 45469 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
	2014-03-05 10:57:51.253  INFO 45469 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1358 ms
	2014-03-05 10:57:51.698  INFO 45469 --- [ost-startStop-1] o.s.b.c.e.ServletRegistrationBean        : Mapping servlet: 'dispatcherServlet' to [/]
	2014-03-05 10:57:51.702  INFO 45469 --- [ost-startStop-1] o.s.b.c.embedded.FilterRegistrationBean  : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
输出格式内容如下：
*	Data and Time：根据时间排序，精确到毫秒
*	Log Level：``ERROR``,``WARN``,``INFO``,``DEBUG``或者``TRACE``。
*	进程ID
*	A``---``表示真实日志的开始。
*	Thread name:方括号内显示线程名称
*	Logger name:通常打印的是类的全路径

### 2，日志输出
Spring Boot日志默认输出在控制台，如果你想除了打印控制台还想写入日志文件，你需要在application.properties里配置loggging.file 或者 logging.path,例如``logging.path=/var/log``

日志文件达到10Mb时会重新生成一个文件，控制台默认输出error,warn,info级别的日志，日志文件的大小限制可以通过设置logging.file.max-size来控制大小，之前生成的文件会无限期的存储，也可以通过设置logging.file.max-history来控制历史日志文件的个数。

### 3，日志级别
所有支持日志系统的都可以通过spring环境设置日志级别，通过使用``logging.level.<logger-name>=<level>``，例如：

	logging.level.root=WARN
	logging.level.org.springframework.web=DEBUG
	logging.level.org.hibernate=ERROR

### 4，自定义的日志配置
我们可以通过配置文件配置``loggging.config``来配置各种各样的日志系统。可以强制Spring Boot使用特定的日志系统,配置``org.springframework.boot.logging.LoggingSystem``=日志系统实现的类的全路径，也可以配置 = ``none``来禁用日志系统。
<table>
	<tr>
		<th>日志系统</th>
		<th>自定义文件</th>
	</tr>
<tr>
		<td>Logback</td>
		<td>``logback-spring.xml``,`` logback-spring.groovy``,`` logback.xml``, or ``logback.groovy``</td>
	</tr>
	<tr>
		<td>Log4j2</td>
		<td>log4j2-spring.xml or log4j2.xml</td>
	</tr>
<tr>
		<td>JDK (Java Util Logging)</td>
		<td>logging.properties</td>
	</tr>
</table>
ps：官方推荐使用-spring这种后缀形式日志文件，例如``logback-spring.xml``。如果你使用标准的配置，spring不能确保正确的日志初始化。因为标准的``logback.xml``配置文件会过早的加载，导致不可用。


