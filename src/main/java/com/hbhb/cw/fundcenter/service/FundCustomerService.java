package com.hbhb.cw.fundcenter.service;

import com.hbhb.cw.flowcenter.vo.NodeInfoVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerExportVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerResVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

public interface FundCustomerService {

    /**
     * 分页获取客户资金列表
     */
    PageResult<CustomerResVO> pageFundCustomer(Integer pageNum, Integer pageSize,
                                               CustomerReqVO cond);

    /**
     * 获取客户资金详情
     */
    CustomerVO getFundCustomerInfo(Integer id);

    /**
     * 条件查询结果导出
     */
    List<CustomerExportVO> getExportList(CustomerReqVO cond);

    /**
     * 查询客户资金的流程信息
     */
    List<NodeInfoVO> getFundCustomerFlow(Long id);
}
