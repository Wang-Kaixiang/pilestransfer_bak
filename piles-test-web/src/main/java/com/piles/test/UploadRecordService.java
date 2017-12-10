package com.piles.test;

import com.alibaba.fastjson.JSONObject;
import com.piles.record.entity.UploadRecordRequest;
import com.piles.record.service.IUploadRecordService;
import com.piles.util.HttpRequest;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UploadRecordService implements IUploadRecordService {
    @Override
    public boolean uploadRecord(UploadRecordRequest uploadRecordRequest) {
        //请求一个接口
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("orderNo",uploadRecordRequest.getOrderNo());
        jsonObject.put("pileNo",uploadRecordRequest.getPileNo());
        jsonObject.put("serial",uploadRecordRequest.getSerial());
        jsonObject.put("endReason",uploadRecordRequest.getEndReason());
        jsonObject.put("totalAmmeterDegree",uploadRecordRequest.getTotalAmmeterDegree());

//        http://tox.tunnel.qydev.com/order/powerEnd
        Map<String,JSONObject> map= new HashedMap();
        map.put("args",jsonObject);

        boolean flag=HttpRequest.httpPostWithJson(map,"http://elec.toxchina.com/Tox_Elec/order/powerEnd");

        return flag;
    }


}
