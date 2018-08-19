package io.github.jhipster.application.web.websocket.dto;

public class CustomerType extends BaseBean{

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomerType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
