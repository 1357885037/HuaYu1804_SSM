package com.huayu.controller;

import com.huayu.bean.Book;
import com.huayu.bean.Neibie;
import com.huayu.bean.Upload;
import com.huayu.service.BookService;
import com.huayu.utills.LayuiData;
import io.swagger.annotations.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api("书籍接口")
@Controller
public class BookController {


   @Autowired
   private BookService bookService;

    @RequestMapping("/query.do")
    @ApiOperation(httpMethod = "get",value = "搜索模糊查询进行分页")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询成功", response =LayuiData.class),
            @ApiResponse(code = 500,message = "查询失败")
    })
    public LayuiData queryall(Model model, @ApiParam(required = false,name = "book",value ="模糊查询参数" ) Book book,@ApiParam(required = false,name = "dangqian",value = "第几页")@RequestParam(value = "page",defaultValue ="0") int dangqian,@ApiParam(required = false,name = "pagesize",value = "一页几条") @RequestParam(value = "limit",defaultValue = "3") int pagesize){
        System.out.println("========>"+book.getName());
        System.out.println("========>"+book.getNid());

        List<Book> list=bookService.queryAll(book,dangqian,pagesize);

        LayuiData layuiData=new LayuiData();
        layuiData.setCode(0);
        layuiData.setCount(bookService.countList(book));
        layuiData.setMsg("");
        layuiData.setData(list);

        //PageInfo pageInfo=new PageInfo(list);
        //model.addAttribute("list",list);
        //model.addAttribute("book",book);
        //model.addAttribute("pageInfo",pageInfo);
        return layuiData;

    }

    @RequestMapping("/addBookOne.do")
    public String addBookOne(Model model){
        List<Neibie> list=bookService.queryNeibie();
        model.addAttribute("list",list);
        return "/adds";
    }

    @RequestMapping("/addBookTwo.do")
    public String addBookTwo(Book book){
        bookService.insert(book);
        return "redirect:query.do";
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Integer delete(String number){
        System.out.println("============>"+number);
        Integer i=1;
        try {
            bookService.delete(number);
        }catch (Exception e){
                i=2;
        }
        return i;
    }

    @RequestMapping("/updateOne.do")
    public String updateOne(String number,Model model){
        Book book=bookService.bookById(number);
        List<Neibie> list=bookService.queryNeibie();
        model.addAttribute("book",book);
        model.addAttribute("list",list);
        return "/update";
    }

    @RequestMapping("/updateTwo.do")
    public String updateTwo(Book book){
        bookService.updateByID(book);
        return "redirect:query.do";
    }

    @RequestMapping("/ceshi.do")
    public String ceshi(){
       Book book=bookService.ceshi();
       System.out.println(book.getName());
        return null;
    }


    @RequestMapping("/deletepl.do")
    public void deletepl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String dd = httpServletRequest.getParameter("deletepls");

        bookService.deletepl(dd);
        try {
            httpServletResponse.getWriter().write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/updateto.do")
    public String updatetoto(Model model,String number){
        Book book=bookService.bookById(number);
        List<Neibie> list=bookService.queryNeibie();
        model.addAttribute("book",book);
        model.addAttribute("list",list);
        return "updateto";
    }

    @RequestMapping("/updateTwo2.do")
    @ResponseBody
    public String updateTwo2(Book book){
        System.out.println("================>:"+book.getNid());
        System.out.println("================>:"+book.getName());
        System.out.println("================>:"+book.getDatebook());
        System.out.println("================>:"+book.getNumber());
        String i="1";
        try{
            bookService.updateByID(book);
        }catch (Exception e){
            i="2";
        }
        return i;
    }

    @RequestMapping("/neibie.do")
    @ResponseBody
    public List<Neibie> neibie(){
        List<Neibie> list=bookService.queryNeibie();
        return list;
    }

    @RequestMapping("/deletespl.do")
    @ResponseBody
    public String deletespl(String str){
        String in="1";
        try {
            bookService.deletepl(str);
             in="1";
        }catch (Exception e){
             in="2";
        }

        return in;
    }

    @RequestMapping("/addlayui.do")
    @ResponseBody
    public String addlayui(Book book){
        String str="1";
        try {
        bookService.insert(book);
         str="1";
        }catch (Exception e){
         str="2";
        }

        return str;
    }

    @RequestMapping("/uploadss.do")
    @ResponseBody
    public Upload upload(@RequestParam("file") MultipartFile pictureFile,HttpServletRequest request){

        // 设置图片名称，不能重复，可以使用uuid
        String picName = UUID.randomUUID().toString();
// 获取文件名
        String oriName = pictureFile.getOriginalFilename();
// 获取图片后缀
        String extName = oriName.substring(oriName.lastIndexOf("."));

        try {
            // 图片上传
            String req=request.getSession().getServletContext().getRealPath("/");
            System.out.println("地址"+req);
// 开始上传
            pictureFile.transferTo(new File(req+"\\imgs\\" + picName + extName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Upload upload=new Upload();
        upload.setCode("0");
        upload.setFilename(picName+extName);

        return upload;
    }

    @RequestMapping("/Mq.do")
    public String Mq(){
        return "layuiqueryall";
    }

    @RequestMapping("/uploadExcel.do")
    @ResponseBody
    public String uploadExcel(@RequestParam("file") MultipartFile pictureFile){
        String i="";
        try {
           bookService.uploadExcel(pictureFile);
           i="1";
       }catch (Exception e){
           i="2";
       }
        return i;
    }

    @RequestMapping("/exportExcel.do")
    public void exportExcel(HttpServletResponse response,Book book) throws IOException {
        response.setContentType("application/x-excel"); // 常见的文件 可以省略
        response.setHeader("content-disposition", "attachment;fileName=data.xls");
            ServletOutputStream on = response.getOutputStream();
            Workbook workbook=bookService.exportExcel(book);
            workbook.write(on);
    }


    @RequestMapping("/sceshi.do")
    public void sceshi(){
        //t添加
        //bookService.sceshi();
        //查询
          bookService.get();
        //删除
          bookService.del();
         //value长度 加了两个引号
          bookService.size();
         //get and set
          bookService.getandset();
    }

    //Redis添加缓存
    @RequestMapping("/huancun.do")
    @ResponseBody
    public Book huancun(String id){
        return bookService.huancun(id);
    }


    //缓存和数据库同步修改
    @RequestMapping("/red_delete.do")
    public void red_delete(String id){
    bookService.red_delete(id);
    }
    @RequestMapping("/red_update.do")
    public void red_upate(Book book){
    book.setName("我*x*xxxx*");
    bookService.red_update(book);
    }


}