package com.bingo.poker.domain.vo;

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
 * 出牌结果表 VO
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
@TableName("poker_result")
@ApiModel(value="PokerResultVo对象", description="出牌结果表")
public class PokerResultVo implements Serializable {

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

    public PokerResultVo setId(Integer id) {
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

    public PokerResultVo setPokerNum(Integer pokerNum) {
        this.pokerNum = pokerNum;
        return this;
    }
    public String getPokerPublicKey() {
        return pokerPublicKey;
    }

    public PokerResultVo setPokerPublicKey(String pokerPublicKey) {
        this.pokerPublicKey = pokerPublicKey;
        return this;
    }
    public String getPokerPrivateKey() {
        return pokerPrivateKey;
    }

    public PokerResultVo setPokerPrivateKey(String pokerPrivateKey) {
        this.pokerPrivateKey = pokerPrivateKey;
        return this;
    }

	public String getCreateTime() {
        return createTime;
    }

    public PokerResultVo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getTbStatus() {
        return tbStatus;
    }

    public PokerResultVo setTbStatus(String tbStatus) {
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
                ", tbStatus=" + tbStatus +
                "}";
    }
}
