package com.hbhb.cw.fundcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.fundcenter.model.FundCustomer;
import com.hbhb.cw.fundcenter.web.vo.CustomerReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerResVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatVO;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.annotation.Param;

import java.util.List;

public interface FundCustomerMapper extends BaseMapper<FundCustomer> {

    PageResult<CustomerResVO> selectPageByCond(@Param("cond") CustomerReqVO cond,
                                               @Param("list") List<Integer> unitIds,
                                               PageRequest request);

    PageResult<CustomerStatVO> selectStatPageByCond(@Param("cond") CustomerStatReqVO cond,
                                                    @Param("list") List<Integer> unitIds,
                                                    PageRequest request);
}