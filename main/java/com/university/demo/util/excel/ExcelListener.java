package com.university.demo.util.excel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.Group;
import com.university.demo.service.GroupService;
import com.university.demo.util.JsonUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 监听类，可以自定义
 * @Date 2018-06-05
 * @Time 16:58
 */

@Component
public class ExcelListener extends AnalysisEventListener {
    //自定义用于暂时存储data。
    //可以通过实例获取该值
    private List<Object> datas = new ArrayList<>();
    private Object object;
    private static ExcelListener listener;

    @Autowired
    private GroupService groupService;

    @PostConstruct
    public void init(){
        listener = this;
        listener.groupService = this.groupService;
    }

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @SneakyThrows
    @Override
    public void invoke(Object object, AnalysisContext context) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        //这边需要判断object是否存在某些属性，一种方法是通过反射来做
        //这边采用转化为json后fastjson写的一个方法来做
        if(JsonUtil.isExistField("gender2",object))
            genderConverter(object);
        if(JsonUtil.isExistField("groupId2",object))
            groupConverter(object);
        //listener.groupService.groupUsers();
        //System.out.println(object);
        datas.add(object);
        //根据业务自行 do something
        doSomething();

        /*
        如数据过大，可以进行定量分批处理
        if(datas.size()<=100){
            datas.add(object);
        }else {
            doSomething();
            datas = new ArrayList<Object>();
        }
         */

    }

    /**
     * 根据业务自行实现该方法
     */
    private void doSomething() {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /*
            datas.clear();
            解析结束销毁不用的资源
         */
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    /* 性别转换 */
    public void genderConverter(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field gender2 = object.getClass().getDeclaredField("gender2");
        Field gender = object.getClass().getDeclaredField("gender");
        gender2.setAccessible(true);
        gender.setAccessible(true);
        String val = (String) gender2.get(object);
        if(StringUtils.isEmpty(val))
            return;
        if(val.equals("男"))
            gender.set(object, 0);
        else if (val.equals("女"))
            gender.set(object, 1);
    }

    /* 部门转换 */
    public void groupConverter(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field f2 = object.getClass().getDeclaredField("groupId2");
        Field f1 = object.getClass().getDeclaredField("groupId");
        f1.setAccessible(true);
        f2.setAccessible(true);
        String val = (String) f2.get(object);
        // 如果是空的不处理
        if(StringUtils.isEmpty(val))
            return;
        // 进行转换操作
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.eq("remark",val);
        // 注意groupSerivce的调用方法
        Group dept = listener.groupService.getOne(wrapper);
        f1.set(object, dept.getId());
    }
}