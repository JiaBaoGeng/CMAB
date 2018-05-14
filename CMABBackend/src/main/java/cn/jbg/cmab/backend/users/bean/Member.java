package cn.jbg.cmab.backend.users.bean;

import java.math.BigDecimal;

public class Member {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.member_id
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    private Long memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.user_id
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.member_credit
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    private Integer memberCredit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.member_level
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    private Integer memberLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.member_desc
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    private String memberDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member.account_balance
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    private BigDecimal accountBalance;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.member_id
     *
     * @return the value of member.member_id
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.member_id
     *
     * @param memberId the value for member.member_id
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.user_id
     *
     * @return the value of member.user_id
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.user_id
     *
     * @param userId the value for member.user_id
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.member_credit
     *
     * @return the value of member.member_credit
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public Integer getMemberCredit() {
        return memberCredit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.member_credit
     *
     * @param memberCredit the value for member.member_credit
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public void setMemberCredit(Integer memberCredit) {
        this.memberCredit = memberCredit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.member_level
     *
     * @return the value of member.member_level
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public Integer getMemberLevel() {
        return memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.member_level
     *
     * @param memberLevel the value for member.member_level
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.member_desc
     *
     * @return the value of member.member_desc
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public String getMemberDesc() {
        return memberDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.member_desc
     *
     * @param memberDesc the value for member.member_desc
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public void setMemberDesc(String memberDesc) {
        this.memberDesc = memberDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member.account_balance
     *
     * @return the value of member.account_balance
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member.account_balance
     *
     * @param accountBalance the value for member.account_balance
     *
     * @mbggenerated Sun Apr 22 20:23:35 CST 2018
     */
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}