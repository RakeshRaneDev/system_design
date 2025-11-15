package org.byte_stroke.inventory_management_system;

    public enum TransactionType {
        STOCK_IN("Stock In"),
        STOCK_OUT("Stock Out"),
        ADJUSTMENT("Adjustment"),
        TRANSFER_IN("Transfer In"),
        TRANSFER_OUT("Transfer Out"),
        RETURN("Return"),
        DAMAGE("Damage"),
        EXPIRY("Expiry");

        private final String description;

        TransactionType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

}
