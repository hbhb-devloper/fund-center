package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.core.utils.DateUtil;
import com.hbhb.cw.fundcenter.enums.InvoiceNoticeState;
import com.hbhb.cw.fundcenter.mapper.FundInvoiceNoticeMapper;
import com.hbhb.cw.fundcenter.model.FundInvoiceNotice;
import com.hbhb.cw.fundcenter.rpc.DictApiExp;
import com.hbhb.cw.fundcenter.rpc.FlowTypeApiExp;
import com.hbhb.cw.fundcenter.rpc.UnitApiExp;
import com.hbhb.cw.fundcenter.rpc.UserApiExp;
import com.hbhb.cw.fundcenter.service.FundInvoiceNoticeService;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeReqVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeResVO;
import com.hbhb.cw.fundcenter.web.vo.FundInvoiceNoticeVO;
import com.hbhb.cw.systemcenter.enums.DictCode;
import com.hbhb.cw.systemcenter.enums.TypeCode;
import com.hbhb.cw.systemcenter.vo.DictVO;

import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wxg
 * @since 2020-09-09
 */
@Service
@Slf4j
public class FundInvoiceNoticeServiceImpl implements FundInvoiceNoticeService {

    @Resource
    private FundInvoiceNoticeMapper fundInvoiceNoticeMapper;
    @Resource
    private UserApiExp userApi;
    @Resource
    private UnitApiExp unitApi;
    @Resource
    private DictApiExp dictApi;
    @Resource
    private FlowTypeApiExp flowTypeApi;

    @Override
    @SuppressWarnings(value = {"rawtypes"})
    public PageResult<FundInvoiceNoticeResVO> pageInvoiceNotice(Integer pageNum, Integer pageSize,
                                                                FundInvoiceNoticeReqVO cond) {
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        PageResult<FundInvoiceNoticeResVO> page = fundInvoiceNoticeMapper.selectPageByCond(cond, request);

        // 项目状态字典
        List<DictVO> stateList = dictApi.getDict(TypeCode.FUND.value(), DictCode.FUND_INVOICE_STATUS.value());
        Map<String, String> stateMap = stateList.stream().collect(
                Collectors.toMap(DictVO::getValue, DictVO::getLabel));

        // 组装
        page.getList().forEach(item -> {
            item.setStateLabel(stateMap.get(item.getState().toString()));
            item.setFlowType(flowTypeApi.getNameById(item.getFlowTypeId()));
            item.setUnitName(unitApi.getUnitInfo(item.getUnitId()).getUnitName());
        });
        return page;
    }

    @Override
    public List<FundInvoiceNoticeVO> listInvoiceNotice(Integer userId) {
        List<FundInvoiceNotice> list = fundInvoiceNoticeMapper.createLambdaQuery()
                .andEq(FundInvoiceNotice::getReceiver, userId)
                .andEq(FundInvoiceNotice::getState, InvoiceNoticeState.UN_READ.value())
                .desc(FundInvoiceNotice::getCreateTime)
                .select();
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Integer> userIds = list.stream().map(FundInvoiceNotice::getPromoter).collect(Collectors.toList());
        Map<Integer, String> userMap = userApi.getUserMapById(userIds);
        return list.stream()
                .map(notice -> FundInvoiceNoticeVO.builder()
                        .id(notice.getId())
                        .content(notice.getContent())
                        .projectId(notice.getInvoiceId())
                        .date(DateUtil.dateToString(notice.getCreateTime()))
                        .userName(userMap.get(notice.getPromoter()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void changeNoticeState(Long id) {
        fundInvoiceNoticeMapper.updateTemplateById(FundInvoiceNotice.builder()
                .id(id).state(InvoiceNoticeState.READ.value())
                .build());
    }

    @Override
    public Long countNotice(Integer userId) {
        return fundInvoiceNoticeMapper.createLambdaQuery()
                .andEq(FundInvoiceNotice::getReceiver, userId)
                .andEq(FundInvoiceNotice::getState, InvoiceNoticeState.UN_READ.value())
                .count();
    }
}
