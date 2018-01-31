package com.piles.common.util;

import com.piles.common.entity.type.TradeType;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * @author lgc48027
 * @version Id: GunStatusMapUtil, v 0.1 2018/1/31 18:45 lgc48027 Exp $
 */
public class GunStatusMapUtil {
    private static Map<String,Integer> map=new HashedMap(  );

    public static void put(String pileNo, TradeType tradeType,int status){
        String key=pileNo+"_"+tradeType.getCode();
        if (map.containsKey( key )){
            Integer val=map.get( key );
            if (status!=val){
                map.replace( key,val,status );
            }
        }else {
            map.put( key,status );
        }
    }

    public static Integer get(String pileNo, int tradeType){
        String key=pileNo+"_"+tradeType;
            return map.get( key );
    }
}
