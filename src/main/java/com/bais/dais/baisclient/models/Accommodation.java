package com.bais.dais.baisclient.models;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation {
    @Getter
    @Setter
    private Residence residence;
    @Getter
    @Setter
    private Payment payment;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Residence {
        @Getter
        @Setter
        private String dormitory;
        @Getter
        @Setter
        private String block;
        @Getter
        @Setter
        private int floor;
        @Getter
        @Setter
        private int cell;
        @Getter
        @Setter
        private int room;
        @Getter
        @Setter
        private int cost;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payment {
        @Getter
        @Setter
        private String iban;
        @Getter
        @Setter
        private String swift;
        @Getter
        @Setter
        private String variable_symbol;
        @Getter
        @Setter
        private int arrears;
        @Getter
        @Setter
        private String scan_to_pay_code;
    }
}