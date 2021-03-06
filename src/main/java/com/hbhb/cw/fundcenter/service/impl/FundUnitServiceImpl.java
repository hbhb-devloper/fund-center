package com.hbhb.cw.fundcenter.service.impl;

import com.hbhb.beetlsql.core.QueryExt;
import com.hbhb.core.bean.BeanConverter;
import com.hbhb.cw.fundcenter.mapper.FundUnitMapper;
import com.hbhb.cw.fundcenter.model.FundUnit;
import com.hbhb.cw.fundcenter.service.FundUnitService;
import com.hbhb.cw.fundcenter.web.vo.FundUnitImportVO;
import com.hbhb.cw.fundcenter.web.vo.FundUnitVO;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wangxiaodang
 * @since 2020-08-13
 */
@Slf4j
@Service
public class FundUnitServiceImpl implements FundUnitService {

    @Resource
    private FundUnitMapper fundUnitMapper;

    private final List<String> msg = new CopyOnWriteArrayList<>();

    @Override
    public PageResult<FundUnit> pageFundUnit(Integer pageNum, Integer pageSize,
                                             String unitCode, String unitName) {
        return fundUnitMapper.createLambdaQuery()
                .andLike(FundUnit::getUnitCode, QueryExt.filterLikeEmpty(unitCode))
                .andLike(FundUnit::getUnitName, QueryExt.filterLikeEmpty(unitName))
                .page(pageNum, pageSize);
    }

    @Override
    public List<FundUnitVO> listFundUnit(String unitCode, String unitName) {
        return fundUnitMapper.createLambdaQuery()
                .andLike(FundUnit::getUnitCode, QueryExt.filterLikeEmpty(unitCode))
                .andLike(FundUnit::getUnitName, QueryExt.filterLikeEmpty(unitName))
                .select(FundUnitVO.class);
    }

    @Override
    public void updateFundUnit(FundUnitVO vo) {
        fundUnitMapper.updateTemplateById(BeanConverter.convert(vo, FundUnit.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFundUnit(List<FundUnitImportVO> dataList, List<String> headerList) {
        List<String> unitCodeList = new ArrayList<>();
        Map<String, String> unitCodeMap = new HashMap<>(100);
        List<String> unitNameList = new ArrayList<>();
        Map<String, String> unitNameMap = new HashMap<>(100);
        // ????????????
        List<String> errorList = new ArrayList<>();
        // ????????????
        List<FundUnit> insertList = new ArrayList<>();

        for (FundUnitImportVO vo : dataList) {
            FundUnit newFundUnit = FundUnit.builder()
                    .unitName(vo.getUnitName())
                    .unitCode(vo.getUnitCode())
                    .state(vo.getState())
                    .initialAmount(vo.getInitialAmount())
                    .build();
            insertList.add(newFundUnit);
            unitCodeList.add(vo.getUnitCode());
            unitNameList.add(vo.getUnitName());
        }
        // ????????????????????????
        for (int i = 0; i < unitCodeList.size(); i++) {
            String key = unitCodeList.get(i);
            String old = unitCodeMap.get(key);
            if (old != null) {
                unitCodeMap.put(key, old + "," + (i + 1));
            } else {
                unitCodeMap.put(key, "" + (i + 1));
            }
        }
//        unitCodeMap.entrySet().forEach();

        for (String key : unitCodeMap.keySet()) {
            String value = unitCodeMap.get(key);
            if (value.contains(",")) {
                errorList.add("????????????:" + key + " ??????,?????? " + value);
            }
        }
        // ????????????????????????
        for (int i = 0; i < unitNameList.size(); i++) {
            String key = unitNameList.get(i);
            String old = unitNameMap.get(key);
            if (old != null) {
                unitNameMap.put(key, old + "," + (i + 1));
            } else {
                unitNameMap.put(key, "" + (i + 1));
            }
        }
        for (String key : unitNameMap.keySet()) {
            String value = unitNameMap.get(key);
            if (value.contains(",")) {
                errorList.add("????????????:" + key + " ??????,?????? " + value);
            }
        }

        // ????????????????????????
        List<FundUnit> unitList = fundUnitMapper.all();
        List<String> info = new ArrayList<>();
        unitList.forEach(item -> {
            info.add(item.getUnitCode());
            info.add(item.getUnitName());
        });
        int i = 2;
        for (FundUnit unit : insertList) {
            if (info.contains(unit.getUnitCode())) {
                errorList.add("???excel???" + i + "??????????????????" + unit.getUnitCode() + "?????????");
            }
            if (info.contains(unit.getUnitName())) {
                errorList.add("???excel???" + i + "??????????????????" + unit.getUnitName() + "?????????");
            }
            i++;
        }

        msg.clear();
        msg.addAll(errorList);
        if (errorList.size() == 0) {
            fundUnitMapper.insertBatch(insertList);
        }
    }

    @Override
    public List<String> getMsg() {
        return this.msg;
    }
}
