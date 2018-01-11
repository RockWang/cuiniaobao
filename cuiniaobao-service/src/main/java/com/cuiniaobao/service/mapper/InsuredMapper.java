package com.cuiniaobao.service.mapper;

import com.cuiniaobao.service.model.Insured;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created: 2017-12-19 20:34:04
 *
 * @author  Michael.Zhang
 */
@Mapper
public interface InsuredMapper extends BaseMapper<Insured> {

    List<Insured> selectByCustomerId(Long customerId);

}
