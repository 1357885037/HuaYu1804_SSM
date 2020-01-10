import com.huayu.bean.Book;
import com.huayu.dao.BookMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.List;

public class Text {

    @Autowired
    private RedisTemplate redisTemplate;

    public void queryall(){
        ClassPathXmlApplicationContext nn = new ClassPathXmlApplicationContext("spring.xml");


      /*  BookMapper BookMapper = (BookMapper) nn.getBean("bookMapper");
        System.out.println("=="+BookMapper);
       List<Book> list=BookMapper.selectList(null);
       System.out.println(list.size());
*/
         SqlSessionFactory  sessionFactory =(SqlSessionFactory)nn.getBean("sqlSessionFactory");
         SqlSession session=sessionFactory.openSession();
         SqlSession session1=sessionFactory.openSession();
         BookMapper mapper=session.getMapper(BookMapper.class);
         BookMapper mapper2=session.getMapper(BookMapper.class);
         List<Book> list=mapper.selectList(null);
         System.out.println(list.toString());
         List<Book> list2=mapper2.selectList(null);
         System.out.println(list2.toString());
         session.commit();

        Jedis jedis=new Jedis("localhost",6379);//redis的链接地址一家链接端口
        jedis.auth("huayu");

        System.out.println(jedis.get("home"));

    }


    public static void main(String[] args){
        Text text=new Text();
        text.queryall();
     /*  Book books=new Book();
       Class c1=Book.class;
       Class c2=books.getClass();

       Object book= c1.getInterfaces();
       Book b=(Book)book;

       Object book2=c2.getInterfaces();
       Book n=(Book) book2;*/

/*        Jedis jedis=new Jedis("localhost",6379);//redis的链接地址一家链接端口
        jedis.auth("huayu");                     //redis的认证密码

        *//*
        * jedis可key的操作
        *
        * *//*
        System.out.println(jedis.flushDB());   //清空当前数据库的所有数据
        System.out.println(jedis.echo("hellow"));

        System.out.println(jedis.exists("foo")); //判断key为foo的这个键是否存在

        jedis.set("key","key");
        jedis.set("key2","key2");
        System.out.println(jedis.exists("key"));  //exists 存在为true 否者为false

        //randowkey 如果数据库k没有key返回null 有key则返回随机任意一个key
        jedis.select(2);
        String  randomKey=jedis.randomKey();
        System.out.println("randomKey:"+randomKey);

        jedis.select(0);
        jedis.expire("key",10); //十秒钟之后这个key过期
        System.out.println(jedis.get("key"));

        System.out.println(jedis.pttl("key2")); //查询这个key的有效毫秒
        System.out.println(jedis.get("key2"));    //如果键不存在返回-2 存在没设置时间返回-1 最后返回毫秒数

        jedis.persist("key");                 //移除key的过期时间

        //获取key的type类型
        System.out.println(jedis.type("key"));


        byte[] bytes=jedis.dump("key2");
        System.out.println(new String(bytes));

        //将key重命名
        jedis.renamenx("key2","key3");

        //查询匹配key
        //keys *      匹配数据库中所有的key
        //keys h?llow 匹配h 任意 llow
        //keys h*llow 匹配  h*** llow
        Set<String> set=jedis.keys("k*"); //拿所有为k*的值放在set中

        //jedis.del("key2");                    //根据key删除

        //向字符后面追加
        jedis.append("key2","1236");

        //给key设定时间
        jedis.setex("mq",2,"mqq");
        System.out.println("="+jedis.get("mq"));
        try {
            Thread.sleep(3000);
        }catch (Exception e){

        }
        System.out.println(jedis.get("mq"));

        //一次添加多个 key value 和取多个value
        jedis.mset("a","1","b","2");
        System.out.println(jedis.mget("a","b"));

        //批量删除
        jedis.del("a","b");


        *//*
        *
        *jedis对链表 lists的操作
        * *//*
        String key="mylists";
        jedis.rpush(key,"aaa");
        jedis.rpush(key,"bbb");
        jedis.rpush(key,"333");

        //获取集合的长度
        System.out.println("length:"+jedis.llen(key));

        //打印队列从索引0 开始 打印到-1 倒数最后一个 答应所有
        System.out.println(jedis.lrange(key,0,-1));

        //索引为1的元素
        System.out.println(jedis.lindex(key,1));

        //根据索引赋值 index超出范围时 为报一个error
        jedis.lset(key,1,"111");
        System.out.println(jedis.lindex(key,1));

        //从队列的右边入队一个元素
        jedis.rpush(key,"-2","-1");

        //从队列的左边入队一个或多个元素
        jedis.lpush(key,"mm","qq");

        //从队列的右边出队以一个元素
        System.out.println("从右出："+ jedis.rpop(key));
        //从队列的左边出队一个元素
        System.out.println( "从左出："+jedis.lpop(key));


        //count>0 从头往尾移除值为value的元素，count为移除的个数
        //count<0 从尾往头移植值为value的元素mcount为移除的个数
        //count=0 移除所有值为value 的元素
       jedis.lrem(key,1,"11");  //从头往尾 移除一个value为11的元素


        //第一个参数为起始坐标不能大于总条数
        //第二个参数如果大于总条数 就当他是最后一个元素的下标
        jedis.lrange(key,0,2);

        //删除区间以外的元素
        jedis.ltrim(key,0,2);


        *//*
        *
        * jedis 对集合 sets的操作
        * *//*

        //清空数据
        System.out.println(jedis.flushDB());

        String myset="myset";
        String myset2="myset2";

        //集合添加元素
        jedis.sadd(myset,"aaa","bbb","ccc");
        jedis.sadd(myset2,"bbb","ccc","ddd");

        //获取集合里面的元素数量
        System.out.println(jedis.scard(myset));

        //获得两个集合交集合，并存储在一个关键的结果集；
        jedis.sinterstore("jiaoji",myset,myset2);
        System.out.println(jedis.smembers("jiaoji"));

        //获得两个集合的并集，并存储在一个关键的结果集
        jedis.sunionstore("bingji",myset,myset2);
        System.out.println(jedis.smembers("bingji"));

        //将myyset集合中myset2没有的元素存储在一个关键的集合中去  反之同样
        jedis.sdiffstore("nn",myset,myset2);
        System.out.println(jedis.smembers("nn"));

        //确定某个元素是该集合的成员 true|false
        System.out.println(jedis.sismember(myset,"aaa"));

        //从key的集合中随机获取一个元素
        System.out.println(jedis.srandmember(myset));

        //将aaa从myset中移动到myset2中
        jedis.smove(myset,myset2,"aaa");

        //从集合中删除一个或多个元素
        jedis.srem(myset,"bbb","ccc");

        System.out.println(jedis.spop(myset2));
        System.out.println(jedis.sismember(myset2,"aaa"));
        System.out.println(jedis.smembers("myset2"));
        System.out.println(jedis.spop(myset2));
        System.out.println(jedis.smembers("myset2"));



        //jedis对有序集合Sort Set的操作
        //清空数据
        System.out.println(jedis.flushDB());
        String sortSet="mySortSet";

        Map<String,Double> map=new HashMap<>();
        map.put("aaa",1001.1);
        map.put("bbb",1002.2);
        map.put("ccc",1003.3);

        //添加数据
        jedis.zadd(sortSet,1004.1,"ddd");
        jedis.zadd(sortSet,map);

        //获取一个排序的集合中的数量
        System.out.println(jedis.zcard(sortSet));

        //根据下标取值 0代表第一个 -1代表最后一个 -2代表倒数第二个
        jedis.zrange(sortSet,0,-1);

        //返回在范围类的 反向集合
        jedis.zrevrange(sortSet,0,-1);

        //元素下标
        jedis.zscore(sortSet,"aaa");

        //删除元素
        jedis.zrem(sortSet,"aaa");
        jedis.zrange(sortSet,0,-1);

        //分数值在 min 和 max 之间的成员的数量。
        System.out.println(jedis.zcount(sortSet,1001,1003));


        *//*
        *
        * jedis对hashs的操作
        * *//*
        //清空数据
        System.out.println(jedis.flushDB());
        String hashs="myhashs";
        Map<String,String> map1=new HashMap<>();
        map1.put("aaa","11");
        map1.put("bbb","22");
        map1.put("ccc","33");

        //添加数据
        jedis.hmset(hashs,map1);
        jedis.hset(hashs,"ddd","44");

        //获取hash中所有的key
        System.out.println(jedis.hkeys(hashs));

        //获取hash中所有key对应的值
        System.out.println(jedis.hvals(hashs));

        //获取hash里所有元素的数量
        System.out.println(jedis.hlen(hashs));

        //获取hash中所有的域和值 以 Map<String,String>的形式返回
        Map<String,String> mm=jedis.hgetAll(hashs);
        System.out.println(mm);

        //判断给定的key是否存在hasha表中
        System.out.println(jedis.hexists(hashs,"AA"));

        //获取hash中指定字段的值
        System.out.println(jedis.hmget(hashs,"aaa","bbb"));

        //获取指定的值
        jedis.hget(hashs,"aaa");

        //删除指定的值
        jedis.hdel(hashs,"aaa");*/


        }

        }
