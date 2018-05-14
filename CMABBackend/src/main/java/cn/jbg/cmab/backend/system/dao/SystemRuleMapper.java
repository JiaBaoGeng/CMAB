package cn.jbg.cmab.backend.system.dao;

import cn.jbg.cmab.backend.system.bean.SystemRule;
import cn.jbg.cmab.backend.system.bean.SystemRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int countByExample(SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int deleteByExample(SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int deleteByPrimaryKey(Long ruleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int insert(SystemRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int insertSelective(SystemRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    List<SystemRule> selectByExample(SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    SystemRule selectByPrimaryKey(Long ruleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int updateByExampleSelective(@Param("record") SystemRule record, @Param("example") SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int updateByExample(@Param("record") SystemRule record, @Param("example") SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int updateByPrimaryKeySelective(SystemRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Wed May 02 22:31:46 CST 2018
     */
    int updateByPrimaryKey(SystemRule record);
}