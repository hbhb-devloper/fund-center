package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.fundcenter.mapper.FundCustomerFileMapper;
import com.hbhb.cw.fundcenter.mapper.FundCustomerFlowMapper;
import com.hbhb.cw.fundcenter.mapper.FundCustomerMapper;
import com.hbhb.cw.fundcenter.model.FundCustomer;
import com.hbhb.cw.fundcenter.model.FundCustomerFile;
import com.hbhb.cw.fundcenter.model.FundCustomerFlow;
import com.hbhb.cw.fundcenter.rpc.DictApiExp;
import com.hbhb.cw.fundcenter.rpc.UnitApiExp;
import com.hbhb.cw.fundcenter.rpc.UserApiExp;
import com.hbhb.cw.fundcenter.service.FundCustomerService;
import com.hbhb.cw.fundcenter.web.vo.CustomerExportVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerFileVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerReqVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerResVO;
import com.hbhb.cw.fundcenter.web.vo.CustomerVO;
import com.hbhb.cw.systemcenter.enums.DictCode;
import com.hbhb.cw.systemcenter.enums.TypeCode;
import com.hbhb.cw.systemcenter.model.Unit;
import com.hbhb.cw.systemcenter.vo.DictVO;
import com.hbhb.cw.systemcenter.vo.UserInfo;

import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class FundCustomerServiceImpl implements FundCustomerService {

    @Resource
    private FundCustomerMapper fundCustomerMapper;
    @Resource
    private FundCustomerFileMapper fundCustomerFileMapper;
    @Resource
    private FundCustomerFlowMapper fundCustomerFlowMapper;
    @Resource
    private DictApiExp dictApi;
    @Resource
    private UnitApiExp unitApi;
    @Resource
    private UserApiExp userApi;

    /**
     * 客户资金列表分页查询
     */
    @Override
    @SuppressWarnings(value = {"rawtypes"})
    public PageResult<CustomerResVO> pageFundCustomer(Integer pageNum, Integer pageSize,
                                                      CustomerReqVO cond) {
        List<Integer> unitIds = unitApi.getSubUnit(cond.getUnitId());
        PageRequest request = DefaultPageRequest.of(pageNum, pageSize);
        PageResult<CustomerResVO> page = fundCustomerMapper.selectPageByCond(cond, unitIds, request);

        // 业务类型
        List<DictVO> busTypeList = dictApi.getDict(
                TypeCode.FUND.value(), DictCode.FUND_BUSINESS_TYPE.value());
        Map<String, String> busTypeMap = busTypeList.stream()
                .collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
        // 资金流向
        List<DictVO> fundFlowsList = dictApi.getDict(
                TypeCode.FUND.value(), DictCode.FUND_FUND_FLOWS.value());
        Map<String, String> fundFlowsMap = fundFlowsList.stream()
                .collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
        // 款项类型
        List<DictVO> amountTypeList = dictApi.getDict(
                TypeCode.FUND.value(), DictCode.FUND_AMOUNT_TYPE.value());
        Map<String, String> amountTypeMap = amountTypeList.stream()
                .collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));
        // 流程状态
        List<DictVO> stateList = dictApi.getDict(
                TypeCode.FUND.value(), DictCode.FUND_INVOICE_STATUS.value());
        Map<String, String> stateMap = stateList.stream()
                .collect(Collectors.toMap(DictVO::getValue, DictVO::getLabel));

        page.getList().forEach(vo -> {
            vo.setBusType(busTypeMap.get(vo.getBusType()));
            vo.setFundFlows(fundFlowsMap.get(vo.getFundFlows()));
            vo.setAmountType(amountTypeMap.get(vo.getAmountType()));
            vo.setState(stateMap.get(vo.getState()));
            vo.setDocument("0".equals(vo.getDocument()) ? "否" : "是");
        });
        return page;
    }

    @Override
    public CustomerVO getFundCustomerInfo(Integer id) {
        FundCustomer fundCustomer = fundCustomerMapper.single(id);
        CustomerVO vo = BeanConverter.convert(fundCustomer, CustomerVO.class);

        Unit unit = unitApi.getUnitInfo(fundCustomer.getUnitId());
        vo.setUnitName(unit.getUnitName());
        vo.setHasInvoice(fundCustomer.getHasInvoice() == 0 ? "否" : "是");

        List<FundCustomerFile> files = fundCustomerFileMapper.createLambdaQuery()
                .andEq(FundCustomerFile::getCustomerId, id)
                .select();
        vo.setFiles(Optional.ofNullable(files)
                .orElse(new ArrayList<>())
                .stream()
                .map(file -> CustomerFileVO.builder()
                        .fileName(file.getTitle())
                        .author(file.getCreateBy())
                        .uploadTime(file.getCreateTime())
                        .fileSize(file.getFileSize())
                        .build())
                .collect(Collectors.toList()));
        return vo;
    }

    @Override
    public List<CustomerExportVO> getExportList(CustomerReqVO cond) {
        PageResult<CustomerResVO> page = this.pageFundCustomer(1, Integer.MAX_VALUE, cond);
        return Optional.ofNullable(page.getList())
                .orElse(new ArrayList<>())
                .stream()
                .map(vo -> BeanConverter.convert(vo, CustomerExportVO.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<FlowNodeDisplayVO> getFundCustomerFlow(Long id) {
//        List<FundCustomerFlow> customerFlows = fundCustomerFlowMapper.createLambdaQuery()
//                .andEq(FundCustomerFlow::getCustomerId, id)
//                .select();
//        return Optional.ofNullable(customerFlows)
//                .orElse(new ArrayList<>())
//                .stream()
//                .map(flow -> {
//                    FlowNodeDisplayVO vo = BeanConverter.convert(flow, FlowNodeDisplayVO.class);
//                    UserInfo userInfo = userApi.getUserInfoById(Integer.valueOf(flow.getUserId()));
//                    vo.setUserName(userInfo.getNickName());
//                    return vo;
//                }).collect(Collectors.toList());
//    }
}
