package com.hbhb.cw.fundcenter.mapper;

import com.hbhb.beetlsql.BaseMapper;
import com.hbhb.cw.fundcenter.model.FundInvoiceNotice;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeReqVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeResVO;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.annotation.Param;
import org.beetl.sql.mapper.annotation.Update;

public interface FundInvoiceNoticeMapper extends BaseMapper<FundInvoiceNotice> {

    PageResult<FundInvoiceNoticeResVO> selectPageByCond(@Param("cond") FundInvoiceNoticeReqVO cond,
                                                        PageRequest request);

    @Update
    void updateStateByInvoiceId(@Param("invoiceId") Long invoiceId, @Param("state") Integer state);
}