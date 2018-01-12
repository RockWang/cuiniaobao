package com.cuiniaobao.service.mapper;

import com.cuiniaobao.service.model.Company;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created: 2017-12-19 20:32:05
 *
 * @author  Michael.Zhang
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

    List<Company> selectAll();

}
