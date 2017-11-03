package com.piles.common.business;

/**
 * Created by lgc48027 on 2017/11/3.
 */
public interface IBusinessFactory {
    IBusiness getByOrder(byte order);
}
