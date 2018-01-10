package com.cuiniaobao.service.model;

import lombok.Data;

import java.util.Date;

/**
 * Created: 2017-12-19 00:03:35
 *
 * @author  Michael.Zhang
 */
@Data
public class Customer {

    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String username;
    private String password;
    private Date registeDate;

}
