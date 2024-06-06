package com.inext.manage_system.service;

import java.util.List;

import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.InvoiceSampleSearchRes;
import com.inext.manage_system.model.InvoiceSample;

public interface InvoiceSampleService {

    // 会社サンプルリスト取得
    public List<InvoiceSample> getSampleList();

    // 会社サンプル取得
    public InvoiceSampleSearchRes getSample(String corpName);

    // 会社サンプル作成/編集
    public BaseRes createOrUpdateSample(InvoiceSample sample);

}
