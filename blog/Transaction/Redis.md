## Redis
    Redis 的全称是：Remote Dictionary.Server，本质上是一个 Key-Value 类型的内存数据库，很像
    memcached，整个数据库统统加载在内存当中进行操作，定期通过异步操作把数据库数据 flush 到硬盘上进行保存。
    因为是纯内存操作，Redis 的性能非常出色，每秒可以处理超过 10 万次读写操作，是已知性能最快的Key-Value DB。
    Redis 的出色之处不仅仅是性能，Redis 最大的魅力是支持保存多种数据结构，
    此外单个 value 的最大限制是 512M，不像 memcached 只能保存 1MB 的数据，因此 Redis 可以用来实现很多有用的功能。
    比方说用他的 List 来做 FIFO 双向链表，实现一个轻量级的高性 能消息队列服务，用他的 Set 可以做高性能的 tag 系统等等。
    另外 Redis 也可以对存入的 Key-Value 设置 expire 时间，因此也可以被当作一 个功能加强版的memcached 来用。 
    Redis 的主要缺点是数据库容量受到物理内存的限制，不能用作海量数据的高性能读写，
    因此 Redis 适合的场景主要局限在较小数据量的高性能操作和运算上。

## Redis 与 memcached
   - memcached 所有的值均是简单的字符串，redis 作为其替代者，支持更为丰富的数据类型
   - redis 的速度比 memcached 快很多
   - redis 可以持久化其数据

## 数据类型？
   - string
        - string类型是Redis最基本的数据类型，一个键最大能存储512MB。
   - list
        -  Redis列表是简单的字符串列表，按照插入顺序排序。
           你可以添加一个元素到列表的头部（左边）或者尾部（右边）
           一个列表最多可以包含 2^32 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。 
   - set
        - Redis 的 Set 是 String 类型的无序集合。
          集合成员是唯一的，这就意味着集合中不能出现重复的数据。
          Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
          集合中最大的成员数为 2^32 - 1 (4294967295, 每个集合可存储40多亿个成员)。 
   - sorted set
   - hash
        - Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。

   - Redis HyperLogLog  
        - 计数器

## 单机、主从、哨兵、集群

## 持久化的两种方式
   - RDB 
      - save 900 1
      - save 300 10
      - save 60 10000
   - AOF
     - appendonly yes
     - 持久化方式
         - everysec
         - allways
         - no   
         
## 数据淘汰策略  
   - 通过配置redis.conf中的maxmemory这个值来开启内存淘汰功能
   - maxmemory为0的时候表示我们对Redis的内存使用没有限制。
   - Redis提供了下面几种淘汰策略供用户选择，其中默认的策略为noeviction策略：
       - volatile-lru
       - volatile-ttl
       - volatile-random
       - allkeys-lru
       - allkeys-random
       - noeviction
   - maxmemory-policy noeviction
   
### MySQL 里有 2000w 数据，redis 中只存 20w 的数据，如何保证 redis 中的数据都是热点数据？
    redis 内存数据集大小上升到一定大小的时候，就会施行数据淘汰策略。
    
## 主从复制原理
   - Sync同步
   - 全量同步、增量同步
   
## 选主策略
   - Raft    
   
## 客户端
   - jedis
   - redisson
   - lettuce
   
## Jedis 与 Redisson 比较   
  
## 序列化
   - JdkSerializationRedisSerializer
   - Jackson2JsonRedisSerializer
   - GenericJackson2JsonRedisSerializer
   - GenericFastJsonRedisSerializer

## 事务
    MULTI、EXEC、DISCARD、WATCH、UNWATCH
    
## Redis 哈希槽
    Redis 集群没有使用一致性 hash,而是引入了哈希槽的概念，
    Redis 集群有 16384 个哈希槽，每个 key 通过 CRC16 校验后对 16384 取模来决定放置哪个槽，
    集群的每个节点负责一部分 hash 槽。

## 缓存穿透
    一般的缓存系统，都是按照 key 去缓存查询，如果不存在对应的 value，就应该去后端系统查找（比如DB）。
    一些恶意的请求会故意查询不存在的 key,请求量很大，就会对后端系统造成很大的压力。这就叫做缓存穿透。

## 缓存雪崩
    当缓存服务器重启或者大量缓存集中在某一个时间段失效，这样在失效的时候，会给后端系统带来很大压力。
    导致系统崩溃。
    

## 布隆过滤器（Bloom Filter）        
    什么情况下需要布隆过滤器？
    
    先来看几个比较常见的例子
    
        字处理软件中，需要检查一个英语单词是否拼写正确
        在 FBI，一个嫌疑人的名字是否已经在嫌疑名单上
        在网络爬虫里，一个网址是否被访问过
        yahoo, gmail等邮箱垃圾邮件过滤功能
    
    这几个例子有一个共同的特点： 如何判断一个元素是否存在一个集合中？
    
    常规思路
        数组
        链表
        树、平衡二叉树、Trie
        Map (红黑树)
        哈希表

    布隆过滤器介绍
    
        巴顿.布隆于一九七零年提出
        一个很长的二进制向量 （位数组）
        一系列随机函数 (哈希)
        空间效率和查询效率高
        有一定的误判率（哈希表是精确匹配）

## Redis Geo    
    地理位置
    
# Redis Modules：
    一种革命的方式  
    
## Redisearch
    在Redis上面实现了一个搜索引擎，但与其他Redis搜索库不同，它不使用内部数据结构，如排序集。
    这也可以实现更高级的功能，如文本查询的完全词组匹配和数字过滤，这对传统的redis搜索几乎是不可能或高效的。       