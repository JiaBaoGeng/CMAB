package cn.jbg.cmab.backend.business.bean;

import java.util.ArrayList;
import java.util.List;

public class BusinessExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public BusinessExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andBusinessIdIsNull() {
            addCriterion("business_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNotNull() {
            addCriterion("business_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdEqualTo(Long value) {
            addCriterion("business_id =", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotEqualTo(Long value) {
            addCriterion("business_id <>", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThan(Long value) {
            addCriterion("business_id >", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThanOrEqualTo(Long value) {
            addCriterion("business_id >=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThan(Long value) {
            addCriterion("business_id <", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThanOrEqualTo(Long value) {
            addCriterion("business_id <=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIn(List<Long> values) {
            addCriterion("business_id in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotIn(List<Long> values) {
            addCriterion("business_id not in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdBetween(Long value1, Long value2) {
            addCriterion("business_id between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotBetween(Long value1, Long value2) {
            addCriterion("business_id not between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrIsNull() {
            addCriterion("business_addr is null");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrIsNotNull() {
            addCriterion("business_addr is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrEqualTo(String value) {
            addCriterion("business_addr =", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrNotEqualTo(String value) {
            addCriterion("business_addr <>", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrGreaterThan(String value) {
            addCriterion("business_addr >", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrGreaterThanOrEqualTo(String value) {
            addCriterion("business_addr >=", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrLessThan(String value) {
            addCriterion("business_addr <", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrLessThanOrEqualTo(String value) {
            addCriterion("business_addr <=", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrLike(String value) {
            addCriterion("business_addr like", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrNotLike(String value) {
            addCriterion("business_addr not like", value, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrIn(List<String> values) {
            addCriterion("business_addr in", values, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrNotIn(List<String> values) {
            addCriterion("business_addr not in", values, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrBetween(String value1, String value2) {
            addCriterion("business_addr between", value1, value2, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessAddrNotBetween(String value1, String value2) {
            addCriterion("business_addr not between", value1, value2, "businessAddr");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerIsNull() {
            addCriterion("business_manager is null");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerIsNotNull() {
            addCriterion("business_manager is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerEqualTo(String value) {
            addCriterion("business_manager =", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotEqualTo(String value) {
            addCriterion("business_manager <>", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerGreaterThan(String value) {
            addCriterion("business_manager >", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerGreaterThanOrEqualTo(String value) {
            addCriterion("business_manager >=", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerLessThan(String value) {
            addCriterion("business_manager <", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerLessThanOrEqualTo(String value) {
            addCriterion("business_manager <=", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerLike(String value) {
            addCriterion("business_manager like", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotLike(String value) {
            addCriterion("business_manager not like", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerIn(List<String> values) {
            addCriterion("business_manager in", values, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotIn(List<String> values) {
            addCriterion("business_manager not in", values, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerBetween(String value1, String value2) {
            addCriterion("business_manager between", value1, value2, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotBetween(String value1, String value2) {
            addCriterion("business_manager not between", value1, value2, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneIsNull() {
            addCriterion("business_phone is null");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneIsNotNull() {
            addCriterion("business_phone is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneEqualTo(String value) {
            addCriterion("business_phone =", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneNotEqualTo(String value) {
            addCriterion("business_phone <>", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneGreaterThan(String value) {
            addCriterion("business_phone >", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("business_phone >=", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneLessThan(String value) {
            addCriterion("business_phone <", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneLessThanOrEqualTo(String value) {
            addCriterion("business_phone <=", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneLike(String value) {
            addCriterion("business_phone like", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneNotLike(String value) {
            addCriterion("business_phone not like", value, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneIn(List<String> values) {
            addCriterion("business_phone in", values, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneNotIn(List<String> values) {
            addCriterion("business_phone not in", values, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneBetween(String value1, String value2) {
            addCriterion("business_phone between", value1, value2, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessPhoneNotBetween(String value1, String value2) {
            addCriterion("business_phone not between", value1, value2, "businessPhone");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailIsNull() {
            addCriterion("business_email is null");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailIsNotNull() {
            addCriterion("business_email is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailEqualTo(String value) {
            addCriterion("business_email =", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailNotEqualTo(String value) {
            addCriterion("business_email <>", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailGreaterThan(String value) {
            addCriterion("business_email >", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailGreaterThanOrEqualTo(String value) {
            addCriterion("business_email >=", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailLessThan(String value) {
            addCriterion("business_email <", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailLessThanOrEqualTo(String value) {
            addCriterion("business_email <=", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailLike(String value) {
            addCriterion("business_email like", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailNotLike(String value) {
            addCriterion("business_email not like", value, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailIn(List<String> values) {
            addCriterion("business_email in", values, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailNotIn(List<String> values) {
            addCriterion("business_email not in", values, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailBetween(String value1, String value2) {
            addCriterion("business_email between", value1, value2, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessEmailNotBetween(String value1, String value2) {
            addCriterion("business_email not between", value1, value2, "businessEmail");
            return (Criteria) this;
        }

        public Criteria andBusinessDescIsNull() {
            addCriterion("business_desc is null");
            return (Criteria) this;
        }

        public Criteria andBusinessDescIsNotNull() {
            addCriterion("business_desc is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessDescEqualTo(String value) {
            addCriterion("business_desc =", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotEqualTo(String value) {
            addCriterion("business_desc <>", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescGreaterThan(String value) {
            addCriterion("business_desc >", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescGreaterThanOrEqualTo(String value) {
            addCriterion("business_desc >=", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescLessThan(String value) {
            addCriterion("business_desc <", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescLessThanOrEqualTo(String value) {
            addCriterion("business_desc <=", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescLike(String value) {
            addCriterion("business_desc like", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotLike(String value) {
            addCriterion("business_desc not like", value, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescIn(List<String> values) {
            addCriterion("business_desc in", values, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotIn(List<String> values) {
            addCriterion("business_desc not in", values, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescBetween(String value1, String value2) {
            addCriterion("business_desc between", value1, value2, "businessDesc");
            return (Criteria) this;
        }

        public Criteria andBusinessDescNotBetween(String value1, String value2) {
            addCriterion("business_desc not between", value1, value2, "businessDesc");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table business
     *
     * @mbggenerated do_not_delete_during_merge Thu Apr 26 21:26:49 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table business
     *
     * @mbggenerated Thu Apr 26 21:26:49 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}