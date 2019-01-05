package com.bingo.poker.domain;

import com.bingo.core.annotation.UpdateType;
import com.bingo.core.annotation.IdType;
import com.bingo.core.annotation.TableName;
import com.bingo.core.annotation.TableLogic;
import com.bingo.core.annotation.FieldFill;

import java.sql.Blob;
import java.time.LocalDateTime;
import com.bingo.core.annotation.TableField;
import com.bingo.core.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 出牌结果表
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
@TableName("poker_result")
@ApiModel(value="PokerResult对象", description="出牌结果表")
public class PokerResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发牌结果集")
    @TableField("poker_result")
    private byte[] pokerResult;

    @ApiModelProperty(value = "出牌时的牌幅数量")
    @TableField("poker_num")
    private Integer pokerNum;

    @ApiModelProperty(value = "公布明文结果解密时所需密钥(公钥)")
    @TableField("poker_public_key")
    private String pokerPublicKey;

    @ApiModelProperty(value = "私钥")
    @TableField("poker_private_key")
    private String pokerPrivateKey;

    @ApiModelProperty(value = "加密后的数据")
    @TableField("poker_private_result")
    private byte[] pokerPrivateResult;

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
    public static final String REF_CLASS = "PokerResult";
    
    public static final String PROP_ID = "id";
    public static final String PROP_POKER_RESULT = "pokerResult";
    public static final String PROP_POKER_NUM = "pokerNum";
    public static final String PROP_POKER_PUBLIC_KEY = "pokerPublicKey";
    public static final String PROP_POKER_PRIVATE_KEY = "pokerPrivateKey";
    public static final String PROP_POKER_PRIVATE_RESULT = "pokerPrivateResult";
    public static final String PROP_CREATE_TIME = "createTime";
    public static final String PROP_MODIFY_TIME = "modifyTime";
    public static final String PROP_TB_STATUS = "tbStatus";
	
	
	/****  数据库表字段 ****/
    public static final String REF_TABLE = "poker_result";
    
    public static final String COL_ID = "id";
    public static final String COL_POKER_RESULT = "poker_result";
    public static final String COL_POKER_NUM = "poker_num";
    public static final String COL_POKER_PUBLIC_KEY = "poker_public_key";
    public static final String COL_POKER_PRIVATE_KEY = "poker_private_key";
    public static final String COL_POKER_PRIVATE_RESULT = "poker_private_result";
    public static final String COL_CREATE_TIME = "create_time";
    public static final String COL_MODIFY_TIME = "modify_time";
    public static final String COL_TB_STATUS = "tb_status";


    public Integer getId() {
        return id;
    }

    public PokerResult setId(Integer id) {
        this.id = id;
        return this;
    }

    public byte[] getPokerResult() {
        return pokerResult;
    }

    public void setPokerResult(byte[] pokerResult) {
        this.pokerResult = pokerResult;
    }

    public Integer getPokerNum() {
        return pokerNum;
    }

    public PokerResult setPokerNum(Integer pokerNum) {
        this.pokerNum = pokerNum;
        return this;
    }
    public String getPokerPublicKey() {
        return pokerPublicKey;
    }

    public PokerResult setPokerPublicKey(String pokerPublicKey) {
        this.pokerPublicKey = pokerPublicKey;
        return this;
    }
    public String getPokerPrivateKey() {
        return pokerPrivateKey;
    }

    public PokerResult setPokerPrivateKey(String pokerPrivateKey) {
        this.pokerPrivateKey = pokerPrivateKey;
        return this;
    }



	public String getCreateTime() {
        return createTime;
    }

    public PokerResult setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public PokerResult setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public String getTbStatus() {
        return tbStatus;
    }

    public PokerResult setTbStatus(String tbStatus) {
        this.tbStatus = tbStatus;
        return this;
    }


    public byte[] getPokerPrivateResult() {
        return pokerPrivateResult;
    }

    public void setPokerPrivateResult(byte[] pokerPrivateResult) {
        this.pokerPrivateResult = pokerPrivateResult;
    }

    @Override
    public String toString() {
        return "PokerResult{" +
                "id=" + id +
                ", pokerResult=" + pokerResult +
                ", pokerNum=" + pokerNum +
                ", pokerPublicKey=" + pokerPublicKey +
                ", pokerPrivateKey=" + pokerPrivateKey +
                ", pokerPrivateResult=" + pokerPrivateResult +
                "}";
    }
}
