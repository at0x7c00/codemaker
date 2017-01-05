package PACKAGE_VALUE;

import com.lenovots.demo.func.service.PageInfo;

import BEAN_FULLCLASSNAME;
/**
 * SERVICE_NAME
 * @author 
 * CREATE_DATE
 */
public interface IBEAN_CLASSNAMEService extends IBaseService<BEAN_CLASSNAME> {

    /**
     * 得到分页信息
     * @param role
     * @return
     */
    public Page<BEAN_CLASSNAME> getListPage(BEAN_CLASSNAME LOWER_CLASSNAME,Page pageInfo);
}
