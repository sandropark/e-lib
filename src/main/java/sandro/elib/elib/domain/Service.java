package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Service {

    @Id @GeneratedValue
    @Column(name = "service_id")
    private Long id;
    private String name;

    public Service(String name) {
        this.name = name;
    }
}
