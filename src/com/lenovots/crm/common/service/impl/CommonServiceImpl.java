package com.lenovots.crm.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.lenovots.crm.common.service.ICommonService;
import com.lenovots.crm.util.PageBean;



@SuppressWarnings("unchecked")
@Service("baseService")
public class CommonServiceImpl implements ICommonService {

	public static String CUSTOM_COMPONENT_NAME = "customProperties";

	@Resource(name = "crmSessionFactory")
	private SessionFactory sf;

	private Session session;

	public void add(Object entity) {
		session = getCurrentSession();
		session.save(entity);
	}

	public List<?> findAll(Class<?> clazz) {
		session = getCurrentSession();
		String hql = "FROM " + clazz.getName();
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		return list;
	}

	public List<?> findByCause(Class<?> clazz, String[] paramNames, Object[] paramValues) {
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
		List<?> list = query.list();
		return list;
	}

	public Object findById(Integer id, Class<?> clazz) {
		if (id == null) {
			return null;
		}
		session = getCurrentSession();
		return session.get(clazz, id);
	}
	
	public void update(Object entity) {
		session = getCurrentSession();
		session.update(entity);
	}

	public int getCountAll(Class<?> clazz) {
		session = getCurrentSession();
		Query query = session.createQuery("SELECT COUNT(*) FROM " + clazz.getName());
		List<Object> result = query.list();
		return ((Long) result.get(0)).intValue();
	}

	public int getCountByCause(Class<?> clazz, String[] paramNames, Object[] paramValues) {
		session = getCurrentSession();
		StringBuffer hql = new StringBuffer("SELECT COUNT(*) FROM " + clazz.getName());
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
		List<Object> result = query.list();
		return ((Long) result.get(0)).intValue();
	}

	public void deleteById(Integer id, Class<?> clazz) {
		session = getCurrentSession();
		Object entity = session.get(clazz, id);
		session.delete(entity);
	}

	public List<?> findByHql(Class<?> clazz, String hql, Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createQuery(hql);
		if (paramValues != null)
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		List<?> list = query.list();
		return list;
	}

	public List findBySql(Class<?> clazz, String sql, Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if (paramValues != null)
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		return query.list();
	}

	public List<?> findByHql(Class<?> clazz, String hql, int startPos, int length, Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createQuery(hql);
		if (paramValues != null && paramValues.length > 0) {
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		}
		query.setFirstResult(startPos);
		query.setMaxResults(length);

		List<?> list = query.list();
		return list;
	}

	public List<?> findBySql(Class<?> clazz, String sql, int startPos, int length, Object[] paramValues) {
		session = getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if (paramValues != null && paramValues.length > 0) {
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		}
		query.setFirstResult(startPos);
		query.setMaxResults(length);

		List<?> list = query.list();
		return list;
	}

	public PageBean<?> findPageBeanOfAll(Class<?> clazz, int pageNum, int pageSize, String... sortProperty) {
		int totalRecordCount = getCountAll(clazz);
		pageNum = adjustmentPageNum(pageNum, pageSize, totalRecordCount);
		int startPos = pageNum * pageSize;
		int length = pageSize;
		String hql = "FROM " + clazz.getName() + " ";
		if (sortProperty != null)
			for (int i = 0; i < sortProperty.length; i++) {
				if (i == 0) {
					hql += " order by " + sortProperty[i];
				} else {
					hql += "," + sortProperty[i];
				}
			}
		List<?> records = findByHql(clazz, hql, startPos, length, null);
		PageBean<?> page = new PageBean(pageNum, pageSize, totalRecordCount, records);
		return page;
	}

	private int adjustmentPageNum(int pageNum, int pageSize, int totalRecourdCount) {
		int pageCount = totalRecourdCount % pageSize == 0 ? totalRecourdCount / pageSize
				: (totalRecourdCount / pageSize) + 1;
		if (pageNum > pageCount - 1) {
			pageNum = pageCount - 1;
		}
		if (pageNum < 0) {
			pageNum = 0;
		}
		return pageNum;
	}

	public PageBean<?> findPageBeanOfHql(Class<?> clazz, String hql, int pageNum, int pageSize, Object[] paramValues) {
		int totalRecordCount = getCountByCause(clazz, hql, paramValues);
		pageNum = adjustmentPageNum(pageNum, pageSize, totalRecordCount);
		List<?> list = findByHql(clazz, hql, pageSize * pageNum, pageSize, paramValues);

		PageBean<?> pageBean = new PageBean(pageNum, pageSize, totalRecordCount, list);
		return pageBean;
	}

	public int getCountByCause(Class<?> clazz, String hql, Object[] paramValues) {
		if (hql.startsWith("SELECT") || hql.startsWith("Select") || hql.startsWith("select")) {
			int fromPos = hql.indexOf("FROM") == -1 ? (hql.indexOf("From") == -1 ? (hql.indexOf("from"))
					: hql.indexOf("From")) : hql.indexOf("FROM");
			String source = hql.substring(0, fromPos);

			String firstPro = null;
			String[] array = source.split(" ");
			for (String str : array) {
				while (str.indexOf(" ") != -1) {
					str = str.replaceAll(" ", "");
				}
				if (!str.equals("")) {
					String[] strs = str.split(",");
					firstPro = strs[0];
				}
			}

			hql = hql.replace(source, "SELECT COUNT(" + firstPro + ") ");

		} else {
			hql = "SELECT COUNT(*) " + hql;
		}

		session = getCurrentSession();
		Query query = session.createQuery(hql);
		if (paramValues != null) {
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
		}
		List<Object> result = query.list();
		
		return ((Long) result.get(0)).intValue();
	}

	public Session getCurrentSession() {
		return sf.getCurrentSession();
	}

}