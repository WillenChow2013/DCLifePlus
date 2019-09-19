package com.dorun.core.dc.model;

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
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@ApiModel(value="OrgInfo对象", description="")
public class OrgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String orgId;

    private String orgName;

    private String mchId;

    private String queryUrl;

    private String chargeUrl;

    private String confirmUrl;

    private String cteateTime;

    private String enc;

    private String type;

    private String orgAbbre;


}
