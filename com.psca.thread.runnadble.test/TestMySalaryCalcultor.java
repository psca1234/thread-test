/**
 * @Description: 测试策略模式计算缴纳的税金
 * @Author: pansc
 * @CreateDate: 2018/12/29 21:01
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/29 21:01
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestMySalaryCalcultor {
    public static void main(String [] args){
        MySalaryCalcultor mySalaryCalcultor = new MySalaryCalcultor(10000,2300,new SalaryCalculatorStrategy(0.1,0.15));

        double result = mySalaryCalcultor.calculator();

        System.out.println("我应该上缴的薪资为："+result);
    }
}
