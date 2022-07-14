# Redis实战例子

# 一、短信登录

以前的登录流程：用户登录，服务器保存session，并且把cookie返回给前端，前端下次访问的时候带上cookie，如果tomcat是集群，这次访问session保存在tomcat1，下次负载均衡，访问到了tomcat2，这时候没有session。

原先解决方案：tomcat的session共享，但有问题，不够及时，占用空间。



现在使用redis，把session存到redis，服务器集群去redis里面取。

