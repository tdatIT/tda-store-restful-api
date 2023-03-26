package com.webapp.tdastore.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

//Creating a Composite Key
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class OrderItemKey implements Serializable {
    private long orderId;
    private long productId;
}
