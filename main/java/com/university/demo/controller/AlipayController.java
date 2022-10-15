package com.university.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.anotation.SysLog;
import com.university.demo.dao.OrderDetailDao;
import com.university.demo.dao.UserDao;
import com.university.demo.entity.Order;
import com.university.demo.entity.OrderDetail;
import com.university.demo.entity.OrderForm;
import com.university.demo.entity.User;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.response.OrderVo;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.service.CartService;
import com.university.demo.service.GroupService;
import com.university.demo.service.OrderService;
import com.university.demo.service.UserService;
import com.university.demo.util.Alipay.AliPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author tesla
 * @since 2022年10月15日
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private OrderService service;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private AliPayConfig aliPayConfig;
    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private CartService cartService;

    /************************ [   alipay   ]***************************************/

    /* 支付接口 */
    @PostMapping(value = "{orderid}/wappay")
    @ResponseBody
    public void wappayOrder(@PathVariable(value = "orderid") String orderid, HttpServletResponse response) {
        Order order = service.getById(orderid);

        //调用支付宝的页面
        // 1、设置请求参数
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(aliPayConfig.getReturnUrl());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());

        // 2、SDK已经封装掉了公共参数，这里只需要传入业务参数，请求参数查阅开头Wiki
        Map<String,String> map = new HashMap<>(16);
        map.put("out_trade_no", order.getId());
        map.put("total_amount", String.valueOf(order.getAmount()));
        map.put("subject", " 订单");   //订单名称
        map.put("body", "购买测试");      //订单描述
        // 销售产品码
        map.put("product_code","FAST_INSTANT_TRADE_PAY");

//      alipayRequest.setBizContent(JsonUtils.objectToJson(map));
        alipayRequest.setBizContent(JSON.toJSONString(map));

        response.setContentType("text/html;charset=utf-8");
        try{
            // 3、生成支付表单
            AlipayTradeWapPayResponse alipayResponse = alipayClient.pageExecute(alipayRequest);
            if(alipayResponse.isSuccess()) {
                String result = alipayResponse.getBody();
                response.getWriter().write(result);
            } else {
                //【支付表单生成】失败，错误信息
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            //【支付表单生成】异常，异常信息
            e.printStackTrace();
        }

        //return orderService.pay(userHolder.getUser().getId(), orderid);
    }

    /* 支付接口 */
    @PostMapping(value = "/pay")
    @ResponseBody
    public void payOrder(@RequestBody OrderForm orderForm, HttpServletResponse response) {
        Order order = service.getById(orderForm.getOrderId());

        //调用支付宝的页面
        // 1、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(aliPayConfig.getReturnUrl());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());

        // 2、SDK已经封装掉了公共参数，这里只需要传入业务参数，请求参数查阅开头Wiki
        Map<String,String> map = new HashMap<>(16);
        map.put("out_trade_no", order.getId());
        map.put("total_amount", String.valueOf(order.getAmount()));
        map.put("subject", orderForm.getSubject());   //订单名称
        map.put("body", "购买测试");      //订单描述
        // 销售产品码
        map.put("product_code","FAST_INSTANT_TRADE_PAY");

//      alipayRequest.setBizContent(JsonUtils.objectToJson(map));
        alipayRequest.setBizContent(JSON.toJSONString(map));

        response.setContentType("text/html;charset=utf-8");
        try{
            // 3、生成支付表单
            AlipayTradePagePayResponse alipayResponse = alipayClient.pageExecute(alipayRequest);
            if(alipayResponse.isSuccess()) {
                String result = alipayResponse.getBody();
                response.getWriter().write(result);
            } else {
                //【支付表单生成】失败，错误信息
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            //【支付表单生成】异常，异常信息
            e.printStackTrace();
        }

        //return orderService.pay(userHolder.getUser().getId(), orderid);
    }


    /**
     * 该方式仅仅在买家付款完成以后进行自动跳转，因此只会进行一次
     * 支付宝服务器同步通知页面，获取支付宝GET过来反馈信息
     * 该方法执行完毕后跳转到成功页即可
     * （1）该方式不是支付宝主动去调用商户页面，而是支付宝的程序利用页面自动跳转的函数，使用户的当前页面自动跳转；
     * （2）返回URL只有一分钟的有效期，超过一分钟该链接地址会失效，验证则会失败
     * （3）可在本机而不是只能在服务器上进行调试
     */
    @GetMapping("/alipay/return")
    public void alipayReturn(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数
        Map<String,String> params = getPayParams(request);
        try {
            // 验证订单
            boolean flag = false;
            String orderId = params.get("out_trade_no");
            Order order = service.getById(orderId);
            if(order != null) {
                flag =  true;
            }
            if(flag) {
                // 验证成功后，修改订单状态为已支付
                /*
                 * 订单状态（与官方统一）
                 * WAIT_BUYER_PAY：交易创建，等待买家付款；
                 * TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款；
                 * TRADE_SUCCESS：交易支付成功；
                 * TRADE_FINISHED：交易结束，不可退款
                 */
                // 获取支付宝订单号
                String tradeNo = params.get("trade_no");
                // 更新状态
                service.pay(order.getUserId(),order.getId());

                //如果这个订单是11充值订单，则增加用户的余额
                if("购买".equals(order.getType())){
                    QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
                    wrapper.eq("oid", orderId);
                    List<OrderDetail> orderDetailList = orderDetailDao.selectList(wrapper);
                    orderDetailList.forEach(d->{
                       d.setStatus(1);
                       orderDetailDao.updateById(d);
                    });
                }else if("账户充值".equals(order.getType())){
                    Double amount = order.getAmount();
                    User user = userDao.selectById(order.getUserId());
                    Double oBal = user.getBal();
                    user.setBal(oBal + amount);
                    userDao.updateById(user);
                }else if("购物".equals(order.getType())){
                }
                // orderInfoService.changeStatus(orderId, "TRADE_SUCCESS", tradeNo);

                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>支付成功</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<div class=\"container\">\n" +
                        "    <div class=\"row\">\n" +
                        "        <p>订单号："+orderId+"</p>\n" +
                        "        <p>支付宝交易号："+tradeNo+"</p>\n" +
                        "        <a href=\"/\">返回首页</a>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");
            } else {
                //【支付宝同步方法】验证失败"
                response.getWriter().write("支付验证失败");
            }
        } catch (Exception e) {
            //【支付宝同步方法】异常"
            e.printStackTrace();
        }
    }

    /**
     * 服务器异步通知，获取支付宝POST过来反馈信息
     * 该方法无返回值，静默处理
     * 订单的状态已该方法为主，其他的状态修改方法为辅 *
     * （1）程序执行完后必须打印输出“success”（不包含引号）。
     * 如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。
     * （2）程序执行完成后，该页面不能执行页面跳转。
     * 如果执行页面跳转，支付宝会收不到success字符，会被支付宝服务器判定为该页面程序运行出现异常，而重发处理结果通知
     * （3）cookies、session等在此页面会失效，即无法获取这些数据
     * （4）该方式的调试与运行必须在服务器上，即互联网上能访问 *
     */
    @PostMapping("/alipay/notify")
    public void alipayNotify(HttpServletRequest request,  HttpServletResponse response){
        /*
         默认只有TRADE_SUCCESS会触发通知，如果需要开通其他通知，请联系客服申请
         触发条件名 	    触发条件描述 	触发条件默认值
        TRADE_FINISHED 	交易完成 	false（不触发通知）
        TRADE_SUCCESS 	支付成功 	true（触发通知）
        WAIT_BUYER_PAY 	交易创建 	false（不触发通知）
        TRADE_CLOSED 	交易关闭 	false（不触发通知）
        来源：https://docs.open.alipay.com/270/105902/#s2
         */
        // 获取参数
        Map<String,String> params = getPayParams(request);
        try{
            // 验证订单
            boolean flag = false;
            //商户订单号
            String orderId = params.get("out_trade_no");
            Order order = service.getById(orderId);
            if(order != null) {
                flag =  true;
            }
            if(flag) {
                //支付宝交易号
                String tradeNo = params.get("trade_no");
                //交易状态
                String tradeStatus = params.get("trade_status");
                switch (tradeStatus) {
                    case "WAIT_BUYER_PAY":
                        //orderInfoService.changeStatus(orderId, tradeStatus);
                        break;
                    /*
                     * 关闭订单
                     * （1)订单已创建，但用户未付款，调用关闭交易接口
                     * （2）付款成功后，订单金额已全部退款【如果没有全部退完，仍是TRADE_SUCCESS状态】
                     */
                    case "TRADE_CLOSED":
                        service.cancel(order.getUserId(),order.getId());
                        break;
                    /*
                     * 订单完成
                     * （1）退款日期超过可退款期限后
                     */
                    case "TRADE_FINISHED" :
                        service.pay(order.getUserId(),order.getId());
                        break;
                    /*
                     * 订单Success
                     * （1）用户付款成功
                     */
                    case "TRADE_SUCCESS" :
                        service.pay(order.getUserId(),order.getId());
                        break;
                    default:break;
                }
                response.getWriter().write("success");
            }else {
                response.getWriter().write("fail");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取支付参数
     */
    private Map<String,String> getPayParams(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>(16);
        Map<String,String[]> requestParams = request.getParameterMap();

        Iterator<String> iter = requestParams.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    // 更新OrderDetail
    @PostMapping("/updateDetail")
    public ServerResponse updateDetail(@RequestBody OrderDetail record) {
        return orderDetailDao.updateById(record)>0 ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }
}

