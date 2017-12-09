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

        boolean flag=HttpRequest.httpPostWithJson("args="+jsonObject.toString(),"http://tox.tunnel.qydev.com/order/powerEnd");

        return flag;
    }

    public static void main(String[] args) {
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("orderNo",123456);
        jsonObject.put("pileNo","00000008000004");
        jsonObject.put("serial",98765445);
        jsonObject.put("endReason",1);
        jsonObject.put("totalAmmeterDegree",1.2);

//        http://tox.tunnel.qydev.com/order/powerEnd
        System.out.println("args="+jsonObject.toString());
    }
}
