/**
 * @author SargerasWang
 */
package com.university.excelUtil;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.sargeraswang.util.ExcelUtil.ExcelCell;
import lombok.Data;

/**
 * The <code>Model</code>
 * 
 * @author Fxy 20210814
 */
@Data
public class MyModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    private Integer a;
    @ExcelProperty(index = 4)
    private String b;
}
