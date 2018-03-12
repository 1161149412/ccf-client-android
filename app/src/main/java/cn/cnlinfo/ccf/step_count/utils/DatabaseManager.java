package cn.cnlinfo.ccf.step_count.utils;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

import cn.cnlinfo.ccf.CCFApplication;

/**
 * Created by JP on 17/11/2
 */

public class DatabaseManager {
    private static String TABLE_NAME;
    private static DatabaseManager databaseManager;
    private static LiteOrm liteOrm;
    private DatabaseManager(){
        liteOrm = LiteOrm.newCascadeInstance(CCFApplication.getContext(),TABLE_NAME);
        /**
         * 开启debug来调试
         */
        liteOrm.setDebugged(true);
    }


    public static synchronized DatabaseManager createTableAndInstance(String tableName){
        TABLE_NAME = tableName+".db";
        if (databaseManager==null){
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    /**
     * 插入一条记录
     * @param t
     */
    public <T> long insert(T t) {
        return liteOrm.save(t);
    }

    /**
     * 插入所有记录
     * @param list
     */
    public  <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     * @param cla
     * @return
     */
    public  <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> getQueryByWhere(Class<T> cla, String[] field, String[] value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i<field.length;i++){
            builder.append(field[i]+"=?");
            if (i==field.length-1){
                break;
            }else {
                builder.append(" and ");
            }
        }
//        Logger.d(builder.toString());
        return liteOrm.<T>query(new QueryBuilder(cla).where(builder.toString(), value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public  <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }

    /**
     * 删除一个数据
     * @param t
     * @param <T>
     */
    public <T> void delete( T t){
        liteOrm.delete( t ) ;
    }

    /**
     * 删除一个表
     * @param cla
     * @param <T>
     */
    public  <T> void delete( Class<T> cla ){
        liteOrm.delete( cla ) ;
    }

    /**
     * 删除集合中的数据
     * @param list
     * @param <T>
     */
    public  <T> void deleteList( List<T> list ){
        liteOrm.delete( list ) ;
    }

    /**
     * 删除数据库
     */
    public void deleteDatabase(){
        liteOrm.deleteDatabase() ;
    }

    /**
     * 仅在已存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }


    /**
     * 更新所有的数据
     * @param list
     * @param <T>
     */
    public static <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }

    /**
     * 关闭数据库
     */

    public void closeDatabase(){
        liteOrm.close();
    }

}
