/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author enrique
 */
@Entity
@Table(name = "detalle_orden", catalog = "restaurante", schema = "")
@NamedQueries({
    @NamedQuery(name = "DetalleOrden.findAll", query = "SELECT d FROM DetalleOrden d"),
    @NamedQuery(name = "DetalleOrden.findByIdOrden", query = "SELECT d FROM DetalleOrden d WHERE d.detalleOrdenPK.idOrden = :idOrden"),
    @NamedQuery(name = "DetalleOrden.findByIdProducto", query = "SELECT d FROM DetalleOrden d WHERE d.detalleOrdenPK.idProducto = :idProducto"),
    @NamedQuery(name = "DetalleOrden.findByCantidad", query = "SELECT d FROM DetalleOrden d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleOrden.findByPrecioUnitario", query = "SELECT d FROM DetalleOrden d WHERE d.precioUnitario = :precioUnitario")})
public class DetalleOrden implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleOrdenPK detalleOrdenPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_unitario", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioUnitario;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "id_orden", referencedColumnName = "id_orden", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Orden orden;

    public DetalleOrden() {
    }

    public DetalleOrden(DetalleOrdenPK detalleOrdenPK) {
        this.detalleOrdenPK = detalleOrdenPK;
    }

    public DetalleOrden(DetalleOrdenPK detalleOrdenPK, int cantidad, BigDecimal precioUnitario) {
        this.detalleOrdenPK = detalleOrdenPK;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public DetalleOrden(int idOrden, int idProducto) {
        this.detalleOrdenPK = new DetalleOrdenPK(idOrden, idProducto);
    }

    public DetalleOrdenPK getDetalleOrdenPK() {
        return detalleOrdenPK;
    }
    
    @JsonbTransient
    public void setDetalleOrdenPK(DetalleOrdenPK detalleOrdenPK) {
        this.detalleOrdenPK = detalleOrdenPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleOrdenPK != null ? detalleOrdenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOrden)) {
            return false;
        }
        DetalleOrden other = (DetalleOrden) object;
        if ((this.detalleOrdenPK == null && other.detalleOrdenPK != null) || (this.detalleOrdenPK != null && !this.detalleOrdenPK.equals(other.detalleOrdenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ues.occ.edu.sv.calendarbk.tpi.entities.DetalleOrden[ detalleOrdenPK=" + detalleOrdenPK + " ]";
    }
    
}
