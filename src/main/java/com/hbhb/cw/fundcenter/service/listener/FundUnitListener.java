package com.hbhb.cw.fundcenter.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hbhb.cw.fundcenter.service.FundUnitService;
import com.hbhb.cw.fundcenter.web.vo.FundUnitImportVO;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxiaogang
 * @since 2020-08-13
 */
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class FundUnitListener extends AnalysisEventListener {
    /**
     * 批处理条数，每隔多少条清理一次list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 表头
     */
    private final List<String> headerList = new ArrayList<>();

    /**
     * 数据行
     */
    private final List<FundUnitImportVO> dataList = new ArrayList<>();
    private final FundUnitService fundUnitService;


    public FundUnitListener(FundUnitService fundUnitService) {
        this.fundUnitService = fundUnitService;

    }

    /**
     * 每次读取一条数据时调用该方法
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        dataList.add((FundUnitImportVO) data);
    }

    /**
     * 所有数据解析完成后调用该方法
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        dataList.clear();
    }

    /**
     * 获取表头
     */
    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        if (!headMap.isEmpty()) {
            // 收集表头值
            headMap.values().forEach(value -> headerList.add((String) value));
        }
    }

    /**
     * 保存客户资金单位信息
     */
    private void saveData() {
        if (!CollectionUtils.isEmpty(dataList)) {
            fundUnitService.saveFundUnit(dataList, headerList);
        }
    }
}
