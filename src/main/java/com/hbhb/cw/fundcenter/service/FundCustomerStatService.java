package com.hbhb.cw.fundcenter.service;

import com.hbhb.cw.fundcenter.web.vo.CustomerStatExportVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerStatVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author tpson
 */
public interface FundCustomerStatService {

    /**
     * 分页查询客户资金数据统计列表
     */
    PageResult<CustomerStatVO> pageCustomerStat(Integer pageNum, Integer pageSize, CustomerStatReqVO cond);

//    /**
//     * 查询一条数据资金统计
//     */
//    CustomerStatVO getStatDetail(Long id);

    /**
     * 客户资金统计数据导出
     */
    List<CustomerStatExportVO> getExportList(CustomerStatReqVO cond);
}
