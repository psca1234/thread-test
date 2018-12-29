/**
 * @Description: 计算税金的具体策略实现
 * @Author: pansc
 * @CreateDate: 2018/12/29 20:55
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/29 20:55
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SalaryCalculatorStrategy implements CalculatorStrategy {
    private double salary_rate;
    private double bonus_rate;

    public double getSalary_rate() {
        return salary_rate;
    }

    public void setSalary_rate(double salary_rate) {
        this.salary_rate = salary_rate;
    }

    public double getBonus_rate() {
        return bonus_rate;
    }

    public void setBonus_rate(double bonus_rate) {
        this.bonus_rate = bonus_rate;
    }

    @Override
    public double calculator(double salary, double bonus) {
        return salary * salary_rate + bonus * bonus_rate;
    }

    public SalaryCalculatorStrategy(double salary_rate, double bonus_rate) {
        this.salary_rate = salary_rate;
        this.bonus_rate = bonus_rate;
    }
}
