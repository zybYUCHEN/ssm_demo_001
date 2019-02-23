package com.itcast.dao;

import com.itcast.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther : 32725
 * @Date: 2019/2/19 19:27
 * @Description: 商品的持久层接口
 */
public interface ProductDao {

//----------------------------保存操作---------------------------------------//


    /**
     * @Author: 32725
     * @Param: [product]
     * @Return: void
     * @Description: 保存商品信息
     **/
    void save(Product product) throws Exception;
//----------------------------更新操作---------------------------------------//

    /**
     * @Author: 32725
     * @Param: [product]
     * @Return: void
     * @Description: 更新用户数据
     **/
    void update(Product product) throws Exception;


    /**
     * @Author: 32725
     * @Param: [status, ids]
     * @Return: void
     * @Description: 修改选中商品的状态
     **/
    void updateStatus(@Param("id") String id, @Param("status") Integer status)throws Exception;

//----------------------------删除操作---------------------------------------//

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: void
     * @Description: 根据用户id删除用户
     **/
    void deleteById(String id) throws Exception;

//----------------------------查询操作---------------------------------------//

    /**
     * @Author: 32725
     * @Param: [id]
     * @Return: com.itcast.domain.Product
     * @Description: 根据id查找商品
     **/
    Product findOne(String id)throws Exception;

    /**
     * @Author: 32725
     * @Param: []
     * @Return: java.util.List<com.itcast.domain.Product>
     * @Description: 查找商品表中所有数据
     */
    List<Product> findAll()throws Exception;
}
