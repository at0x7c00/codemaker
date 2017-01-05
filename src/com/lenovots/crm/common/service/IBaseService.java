package com.lenovots.crm.common.service;

import java.util.List;

import org.hibernate.Session;

import com.lenovots.crm.util.PageBean;



/**
 * dao访问基类 
 * @author 胡桥
 * @param <T>
 */
public interface IBaseService<T>{
	
	/**
	 * 添加
	 */
	public Object add(T entity);
	
	/**
	 * 批量添加
	 * @param entityList
	 * @param size
	 */
	public void batchAdd(List<T> entityList);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDelete(Integer[] ids);
	
	public void batchDelete(List<Integer> ids);
	
	/**
	 * 更新
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 按照Id查询
	 * @param id
	 * @return 
	 */
	public T findById(Integer id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 分页查询所有
	 * @param startPos 起始位置
	 * @param length 最多返回记录数
	 * @return
	 */
	public List<T> findAll(int startPos,int length);
	
	/** 
	 * 分页查询所有
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示记录数
	 * @param sortProperty 指定0个或多个排序字段，可以指定升序或降薪的排序方式,如：<br/>
	 * findPageBeanByCause(20,10,"id asc","name desc") <br/>
	 * 【注】当数据库表不存在指定的字段时将抛异常
	 * @return 分页查询对象PageBean
	 * @return 
	 */
	public PageBean<T> findPageBeanOfAll(int pageNum,int pageSize,String...sortProperty);
	
	/**
	 * 按照给定的条件查询
	 * 如：查询User表时<br/>
	 *    paramNames=new String[]{"name","password"}<br/>
	 *    paramValues=new Object[]{"张三","1234"}<br/>
	 *    则生成的Sql查询为：select * from user where name="张三" and password="1234"<br/>
	 * @param paramNames
	 * @param paramValues
	 * @return
	 */
	public List<T> findByCause(String[] paramNames,Object[] paramValues);
	
	/**
	 * 按照给定的分页条件查询
	 * @param paramNames
	 * @param paramValues
	 * @return
	 */
	public List<T> findByCause(String[] paramNames,Object[] paramValues,int startPos,int length);
	
	/**
	 * 按照条件分页查询<br/>
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示记录数
	 * @param sortProperty 指定0个或多个排序字段，可以指定升序或降薪的排序方式,如：<br/>
	 * findPageBeanByCause(20,10,"id asc","name desc") <br/>
	 * 【注】当数据库表不存在指定的字段时将抛异常
	 * @return 分页对象PageBean
	 */
	public PageBean<T> findPageBeanByCause(String[] paramNames,Object[] paramValues,int pageNum,int pageSize,String...sortProperty);
	
	/**
	 * 查询所有记录数量
	 * @return
	 */
	public int getCountAll();
	
	/**
	 * 查询满足条件的所有记录数量
	 * @param paramNames
	 * @param paramValues
	 * @param startPos
	 * @param length
	 * @return
	 */
	public int getCountByCause(String[] paramNames, Object[] paramValues) ;
	
	/**
	 * 获取指定条件的记录总数
	 * @param hql
	 * @param paramValues
	 * @return
	 */
	public int getCountByCause(String hql,Object[]paramValues);
	
	/**
	 * 按照给定的hql查询
	 * @param hql
	 * @param paramValues 传递的参数，没有参数时可以传递null
	 * @return
	 */
	public List<T> findByHql(String hql,Object[] paramValues);

	/**
	 * 按照给定的sql查询
	 * @param sql
	 * @param paramValues 传递的参数，没有参数时可以传递null
	 * @return
	 */
	public List findBySql(String sql,Object[] paramValues);
	
	/**
	 * 按照给定的hql分页查询
	 * @return
	 * @param paramValues 传递的参数，没有参数时可以传递null
	 */
	public List<T> findByHql(String hql,int startPos,int length,Object[] paramValues);

	/**
	 * 按照给定的sql分页查询
	 * @return
	 * @param paramValues 传递的参数，没有参数时可以传递null
	 */
	public List findBySql(String sql,int startPos,int length,Object[] paramValues);
	
	/**
	 * 按照给定的hql分页查询
	 * @return
	 * @param paramValues 传递的参数，没有参数时可以传递null
	 */
	public PageBean<T> findPageBeanOfHql(String hql,int pageNum,int pageSize,Object[] paramValues);
	
	/**
	 * 获取与当前请求相关的Session 
	 * @return
	 */
	public Session getCurrentSession();
	
}
