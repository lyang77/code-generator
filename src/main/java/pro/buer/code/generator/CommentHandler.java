package pro.buer.code.generator;

import java.sql.Connection;
import java.util.Map;

/**
 *
 *
 * create date:2017/7/26
 * remark:
 */
public interface CommentHandler{

    Map<String,String> getTableComment(Connection connection);

    Map<String,String> getFieldComment(Connection connection, String table);
}
