package   com.his.master.common;

import com.baomidou.mybatisplus.annotation.TableField;
import   com.his.master.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
  
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    @TableField(exist = false)
    private long current = 1;

    /**
     * 页面大小
     */
    @TableField(exist = false)
    private long pageSize = 10;

    /**
     * 排序字段
     */
    @TableField(exist = false)
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    @TableField(exist = false)
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
