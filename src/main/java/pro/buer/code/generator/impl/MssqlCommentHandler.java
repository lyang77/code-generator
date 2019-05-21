package pro.buer.code.generator.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import pro.buer.code.generator.CommentHandler;

/**
 *
 *
 * create date:2017/7/26
 * remark:
 */
public class MssqlCommentHandler implements CommentHandler{

    @Override
    public Map<String,String> getTableComment(Connection connection){
        ResultSet rs = runSql(connection, "SELECT objname, cast(value as varchar(500)) as value FROM fn_listextendedproperty ('MS_DESCRIPTION','schema', 'dbo', 'table', null,null,null) ");
        return buildMap(rs);
    }

    @Override
    public Map<String,String> getFieldComment(Connection connection, String table){
        ResultSet rs = runSql(connection, "SELECT objname, cast(value as varchar(500)) as value FROM fn_listextendedproperty ('MS_DESCRIPTION','schema', 'dbo', 'table', '" + table + "','column',null) ");
        return buildMap(rs);
    }

    private Map<String,String> buildMap(ResultSet rs){
        Map<String,String> map = new HashMap<>();
        try{
            if(rs != null){
                while(rs.next()){
                    map.put(rs.getString("objname"), rs.getString("value"));
                }
                return map;

            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return map;
    }

    private ResultSet runSql(Connection connection, String sql){
        try{
            Statement st = connection.createStatement();
            return st.executeQuery(sql);
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
