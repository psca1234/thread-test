/**
 * @Description: 业务逻辑方法
 * @Author: pansc
 * @CreateDate: 2018/12/29 20:58
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/29 20:58
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class MySalaryCalcultor {
    private double salary;
    private double bonus;
    private CalculatorStrategy cal;
    public double calculator(){
        return cal(salary,bonus);
    }

    private double cal(double salary, double bonus) {
        return cal.calculator(salary, bonus);
    }

    public MySalaryCalcultor(double salary, double bonus, CalculatorStrategy cal) {
        this.salary = salary;
        this.bonus = bonus;
        this.cal = cal;
    }
}
