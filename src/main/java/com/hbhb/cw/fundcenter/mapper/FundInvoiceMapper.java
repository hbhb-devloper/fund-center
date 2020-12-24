package com.hbhb.cw.fundcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.fundcenter.model.FundInvoice;
import com.hbhb.cw.fundcenter.web.vo.InvoiceReqVO;
import com.hbhb.cw.fundcenter.web.vo.InvoiceResVO;

import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.annotation.Param;

import java.util.List;

public interface FundInvoiceMapper extends BaseMapper<FundInvoice> {

    PageResult<InvoiceResVO> selectPageByCond(@Param("cond") InvoiceReqVO cond,
                                              @Param("list") List<Integer> unitIds,
                                              PageRequest request);

}