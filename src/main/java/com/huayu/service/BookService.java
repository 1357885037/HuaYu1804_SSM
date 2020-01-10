package com.huayu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huayu.bean.Book;
import com.huayu.bean.Neibie;
import com.huayu.dao.BookMapper;
import com.mysql.jdbc.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@EnableCaching
@CacheConfig(cacheNames = "book")
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    public List<Book> queryAll(Book book, int dangqian, int pagesize) {
        Page nn = PageHelper.startPage(dangqian, pagesize);

        QueryWrapper queryWrapper = new QueryWrapper();

        if (!StringUtils.isNullOrEmpty(book.getName())) {
            queryWrapper.like("name", book.getName());
        }

        if (book.getNid() != null && book.getNid() != 0) {
            queryWrapper.eq("nid", book.getNid());
        }
        List<Book> list = bookMapper.selectList(queryWrapper);

        //List<Book> list=bookMapper.queryAll(book);
        return list;

    }

    public Integer countList(Book book) {
        QueryWrapper queryWrapper = new QueryWrapper();

        if (!StringUtils.isNullOrEmpty(book.getName())) {
            queryWrapper.like("name", book.getName());
        }

        if (book.getNid() != null && book.getNid() != 0) {
            queryWrapper.eq("nid", book.getNid());
        }
        return bookMapper.selectList(queryWrapper).size();
    }

    public void deletepl(String dd) {
        if (!StringUtils.isNullOrEmpty(dd)) {
            bookMapper.deletepl(dd);
        }
    }

    public void deletespl(String pl) {
        bookMapper.deleteBatchIds(Arrays.asList(pl.split(",")));
    }

    public List<Neibie> queryNeibie() {
        return bookMapper.queryNeibie();
    }

    public void insert(Book book) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nn = simpleDateFormat.format(date);
        book.setDatebook(nn);
        bookMapper.insert(book);
    }

    public void delete(String number) {
        bookMapper.deleteById(number);
    }

    public Book bookById(String number) {
        return bookMapper.selectById(number);
    }

    public void updateByID(Book book) {
        bookMapper.updateById(book);
    }

    public Book ceshi() {
        return bookMapper.selectById("12");
    }

    public void uploadExcel(MultipartFile pictureFile) {

        List<Book> list = new ArrayList<>();
        InputStream in = null;
        try {
            in = pictureFile.getInputStream();
            Workbook workbook = WorkbookFactory.create(in);     //Excel文件
            Sheet sheet = workbook.getSheetAt(0);              //sheet页 索引从0开始
            int num = sheet.getLastRowNum();                     //最后一行

            for (int i = 1; i <= num; i++) {
                Book book = new Book();
                System.out.println("行==》" + num);
                Row row = sheet.getRow(i); //获取列
                for (int j = 0; j < row.getLastCellNum(); j++) {
                        System.out.println("列==》" + row.getLastCellNum());
                        Cell nn = row.getCell(j);
                        switch (j) {
                        case 0:
                            System.out.println("xxx:"+nn.getNumericCellValue());
                            String number = String.valueOf(nn.getNumericCellValue());
                            number = number.substring(0, number.length() - 2);
                            System.out.println(number);
                            book.setNumber(number);
                            break;
                        case 1:
                            String name = String.valueOf(nn.getStringCellValue());
                            System.out.println(name);
                            book.setName(name);
                            break;
                        case 2:
                            String zuoze = nn.getStringCellValue();
                            System.out.println(zuoze);
                            book.setZuoze(zuoze);
                            break;
                        case 3:
                            double price = nn.getNumericCellValue();
                            System.out.println(price);
                            book.setPrice(price);
                            break;
                        case 4:
                            String jianjie = nn.getStringCellValue();
                            System.out.println(jianjie);
                            book.setJianjie(jianjie);
                            break;
                        case 5:
                            String nid = nn.getStringCellValue();
                            Integer kk = null;
                            if (nid.equals("历史类")) {
                                kk = 2;
                            } else if (nid.equals("政治类")) {
                                kk = 3;
                            }
                            System.out.println(nid);
                            book.setNid(kk);
                            break;
                    }
                }
                list.add(book);
            }
            for (Book book : list) {
                //System.out.println(book.toString());
                bookMapper.insert(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Workbook exportExcel(Book book) {
        QueryWrapper queryWrapper = new QueryWrapper();

        if (!StringUtils.isNullOrEmpty(book.getName())) {
            queryWrapper.like("name", book.getName());
        }

        if (book.getNid() != null && book.getNid() != 0) {
            queryWrapper.eq("nid", book.getNid());
        }
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("书籍页");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("编号");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("名称");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("作者");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue("价格");
        Cell cell5 = row.createCell(4);
        cell5.setCellValue("简介");
        Cell cell6 = row.createCell(5);
        cell6.setCellValue("类别");
        Cell cell7 = row.createCell(7);
        cell7.setCellValue("时间");
        List<Book> list = bookMapper.selectList(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            Row rows = sheet.createRow(i + 1);
            Cell cell8 = rows.createCell(0);
            cell8.setCellValue(list.get(i).getNumber());
            Cell cell9 = rows.createCell(1);
            cell9.setCellValue(list.get(i).getName());
            Cell cell10 = rows.createCell(2);
            cell10.setCellValue(list.get(i).getZuoze());
            Cell cell11 = rows.createCell(3);
            cell11.setCellValue(list.get(i).getPrice());
            Cell cell12 = rows.createCell(4);
            cell12.setCellValue(list.get(i).getJianjie());
            Cell cell13 = rows.createCell(5);
            cell13.setCellValue(list.get(i).getNid());
            Cell cell14 = rows.createCell(7);
            cell14.setCellValue(list.get(i).getDatebook());
        }
        return workbook;
    }


    //redis 实现缓存
    @Cacheable(key = "'book_'+#id")
    public Book huancun(String id){
    return bookMapper.selectById(id);
    }

    //删除缓存和数据库里的值
    @CacheEvict(key="'book_'+#id")
    public void red_delete(String id){
        bookMapper.deleteById(id);
    }
    //redis修改 同步
    @CachePut(key = "'book_'+#book.number")
    public Book red_update(Book book){
        bookMapper.updateById(book);
        return book;
    }





    //redis 对String类型的添加
    public void sceshi(){
        redisTemplate.opsForValue().set("xxxs","xxx");
    }

    //redis 对String类型的查询
    public void get(){
        Object nn = redisTemplate.opsForValue().get("xxxs");
        System.out.println("========>>>"+nn);
    }

    //redis 对String类型的删除
    public void del(){
        redisTemplate.delete("xxxs");
    }

    //redis 对String类型的value长度 加了两个引号
    public void size(){
       Long nn=redisTemplate.opsForValue().size("xxx");
        System.out.println(nn);
    }

    public void getandset(){
        Object zz = redisTemplate.opsForValue().getAndSet("czz", "zzc");
        System.out.println(zz);
    }

}
