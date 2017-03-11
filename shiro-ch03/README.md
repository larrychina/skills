spring mvc 整合shiro

##1,在自定义realm中可以设置自定义加密策略##

        <!-- 设置shiro加密策略，此处采用md5加密算法(username+salt) -->
            <bean id="credentialsMatcher" class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
                <property name="hashAlgorithmName" value="md5"/>
                <property name="hashIterations" value="2"/>
                <property name="storedCredentialsHexEncoded" value="true"/>
            </bean>

            <!-- Realm实现 -->
            <bean id="userRealm" class="org.larry.shiro.realm.UserRealm">
                <property name="credentialsMatcher" ref="credentialsMatcher"/>
                <property name="cachingEnabled" value="false"/>
            </bean>

##2, 过滤器：
        在web.xml中配置的过滤器shiroFilter,
        <filter>
            <filter-name>shiroFilter</filter-name>
            <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
            <init-param>
                <param-name>targetFilterLifecycle</param-name>
                <param-value>true</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>shiroFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>

        targetFilterLifecycle的作用是自动到spring容器中去查找名字为shiroFilter的bean，并把所有Filter的操作委托给他。
        shiroFilter可以设置默认的loginUrl，successUrl...用来设置过滤器拦截的登录路径

    对于shiro过滤器链,根据个人理解此处做简单介绍：在使用FormAuthencationFilter进行表单登录验证的时候，此时可以省去自己实现登录操作（当然自己也可以重写），但是在这里有个问题：
    我的项目路径是http://localhost:8080/ ,我在使用了FormAuthencationFilter配置了successUrl,但是登录成功后，一直跳转到http://localhost:8080/,对于这个问题折腾了好几个小时，一直找不到原因，后来跟了源码才发现问题,
    shiro会记住你之前访问的路径，比如你第一次登录http://localhost:8080/ ,如果配置了登录拦截，在登录成功后，shiro会默认取你的之前访问的地址，而不会跳转到你配置的successUrl
    我们在使用shiro时，通常登录成功后，会跳转到之前访问的页面，如果们访问的是登录页面的话，shiro就会根据我们配置的successUrl去重定向，当我们没有做配置的话，shiro就会重定向到默认的路径/
    ，
    具体在源码中可以看到：

     1.shiro会把请求信息保存到session中：

         SavedRequest savedRequest = new SavedRequest(httpRequest);
                session.setAttribute("shiroSavedRequest", savedRequest);

     2.然后判断是否已经登录，如果没有登录，就会跳到登录页面，用户输入凭证之后就会交给FormAuthenticationFilter这个类来处理；

     3.如果登录成功之后就会调用一下方法重定向：
      protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
              this.issueSuccessRedirect(request, response);
              return false;
       }

     其中的issueSuccessRedirect方法如下：
     protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
             WebUtils.redirectToSavedRequest(request, response, this.getSuccessUrl());
         }

     FormAuthenticationFilter出来之后就会交给这个方法处理重定向：
        WebUtils.redirectToSavedRequest(request, response, this.getSuccessUrl());//进入此方法可以发现，shiro去session中找之前的url，如果没有就会跳转到配置的successUrl

    现实中，还有一种需求，就是需要登录后跳转到指定的地址，比如通常会跳转到首页，那么我们该怎么做呢？

    做法很简单，在WebUtils中有个方法叫做：
    public static SavedRequest getAndClearSavedRequest(ServletRequest request) {
            SavedRequest savedRequest = getSavedRequest(request);
            if(savedRequest != null) {
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                session.removeAttribute("shiroSavedRequest");
            }

            return savedRequest;
        }

    因此，我们可以重写FormAuthencationFilter:    在登录成功重定向的方法中调用清除saveUrl:
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
                  WebUtils.getAndClearSaveRequest(request) ; // 清除之前保存的
                  //this.issueSuccessRedirect(request, response);
                  return false;
           }
    @Override
        protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
            WebUtils.issueRedirect(request, response, "指定的url(getSuccessUrl())", null, true);
        }