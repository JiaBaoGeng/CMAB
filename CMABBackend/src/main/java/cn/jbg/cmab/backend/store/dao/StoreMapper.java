package cn.jbg.cmab.backend.store.dao;

import cn.jbg.cmab.backend.store.bean.Store;
import cn.jbg.cmab.backend.store.bean.StoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int countByExample(StoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int deleteByExample(StoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int deleteByPrimaryKey(Long storeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int insert(Store record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int insertSelective(Store record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    List<Store> selectByExample(StoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    Store selectByPrimaryKey(Long storeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int updateByExampleSelective(@Param("record") Store record, @Param("example") StoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int updateByExample(@Param("record") Store record, @Param("example") StoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int updateByPrimaryKeySelective(Store record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table store
     *
     * @mbggenerated Mon May 14 01:24:39 CST 2018
     */
    int updateByPrimaryKey(Store record);
}