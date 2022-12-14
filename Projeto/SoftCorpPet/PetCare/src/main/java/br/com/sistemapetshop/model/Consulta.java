package br.com.sistemapetshop.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Jonathan Romualdo, allanfreitas
 */
@Entity
@Table(name = "tb_consulta")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "disc_consulta", discriminatorType = DiscriminatorType.STRING, length = 3)
@Access(AccessType.FIELD)
public abstract class Consulta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta", nullable = false, unique = true)
    protected Long idConsulta; // O id é herdado pelas subclasses

    @NotBlank
    @Column(name = "dat_dataMarcada")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataMarcada;

    @NotBlank
    @Column(name = "str_status", length = 60)
    private StatusConsulta status;
    
    // getters e Setters -----------------------------
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Date getDataMarcada() {
        return dataMarcada;
    }

    public void setDataMarcada(Date dataMarcada) {
        this.dataMarcada = dataMarcada;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }
    
}
