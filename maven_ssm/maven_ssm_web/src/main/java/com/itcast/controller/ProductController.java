package com.itcast.controller;

import com.github.pagehelper.PageInfo;
import com.itcast.domain.Product;
import com.itcast.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/19 19:57
 * @Description: 商品管理的控制层
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

//----------------------------保存操作---------------------------------------//
    /**
    * @Author: 32725
    * @Param: [product]
    * @Return: java.lang.String
    * @Description: 新建商品
    **/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(Product product) throws Exception {
        productService.save(product);
        return "redirect:/product/find";
    }
//----------------------------删除操作---------------------------------------//
    /**
    * @Author: 32725
    * @Param: [ids, referer]
    * @Return: java.lang.String
    * @Description:  删除选中的商品
    **/
    @RequestMapping(value = "/delete/{_ids}", method = RequestMethod.GET)
    public String deleteSelect(@PathVariable String _ids, @RequestHeader("referer") String referer) throws Exception {

        String[] ids = _ids.split(",");
        for (String id : ids) {
            productService.deleteById(id);
        }
        return "redirect:" + referer;
    }
//----------------------------更新操作---------------------------------------//
    /**
    * @Author: 32725
    * @Param: [status, referer]
    * @Return: java.lang.String
    * @Description: 修改选中商品的状态
    **/
    @RequestMapping("update/{status}/{_ids}")
    public String updateStatus(@PathVariable Integer status,@PathVariable String _ids,
                               @RequestHeader("referer") String referer)throws Exception{

        String[] ids = _ids.split(",");
        for (String id : ids) {
            productService.updateStatus(id,status);
        }
        return "redirect:"+referer;
    }

    /**
    * @Author: 32725
    * @Param: [id, model]
    * @Return: java.lang.String
    * @Description: 修改商品数据时回显数据
    **/
    @RequestMapping("/update/{id}")
    public String update(@PathVariable String id,Model model) throws Exception {
        Product product = productService.findOne(id);
        model.addAttribute("product", product);
        return "product-update";
    }
    /**
    * @Author: 32725
    * @Param: [product]
    * @Return: java.lang.String
    * @Description: 更新商品数据
    **/
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public String updateProduct(Product product)throws Exception{

        productService.update(product);
        return "redirect:/product/find";
    }
//----------------------------查询操作---------------------------------------//
    /**
     * @Author: 32725
     * @Param: [model]
     * @Return: java.lang.String
     * @Description: 查询所有用户
     **/
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/find",method = {RequestMethod.GET})
    public String findAll(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer _pageSize,
                          @RequestParam(name = "term", required = false, defaultValue = "") String term,
                          Model model) throws Exception {
        //0.全局通过每页展示数据量来设置默认pageSize
        Integer pageSize =5;
        if (_pageSize!=5){
            pageSize=_pageSize;
        }
        //1.传递分页参数，currentPage当前页，默认为1，pageSize每页展示的数据条数，默认为5
        List<Product> list = productService.findAll(pageNum,pageSize,term);
        //2.使用PageInfo封装分页数据
        PageInfo pageInfo = new PageInfo(list);
        //3.添加入request域中
        model.addAttribute("pageInfo", pageInfo);
        //4.跳转展示页面
        return "product-list";
    }
}
