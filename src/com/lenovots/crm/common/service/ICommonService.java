package com.lenovots.crm.common.service;

import java.util.List;

import org.hibernate.Session;

import com.lenovots.crm.util.PageBean;


/**
 * dao访问基类
 * 
 * @author 胡桥
 * @param <T>
 */
public interface ICommonService {

	/**
	 * 添加
	 */
	public void add(Object entity);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteById(Integer id, Class<?> clazz);

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public void update(Object entity);

	/**
	 * 按照Id查询
	 * 
	 * @param id
	 * @return
	 */
	public Object findById(Integer id, Class<?> clazz);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<?> findAll(Class<?> clazz);

	/**
	 * 分页查询所有
	 * 
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            每页显示记录数
	 * @param sortProperty
	 *            指定0个或多个排序字段，可以指定升序或降薪的排序方式,如：<br/>
	 *            findPageBeanByCause(20,10,"id asc","name desc") <br/>
	 *            【注】当数据库表不存在指定的字段时将抛异常
	 * @return 分页查询对象PageBean
	 * @return
	 */
	public PageBean<?> findPageBeanOfAll(Class<?> clazz, int pageNum, int pageSize, String... sortProperty);

	/**
	 * 查询所有记录数量
	 * 
	 * @return
	 */
	public int getCountAll(Class<?> clazz);

	/**
	 * 查询满足条件的所有记录数量
	 * 
	 * @param paramNames
	 * @param paramValues
	 * @param startPos
	 * @param length
	 * @return
	 */
	public int getCountByCause(Class<?> clazz, String[] paramNames, Object[] paramValues);

	/**
	 * 获取指定条件的记录总数
	 * 
	 * @param hql
	 * @param paramValues
	 * @return
	 */
	public int getCountByCause(Class<?> clazz, String hql, Object[] paramValues);

	/**
	 * 按照给定的hql查询
	 * 
	 * @param hql
	 * @param paramValues
	 *            传递的参数，没有参数时可以传递null
	 * @return
	 */
	public List<?> findByHql(Class<?> clazz, String hql, Object[] paramValues);

	/**
	 * 按照给定的sql查询
	 * 
	 * @param sql
	 * @param paramValues
	 *            传递的参数，没有参数时可以传递null
	 * @return
	 */
	public List<?> findBySql(Class<?> clazz, String sql, Object[] paramValues);

	/**
	 * 按照给定的hql分页查询
	 * 
	 * @return
	 * @param paramValues
	 *            传递的参数，没有参数时可以传递null
	 */
	public List<?> findByHql(Class<?> clazz, String hql, int startPos, int length, Object[] paramValues);

	/**
	 * 按照给定的sql分页查询
	 * 
	 * @return
	 * @param paramValues
	 *            传递的参数，没有参数时可以传递null
	 */
	public List<?> findBySql(Class<?> clazz, String sql, int startPos, int length, Object[] paramValues);

	/**
	 * 按照给定的hql分页查询
	 * 
	 * @return
	 * @param paramValues
	 *            传递的参数，没有参数时可以传递null
	 */
	public PageBean<?> findPageBeanOfHql(Class<?> clazz, String hql, int pageNum, int pageSize, Object[] paramValues);

	/**
	 * 获取与当前请求相关的Session
	 * 
	 * @return
	 */
	public Session getCurrentSession();

}
