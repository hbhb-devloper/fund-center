package com.hbhb.cw.fundcenter.service;

import com.hbhb.cw.fundcenter.model.FundUnit;
import com.hbhb.cw.fundcenter.web.vo.FundUnitImportVO;
import com.hbhb.cw.fundcenter.web.vo.FundUnitVO;

import org.beetl.sql.core.page.PageResult;

import java.util.List;

public interface FundUnitService {

    /**
     * 分页获取单位信息列表
     */
    PageResult<FundUnit> pageFundUnit(Integer pageNum, Integer pageSize,
                                      String unitCode, String unitName);

    /**
     * 获取单位信息列表
     */
    List<FundUnitVO> listFundUnit(String unitCode, String unitName);

    /**
     * 修改单位信息
     */
    void updateFundUnit(FundUnitVO vo);

    /**
     * 批量保存客户资金单位信息
     */
    void saveFundUnit(List<FundUnitImportVO> dataList, List<String> headerList);

    /**
     * 导入返回结果集
     */
    List<String> getMsg();
}
