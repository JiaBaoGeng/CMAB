package cn.jbg.cmab.backend.module.dao;

import cn.jbg.cmab.backend.module.bean.Module;
import cn.jbg.cmab.backend.module.bean.ModuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ModuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int countByExample(ModuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int deleteByExample(ModuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int deleteByPrimaryKey(Long moduleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int insert(Module record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int insertSelective(Module record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    List<Module> selectByExample(ModuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    Module selectByPrimaryKey(Long moduleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int updateByExampleSelective(@Param("record") Module record, @Param("example") ModuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int updateByExample(@Param("record") Module record, @Param("example") ModuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int updateByPrimaryKeySelective(Module record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table module
     *
     * @mbggenerated Sun Apr 22 20:24:33 CST 2018
     */
    int updateByPrimaryKey(Module record);
}