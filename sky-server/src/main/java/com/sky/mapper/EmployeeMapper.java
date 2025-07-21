package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user, status) " +
            "values (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);
    /** @AutoFill：注解写在方法签名上方
     * 需要提前自定义切面 AutoFillAspect 的 autoFill 方法
     * 自动填充新增数据的公共字段。
     * 该注解用于在插入数据（如 insert 方法）时，自动为实体对象设置
     * 创建时间（createTime）、创建人（createUser）、修改时间（updateTime）、
     * 修改人（updateUser）等公共字段，避免手动赋值，确保数据统一性。
     */


    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 根据主键动态修改属性
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);
    /**
     * 自动填充更新数据的公共字段。
     * 该注解用于在更新数据（如 update 方法）时，自动为实体对象设置
     * 修改时间（updateTime）、修改人（updateUser）等公共字段，确保记录更新信息完整。
     */

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
