package io.github.jhipster.application.web.websocket.dto;

import java.util.ArrayList;
import java.util.List;

public class CustomerRelation extends BaseBean{

    private Long id;
    private List<Customer> customers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "CustomerRelation{" +
            "id=" + id +
            ", customers=" + customers +
            '}';
    }
}
