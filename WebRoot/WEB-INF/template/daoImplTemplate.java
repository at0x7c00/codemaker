package PACKAGE_VALUE;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lenovots.jdhr.hr.entity.HrCategory;
/**
 * DAO_IMPL_NAME
 * @author auto created by pc
 * CREATE_DATE
 */
@Repository
public class BEAN_CLASSNAMEDaoImpl extends BaseDaoImpl<BEAN_CLASSNAME> implements IBEAN_CLASSNAMEDao {

    @Override
    public Long findListRowCount(BEAN_CLASSNAME LOWER_CLASSNAME) {
        Criteria criteria = getSession().createCriteria(BEAN_CLASSNAME.class).setProjection(Projections.rowCount());
        addQueryCause(criteria,LOWER_CLASSNAME);
        return (Long) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BEAN_CLASSNAME> findListPage(BEAN_CLASSNAME LOWER_CLASSNAME, Page pageInfo){
    	Criteria criteria = getSession().createCriteria(BEAN_CLASSNAME.class).setFirstResult(pageInfo.getStartIndex()).setMaxResults(pageInfo.getNumPerPage());
        addQueryCause(criteria,LOWER_CLASSNAME);
        if(pageInfo.getOrderField()!=null && !pageInfo.getOrderField().trim().equals("")){
         	if(pageInfo.getOrderDirection()==null || pageInfo.getOrderDirection().trim().equals("asc")){
         		criteria.addOrder(Order.asc(pageInfo.getOrderField()));
         	}else{
         		criteria.addOrder(Order.desc(pageInfo.getOrderField()));
         	}
         }else{
         	criteria.addOrder(Order.asc("ID_PROPERTY_NAME")); 
         }
        return criteria.list();
    }
    
    private void addQueryCause(Criteria criteria,BEAN_CLASSNAME LOWER_CLASSNAME){
    	 HAVE_OR_NOTHAVE_CAUSE_FOR_HQL
	}

}