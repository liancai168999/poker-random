package com.bingo.poker.param;

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

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


/**
 * <p>
 * 设置初始化牌的类型-数值
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
@ApiModel(value="PokerType对象", description="设置初始化牌的类型-数值")
public class PokerTypeParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "牌面对应数字")

    @Length(min = 0, max = 200, message = "牌面对应数字长度需要在1~200个字之间。")
    private String num;

    @ApiModelProperty(value = "牌面花色")

    @Length(min = 0, max = 200, message = "牌面花色长度需要在1~200个字之间。")
    private String type;




    public Integer getId() {
        return id;
    }

    public PokerTypeParam setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNum() {
        return num;
    }

    public PokerTypeParam setNum(String num) {
        this.num = num;
        return this;
    }
    public String getType() {
        return type;
    }

    public PokerTypeParam setType(String type) {
        this.type = type;
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
