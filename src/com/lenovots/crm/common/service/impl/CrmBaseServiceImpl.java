package com.lenovots.crm.common.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.lenovots.crm.common.service.IBaseService;
import com.lenovots.crm.util.PageBean;




@SuppressWarnings("unchecked")
public class CrmBaseServiceImpl<T> implements IBaseService<T> {
	

    
	protected Class clazz;
	
	@Resource(name="crmSessionFactory")
	private SessionFactory sf;
	
	private Session session;

	public CrmBaseServiceImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class) pt.getActualTypeArguments()[0];
	}

    /** 
     * 重新加载hibernate映射
     */
   public synchronized  void reload() {
      ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
      LocalSessionFactoryBean lsfb = (LocalSessionFactoryBean)ac.getBean("&crmSessionFactory");    
      try {
         lsfb.afterPropertiesSet(); 
      } catch (Exception e) {    
         e.printStackTrace();    
      }    
    }
	  
	
	public Object add(T entity) {
		session =getCurrentSession();
		return session.save(entity);
	}

	public void delete(Integer id) {
		session = getCurrentSession();
		session.delete(findById(id));
	}

	
	public void batchDelete(Integer[] ids){
		for(Integer id : ids){
			delete(id);
		}
	}
	
	public void batchDelete(List<Integer> ids){
		for(Integer id : ids){
			delete(id);
		}
	}
	
	public List<T> findAll() {
		session = getCurrentSession();
		String hql = "FROM " + this.clazz.getName();
		Query query = session.createQuery(hql);
		List<T> list = query.list();
		return list;
	}
	
	public synchronized void batchAdd(List<T> entityList){  
		try{
			session = getCurrentSession();
	        for (int i=0;i< entityList.size();i++) {  
		        session.save(entityList.get(i));  
	        }  
		}catch(Exception e){
			e.printStackTrace();
		}
    }    
	
	public void batchExecute(final String hql, final Object[] paramValues) {  
		session = getCurrentSession();
		Query query = session.createQuery(hql);
		if (paramValues != null)
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
		}
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.in("id", paramValues));
		//criteria.lis
		//query.executeUpdate();
    } 

	public List<T> findByCause(String[] paramNames, Object[] paramValues) {
		session = getCurrentSession();
		StringBuffer hql = new StringBuffer();
		hql.append("FROM " + clazz.getName() + " WHERE 1=1 ");
		int count = 0;
		for (String str : paramNames) {
			if (!paramNames[count].trim().equals("")) {
				hql.append("AND " + str + "=? ");
				count++;
			}
		}
		Query query = session.createQuery(hql.toString());
		for (int i = 0; i < count; i++) {
			query.setParameter(i, paramValues[i]);
		}
		List<T> list = query.list();
		return list;
	}

	public T findById(Integer id) {
		if(id==null){
			return null;
		}
		session = getCurrentSession();
		return (T) session.get(this.clazz, id);
	}

	public void update(T entity) {
		session = getCurrentSession();
		session.update(entity);
	}
	

	/**
	 *获取当前线程绑定的Session
	 * @return
	 */
	public List<T> findAll(int startPos, int length) {
		session = getCurrentSession();
		Query query = session.createQuery("FROM " + clazz.getName());
		//session.createSQLQuery(arg0)
		query.setFirstResult(startPos);
		query.setMaxResults(length);
		List<T> list = query.list();
		return list;
	}

	public List<T> findByCause(String[] paramNames, Object[] paramValues,
			int startPos, int length) {
		session = getCurrentSession();
		StringBuffer hql = new StringBuffer();
		hql.append("FROM " + clazz.getName() + " WHERE 1=1 ");
		int count = 0;
		for (String str : paramNames) {
			if (!paramNames[count].trim().equals("")) {
				hql.append("AND " + str + "=? ");
				count++;
			}
		}
		Query query = session.createQuery(hql.toString());
		for (int i = 0; i < count; i++) {
			query.setParameter(i, paramValues[i]);
		}
		
		query.setFirstResult(startPos);
		query.setMaxResults(length);
		List<T> list = query.list();
		return list;
	}

	public int getCountAll() {
		session = getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(*) FROM " + clazz.getName());
		List<Object> result=query.list();
		return ((Long)result.get(0)).intValue();
	}

	
	public int getCountByCause(String[] paramNames, Object[] paramValues) {
		session = getCurrentSession();
		StringBuffer hql=new StringBuffer("SELECT COUNT(*) FROM " + clazz.getName());
		int count = 0;
		for (String str : paramNames) {
			if (!paramNames[count].trim().equals("")) {
				hql.append("AND " + str + "=? ");
				count++;
			}
		}
		Query query = session.createQuery(hql.toString());
		for (int i = 0; i < count; i++) {
			query.setParameter(i, paramValues[i]);
		}
		List<Object> result=query.list();
		return ((Long)result.get(0)).intValue();
	}

	public void deleteById(Integer id) {
		session = getCurrentSession();
		T entity = (T) session.get(clazz, id);
		session.delete(entity);
	}

	public List<T> findByHql(String hql, Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createQuery(hql);
		if (paramValues != null)
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		List<T> list = query.list();
		if(list==null){
			return Collections.EMPTY_LIST;
		}
		return list;
	}
	
	public List findBySql(String sql, Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if (paramValues != null)
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		return query.list();
	}

	public List<T> findByHql(String hql, int startPos, int length,Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createQuery(hql);
		if (paramValues!=null&&paramValues.length>0){
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		}
		query.setFirstResult(startPos);
		query.setMaxResults(length);
		
		List<T> list = query.list();
		return list;
	}

	public List<T> findBySql(String sql, int startPos, int length,Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if (paramValues!=null&&paramValues.length>0){
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		}
		query.setFirstResult(startPos);
		query.setMaxResults(length);
		
		List<T> list = query.list();
		return list;
	}
	
	public PageBean<T> findPageBeanOfAll(int pageNum, int pageSize,String...sortProperty) {
		
		int totalRecordCount=getCountAll();
		pageNum = adjustmentPageNum(pageNum, pageSize, totalRecordCount);
		int startPos=pageNum*pageSize;
		int length=pageSize;
		String hql="FROM "+clazz.getName()+" ";
		if(sortProperty!=null)
		for(int i=0;i<sortProperty.length;i++){
			if(i==0){
				hql+=" order by "+sortProperty[i];
			}else{
				hql+=","+sortProperty[i];
			}
		}
		List<T> records=findByHql(hql,startPos,length,null);
	    PageBean<T> page=new PageBean<T>(pageNum,pageSize,totalRecordCount,records);
		return page;
	}
	
	

	private int adjustmentPageNum(int pageNum, int pageSize, int totalRecourdCount) {
		int pageCount=totalRecourdCount%pageSize==0?totalRecourdCount/pageSize:(totalRecourdCount/pageSize)+1;
		if(pageNum>pageCount-1){
			pageNum=pageCount-1;
		}
		if(pageNum<0){
			pageNum=0;
		}
		return pageNum;
	}

	public PageBean<T> findPageBeanByCause(String[] paramNames,Object[] paramValues,int pageNum,int pageSize,String...sortProperty) {
		int totalRecordCount=getCountAll();
		pageNum = adjustmentPageNum(pageNum, pageSize, totalRecordCount);
		int startPos=pageNum*pageSize;
		int length=pageSize;
		StringBuffer hql=new StringBuffer();
		hql.append("FROM "+clazz.getName()+" WHERE 1 = 1");
		int count=0;
		for (String str : paramNames) {
			if (!paramNames[count].trim().equals("")) {
				hql.append("AND " + str + "=? ");
				count++;
			}
		}
		if(sortProperty!=null)
		for(int i=0;i<sortProperty.length;i++){
			if(i==0){
				hql.append(" order by "+sortProperty[i]);
			}else{
				hql.append(","+sortProperty[i]);
			}
		}
		List<T> records=findByHql(hql.toString(),startPos,length,paramValues);
	    PageBean<T> page=new PageBean<T>(pageNum,pageSize,totalRecordCount,records);
		return page;
	}

	public PageBean<T> findPageBeanOfHql(String hql, int pageNum, int pageSize,Object[] paramValues) {
		int totalRecordCount=getCountByCause(hql, paramValues);
		pageNum = adjustmentPageNum(pageNum, pageSize, totalRecordCount);
		List<T> list=findByHql(hql, pageSize*pageNum, pageSize, paramValues);
		
		PageBean<T> pageBean=new PageBean<T>(pageNum,pageSize,totalRecordCount,list);
		return pageBean;
	}

	public int getCountByCause(String hql, Object[] paramValues){
		if(hql.startsWith("SELECT")||hql.startsWith("Select")||hql.startsWith("select")){
			int fromPos=hql.indexOf("FROM")==-1?(hql.indexOf("From")==-1?(hql.indexOf("from")):hql.indexOf("From")):hql.indexOf("FROM");
			String source=hql.substring(0, fromPos);
			
			String firstPro=null;
			String[] array=source.split(" ");
            for(String str:array){
            	while(str.indexOf(" ")!=-1){
            		str=str.replaceAll(" ", "");
            	}
            	if(!str.equals("")){
            		String[] strs=str.split(",");
            		firstPro=strs[0];
            	}
            }
			
			hql=hql.replace(source, "SELECT COUNT("+firstPro+") ");
			
		}else{
			hql="SELECT COUNT(*) "+hql;
		}
	    session=getCurrentSession();
	    Query query=session.createQuery(hql);
	    if(paramValues!=null){  
		    for(int i=0;i<paramValues.length;i++){
		    	query.setParameter(i, paramValues[i]);
		    }
	    }
	    List<Object> result=query.list();
		return ((Long)result.get(0)).intValue();
	}

	public Session getCurrentSession() {
		Session s = sf.getCurrentSession();
		if(!s.isOpen()){
			s = sf.openSession();
		}
		return s;
	}
	

	public Class getEntityClass() {
		return this.clazz;
	}
}