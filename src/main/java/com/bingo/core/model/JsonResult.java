package com.bingo.core.model;

import com.bingo.core.enums.ResultStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "JsonResult对象", description = "JsonResult返回结果")
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ApiModelProperty(value = "version")
    private String version = "1.0";
    /**
     * 响应业务状态
     */
    @ApiModelProperty(value = "响应业务状态")
    private Integer statusCode;
    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String statusMsg;
    /**
     * 响应中的数据
     */
    @ApiModelProperty(value = "响应中的数据")
    private Object data;

    @ApiModelProperty(value = "token")
    private String token;
    /**
     * 明细返回码
     **/
    @ApiModelProperty(value = "明细返回码")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subCode;
    /**
     * 明细返回码描述
     **/
    @ApiModelProperty(value = "明细返回码描述")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subMsg;
    /**
     * 建议处理方案
     **/
    @ApiModelProperty(value = "建议处理方案")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String solution;

    @Override
    public String toString() {
        return "JsonResult{" +
                "version='" + version + '\'' +
                ", statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                ", subCode='" + subCode + '\'' +
                ", subMsg='" + subMsg + '\'' +
                ", solution='" + solution + '\'' +
                '}';
    }

    public JsonResult() {
    }

    public JsonResult(Integer status, String statusMsg, Object data) {
        this.statusCode = status;
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public JsonResult(Object data) {
        this.statusCode = ResultStatusEnum.OK.getCode();
        this.statusMsg = "";
        this.data = data;
    }

    public JsonResult(Integer status, String statusMsg, Object data, String version, String token, String subCode, String subMsg, String solution) {
        this.version = version;
        this.token = token;
        this.subCode = subCode;
        this.subMsg = subMsg;
        this.solution = solution;
        this.statusCode = status;
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public JsonResult(Integer status, String statusMsg, Object data, String subCode, String subMsg, String solution, String token) {
        this.token = token;
        this.subCode = subCode;
        this.subMsg = subMsg;
        this.solution = solution;
        this.statusCode = status;
        this.statusMsg = statusMsg;
        this.data = data;
    }


    public JsonResult(Integer status, String statusMsg, Object data, String token) {
        this.token = token;
        this.statusCode = status;
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public static JsonResult build(Integer status, String msg, Object data) {
        return new JsonResult(status, msg, data);
    }

    public static JsonResult build(Integer status, String msg, Object data, String token) {
        return new JsonResult(status, msg, data, token);
    }

    public static JsonResult build(Integer status, String msg, Object data, String subCode, String subMsg, String solution, String token) {
        return new JsonResult(status, msg, data, subCode, subMsg, solution, token);
    }

    public static JsonResult noData() {
        return new JsonResult(ResultStatusEnum.NULL_DATA.getCode(), null, null);
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok() {
        return new JsonResult(null);
    }

    public static JsonResult errorMsg(String msg) {
        return new JsonResult(ResultStatusEnum.ERROR_DATA.getCode(), ResultStatusEnum.ERROR_DATA.getMessage(), null);
    }

    public static JsonResult errorPermission() {
        return new JsonResult(ResultStatusEnum.NO_PERMISSION.getCode(), ResultStatusEnum.NO_PERMISSION.getMessage(), null);
    }

    public static JsonResult errorTokenMsg(String msg) {
        return new JsonResult(ResultStatusEnum.TOKEN_LOSE.getCode(), ResultStatusEnum.TOKEN_LOSE.getMessage(), null);
    }

    public static JsonResult errorException(String msg) {
        return new JsonResult(ResultStatusEnum.SERVER.getCode(), ResultStatusEnum.SERVER.getMessage(), null);
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: 将json结果集转化为JSONResult对象
     * 需要转换的对象是一个类
     */
    public static JsonResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("statusMsg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @return
     * @Description: 没有object对象的转化
     */
    public static JsonResult format(String json) {
        try {
            return MAPPER.readValue(json, JsonResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: Object是集合转化
     * 需要转换的对象是一个list
     */
    public static JsonResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("statusMsg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isOK() {
        return this.statusCode == 200;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
