package com.bingo.poker.domain.vo;

import com.bingo.core.annotation.UpdateType;
import com.bingo.core.annotation.IdType;
import com.bingo.core.annotation.TableName;
import com.bingo.core.annotation.TableLogic;
import com.bingo.core.annotation.FieldFill;
import java.time.LocalDateTime;
import com.bingo.core.annotation.TableField;
import com.bingo.core.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 设置初始化牌的类型-数值 VO
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
@TableName("poker_type")
@ApiModel(value="PokerTypeVo对象", description="设置初始化牌的类型-数值")
public class PokerTypeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "牌面对应数字")
    @TableField("num")
    private String num;

    @ApiModelProperty(value = "牌面花色")
    @TableField("type")
    private String type;

	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    @TableField
    private String createTime;
    /**状态：正常，正常；删除，删除；*/
    @TableLogic
    private String tbStatus;


    public Integer getId() {
        return id;
    }

    public PokerTypeVo setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNum() {
        return num;
    }

    public PokerTypeVo setNum(String num) {
        this.num = num;
        return this;
    }
    public String getType() {
        return type;
    }

    public PokerTypeVo setType(String type) {
        this.type = type;
        return this;
    }

	public String getCreateTime() {
        return createTime;
    }

    public PokerTypeVo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getTbStatus() {
        return tbStatus;
    }

    public PokerTypeVo setTbStatus(String tbStatus) {
        this.tbStatus = tbStatus;
        return this;
    }

    @Override
    public String toString() {
        return "PokerType{" +
        "id=" + id +
        ", num=" + num +
        ", type=" + type +
        "}";
    }
}
