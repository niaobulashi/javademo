package com.niaobulashi.microservices.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class T0_Sentinel {

    @SentinelResource("HelloWorld")
    public void helloWorld() {
        // 资源中的逻辑
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        // 配置规则
        initFlowRules();
        while (true) {
            try (Entry entry = SphU.entry("HelloWorld")){
                // 调用被保护的资源
                System.out.println("hello world");
            } catch (Exception e) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    /**
     * 通过流控规则来指定允许该资源通过的请求次数，
     * 例如下面的代码定义了资源 HelloWorld 每秒最多只能通过 20 个请求。
     */
    private static void initFlowRules() {
        // 初始化流控规则
        List<FlowRule> rules = new ArrayList<>();
        // 创建一个流控规则实例
        FlowRule rule = new FlowRule();
        // 设置资源为"HelloWorld"
        rule.setResource("HelloWorld");
        // 设置规则等级为QPS等级
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置QPS限制为20
        rule.setCount(100);
        // 将规则添加到规则列表中
        rules.add(rule);
        // 加载规则列表
        FlowRuleManager.loadRules(rules);
    }

}
