package com.sky.service.impl;


import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品和对应的口味（两张表：菜品表、口味表）
     *
     * @param dishDTO
     */
    @Transactional
    //（两张表，加事物注解）Spring 事务管理，要同时往两张表插入数据（菜品表和口味表），一旦有一步失败就必须回滚

    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish(); //创建一个 Dish 实体对象
        BeanUtils.copyProperties(dishDTO, dish);
        //把前端传来的 DishDTO 对象中对应字段的值复制到 Dish 对象中（避免手动逐个 set）


        //向菜品表插入1条数据
        dishMapper.insert(dish);//后绪步骤实现


        //获取insert语句生成的主键值
        Long dishId = dish.getId();
        //拿到插入后生成的菜品主键 ID，用来给每个 flavor 设置 dishId 外键


        List<DishFlavor> flavors = dishDTO.getFlavors();
        //这是一个 “菜品口味”对象的列表，每个 DishFlavor 代表一种口味配置。
        // “装着多个 DishFlavor 对象的列表”，变量名就叫 flavors
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);//后绪步骤实现
        }
//        判断 DishDTO 中是否包含口味列表。
//        给每一个 DishFlavor 设置它所属的 dishId。
//        批量插入到 dish_flavor 表中。

    }

}
