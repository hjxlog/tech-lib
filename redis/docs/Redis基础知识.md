# Redis基础知识

## 一、概念

key-value的非关系型数据库，NO-SQL，缓存是为了解决两边处理速度不一致的问题。比如大量请求过来，数据库扛不住，但是redis的qps高，可以抗，不能扛就做成集群，接着扛。

NoSQL的事务，BASE，了解BASE是什么？

> 特征

单线程的，每个命令具有原子性

默认端口：6379

## 二、常用命令

### key

key的格式：•[项目名]:[业务名]:[类型]:[id] ，使用冒号 ： 分开，可以用作分组

<img src="C:\Users\hjx\AppData\Roaming\Typora\typora-user-images\image-20220713101623698.png" alt="image-20220713101623698" style="zoom:50%;" />

```bash
# 查看帮助命令，要在redis客户端才行
help keys
# 查询所有key，后面的是正则表达式，使用模糊匹配的方式，最好不要在生产环境使用
keys * 

# 删除key，删除k1
del k1

# 判断Key是否存在
exists key

# 给Key设置一个有效期，单位为秒
expire key second

# 查看key剩余有效期
ttl key
```

### 2.1 string

三种格式：string, int ,float

无论那种形式，底层都是字节数组形式存储，只是编码不同。

```bash
# 添加或者修改
set key value

# 根据key获取value
get key

# 批量添加多个key value
mset k1 v1 k2 v2 k3 v3

# 批量获取value
mget k1 k2 k3

# 整型key自增1，如果不是会报错
incr key

# 自己设定步长增长
incrby key increment

# 浮点型增长
incrbyfloat key increment

# 不存在才执行
setnx key value

# 添加键值对，设置有效期
setex key seconds value
```

### hash

可以把它简单当成java的hashmap，对照string命令，很多都是加上h即可

```bash
# 添加或者修改
hset key field value

# 获取hash类型的Key的field值
hget key field

# 存多组，现在5.0版本，直接用hset也可以
hmset key field value field1 value1

# 获取多个field
hmget key field field1

# 获取Key所有的键值对
hgetall key

# 获取key所有的field
hkeys key

# 获取key所有的value
hvals key

# key的某个field增长
hincrby key field increment

# 添加key某field值，不存在才添加
hsetnx key field value
```

### list

类似于Java中的linkedlist，简单看作一个双向链表

```bash
# 从列表左侧插入，存入的是 c b a
lpush key a b c

# 从左侧移除并返回第一个元素
lpop key

# 从右边插入
rpush key 1 2 3

# 从右边取一个
rpop key

# 查看范围，从0开始的
lrange key start end

# 从左侧阻塞获取，获取不到就等待，直到超时返回
blpop key timeout(秒为单位)

# 从右侧阻塞获取
brpop key timeout
```

### set

可以简单看作是Java中的hashset

> 特征：

无序：每次插入是根据hash算法计算下标位置的

不可重复

支持交集、并集、差集

```bash
# 向set中添加一个或者多个
sadd key a b c

# 移除指定元素
srem key b c

# 返回set个数
scard key

# 判断元素是否存在在set中
smember key member

# 获取set中所有元素
smembers key

# 求key1 key2 的交集
sinter key1 key2

# 求key1 key2的差集
sdiff key1 key2 (以key1为准)

# 求key1 key2 的并集
sunion key1 key2 
```

### sortedset

可以排序的集合，类似Java中的treeSet，可排序，不可重复

可以用来做排行榜功能

```bash
# 添加一个或多个元素到sorted set ，如果已经存在则更新其score值
zadd key score member # score在member前面的

# 删除sorted set 中的一个指定元素
zrem key member

# 获取指定元素的score值
zscore key member

# 获取指定元素排名，下标从0开始的
zrank key member

# 获取元素个数
zcard key

# 统计score值在给定范围内的所有元素的个数
zcount key min max (闭区间)

# 让sorted set中的指定元素自增，步长为指定的increment值
zincrby key increment member

# 按照score排序后，获取指定排名范围内的元素
zrange key min max (比如 0 2 那就是升序的前三名)

# 按照score排序后，获取指定score范围内的元素
zrangebyscore key min max

# 求差集、交集、并集
zdiff zinter zunion

# 默认都是升序，如果要降序，在z后面加上rev

```

## 三、redis的Java客户端

jedis：命令和原生命令一样，线程不安全，需要基于连接池来使用

lettuce：基于netty实现，支持同步、异步和响应式编程方式，并且是线程安全的。支持Redis的哨兵模式、集群模式和管道模式。

redisson：Redisson是一个基于Redis实现的分布式、可伸缩的Java数据结构集合。包含了诸如Map、Queue、Lock、 Semaphore、AtomicLong等强大功能

### springboot集成redis

#### 添加依赖和测试

添加依赖

```java
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

配置

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    lettuce:
      pool:
        max-active: 8 # 最大连接数
        max-idle: 8 # 最大空闲连接
        min-idle: 0 # 最小空闲连接
        max-wait: 100 # 连接等待时间，单位为ms
```



测试：

```java
 @Resource
    RedisTemplate redisTemplate;

    @Test
    void testString(){
        // 插入一条数据
        redisTemplate.opsForValue().set("name","李四");
        // 取出数据
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);

    }
```

发现使用客户端，get name，获取不到，查看可知，乱码了

查看RedisTemplate源码，可以发现

```java
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer keySerializer = null;
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer valueSerializer = null;
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer hashKeySerializer = null;
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer hashValueSerializer = null;
```

这些一开始都是null，@Nullable可以为空，在``afterPropertiesSet()``方法中，如果没有设定序列化器，则会使用jdk序列化器

```java
if (defaultSerializer == null) {

			defaultSerializer = new JdkSerializationRedisSerializer(
					classLoader != null ? classLoader : this.getClass().getClassLoader());
		}

		if (enableDefaultSerializer) {

			if (keySerializer == null) {
				keySerializer = defaultSerializer;
				defaultUsed = true;
			}
			if (valueSerializer == null) {
				valueSerializer = defaultSerializer;
				defaultUsed = true;
			}
			if (hashKeySerializer == null) {
				hashKeySerializer = defaultSerializer;
				defaultUsed = true;
			}
			if (hashValueSerializer == null) {
				hashValueSerializer = defaultSerializer;
				defaultUsed = true;
			}
		}
```

打断点到set()那里调试一下，默认进入jdk序列化器，序列化后转换成byte[]存储

```java
	@Override
	public void serialize(Object object, OutputStream outputStream) throws IOException {
		if (!(object instanceof Serializable)) {
			throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
					"but received an object of type [" + object.getClass().getName() + "]");
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
	}
```

#### 序列化

添加配置类：

```java
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        // 创建RedisTemplate对象
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 创建json序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 设置key的序列化
        template.setKeySerializer(RedisSerializer.string()); // StringRedisSerializer.UTF_8
        template.setHashKeySerializer(RedisSerializer.string());

        // 设置value序列化
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);

        return template;
    }

}
```

#### 存对象

```java
    @Test
    void testSaveUser(){
        User user = new User("张三",22);
        redisTemplate.opsForValue().set("user:1",user);
        User rlt = (User)redisTemplate.opsForValue().get("user:1");
        System.out.println("rlt = " + rlt);
    }
```

redis存储内容：

```json
{
  "@class": "com.hjxlog.domain.User",
  "name": "张三",
  "age": 22
}
```

利用@Class来实现反序列化，但是占用的内存太大了，不要这么用。我们自己手动进行序列化和反序列化。

#### 使用StringRedisTemplate

```java
	public StringRedisTemplate() {
		setKeySerializer(RedisSerializer.string());
		setValueSerializer(RedisSerializer.string());
		setHashKeySerializer(RedisSerializer.string());
		setHashValueSerializer(RedisSerializer.string());
	}
```

```java
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void testSaveUser() {
        User user = new User("张三", 22);
        // 手动序列化
        String jsonString = JSONUtil.toJsonStr(user);

        stringRedisTemplate.opsForValue().set("user:1", jsonString);
        String json = stringRedisTemplate.opsForValue().get("user:1");

        // 手动反序列化
        User parse = JSONUtil.toBean(json, User.class);
        System.out.println("parse = " + parse);
    }
```

使用hutool工具包

