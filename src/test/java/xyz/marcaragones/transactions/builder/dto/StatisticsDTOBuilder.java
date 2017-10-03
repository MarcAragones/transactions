package xyz.marcaragones.transactions.builder.dto;


import xyz.marcaragones.transactions.dto.StatisticsDTO;

public class StatisticsDTOBuilder {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    private StatisticsDTOBuilder() {}

    public static StatisticsDTOBuilder aStatisticsDTOBuilder() {
        return new StatisticsDTOBuilder();
    }

    public StatisticsDTOBuilder withSum(double sum) {
        this.sum = sum;
        return this;
    }

    public StatisticsDTOBuilder withAvg(double avg) {
        this.avg = avg;
        return this;
    }

    public StatisticsDTOBuilder withMax(double max) {
        this.max = max;
        return this;
    }

    public StatisticsDTOBuilder withMin(double min) {
        this.min = min;
        return this;
    }

    public StatisticsDTOBuilder withCount(long count) {
        this.count = count;
        return this;
    }

    public StatisticsDTO build() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setSum(sum);
        statisticsDTO.setAvg(avg);
        statisticsDTO.setMax(max);
        statisticsDTO.setMin(min);
        statisticsDTO.setCount(count);
        return statisticsDTO;
    }
}
