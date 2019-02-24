package com.itcast.service.impl;

import com.github.pagehelper.PageHelper;
import com.itcast.dao.ProductDao;
import com.itcast.domain.Product;
import com.itcast.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Auther : 32725
 * @Date: 2019/2/19 19:46
 * @Description: 商品管理的业务层接口实现类
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
//----------------------------保存操作---------------------------------------//
    /**
     * @Author: 32725
     * @Param: [product]
     * @Return: void
     * @Description: 保存商品信息
     **/
    @Override
    public void save(Product product) throws Exception{
        productDao.save(product);
    }
//----------------------------更新操作---------------------------------------//
    /**
     * @Author: 32725
     * @Param: [product]
     * @Return: void
     * @Description: 更新用户数据
     **/
    @Override
    public void update(Product product) throws Exception{
        productDao.update(product);
    }
    /**
     * @Author: 32725
     * @Param: [status, ids]
     * @Return: void
     * @Description: 修改选中商品的状态
     **/
    @Override
    public void updateStatus(String id,Integer status) throws Exception {
        productDao.updateStatus(id,status);
    }


//----------------------------删除操作---------------------------------------//
    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 根据用户id删除用户
     **/
    @Override
    public void deleteById(String id)throws Exception {
        productDao.deleteById(id);
    }

//----------------------------查询操作---------------------------------------//
    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.Product>
     * @Description: 查找商品表中所有数据
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Product> findAll(Integer pageNum,Integer pageSize,String term) throws Exception{

        //1.使用PageHelper静态方法进行物理分页
        PageHelper.startPage(pageNum, pageSize);
        //2.判断条件是否存在,存在就加上模糊查询
        if (term!=null&&!term.equals("")){
            term="%"+term+"%";
        }
        //3.执行查询方法
        return productDao.findAll(term);
    }

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: com.itcast.domain.Product
     * @Description: 根据id查找商品
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Product findOne(String id)throws Exception{

        return productDao.findOne(id);
    }

}
