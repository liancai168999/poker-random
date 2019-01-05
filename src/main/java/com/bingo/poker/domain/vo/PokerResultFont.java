package com.bingo.poker.domain.vo;

import com.bingo.core.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

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
public class PokerResultFont implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "出牌时的牌幅数量")
    @TableField("poker_num")
    private Integer pokerNum;

    @ApiModelProperty(value = "公布明文结果解密时所需密钥(公钥)")
    @TableField("poker_public_key")
    private String pokerPublicKey;


    @ApiModelProperty(value = "加密后的数据")
    @TableField("poker_private_result")
    private String pokerPrivateResult;

	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    @TableField
    private String createTime;
    /**状态：正常，正常；删除，删除；*/
    @TableLogic
    private String tbStatus;

    private String publishStatus;

    private String url;


    public Integer getId() {
        return id;
    }

    public PokerResultFont setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getPokerNum() {
        return pokerNum;
    }

    public PokerResultFont setPokerNum(Integer pokerNum) {
        this.pokerNum = pokerNum;
        return this;
    }
    public String getPokerPublicKey() {
        return pokerPublicKey;
    }

    public PokerResultFont setPokerPublicKey(String pokerPublicKey) {
        this.pokerPublicKey = pokerPublicKey;
        return this;
    }

	public String getCreateTime() {
        return createTime;
    }

    public PokerResultFont setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getTbStatus() {
        return tbStatus;
    }

    public PokerResultFont setTbStatus(String tbStatus) {
        this.tbStatus = tbStatus;
        return this;
    }

    public String getPokerPrivateResult() {
        return pokerPrivateResult;
    }

    public PokerResultFont setPokerPrivateResult(String pokerPrivateResult) {
        this.pokerPrivateResult = pokerPrivateResult;
        return this;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PokerResult{" +
                "id=" + id +
                ", pokerNum=" + pokerNum +
                ", pokerPublicKey=" + pokerPublicKey +
                ", pokerPrivateResult=" + pokerPrivateResult +
                ", publishStatus=" + publishStatus +
                ", url=" + url +
                ", tbStatus=" + tbStatus +
                "}";
    }
}
