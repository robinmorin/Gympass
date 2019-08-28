package models;

import java.time.LocalTime;

/**
 * Class that contains values own of the one round execute
 */
public class ValuesAround {
    private LocalTime hourAround;
    private LocalTime timeAround;
    private Double averageAround;
    private Integer numberAround;

    //Fiz no pattern Builder por preferencia pessoal, mas pode ser um Bean normal.
    private ValuesAround(){}

    public static class Builder {
        private ValuesAround _value = new ValuesAround();

        public Builder(){}

        public Builder setHourAround(LocalTime hourAround) {
            _value.hourAround = hourAround;
            return this;
        }
        public Builder setTimeAround(LocalTime timeAround) {
            _value.timeAround = timeAround;
            return this;
        }
        public Builder setAverageAround(Double averageAround) {
            _value.averageAround = averageAround;
            return this;
        }
        public Builder setNumberAround(Integer numberAround) {
            _value.numberAround = numberAround;
            return this;
        }
        public ValuesAround build() {
            return _value;
        }
    }

    /**
     * Hour/minute/second to Pilot arrive around
     *
     * @return
     */
    public LocalTime getHourAround() {
        return hourAround;
    }

    /**
     * Timelapse Around
     *
     * @return
     */
    public LocalTime getTimeAround() {
        return timeAround;
    }

    /**
     * Average of Around
     *
     * @return
     */
    public Double getAverageAround() {
        return averageAround;
    }

    /**
     * Number of Around the Kart
     *
     * @return Integer
     */
    public Integer getNumberAround() {
        return numberAround;
    }

}
