package cn.jbg.cmab.backend.admin.bean;

import java.util.Date;

public class Authority {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority.authority_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    private Long authorityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority.admin_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    private Long adminId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority.module_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    private Long moduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority.granter
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    private Long granter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority.grant_time
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    private Date grantTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authority.authority_desc
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    private String authorityDesc;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority.authority_id
     *
     * @return the value of authority.authority_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public Long getAuthorityId() {
        return authorityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority.authority_id
     *
     * @param authorityId the value for authority.authority_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority.admin_id
     *
     * @return the value of authority.admin_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority.admin_id
     *
     * @param adminId the value for authority.admin_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority.module_id
     *
     * @return the value of authority.module_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority.module_id
     *
     * @param moduleId the value for authority.module_id
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority.granter
     *
     * @return the value of authority.granter
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public Long getGranter() {
        return granter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority.granter
     *
     * @param granter the value for authority.granter
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public void setGranter(Long granter) {
        this.granter = granter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority.grant_time
     *
     * @return the value of authority.grant_time
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public Date getGrantTime() {
        return grantTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority.grant_time
     *
     * @param grantTime the value for authority.grant_time
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authority.authority_desc
     *
     * @return the value of authority.authority_desc
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public String getAuthorityDesc() {
        return authorityDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authority.authority_desc
     *
     * @param authorityDesc the value for authority.authority_desc
     *
     * @mbggenerated Sun Apr 22 20:12:41 CST 2018
     */
    public void setAuthorityDesc(String authorityDesc) {
        this.authorityDesc = authorityDesc;
    }
}