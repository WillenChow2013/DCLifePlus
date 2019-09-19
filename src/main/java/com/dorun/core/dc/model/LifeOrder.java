package com.dorun.core.dc.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author AleeX
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LifeOrder对象", description="")
public class LifeOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String orgId;

    private String dNo;

    private String consNo;

    private String meterNo;

    private String consName;

    private String consAddr;

    private BigDecimal prepayAmt;

    private Integer tPq;

    private BigDecimal rcvblAmt;

    private BigDecimal rcvedAmt;

    private String invoiceNo;

    private Timestamp payTime;

    private String status;

    private String transactionId;

    private String billNo;

    private String billMonth;


}
