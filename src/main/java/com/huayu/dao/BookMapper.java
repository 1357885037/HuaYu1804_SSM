package com.huayu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huayu.bean.Book;
import com.huayu.bean.Neibie;
import com.huayu.cache.RedisCache;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace(implementation = RedisCache.class)
public interface BookMapper extends BaseMapper<Book> {


 @Results({
         @Result(column = "nid",property = "leibie",one = @One(select = "leibieById") )
 })
 @SelectProvider(type = SqlProvider.class ,method ="select")
 public List<Book> queryAll(Book book);


 @Select("select * from book")
 public IPage<Book> selectall(Page<Book> page);

 @Select("select * from neibie where nid=#{value}")
 public Neibie leibieById(Integer nid);

 @Select("select * from neibie")
public List<Neibie> queryNeibie();

 //已使用mybatis plus
 @SelectKey( keyProperty = "number",statement = "select uuid()",before = true,resultType = String.class)
 @Insert("insert into book values (#{number},#{name},#{zuoze},#{price},#{jianjie},#{nid},#{datebook}) ")
 public void insertm(Book book);

 //已使用mybatis plus
 @Delete("delete  from book where number=#{value}")
 public void deletess(String number);

 //已使用mybatis plus
 @Select("select * from book where number=#{value}")
 public Book booById(String number);

 //已使用mybatis plus
 @Update("update book set name=#{name},zuoze=#{zuoze},price=#{price},jianjie=#{jianjie},nid=#{nid} where number=#{number}")
 public void updateByIds(Book book);

 @DeleteProvider(type = SqlProvider.class,method = "deletepl")
 public  void deletepl(String dd);


}
