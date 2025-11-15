package org.byte_stroke.parking_lot.model;

import org.byte_stroke.parking_lot.enums.PaymentStatus;
import java.util.*;

public abstract  class Payment {
  protected  double amount;
  protected PaymentStatus status;
  protected Date timestamp;

  public Payment(double amount) {
    this.amount = amount;
    this.status = PaymentStatus.PENDING;
    this.timestamp = new Date();
  }

  public  abstract boolean initiatePayment();
}
