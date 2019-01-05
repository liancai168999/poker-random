package com.bingo.poker.param;

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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


/**
 * <p>
 * 出牌结果表
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
@ApiModel(value="PokerResult对象", description="出牌结果表")
public class PokerResultParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;

    @ApiModelProperty(value = "发牌结果集")

    private byte[] pokerResult;

    @ApiModelProperty(value = "出牌时的牌幅数量")

    private Integer pokerNum;

    @ApiModelProperty(value = "公布明文结果解密时所需密钥(公钥)")

    private String pokerPublicKey;

    @ApiModelProperty(value = "私钥")

    private String pokerPrivateKey;


    @ApiModelProperty(value = "加密后的数据")

    private byte[] pokerPrivateResult;



    public Integer getId() {
        return id;
    }

    public PokerResultParam setId(Integer id) {
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

    public PokerResultParam setPokerNum(Integer pokerNum) {
        this.pokerNum = pokerNum;
        return this;
    }
    public String getPokerPublicKey() {
        return pokerPublicKey;
    }

    public PokerResultParam setPokerPublicKey(String pokerPublicKey) {
        this.pokerPublicKey = pokerPublicKey;
        return this;
    }
    public String getPokerPrivateKey() {
        return pokerPrivateKey;
    }

    public PokerResultParam setPokerPrivateKey(String pokerPrivateKey) {
        this.pokerPrivateKey = pokerPrivateKey;
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
