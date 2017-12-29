package com.cuiniaobao.api.service;

import com.cuiniaobao.api.model.Demand;

/**
 * Created: 2017-12-19 20:16:43
 *
 * @author  Michael.Zhang
 */
public interface DemandService {

    void add(Demand demand);

    void remove(Long demandId);

    void modify(Demand demand);

    Demand findById(Long demandId);

    Demand findByCustomerId(Long customerId);

}
