package com.bingo.poker.domain;

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
 * 设置初始化牌的类型-数值
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
@TableName("poker_type")
@ApiModel(value="PokerType对象", description="设置初始化牌的类型-数值")
public class PokerType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "牌面对应数字")
    @TableField("num")
    private String num;

    @ApiModelProperty(value = "牌面花色")
    @TableField("type")
    private String type;

	/**
     * 创建时间
     */
    @TableField(fill=FieldFill.INSERT, updatable = false, update= "now()", updateType = UpdateType.DB)
    private String createTime;

    /**
     * 更新时间
     */
    @TableField(fill=FieldFill.INSERT_UPDATE, update = "now()", updateType = UpdateType.DB)
    private LocalDateTime modifyTime;

    /**
     * 状态：正常，正常；删除，删除；
     */
    @TableLogic
    @TableField(fill=FieldFill.INSERT, update = "正常", updateType = UpdateType.FIXED)
    private String tbStatus;


	/**** 类字段 ****/
    public static final String REF_CLASS = "PokerType";
    
    public static final String PROP_ID = "id";
    public static final String PROP_NUM = "num";
    public static final String PROP_TYPE = "type";
    public static final String PROP_CREATE_TIME = "createTime";
    public static final String PROP_MODIFY_TIME = "modifyTime";
    public static final String PROP_TB_STATUS = "tbStatus";
	
	
	/****  数据库表字段 ****/
    public static final String REF_TABLE = "poker_type";
    
    public static final String COL_ID = "id";
    public static final String COL_NUM = "num";
    public static final String COL_TYPE = "type";
    public static final String COL_CREATE_TIME = "create_time";
    public static final String COL_MODIFY_TIME = "modify_time";
    public static final String COL_TB_STATUS = "tb_status";


    public Integer getId() {
        return id;
    }

    public PokerType setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNum() {
        return num;
    }

    public PokerType setNum(String num) {
        this.num = num;
        return this;
    }
    public String getType() {
        return type;
    }

    public PokerType setType(String type) {
        this.type = type;
        return this;
    }



	public String getCreateTime() {
        return createTime;
    }

    public PokerType setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public PokerType setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public String getTbStatus() {
        return tbStatus;
    }

    public PokerType setTbStatus(String tbStatus) {
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
