package PACKAGE_VALUE;
import java.util.List;

import com.lenovots.jdhr.employee.entity.EmployeeAttribute;

import BEAN_FULLCLASSNAME;
/**
 * DAO_NAME
 * @author auto created by pc
 * CREATE_DATE
 */
public interface IBEAN_CLASSNAMEDao extends IBaseDao<BEAN_CLASSNAME> {

    /**
     * 查询记录数
     * @param role
     * @return
     */
	Long findListRowCount(BEAN_CLASSNAME LOWER_CLASSNAME);

    /**
     * 分页查询角色信息
     * @param role
     * @return
     */
    List<BEAN_CLASSNAME> findListPage(BEAN_CLASSNAME LOWER_CLASSNAME, Page pageInfo);
}