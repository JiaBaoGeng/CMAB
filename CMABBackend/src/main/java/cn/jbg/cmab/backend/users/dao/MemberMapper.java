package cn.jbg.cmab.backend.users.dao;

import cn.jbg.cmab.backend.users.bean.Member;
import cn.jbg.cmab.backend.users.bean.MemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int countByExample(MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int deleteByExample(MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int deleteByPrimaryKey(Long memberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int insert(Member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int insertSelective(Member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    List<Member> selectByExample(MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    Member selectByPrimaryKey(Long memberId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int updateByPrimaryKeySelective(Member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    int updateByPrimaryKey(Member record);
}