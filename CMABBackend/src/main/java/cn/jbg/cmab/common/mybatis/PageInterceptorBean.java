package cn.jbg.cmab.common.mybatis;


import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;

/**
 * Created by jbg on 2018/3/24.
 */
public class PageInterceptorBean {

    PreparedStatementHandler preparedStatHandler;

    BoundSql boundSql;

    String originalSql;

    Configuration configuration;

    RowBounds rowBounds;

    Connection connection;

    String countSql;

    public PreparedStatementHandler getPreparedStatHandler() {
        return preparedStatHandler;
    }

    public void setPreparedStatHandler(PreparedStatementHandler preparedStatHandler) {
        this.preparedStatHandler = preparedStatHandler;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

    public void setBoundSql(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    public String getOriginalSql() {
        return originalSql;
    }

    public void setOriginalSql(String originalSql) {
        this.originalSql = originalSql;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public RowBounds getRowBounds() {
        return rowBounds;
    }

    public void setRowBounds(RowBounds rowBounds) {
        this.rowBounds = rowBounds;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }
}



