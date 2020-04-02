/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author enrique
 */
@Entity
@Table(name = "orden", catalog = "restaurante", schema = "")
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o"),
    @NamedQuery(name = "Orden.findByIdOrden", query = "SELECT o FROM Orden o WHERE o.idOrden = :idOrden"),
    @NamedQuery(name = "Orden.findByFecha", query = "SELECT o FROM Orden o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "Orden.findByMesa", query = "SELECT o FROM Orden o WHERE o.mesa = :mesa"),
    @NamedQuery(name = "Orden.findByCliente", query = "SELECT o FROM Orden o WHERE o.cliente = :cliente"),
    @NamedQuery(name = "Orden.findByEstado", query = "SELECT o FROM Orden o WHERE o.estado = :estado"),
    @NamedQuery(name = "Orden.findByTotal", query = "SELECT o FROM Orden o WHERE o.total = :total"),
    @NamedQuery(name = "Orden.findByObservacion", query = "SELECT o FROM Orden o WHERE o.observacion = :observacion")})
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden", nullable = false)
    private Integer idOrden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 80)
    @Column(name = "mesa", length = 80)
    private String mesa;
    @Size(max = 80)
    @Column(name = "cliente", length = 80)
    private String cliente;
    @Size(max = 5)
    @Column(name = "estado", length = 5)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total", nullable = false)
    private double total;
    @Size(max = 200)
    @Column(name = "observacion", length = 200)
    private String observacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orden")
    private List<DetalleOrden> detalleOrdenList;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;

    public Orden() {
    }

    public Orden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Orden(Integer idOrden, Date fecha, double total) {
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.total = total;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<DetalleOrden> getDetalleOrdenList() {
        return detalleOrdenList;
    }

    public void setDetalleOrdenList(List<DetalleOrden> detalleOrdenList) {
        this.detalleOrdenList = detalleOrdenList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ues.occ.edu.sv.calendarbk.tpi.entities.Orden[ idOrden=" + idOrden + " ]";
    }
    
}
