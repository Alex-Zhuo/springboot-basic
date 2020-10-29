package com.abab.common.entity.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author alex
 * @Date: Created in 2020/10/29 11:50
 * @IDE: Intellij Idea 2020.3
 * @Description: 分页查询返回封装数据
 */
@Data
public class Page<T> {

    /**
     * 分页数据
     */
    @ApiModelProperty(value = "返回数据")
    private List<T> records;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "返回数据总数")
    private Integer total;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Integer pages;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页码")
    @Min(value = 1, message = "请输入正确的页码")
    private Integer current;

    /**
     * 每页显示数量
     */
    @ApiModelProperty(value = "每页显示数量")
    @Min(value = 1, message = "请输入正确的数量")
    private Integer size;
    
    /**
     * 设置MySQL查询中 limit offset
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public Integer getStart() {
        return (this.current - 1) * size;
    }

    /**
     * 设置总记录数和页面总数
     *
     * @param total 总记录数
     */
    @ApiModelProperty(hidden = true)
    public void setTotal(Integer total) {
        this.total = total;
        this.setPages(this.total % this.size > 0 ? this.total / this.size + 1 : this.total / this.size);
    }
}
