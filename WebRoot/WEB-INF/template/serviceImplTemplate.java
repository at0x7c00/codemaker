package PACKAGE_VALUE;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.lenovots.jdhr.util.web.Page;

import BEAN_FULLCLASSNAME;
import INTERFACE_FULLCLASSNAME;

/**
 * SERVICE_IMPL_NAME
 * @author 
 * CREATE_DATE
 */
@Service
public class BEAN_CLASSNAMEServiceImpl extends BaseServiceImpl<BEAN_CLASSNAME> implements IBEAN_CLASSNAMEService {

    @Resource
    private IBEAN_CLASSNAMEDao LOWER_CLASSNAMEDao;
    
    @Override
    public Page<BEAN_CLASSNAME> getListPage(BEAN_CLASSNAME LOWER_CLASSNAME,Page pageInfo) {
      	pageInfo.setTotalCount(LOWER_CLASSNAMEDao.findListRowCount(LOWER_CLASSNAME).intValue());
		pageInfo.setOrderField(pageInfo.getOrderField() == null ? "id": pageInfo.getOrderField());
		pageInfo.setOrderDirection(pageInfo.getOrderDirection() == null ? "asc": pageInfo.getOrderDirection());
		pageInfo.setList(LOWER_CLASSNAMEDao.findListPage(LOWER_CLASSNAME,pageInfo));
        return pageInfo;
    }
}