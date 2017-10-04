package xyz.marcaragones.transactions.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statistics")
public class StatisticsEntity implements Serializable {

    @Id
    private long id;
    private double sum;
    private double max;
    private double min;
    private long count;

    public StatisticsEntity() {
        id = 1;
        min = Double.MAX_VALUE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAvg() {
        if (count == 0)
            return 0;

        return sum / count;
    }

    public synchronized void addAmount(double amount) {
        sum += amount;
        count++;
        if (amount > max) max = amount;
        if (amount < min) min = amount;
    }

    public synchronized void removeAmount(double amount) {
        sum -= amount;
        count--;
    }
}
